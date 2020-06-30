package freemarker.test;

import com.core.pojo.ceshilei.FreeMarkerceshi;
import com.core.pojo.user.User;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.junit.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.*;

public class FreeMarkerTest {
    @Test
    public void demo1() throws Exception {
        // 1.创建 configration实例
        Configuration config = new Configuration();
        // 2.设置模版url
        String tempdir = "F:/idea/qingniaoshangchneg/qingniaoshangcheng/console/src/main/webapp/WEB-INF/template/";
        // 3.加载模版目录和设置模板编码方式
        config.setDirectoryForTemplateLoading(new File(tempdir));
        config.setDefaultEncoding("utf-8");
        // 4.返回指定模版对象
        Template template = config.getTemplate("temp.html");
        // 5.定义一个map用来存放数据
        Map map = new HashMap();
        map.put("name", "hello freemarker");
        // 6.输出文件的目录和名称
        Writer writer = new FileWriter("F:/freemarker/hello.html");
        // 7.通过模版输出数据
        template.process(map, writer);
        // 8.关流
        writer.close();
    }


    // 输出 object
    @Test
    public void dmeo2() throws Exception {
        Configuration configuration = new Configuration();
        // 设置模版所在目录
        configuration.setDirectoryForTemplateLoading(
                new File("F:/idea/qingniaoshangchneg/qingniaoshangcheng/console/src/main/webapp/WEB-INF/template/"));
        // 设置模版编码
        configuration.setDefaultEncoding("utf-8");
        // 获得模版对象
        Template template = configuration.getTemplate("temp.html");
        FreeMarkerceshi user = new FreeMarkerceshi();
        user.setName("张三");
        user.setAge("76");
        Map map = new HashMap<>();
        map.put("user", user);
        Writer writer = new FileWriter("F:/freemarker/object.html");
        template.process(map, writer);

        /**
         * 取值
         * ${buyer.username} ${buyer.realName}
         */
    }


    //输出 list 集合数据
    @Test
    public void demo3() throws Exception {
        Configuration configuration = new Configuration();
        //设置模版和编码方式
        configuration.setDefaultEncoding("utf-8");
        configuration.setDirectoryForTemplateLoading(new File("F:/idea/qingniaoshangchneg/qingniaoshangcheng/console/src/main/webapp/WEB-INF/template/"));
        Template template = configuration.getTemplate("temp.html");
        Map map = new HashMap<>();
        List user = new ArrayList();
        FreeMarkerceshi buyer1 = new FreeMarkerceshi();
        buyer1.setName("张三");
        buyer1.setAge("张三丰");
        user.add(buyer1);
        FreeMarkerceshi buyer2 = new FreeMarkerceshi();
        buyer2.setName("张三");
        buyer2.setAge("张三丰");

        user.add(buyer2);
        Writer out = new FileWriter("F:/freemarker/list.html");
        map.put("users",user);
        template.process(map, out);
        out.close();

        /**
         * 取值
         * <#list buyers as buyer>
         * 		${buyer.username}
         * </#list>
         *
         */
    }

    //输出map集合
    @Test
    public void demo4() throws Exception {
        Configuration configuration = new Configuration();
        configuration.setDefaultEncoding("utf-8");
        configuration.setDirectoryForTemplateLoading(new File("F:/idea/qingniaoshangchneg/qingniaoshangcheng/console/src/main/webapp/WEB-INF/template/"));
        Template template = configuration.getTemplate("temp.html");
        Map root = new HashMap();
        Map m = new HashMap();
        m.put("aaa",123);
        m.put("bbb",456);
        root.put("m",m);
        Writer out = new FileWriter(new File("F:/freemarker/map.html"));
        template.process(root, out);
        out.close();

        /** 取值方式
         <#list m?keys as key>
         ${m[key]}
         </#list>
         */
    }

    @Test
    public void demo5() throws Exception {
        Configuration configuration = new Configuration();
        configuration.setDefaultEncoding("utf-8");
        configuration.setDirectoryForTemplateLoading(
                new File("F:/idea/qingniaoshangchneg/qingniaoshangcheng/console/src/main/webapp/WEB-INF/template/"));
        Template template = configuration.getTemplate("temp.html");
        Map root = new HashMap(); // 创建map集合
        List<Map> maps = new ArrayList();
        Map map = new HashMap();
        map.put("name", "张三");
        map.put("age", "25");
        maps.add(map);
        root.put("maps", maps);
        Writer out = new FileWriter("F:/freemarker/list_map.html");
        template.process(root, out);
        out.close();
    }

    /**
     * 取值方式
     *
     * <#list maps as map> ${map.name}/${map.age} </#list>
     *
     * <#list maps as m> <#list m?keys as k> ${m[k]} </#list> </#list>
     *
     * @throws Exception
     */


    // 获取循环中的下标
    @Test
    public void demo6() throws Exception {
        Configuration configuration = new Configuration();
        configuration.setDefaultEncoding("utf-8");
        configuration.setDirectoryForTemplateLoading(
                new File("F:/idea/qingniaoshangchneg/qingniaoshangcheng/console/src/main/webapp/WEB-INF/template/"));
        Template template = configuration.getTemplate("temp.html");
        Map root = new HashMap();
        List buyers = new ArrayList();
        FreeMarkerceshi buyer1 = new FreeMarkerceshi();
        buyer1.setName("张三");
        buyer1.setAge("张三丰");
        buyers.add(buyer1);
        FreeMarkerceshi buyer2 = new FreeMarkerceshi();
        buyer2.setName("张三");
        buyer2.setAge("张三丰");
        buyers.add(buyer2);
        root.put("buyers", buyers);
        Writer out = new FileWriter(new File("F:/freemarker/index.html"));
        template.process(root, out);
        out.close();

        /**
         <#list buyers as b>
         ${b_index}
         </#list>
         */
    }

// 模版中赋值
// 模版中赋值
@Test
public void demo7() throws Exception {
    // 1.创建 configration实例
    Configuration config = new Configuration();
    // 2.设置模版url
    String tempdir = "F:/idea/qingniaoshangchneg/qingniaoshangcheng/console/src/main/webapp/WEB-INF/template/";
    // 3.加载模版目录和设置模板编码方式
    config.setDirectoryForTemplateLoading(new File(tempdir));
    config.setDefaultEncoding("utf-8");
    // 4.返回指定模版对象
    Template template = config.getTemplate("temp.html");
    Map root = new HashMap<>();
    root.put("word","世界");
    Writer out = new FileWriter(new File("F:/freemarker/tmp.html"));
    template.process(root, out);

    out.close();
    /**

     1:<#assign a=0 />
     ${a}
     2:<#assign b="${word}" />
     ${b}
     3:<#assign c>世界太好了</#assign>
     ${c}
     4:
     <#list ["星期一", "星期二", "星期三", "星期四", "星期五", "星期六", "星期天"] as n>
     ${n}
     </#list>

     */
//    	<#list ["星期一", "星期二", "星期三", "星期四", "星期五", "星期六", "星期天"] as n>
//		<#if n != "星期一">
//            ${n}
//		</#if>
//	</#list>


//    	//按索引
//	 <#list ["星期一", "星期二", "星期三", "星期四", "星期五", "星期六", "星期天"] as n>
//		<#if n_index != 0>
//		   ${n}
//		</#if>
//	</#list>


    //	||   &&
//	<#list ["星期一", "星期二", "星期三", "星期四", "星期五", "星期六", "星期天"] as n>
//		<#if (n_index == 1) || (n_index == 3)>
//            ${n}
//		</#if>
//	</#list>


//    	#else
//	<#list ["星期一", "星期二", "星期三", "星期四", "星期五", "星期六", "星期天"] as n>
//		<#if (n_index == 1) || (n_index == 3)>
//		    ${n} --红色
//		<#else>
//			${n} --绿色
//		</#if>
//	</#list>


}

@Test
    public void riqi() throws Exception {
    //    时间格式
    // 1.创建 configration实例
    Configuration config = new Configuration();
    // 2.设置模版url
    String tempdir = "F:/idea/qingniaoshangchneg/qingniaoshangcheng/console/src/main/webapp/WEB-INF/template/";
    // 3.加载模版目录和设置模板编码方式
    config.setDirectoryForTemplateLoading(new File(tempdir));
    config.setDefaultEncoding("utf-8");
    // 4.返回指定模版对象
    Template template = config.getTemplate("temp.html");
    Map roots = new HashMap();
//    roots.put("cur_time",new Date());
    roots.put("val",null);
//    ${val!"我是null!"}
    Writer out = new FileWriter(new File("F:/freemarker/riqi.html"));
    template.process(roots, out);
    out.close();


//    1:date
//${cur_time?date}
//2:datetime
//${cur_time?datetime}
//3:time
//${cur_time?time}

}
//包含其他页面的标签
//<#include "/include/head.html">

}
