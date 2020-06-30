package com.qingniao.partal;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.core.pojo.user.Addr;
import com.core.service.AddrService;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.core.common.Constants;
import com.core.common.LocalSessionProvider;
import com.core.pojo.UserTest;
import com.core.pojo.cart.UserCart;
import com.core.pojo.cart.UserItem;
import com.core.pojo.product.Sku;
import com.core.service.SkuService;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Protocol;

@Controller
public class CarController {

    @Autowired
    SkuService skuService;

    @Autowired
    LocalSessionProvider localSessionProvider;

    @Autowired
    JedisPool jedisPool;
    @Autowired
    AddrService addrService;

    ObjectMapper om = new ObjectMapper();

    //购买商品
    @RequestMapping("/buy/shopping.html")
    public String cart(Long skuId,Integer amount,Model model,HttpServletRequest request,HttpServletResponse response) throws JsonParseException, JsonMappingException, IOException {

        om.setSerializationInclusion(Inclusion.NON_NULL);//把null值过滤掉

        //创建购物车
        UserCart userCart = null;
        Cookie[] cookies = request.getCookies();

        //到cookies里面查找购物车
        userCart = findUserCartByCookies(cookies);

        //判断购物车是否为空
        if(userCart == null) {
            //初始化购物车
            userCart = new UserCart();
        }

        //判断用户是否登录
        String username = localSessionProvider.getAttribute(request, response, Constants.USER_NAME);

        if(username !=null ) {
            //登录情况下购物车的处理
            Jedis jedis = jedisPool.getResource();
            //把cookie中的信息保存到redis中
            List<UserItem> items = userCart.getItems();	//从cookie里面取出来的数据
            for (UserItem userItem : items) {
                //如果存在就追加
                if(jedis.hexists("userItem:"+username,skuId.toString())) {
                    jedis.hincrBy("userItem:"+username,skuId.toString(),amount);	//数量追加
                }else {
                    jedis.lpush("userCart:"+username,userItem.getSku().getId().toString());
                    //保存购物项中的数据(skuId amount)
                    jedis.hset("userItem:"+username,userItem.getSku().getId().toString(),userItem.getAmount().toString());
                }

            }

            // 清空购物车和cookie信息
            userCart.clearCart();
            clearCart(request, response);

            //添加当前购买的商品保存到redis中
            if(skuId !=null) {
                //如果已经该商品已经存在那么就追加
                if(jedis.hexists("userItem:"+username,skuId.toString())) {
                    jedis.hincrBy("userItem:"+username,skuId.toString(),amount);
                }else {
                    //保存购物项
                    jedis.lpush("userCart:"+username,skuId.toString());
                    //保存购物项中的数据
                    jedis.hset("userItem:"+username,skuId.toString(),amount.toString());
                }

            }

            //把redis中的数据遍历加载到购物车中
            List<String> keys = jedis.lrange("userCart:"+username,0,-1);
            if(keys!=null) {
                for (String skid : keys) {
                    //创建sku
                    Sku sku = new Sku();
                    sku.setId(Long.parseLong(skid));

                    //创建购物项
                    UserItem userItem = new UserItem();
                    userItem.setSku(sku);
                    userItem.setAmount(Integer.parseInt(jedis.hget("userItem:"+username,skid)));

                    //添加到购物车
                    userCart.addUserItem(userItem);
                }
            }

        }else {
            //把购买的商品添加到购物车
            if(skuId != null) {
                //把商品追加到购物车
                userItemToCart(userCart,skuId,amount,response);
            }
        }


        //页面要显示购物车信息 需要把购物车里面的数据全部加载出来然后返回到页面进行显示
        showCart(userCart);

        //需要把购物车中的数据进行排序
        Collections.sort(userCart.getItems(),new Comparator<UserItem>() {

            //对集合进行倒序
            @Override
            public int compare(UserItem o1, UserItem o2) {
                return -1;
            }
        });

        model.addAttribute("userCart",userCart);

        return "product/cart";
    }


    /**
     * 从cookie中查询购物车
     * @throws IOException
     * @throws JsonMappingException
     * @throws JsonParseException
     *
     * */
    private UserCart findUserCartByCookies(Cookie[] cookies) throws JsonParseException, JsonMappingException, IOException {
        if(cookies !=null && cookies.length>0) {
            for (Cookie cookie : cookies) {
                if(Constants.USER_CART.equals(cookie.getName())) {
                    String value = cookie.getValue();
                    return om.readValue(value,UserCart.class);
                }
            }
        }
        return null;
    }

    /**
     * 把商品追加到购物车然后回写cookie
     * @throws IOException
     * @throws JsonMappingException
     * @throws JsonGenerationException
     *
     * */
    private void userItemToCart(UserCart userCart, Long skuId, Integer amount, HttpServletResponse response) throws JsonGenerationException, JsonMappingException, IOException {
        //创建sku对象
        Sku sku = new Sku();
        sku.setId(skuId);

        //创建购物项
        UserItem userItem = new UserItem();
        userItem.setAmount(amount);
        userItem.setSku(sku);

        //把购物项添加到购物车
        userCart.addUserItem(userItem);

        om.setSerializationInclusion(Inclusion.NON_NULL);
        StringWriter w = new StringWriter();
        om.writeValue(w,userCart);	//把购物车换换成json字符串

        //回写浏览器
        Cookie cookie = new Cookie(Constants.USER_CART,w.toString());
        cookie.setMaxAge(60*60*24*7);	//保存一周
        cookie.setPath("/");
        response.addCookie(cookie);
    }

    //回写浏览器
    private void userItemToCart(UserCart userCart,HttpServletResponse response) throws JsonGenerationException, JsonMappingException, IOException {
        StringWriter w = new StringWriter();
        om.setSerializationInclusion(Inclusion.NON_NULL);
        om.writeValue(w, userCart);

        //回写浏览器
        Cookie cookie = new Cookie(Constants.USER_CART,w.toString());
        cookie.setMaxAge(60*60*24*7);
        cookie.setPath("/");
        response.addCookie(cookie);
    }

    /**
     * 购物车数据加载回显
     * @param userCart
     */
    private void showCart(UserCart userCart) {
        List<UserItem> userItems = userCart.getItems();
        if(userItems != null) {
            for (UserItem userItem : userItems) {
                Sku sku = skuService.loadSkuById(userItem.getSku().getId());
                userItem.setSku(sku);
            }

        }
    }

    /**
     * 删除购物项
     * @throws IOException
     * @throws JsonMappingException
     * @throws JsonParseException
     *
     * */
    @RequestMapping("/delelte/userItem.html")
    public String userItemDelete(Long skuId,HttpServletRequest request,HttpServletResponse response) throws JsonParseException, JsonMappingException, IOException {

        String username = localSessionProvider.getAttribute(request, response,Constants.USER_NAME);
        if(username != null) {
            //删除购物车中的商品
            Jedis jedis = jedisPool.getResource();
            //删除购物车中
            jedis.lrem("userCart:"+username,1,skuId.toString());	//第二个参数表示删除的数量，因为list里面是可以重复的
            //删除购物项中
            jedis.hdel("userItem"+username,skuId.toString());

        }else {
            om.setSerializationInclusion(Inclusion.NON_NULL);
            UserCart userCart = null;
            Cookie[] cookies = request.getCookies();
            userCart = findUserCartByCookies(cookies);
            userCart.delUserItem(skuId);
            //回写浏览器
            userItemToCart(userCart,response);
        }
        return "redirect:/buy/shopping.html";
    }

    /***
     * 商品数量追加和减少
     * @param skuId
     * @param amount
     * @param request
     * @param response
     * @throws IOException
     * @throws JsonMappingException
     * @throws JsonParseException
     */
    @RequestMapping("/userItem/addAmount.html")
    public void userItemAddAmount(Long skuId,Integer amount,HttpServletRequest request,HttpServletResponse response) throws JsonParseException, JsonMappingException, IOException {
        UserCart userCart = new UserCart();

        //判断用户是否登录
        String username = localSessionProvider.getAttribute(request, response, Constants.USER_NAME);
        if(username !=null) {
            Jedis jedis = jedisPool.getResource();
            //数量追加
            jedis.hincrBy("userItem:"+username,skuId.toString(),Long.parseLong(amount.toString()));

            //封装购物车数据
            List<String> keys = jedis.lrange("userCart:"+username, 0, -1);
            for (String key : keys) {
                Sku sku = new Sku();
                sku.setId(Long.parseLong(key));
                UserItem userItem = new UserItem();
                userItem.setSku(sku);
                userItem.setAmount(Integer.parseInt(jedis.hget("userItem:"+username, key.toString())));
                userCart.addUserItem(userItem);
            }
        }else {
            om.setSerializationInclusion(Inclusion.NON_NULL);
            userCart = findUserCartByCookies(request.getCookies());
            // 追加商品的时候，如果是重复会对数量进行叠加
            userItemToCart(userCart, skuId, amount, response);
        }

        // 回显数据
        showCart(userCart);
        // 重新计算 小计，价格 运费 总价格
        JSONObject jo = new JSONObject();
        jo.put("allProductAmount", userCart.getAllProductAmount());
        jo.put("price", userCart.getPrice());
        jo.put("extra", userCart.getExtra());
        jo.put("allPrice", userCart.getAllPrice());
        response.getWriter().write(jo.toString());
    }

    /**
     * 清空购物车
     * */
    @RequestMapping("/cart/clearCart.html")
    public String clearCart(HttpServletRequest request, HttpServletResponse response) {
        String username = localSessionProvider.getAttribute(request, response,Constants.USER_NAME);
        if(username !=null) {
            //清空购物车和购物项
            Jedis jedis = jedisPool.getResource();
            jedis.del("userCart:"+username);
            jedis.del("userItem:"+username);
        }

        // 清空cookie
        Cookie cookie = new Cookie(Constants.USER_CART, null);
        cookie.setMaxAge(0);
        cookie.setPath("/");
        response.addCookie(cookie);

        return "redirect:/buy/shopping.html";
    }

    @RequestMapping("/buy/balanceAccounts.html")
    public String balanceAccounts(HttpServletRequest request,HttpServletResponse response,Model model) {
        UserCart userCart = new UserCart();

        //结算必须登录
        String username = localSessionProvider.getAttribute(request, response, Constants.USER_NAME);
        Jedis jedis = jedisPool.getResource();

        //如果购买限制大于库存那么是无货的，需要跳转到商品显示页，显示无货无法购买

        Boolean flag = true;

        //判断购物车中是否有商品
        List<String> keys = jedis.lrange("userCart:"+username,0,-1);
        if(keys !=null && keys.size() !=0) {
            for (String key : keys) {
                Sku sku = new Sku();
                sku.setId(Long.parseLong(key));
                UserItem userItem = new UserItem();
                userItem.setSku(sku);
                userItem.setAmount(Integer.parseInt(jedis.hget("userItem:"+username,key)));
                //判断购物项是否有货 		判断就买的数量是否大于 sku里面的stock库存
                Sku s = skuService.loadSkuById(Long.parseLong(key));
                if(s.getStock()<userItem.getAmount()) {
                    userItem.setHave(false); 	//设置无货
                    flag = false;
                }
                userCart.addUserItem(userItem);

            }

            // 数据加载
            showCart(userCart);

            //如果无货那么需要返回到结算页面，如果有货需要跳转到订单详情页
            if(flag) {
                //所有商品都有货 需要通过用户名称去查询地址等信息
                Addr addr = addrService.selectAddrByUsername(username);
                model.addAttribute("addr",addr);
                //跳转到订单详情页面
                model.addAttribute("userCart",userCart);
                return "product/productOrder";
            }else {
                //如果包含无货的商品
                model.addAttribute("userCart",userCart);
                return "product/cart";
            }


        }else {
            // 购物车为空
            return "redirect:/buy/shopping.html";
        }

    }


}
