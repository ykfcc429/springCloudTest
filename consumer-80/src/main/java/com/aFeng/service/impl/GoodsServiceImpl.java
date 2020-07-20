package com.aFeng.service.impl;

import com.aFeng.service.GoodsService;
import com.aFeng.util.RedisUtil;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;
import redis.clients.jedis.Jedis;

@Service
public class GoodsServiceImpl implements GoodsService {

    @Value("${provider.url}")
    private String URL;

    RedisUtil redisUtil;

    RestTemplate restTemplate;

    @Autowired
    public void setRedisUtil(RedisUtil redisUtil) {
        this.redisUtil = redisUtil;
    }

    @Autowired
    public void setRestTemplate(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public Object findById(Long id) {
        Jedis jedis = redisUtil.getInstance();
        String s = jedis.get("goods:" + id);
        if(StringUtils.isEmpty(s)){
            Object object = restTemplate.getForObject(URL+"/goods/get/"+id,Object.class);
            s = JSON.toJSONString(object);
            jedis.set("goods:"+id,s);
        }
        jedis.close();
        return JSON.parse(s);
    }
}
