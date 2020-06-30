package com.console.controller;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.crypto.SecretKey;

import com.qingniao.common.page.PageInfo;
import org.apache.taglibs.standard.tei.ForEachTEI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.core.dao.product.ProductMapper;
import com.core.dao.product.SkuMapper;
import com.core.pojo.Brand;
import com.core.pojo.BrandExample;
import com.core.pojo.product.Color;
import com.core.pojo.product.ColorCriteria;
import com.core.pojo.product.Feature;
import com.core.pojo.product.FeatureCriteria;
import com.core.pojo.product.Img;
import com.core.pojo.product.Product;
import com.core.pojo.product.ProductCriteria;
import com.core.pojo.product.ProductCriteria.Criteria;
import com.core.pojo.product.Sku;
import com.core.pojo.product.Type;
import com.core.pojo.product.TypeCriteria;
import com.core.service.BrandService;
import com.core.service.ColorService;
import com.core.service.FeatureService;
import com.core.service.ImgService;
import com.core.service.ProductService;
import com.core.service.SkuService;
import com.core.service.TypeService;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * 商品controller
 *
 */

@Controller
public class ProductController {



    @Autowired
    TypeService typeService;

    @Autowired
    BrandService brandService;

    @Autowired
    FeatureService featureService;

    @Autowired
    ColorService colorService;

    @Autowired
    ProductService productService;

    @Autowired
    SkuService skuService;

    @Autowired
    ImgService imgService;

    @Resource
    JedisPool jedisPool;

    @RequestMapping("/product/list.do")
    public String productList(String name,Long brandId,Boolean isShow,Integer pageNo, Model model) {
        //加载品牌数据
        BrandExample brandExample = new BrandExample();
        brandExample.setStatus(1);
        List<Brand> brands = brandService.selectAllByExample(brandExample);
        model.addAttribute("brands",brands);

        ProductCriteria productCriteria = new ProductCriteria();
        Criteria createCriteria = productCriteria.createCriteria();

        //制作分页工具栏
        StringBuilder stringBuilder = new StringBuilder();

        if(pageNo !=null) {
            productCriteria.setPageNo(pageNo);
            model.addAttribute("pageNo",pageNo);
        }else {
            productCriteria.setPageNo(1);
        }

        //判断上下架状态
        if(isShow !=null) {
            createCriteria.andIsShowEqualTo(isShow);
            stringBuilder.append("isShow="+isShow);
            model.addAttribute("isShow",isShow);
        }else {
            createCriteria.andIsShowEqualTo(false);	//默认查询下架的商品
            stringBuilder.append("isShow=false");
        }

        //判断名称like模糊查询
        if(name !=null && name.trim().length() !=0) {
            createCriteria.andNameLike("%"+name+"%");
            stringBuilder.append("&name="+name);
            model.addAttribute("name",name);
        }

        //判断通过品牌id查询
        if(brandId !=null) {
            createCriteria.andBrandIdEqualTo(brandId);
            stringBuilder.append("&brandId="+brandId);
            model.addAttribute("brandId",brandId);
        }

        //查询
        PageInfo pageInfo = productService.selectByExample(productCriteria);
        //制作分页工具栏
        String url = "/product/list.do";
        pageInfo.pageView(url, stringBuilder.toString());
        model.addAttribute("pageInfo",pageInfo);
        return "product/list";
    }


    //去添加页面
    @RequestMapping("/product/add.do")
    public String add(Model model) {
        //商品类型
        TypeCriteria typeCriteria = new TypeCriteria();
        typeCriteria.createCriteria().andParentIdNotEqualTo(0l);
        List<Type> types = typeService.selectTypeByTypeCriteria(typeCriteria);
        model.addAttribute("types",types);

        //商品品牌
        BrandExample brandExample = new BrandExample();
        brandExample.setStatus(1);
        List<Brand> brands = brandService.selectAllByExample(brandExample);
        model.addAttribute("brands",brands);

        //材质
        FeatureCriteria featureCriteria = new FeatureCriteria();
        featureCriteria.createCriteria().andIsDelEqualTo(true);
        List<Feature> features = featureService.selectFeatureByFeatureCriteria(featureCriteria);
        model.addAttribute("features", features);

        //颜色
        ColorCriteria colorCriteria = new ColorCriteria();
        colorCriteria.createCriteria().andParentIdNotEqualTo(0l);
        List<Color> colors = colorService.selectColorByColorCriteria(colorCriteria);
        model.addAttribute("colors",colors);
        return "product/add";
    }
    //保存数据
    @RequestMapping("/product/save.do")
    public String save(Product product) {
        //保存商品数据
        Jedis jedis = jedisPool.getResource();
        Long pid = jedis.incr("productId");
        product.setId(pid);
        product.setIsShow(false); //下架状态
        product.setIsDel(false); // 默认是false
        product.setCreateTime(new Date()); //生成时间
        productService.insertSelective(product);	//插入数据并且返回id

        //保存图片数据
        Img img = product.getImg();
        img.setIsDef(false); //默认是false
        img.setProductId(pid);
        imgService.insertSelective(img);

        //保存sku(最小销售单元)
        String colors = product.getColors();
        String sizes = product.getSizes();

        //遍历颜色和尺码创建sku
        for (String color : colors.split(",")) {
            Sku sku = new Sku();
            sku.setColorId(Long.parseLong(color));	//设置颜色id
            sku.setProductId(pid); 					//设置商品id
            sku.setCreateTime(new Date());			//创建时间
            for (String  size : sizes.split(",")) {
                sku.setSize(size);	//尺码
                //运费
                sku.setDeliveFee(10f);
                //市场价格
                sku.setMarketPrice(150f);
                //售价
                sku.setPrice(99f);
                //库存
                sku.setStock(100);
                //购买限制
                sku.setUpperLimit(100);
                skuService.insertSelective(sku);
            }
        }
        return "redirect:/product/list.do";
    }

    /**
     * 商品上架
     */
    @RequestMapping("/product/onSale.do")
    public String onSale(Long[] ids) {
        productService.onSale(ids);
        //编写商品上架的方法
        return "redirect:/product/list.do";
    }
}