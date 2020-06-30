package com.core.service;

import java.util.List;

import com.core.pojo.Brand;
import com.core.pojo.BrandExample;
import com.qingniao.common.page.PageInfo;

/**
 * 品牌添加service
 */

public interface BrandService {
    public void insertBrand(Brand brand);

    public PageInfo selectByExample(BrandExample brandExample);

    public void batchDelete(Long[] ids);

    public Brand selectById(Long id);

    public void editSave(Brand brand);

    public List<Brand> selectAllByExample(BrandExample brandExample);
}
