package com.qingniao.partal;


import com.core.pojo.Brand;
import com.core.pojo.product.*;
import com.core.service.ProductService;
import com.core.service.SkuService;
import com.qingniao.common.page.PageInfo;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.io.UnsupportedEncodingException;
import java.util.*;

@Controller
public class ProductController {
    @Autowired
    SolrServer solrServer;

    @Autowired
    JedisPool jedisPool;
    @Autowired
    ProductService productService;
    @Autowired
    SkuService skuService;

    /**
     * 查询商品信息
     *
     * @throws SolrServerException
     * @throws UnsupportedEncodingException
     */
    @RequestMapping("/index.html")
    public String list(Model model, String pname, Integer pageNo, Long brandId, String price) throws SolrServerException, UnsupportedEncodingException {
        //从缓存中加载品牌数据
        Jedis jedis = jedisPool.getResource();
        List<Brand> brands = new ArrayList();
        Set<String> keys = jedis.keys("brand*");
        for (String key : keys) {
            Brand b = new Brand();
            b.setId(Long.parseLong(jedis.hget(key, "id")));
            b.setName(jedis.hget(key, "name"));
            brands.add(b);
        }
        model.addAttribute("brands", brands);


        ProductCriteria productCriteria = new ProductCriteria();
        productCriteria.setPageSize(4);
        //制作分页工具栏
        StringBuilder stringBuilder = new StringBuilder();
        SolrQuery solrQuery = new SolrQuery();


        //判断当前页
        if (pageNo != null) {
            productCriteria.setPageNo(pageNo);
            model.addAttribute("pageNo", pageNo);
        } else {
            productCriteria.setPageNo(1);
        }

        //判断名称
        if (pname != null && pname.trim().length() > 0) {
            //通过名称进行查询
//			pname = new String(pname.getBytes("iso-8859-1"),"utf-8");
            solrQuery.set("q", "name_ik:" + pname);

            //高亮显示搜索的关键字
            //开启高亮
            solrQuery.setHighlight(true);
            //设置需要高亮的字段
            solrQuery.addHighlightField("name_ik");
            //设置高亮前缀
            solrQuery.setHighlightSimplePre("<span style='color:red'>");
            //设置高亮后缀
            solrQuery.setHighlightSimplePost("</span>");

            model.addAttribute("pname", pname);
            stringBuilder.append("pname=" + pname);
        } else {
            //如果没有输入名称就查询所有数据
            solrQuery.set("q", "*:*");
        }

        //定义标记控制已选条件显示
        Boolean flag = false;
        //定义存放已选条件的集合
        HashMap condition = new HashMap();

        //判断品牌
        if (brandId != null) {
            solrQuery.addFacetQuery("brandId:" + brandId);
            model.addAttribute("brandId", brandId);
            stringBuilder.append("&brandId=" + brandId);
            condition.put("品牌:", jedis.hget("brand" + brandId, "name"));
            flag = true;
        }
        //判断价格
        if (price != null) {
            //price0-79
            String[] p = price.split("-");
            if (p.length == 2) {
                Float start = new Float(p[0]);//0
                Float end = new Float(p[1]);//99
                solrQuery.addFilterQuery("price:[" + start + " TO " + end + "]");
                condition.put("价格", price);
            } else {
                //600以上
                Float start = new Float(600);
                Float end = new Float(1000000000f);
                solrQuery.addFilterQuery("price:[" + start + " TO " + end + "]");
                condition.put("价格", price);
            }
            model.addAttribute("price", price);
            stringBuilder.append("&price=" + price);
            //改变标记
            flag = true;
        }
        //回显数据
        model.addAttribute("flag", flag);
        model.addAttribute("condition", condition);


        //从solr服务区里面查询出商品 每页显示4条
        solrQuery.setStart(productCriteria.getStartRow());    //起始页
        solrQuery.setRows(productCriteria.getPageSize());   //每页显示多少条数据
        solrQuery.addSort("price", SolrQuery.ORDER.asc);
        QueryResponse queryResponse = solrServer.query(solrQuery);
        SolrDocumentList docs = queryResponse.getResults();
        List<Product> products = new ArrayList();
        //循环遍历取出所有数据放到list集合里面
        for (SolrDocument doc : docs) {
            Product product = new Product();
            //id
            String id = (String) doc.get("id");
            product.setId(Long.parseLong(id));


            //判断name
            if (pname != null && pname.trim().length() > 0) {
                //获取高亮数据
                Map<String, Map<String, List<String>>> highlighting = queryResponse.getHighlighting();
                Map<String, List<String>> map = highlighting.get(id);
                List<String> list = map.get("name_ik");
                String s = list.get(0);
                product.setName(s);
            } else {
                String name = (String) doc.get("name_ik");
                product.setName(name);
            }

            //url
            String url = (String) doc.get("url");
            Img img = new Img();
            img.setUrl(url);
            product.setImg(img);
            //brandId
            Long bid = (Long) doc.get("brandId");
//            product.setBrandId(Long.parseLong(bid.toString()));
            product.setBrandId(bid);
            //price
            float p = (float) doc.get("price");
            product.setPrice(p);
            products.add(product);
        }
        PageInfo pageInfo = new PageInfo(productCriteria.getPageNo(), productCriteria.getPageSize(), (int) docs.getNumFound());
        pageInfo.setList(products);
        String url = "/index.html";
        pageInfo.pageView(url, stringBuilder.toString());
        model.addAttribute("pageInfo", pageInfo);
        return "product/product";
    }

    @RequestMapping("/product/detail.html")
    public String detail(Long id,Model model) {
        //加载商品数据和图片
        Product product = productService.selectProductAndImgByProductId(id);
//        加载sku
        SkuCriteria skuCriteria = new SkuCriteria();
        skuCriteria.createCriteria().andProductIdEqualTo(id);
        List<Sku> skus = skuService.selectSkuByProductId(skuCriteria);
        //颜色
        List colors = new ArrayList();
        for (Sku sku:skus) {
colors.add(sku.getColor());
        }
        model.addAttribute("product",product);
        model.addAttribute("skus",skus);
        model.addAttribute("colors",colors);
        return "product/productDetail";
    }
}
