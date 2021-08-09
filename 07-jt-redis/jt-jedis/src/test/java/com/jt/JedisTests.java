package com.jt;

import org.junit.Test;
import redis.clients.jedis.Jedis;

import java.util.concurrent.TimeUnit;

public class JedisTests {

    @Test
    public void testStringOper() throws InterruptedException {
        //建立连接(与redis建立连接)
        Jedis jedis = new Jedis("192.168.126.128", 6379);
        String result = jedis.ping();
        System.out.println(result);
        //jedis.auth("123456");//假设设置了密码,需要密码认证
        //存储数据
        jedis.set("count", "1");
        jedis.set("id", "10001");
        //操作数据
        //更新数据
        jedis.expire("id", 1);
        jedis.incr("count");
        //获得数据
        String count = jedis.get("count");
        TimeUnit.SECONDS.sleep(1);//休眠一秒
        String id = jedis.get("id");
        System.out.println("cart.count=" + count);
        System.out.println("cart.id=" + id);

        //释放资源
        jedis.close();

    }

    //作业,尝试实现哈希,列表,集合类型的操作
    @Test
    public void testHashOper(){

    }
    @Test
    public void testListOper(){

    }
    @Test
    public void testSetOper(){

    }

}
