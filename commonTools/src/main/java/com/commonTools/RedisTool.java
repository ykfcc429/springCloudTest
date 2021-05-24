package com.commonTools;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.params.SetParams;

@Component
@AllArgsConstructor
public class RedisTool {

    private static final long INTERNALLOCKLEASETIME = 10000;//锁过期时间

    private static final SetParams params = SetParams.setParams().nx().px(INTERNALLOCKLEASETIME);

    private final JedisPool jedisPool;

    public Jedis getInstance(){
        return jedisPool.getResource();
    }

    /**
     * 加锁
     */
    public boolean lock(String id){
        try (Jedis jedis = getInstance()) {
            //SET命令返回OK ，则证明获取锁成功
            String lock = jedis.set(id, "1", params);
            return lock != null;
        }
    }
}
