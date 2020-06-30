package com.core.dao;
import com.core.pojo.Brand;
import com.core.pojo.BrandExample;

import java.util.List;


/**
 * 品牌dao
 *
 */
public interface BrandMapper {
	public void insertBrand(Brand brand);
	public List<Brand> selectByExample(BrandExample brandExample);
	public Integer selectCount(BrandExample brandExample);
	public void batchDelete(Long[] ids);
	public Brand selectById(Long id);
	public void editSave(Brand brand);
	public List<Brand> selectAllByExample(BrandExample brandExample);
}
