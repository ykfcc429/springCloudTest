package com.consumer81.util;

import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * 记得释放连接哦
 */
@Component
public class RedisUtil {

    private JedisPool jedisPool;

    public RedisUtil(){
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(100);
        config.setMaxIdle(20);
        this.jedisPool = new JedisPool(config);
    }

    public Jedis getInstance(){
        return jedisPool.getResource();
    }
}
