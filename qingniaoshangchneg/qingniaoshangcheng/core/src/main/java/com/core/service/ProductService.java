package com.core.service;

import com.core.pojo.product.Product;
import com.core.pojo.product.ProductCriteria;
import com.qingniao.common.page.PageInfo;

public interface ProductService {
	public void insertSelective(Product product);
	public PageInfo selectByExample(ProductCriteria productCriteria);
	public void onSale(Long[] ids);
	public Product selectProductAndImgByProductId(Long id);

}
