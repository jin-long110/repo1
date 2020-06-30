package com.core.service.impl;



import java.util.List;

import com.core.dao.product.ColorMapper;
import com.core.dao.product.ImgMapper;
import com.core.dao.product.ProductMapper;
import com.core.dao.product.SkuMapper;
import com.core.pojo.product.*;
import com.core.service.ColorService;
import com.core.service.SkuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
public class SkuServiceImpl implements SkuService {
	@Autowired
	SkuMapper skuMapper;
	
	@Autowired
	ColorMapper colorMapper;

	@Autowired
	ColorService colorService;
	 @Autowired
	ProductMapper productMapper;
	 @Autowired
	ImgMapper imgMapper;

	@Override
	public void insertSelective(Sku record) {
		skuMapper.insertSelective(record);
	}

	@Override
	public List<Sku> selectSkuByProductId(SkuCriteria skuCriteria) {
		List<Sku> skus = skuMapper.selectByExample(skuCriteria);
		//遍历sku结果集加载颜色
		for (Sku sku : skus) {
			ColorCriteria colorCriteria = new ColorCriteria();
			colorCriteria.createCriteria().andIdEqualTo(sku.getColorId());
			List<Color> colors = colorMapper.selectByExample(colorCriteria);
			sku.setColor(colors.get(0));
		}
		return skus;
	}

	@Override
	public void updateSku(Sku sku) {
		skuMapper.updateByPrimaryKeySelective(sku);
	}

	//通过sku进行初始化
	@Override
	public Sku loadSkuById(Long id) {
		Sku sku = skuMapper.selectByPrimaryKey(id);
		//关联颜色
		sku.setColor(colorMapper.selectByPrimaryKey(sku.getColorId()));
		//管理商品
		Product product = productMapper.selectByPrimaryKey(sku.getProductId());
		sku.setProduct(product);
		//关联图片
		ImgCriteria imgCriteria = new ImgCriteria();
		imgCriteria.createCriteria().andProductIdEqualTo(product.getId()).andIsDefEqualTo(false);
		List<Img> imgs = imgMapper.selectByExample(imgCriteria);
		product.setImg(imgs.get(0));
		//关联商品
		sku.setProduct(product);
		return sku;
	}
}
