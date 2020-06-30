package com.core.service.impl;

import java.io.IOException;
import java.util.*;

import com.core.common.FreemakerUtils;
import com.core.dao.product.ImgMapper;
import com.core.dao.product.ProductMapper;
import com.core.dao.product.SkuMapper;
import com.core.pojo.product.*;
import com.core.service.ProductService;
import com.core.service.SkuService;
import com.qingniao.common.page.PageInfo;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import javax.annotation.Resource;


@Service
@Transactional
public class ProductServiceImpl implements ProductService {
	@Autowired
	ProductMapper productMapper;
	
	@Autowired
	ImgMapper imgMapper;
	
	@Autowired
	SolrServer solrServer;
	
	@Autowired
	SkuMapper skuMapper;
	@Autowired
	SkuService skuService;
	@Autowired
	FreemakerUtils freemakerUtils;
	@Autowired
	JedisPool jedisPool;
	
	@Override
	public void insertSelective(Product product) {
		productMapper.insertSelective(product);
	}

	@Override
	public PageInfo selectByExample(ProductCriteria productCriteria) {
		PageInfo pageInfo = new PageInfo(productCriteria.getPageNo(),productCriteria.getPageSize(),productMapper.countByExample(productCriteria));
		//使用pageInfo的pageNo来矫正productCriteria的pageNo
		productCriteria.setPageNo(pageInfo.getPageNo());
		List<Product> products = productMapper.selectByExample(productCriteria);
		//加载图片数据
		for (Product product : products) {
			ImgCriteria imgCriteria = new ImgCriteria();
			imgCriteria.createCriteria().andProductIdEqualTo(product.getId()).andIsDefEqualTo(false);
			List<Img> imgs = imgMapper.selectByExample(imgCriteria);
			product.setImg(imgs.get(0));
		}
		pageInfo.setList(products);
		return pageInfo;
	}
	
	/**
	 * 商品上架
	 */

	/**
	 * 商品上架
	 */

	@Override
	public void onSale(Long[] ids) {
		//修改商品的状态
		for (Long pid : ids) {
			Product product = new Product();
			product.setId(pid);
			product.setIsShow(true); //设置商品为上架状态
			productMapper.updateByPrimaryKeySelective(product);
			Product p = productMapper.selectByPrimaryKey(pid);

			//保存商品的数据到solr服务器
			SolrInputDocument doc = new SolrInputDocument();
			//id
			doc.setField("id",pid);
			//name
			doc.setField("name_ik", p.getName());
			//url
			ImgCriteria imgCriteria = new ImgCriteria();
			imgCriteria.createCriteria().andProductIdEqualTo(pid).andIsDefEqualTo(false);
			List<Img> imgs = imgMapper.selectByExample(imgCriteria);
			doc.setField("url",imgs.get(0).getUrl());
			//price
			//1  通过productId查询sku表 做排序 asc price 升序 然后limit 0 -1
			//2 select min(price) from qnsport_sku where product_id = #{id} and stock>0
			float price = skuMapper.selectPriceByProductId(pid);
			doc.setField("price",price);
			//设置brandId
			doc.setField("brandId",p.getBrandId());

			try {
				solrServer.add(doc);
				solrServer.commit();
			} catch (SolrServerException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

			//生成静态页面
			//1.生成静态页需要的数据
			Map root = new HashMap();
			root.put("productId", pid);

			Product pro = selectProductAndImgByProductId(pid);
			SkuCriteria skuCriteria = new SkuCriteria();
			skuCriteria.createCriteria().andProductIdEqualTo(pid);
			List<Sku> skus = skuService.selectSkuByProductId(skuCriteria);
			//颜色
			Set colors = new HashSet();
			for (Sku sku : skus) {
				colors.add(sku.getColor());
			}
			root.put("product",pro);
			root.put("skus", skus);
			root.put("colors",colors);

			//2.调用工具类进行生成
			freemakerUtils.toHTML(root);
		}

	}
	/**
	 * 查询商品并且关联图片
	 */
	@Override
	public Product selectProductAndImgByProductId(Long id) {
		Product product = productMapper.selectByPrimaryKey(id);
		ImgCriteria imgCriteria = new ImgCriteria();
		imgCriteria.createCriteria().andProductIdEqualTo(id).andIsDefEqualTo(false);
		List<Img> imgs = imgMapper.selectByExample(imgCriteria);
		product.setImg(imgs.get(0));
		return product;
	}




}
