package com.core.redis.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring/applicationContext-*.xml")
public class JedisTest {
    @Test
    public void redistest() {
        Jedis jedis = new Jedis("192.168.186.131", 6379);
        jedis.set("name", "李四");
    }

    @Test
    public void redistest1(){
        Jedis jedis = new Jedis("192.168.186.130",6379);
        String name = jedis.get("name");
        System.out.println(name);
    }
    //配置jedis的连接池
    @Test
    public void redistest2(){
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxTotal(50);
        JedisPool jedisPool = new JedisPool(jedisPoolConfig, "192.168.186.131", 6379);
        Jedis jedis = jedisPool.getResource();
        jedis.hset("user3","age","5");
        jedis.hset("user3","name","李四");
        String age = jedis.hget("user3", "age");
        String name = jedis.hget("user3", "name");
        System.out.println(name+age);
        jedis.close();
    }
    @Autowired
    JedisPool jedisPool;
    //redis整合spring进行测试
    @Test
    public void redistest3(){
        Jedis jedis = jedisPool.getResource();
        jedis.del("user3");
        jedis.close();
    }
}
