package com.core.service;

import java.util.List;

import com.core.pojo.product.Sku;
import com.core.pojo.product.SkuCriteria;

public interface SkuService {
	void insertSelective(Sku record);
	public List<Sku> selectSkuByProductId(SkuCriteria skuCriteria);
	public void updateSku(Sku sku);

    Sku loadSkuById(Long id);
}
