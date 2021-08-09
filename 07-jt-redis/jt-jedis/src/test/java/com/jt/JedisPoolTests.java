package com.jt;

import org.junit.Test;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class JedisPoolTests {
    @Test
    public void testJedisPool(){
        //定义连接池的配置
        JedisPoolConfig config=new JedisPoolConfig();
        config.setMaxTotal(1000);//最大连接数
        config.setMaxIdle(60);//最大空闲时间(连接不用了要释放)
        //创建连接池
        JedisPool jedisPool=
                new JedisPool(config,"192.168.126.130",6379);
        //从池中获取一个连接
        Jedis resource = jedisPool.getResource();
        resource.auth("123456");
        //通过jedis连接存取数据
        resource.set("class","cgb2004");
        String clazz=resource.get("class");
        System.out.println(clazz);
        //将链接返回池中
        resource.close();
        //关闭连接池
        jedisPool.close();
    }
}
