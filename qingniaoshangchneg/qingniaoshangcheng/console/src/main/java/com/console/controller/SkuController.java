package com.console.controller;

import java.awt.PageAttributes.MediaType;
import java.io.IOException;
import java.util.List;

import javax.print.attribute.standard.Media;
import javax.servlet.http.HttpServletResponse;
import javax.swing.plaf.synth.SynthSeparatorUI;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.core.pojo.product.Sku;
import com.core.pojo.product.SkuCriteria;
import com.core.service.SkuService;

/**
 * 处理sku的请求
 */

@Controller
public class SkuController {

    @Autowired
    SkuService skuService;

    @RequestMapping("/sku/list.do")
    public String list(Long productId, Model model) {
        SkuCriteria skuCriteria = new SkuCriteria();
        skuCriteria.createCriteria().andProductIdEqualTo(productId);
        //通过productId查询库存信息
        List<Sku> skus = skuService.selectSkuByProductId(skuCriteria);
        model.addAttribute("skus", skus);
        return "sku/list";
    }

    @RequestMapping("/sku/save.do")
    public void save(Sku sku, HttpServletResponse response) throws IOException {
        response.setContentType("application/json;charset=utf-8");
        skuService.updateSku(sku);
        JSONObject jo = new JSONObject();
        jo.put("message", "保存成功");
        response.getWriter().write(jo.toString());
    }
}
