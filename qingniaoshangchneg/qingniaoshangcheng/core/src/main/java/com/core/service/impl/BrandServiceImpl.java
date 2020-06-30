package com.core.service.impl;

import java.text.BreakIterator;

import java.util.List;

import com.qingniao.common.page.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.core.dao.BrandMapper;
import com.core.pojo.Brand;
import com.core.pojo.BrandExample;
import com.core.service.BrandService;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * 品牌的业务类
 */

@Transactional
@Service
public class BrandServiceImpl implements BrandService {

    @Autowired
    BrandMapper brandMapper;

    @Autowired
    JedisPool jedisPool;

    @Override
    public void insertBrand(Brand brand) {
        brandMapper.insertBrand(brand);
        //把数据缓存到redis中
        Jedis jedis = jedisPool.getResource();
        //保存id
        jedis.hset("brand" + brand.getId(), "id", brand.getId().toString());
        //保存name
        jedis.hset("brand" + brand.getId(), "name", brand.getName());
        jedis.close();
    }

    @Override
    public PageInfo selectByExample(BrandExample brandExample) {
        //使用分页查询
        PageInfo pageInfo = new PageInfo(brandExample.getPageNo(), brandExample.getPageSize(), brandMapper.selectCount(brandExample));
        brandExample.setPageNo(pageInfo.getPageNo());    // 矫正当前页
        List<Brand> brands = brandMapper.selectByExample(brandExample);
        pageInfo.setList(brands);
        return pageInfo;
    }


    @Override
    public void batchDelete(Long[] ids) {
        //删除缓存中的数据
        Jedis jedis = jedisPool.getResource();
        for (Long id : ids) {
            jedis.del("brand" + id);
        }
        jedis.close();
        //删除数据库的数据
        brandMapper.batchDelete(ids);
    }

    @Override
    public Brand selectById(Long id) {
        return brandMapper.selectById(id);
    }

    //修改数据更新到缓存
    @Override
    public void editSave(Brand brand) {
        brandMapper.editSave(brand);
        Jedis jedis = jedisPool.getResource();
        jedis.hset("brand" + brand.getId(), "id", brand.getId().toString());
        jedis.hset("brand" + brand.getId(), "name", brand.getName());
        jedis.close();
    }

    @Override
    public List<Brand> selectAllByExample(BrandExample brandExample) {
        return brandMapper.selectAllByExample(brandExample);
    }
}
