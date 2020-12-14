package com.aFeng.service.impl;

import com.aFeng.pojo.Goods;
import com.aFeng.service.GoodsService;
import com.aFeng.util.CommonUtil;
import com.aFeng.util.MapUtil;
import com.aFeng.util.RedisUtil;
import com.alibaba.fastjson.JSON;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import redis.clients.jedis.Jedis;

import java.util.List;
import java.util.Map;

@Service
public class GoodsServiceImpl implements GoodsService {

    @Value("${provider.url}")
    private String URL;

    private RedisUtil redisUtil;

    private RestTemplate restTemplate;

    private RabbitTemplate rabbitTemplate;

    @Autowired
    public void setRabbitTemplate(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Autowired
    public void setRedisUtil(RedisUtil redisUtil) {
        this.redisUtil = redisUtil;
    }

    @Autowired
    public void setRestTemplate(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    /**
     * 双检锁,仅适用于单应用的前提下,如果redis中没有缓存数据,仅允许单个线程去访问生产者(数据库)
     * 如果有多个消费者服务,应采用redis的分布式锁
     * @param id 商品ID
     * @return 商品信息的JSON字符串
     */
    @SuppressWarnings("all")
    @Override
    public Goods findById(Long id) {
        Jedis jedis = redisUtil.getInstance();
        Map<String,String> map = jedis.hgetAll("goods:" + id);
        String s;
        if(map.isEmpty()){
            synchronized (this){
                if(map.isEmpty()){
                    Goods goods = restTemplate.getForObject(URL+"/goods/get/"+id,Goods.class);
                    s = JSON.toJSONString(goods);
                    // redis hash本身只支持String类型的值
                    //这里value虽然强转成String了,但还是属于原来的类,BigDecimal等无法转为String的类会在hSet的时候报错
                    map = (Map<String, String>) JSON.parse(s);
                    MapUtil.convertValueToString(map);
                    jedis.hmset("goods:"+id,map);
                }else
                    map = jedis.hgetAll("goods:" + id);
            }
        }
        jedis.close();
        s = JSON.toJSONString(map);
        return JSON.parseObject(s,Goods.class);
    }

    @Override
    public List<Goods> list() {
        return CommonUtil.cast(restTemplate.getForObject(URL + "/goods/list", List.class));
    }

    @Override
    public String buy(Long id) {
        rabbitTemplate.convertAndSend("TestDirectExchange","TestDirectRouting",id);
        return "请等候";
    }
}
