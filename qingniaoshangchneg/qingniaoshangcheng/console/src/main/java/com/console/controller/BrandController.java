package com.console.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import com.qingniao.common.page.PageInfo;
import com.core.pojo.Brand;
import com.core.pojo.BrandExample;
import com.core.service.BrandService;

/**
 * 品牌controller
 */

@Controller
public class BrandController {

    @Autowired
    BrandService brandService;

    //进入品牌页面
    @RequestMapping("/brand/list.do")
    public String brandList(String name, Integer status, Integer pageNo, Model model, HttpServletRequest request) throws Exception {

        BrandExample brandExample = new BrandExample();

        //制作分页工具栏
        StringBuilder stringBuilder = new StringBuilder();


        if (pageNo != null) {
            brandExample.setPageNo(pageNo);
        } else {
            brandExample.setPageNo(1); //如果是null那么就给默认值 1
        }

        if (name != null && name.trim().length() != 0) {
            brandExample.setName(name);
            model.addAttribute("name", name);
            stringBuilder.append("name=" + name);

//			String method = request.getMethod();
//			if(method.equals("GET")) {
//				brandExample.setName(new String(name.getBytes("ISO-8859-1"),"utf-8"));
//				name = new String(name.getBytes("ISO-8859-1"),"utf-8");
//				model.addAttribute("name",name);
//				stringBuilder.append("name="+name);
//			}
        }

        //判断条件
        if (status == null) {
            brandExample.setStatus(1); //如果是null那么默认查询在售的品牌
        } else {
            brandExample.setStatus(status);
            model.addAttribute("status", status);
            stringBuilder.append("&status=" + status);
            //页面回显
        }

        String url = "/brand/list.do";
        PageInfo pageInfo = brandService.selectByExample(brandExample);
        pageInfo.pageView(url, stringBuilder.toString());
        //把查询出来的数据放到model
        model.addAttribute("pageInfo", pageInfo);
        //回显当前页
        model.addAttribute("pageNo", pageNo);
        return "brand/list";
    }

    //跳转到添加页面
    @RequestMapping("/brand/add.do")
    public String add() {
        return "brand/add";
    }

    //编写品牌的添加方法
    @RequestMapping("/brand/save.do")
    public String addBrand(Brand brand) {
        brandService.insertBrand(brand);
        return "redirect:/brand/list.do";
    }

    //批量删除和单删的方法编写
    @RequestMapping("/brand/batchDelete.do")
    public String batchDelete(Long[] ids, Integer pageNo, String name, Integer status, Long id, Model model) {
        //参数传递给查询的方法
        if (pageNo != null) {
            model.addAttribute("pageNo", pageNo);
        }
        if (name != null) {
            model.addAttribute("name", name);
        }
        if (name != null) {
            model.addAttribute("status", status);
        }
        if (id != null) {
            //单个删除
            ids = new Long[]{id};
        }
        brandService.batchDelete(ids);
        return "redirect:/brand/list.do";
    }

    //去修改页面
    @RequestMapping("/brand/edit.do")
    public String edit(Long id, Model model) {
        Brand brand = brandService.selectById(id);
        model.addAttribute("brand", brand);
        return "brand/edit";
    }

    //保存编辑之后的数据
    @RequestMapping("/brand/editSave.do")
    public String editSave(Brand brand) {
        brandService.editSave(brand);
        return "redirect:/brand/list.do";
    }
}