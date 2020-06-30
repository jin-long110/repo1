package com.qingniao.partal;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.core.common.LocalSessionProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.core.common.Constants;
import com.core.common.LocalSessionProvider;
import com.core.pojo.cart.UserCart;
import com.core.pojo.cart.UserItem;
import com.core.pojo.order.Order;
import com.core.pojo.product.Sku;
import com.core.service.OrderService;
import com.core.service.SkuService;
import com.core.service.impl.OrderServiceImpl;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/***
 * 订单模块
 *
 */

@Controller
public class OrderControlller {
	
	@Autowired
	LocalSessionProvider localSessionProvider;
	
	@Autowired
	JedisPool jedisPool;
	
	@Autowired
	SkuService skuService;
	
	@Autowired
	OrderService orderServiceImpl;
	
	/**
	 * 提交订单保存数据
	 * 
	 * */
	@RequestMapping("/order/submitOrder.html")
	public String submitOrder(HttpServletRequest request,HttpServletResponse response, Order order) {
		UserCart userCart = new UserCart();
		String username = localSessionProvider.getAttribute(request, response, Constants.USER_NAME);
		//订单所属用户
		order.setBuyerId(username);
		Jedis jedis = jedisPool.getResource();
		//从缓存中取出购物车数据
		List<String> keys = jedis.lrange("userCart:"+username,0,-1);
		for (String key : keys) {
			Sku sku = new Sku();
			sku.setId(Long.parseLong(key));
			UserItem userItem = new UserItem();
			userItem.setSku(sku);
			userItem.setAmount(Integer.parseInt(jedis.hget("userItem:"+username,key)));
			userCart.addUserItem(userItem);
		}
		
		//加载购物车数据
		showCart(userCart);
		
		//保存订单和订单详情数据
		orderServiceImpl.insertOrder(order, userCart);
		
		// 修改商品的库存
		for (String skuId : keys) {
			Sku sku = skuService.loadSkuById(Long.parseLong(skuId));
			//取出购买的数量
			String aomount = jedis.hget("userItem:"+username,skuId);
			sku.setStock(sku.getStock() - Integer.parseInt(aomount));
			//更新
			skuService.updateSku(sku);
		}
		
		//清空购物车
		jedis.del("userCart:"+username,"userItem:"+username);
		return "product/confirmOrder";
	}
	
	/**
	 * 加载购物车数据
	 */
	private void showCart(UserCart userCart) {
		List<UserItem> userItems = userCart.getItems();
		if (userItems != null) {
			for (UserItem userItem : userItems) {
				Sku sku = skuService.loadSkuById(userItem.getSku().getId());
				userItem.setSku(sku);
			}

		}
	}
	
}
