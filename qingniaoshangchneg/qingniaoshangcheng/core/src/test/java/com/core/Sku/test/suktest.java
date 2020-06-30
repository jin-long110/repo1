package com.core.Sku.test;

import com.core.dao.product.SkuMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring/applicationContext-*.xml")
public class suktest {
    @Autowired
    SkuMapper skuMapper;

    //查询
    @Test
    public void skutest1() {
        float v = skuMapper.selectPriceByProductId(10l);
        System.out.println(v);
    }
}
