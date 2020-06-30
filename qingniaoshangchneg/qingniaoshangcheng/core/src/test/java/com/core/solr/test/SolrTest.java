package com.core.solr.test;


import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring/applicationContext-*.xml")
public class SolrTest {
    //    		// 向solr 服务器里面添加数据
    @Test
    public void demo1() throws Exception, IOException {
        //创建solrServer
        SolrServer server = new HttpSolrServer("http://192.168.186.130:8080/solr");
        //创建存储数据的对象
        SolrInputDocument document = new SolrInputDocument();
        document.addField("id", 2);
        document.addField("name", "张三");
        //添加
        server.add(document);
        //提交
        server.commit();
    }

    //查询所有数据
    @Test
    public void demo2() throws Exception {
        SolrServer server = new HttpSolrServer("http://192.168.186.131:8080/solr");

        //查询
        SolrQuery solrQuery = new SolrQuery();
        solrQuery.set("q", "*:*");    //表示查询所有数据
        QueryResponse queryResponse = server.query(solrQuery);
        //获取结果集
        SolrDocumentList docs = queryResponse.getResults();
        //遍历结果集
        for (SolrDocument solrDocument : docs) {
            String id = (String) solrDocument.get("id");
            String name = (String) solrDocument.get("name");
            System.out.println(id + "---" + name);
        }
    }

    @Autowired
    SolrServer solrServer;

    @Test
    public void shanchuhuancun() throws IOException, SolrServerException {
        String[] a = {"1019", "1020", "1021"};
        for (int i = 0; i < a.length; i++) {
            String b = a[i];
            solrServer.deleteById(b);
            solrServer.commit();
        }

    }
}
