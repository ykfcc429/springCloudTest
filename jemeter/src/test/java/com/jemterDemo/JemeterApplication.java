package com.jemterDemo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;

@SpringBootTest
@RunWith(SpringRunner.class)
public class JemeterApplication {

    @Test
    public void test(){
        HostAndPort hostAndPort = new HostAndPort("127.0.0.1",6379);
        Jedis jedis = new Jedis(hostAndPort);
        jedis.set("aFeng","handsome");
        System.out.println(jedis.get("aFeng"));
    }
}
