package com.core.common;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfig;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

/**
 * 生成静态页工具类
 *
 */

@Service
public class FreemakerUtils implements ServletContextAware {
    @Resource
    FreeMarkerConfig freeMarkerConfig;
    @Resource
    private ServletContext servletContext;

    //生成商品静态页
    public void toHTML(Map map) {
        Configuration configuration = freeMarkerConfig.getConfiguration();
        try {
            //使用商品的id作为静态页的名称
            Long id = (Long)map.get("productId");
            Template template = configuration.getTemplate("productDetail.html");
            String path = "/html/product/" + id + ".html";	//相对路径
            //获取服务器绝对路径
            String realPath = servletContext.getRealPath("");
            String filePaht = realPath+path;	//绝对路径

            //生成文件的时候必须指定utf-8编码方式
            Writer out = new OutputStreamWriter(new FileOutputStream(filePaht),"utf-8");
            template.process(map, out);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TemplateException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setServletContext(ServletContext servletContext) {
        this.servletContext = servletContext;
    }
}