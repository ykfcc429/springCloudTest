package com.consumer81.service.impl;

import com.api79.dist.GoodsServiceApi;
import com.aFeng.pojo.Goods;
import com.consumer81.util.MapUtil;
import com.consumer81.util.RedisUtil;
import com.alibaba.fastjson.JSON;
import com.consumer81.service.GoodsService;
import com.rabbitmq.client.Channel;
import feign.Feign;
import feign.FeignException;
import feign.Response;
import feign.codec.DecodeException;
import feign.codec.Decoder;
import feign.gson.GsonDecoder;
import org.springframework.amqp.core.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

@Service
public class GoodsServiceImpl implements GoodsService {

    private RedisUtil redisUtil;

    @Autowired
    GoodsServiceApi goodsServiceApi;

    @Autowired
    public void setRedisUtil(RedisUtil redisUtil) {
        this.redisUtil = redisUtil;
    }

    private volatile boolean flag = false;

    /**
     * 双检锁,仅适用于单应用的前提下,如果redis中没有缓存数据,仅允许单个线程去访问生产者(数据库)
     *
     * 说道双检锁,就是为了防止多线程下的缓存击穿问题,低并发的情况下,不需要 volatile 对象来保护,
     * 在高并发下,会出乱子,会使map.isEmpty的值更改后其他线程看不到,而继续去数据库读取数据
     * 所以我们需要一个 volatile 对象来验证,当一个线程对其的值进行改变的时候,能够马上通知其他的线程
     * 这样才安全
     *
     * 当然啦,直接用分布式锁也可以,但是当一个线程在查询数据库的时候,这个瞬间的其他请求全部会返回空值
     * 这是我不希望看到的,当然如果是高并发而请求又十分重要的,比如秒杀的场合,就必须要用消息队列了
     * 请求不是特别重要的话,在集群下丢失一个瞬间的请求我认为是可以接受的.
     *
     * 如果有多个消费者服务,应采用redis的分布式锁
     * @param id 商品ID
     * @return 商品信息的JSON字符串
     */
    @SuppressWarnings("all")
    @Override
    public Goods findById(Long id) throws InterruptedException {
        Jedis jedis = redisUtil.getInstance();
        Map<String,String> map = jedis.hgetAll("goods:" + id);
        String s;
        flag = map.isEmpty();
        if(flag){
            synchronized (this){
                if(flag){
                    Goods goods = goodsServiceApi.getGoodsById(id);
                    s = JSON.toJSONString(goods);
                    // redis hash本身只支持String类型的值
                    //这里value虽然强转成String了,但还是属于原来的类,BigDecimal等无法转为String的类会在hSet的时候报错
                    map = (Map<String, String>) JSON.parse(s);
                    MapUtil.convertValueToString(map);
                    jedis.hmset("goods:"+id,map);
                    flag = map.isEmpty();
                }else{
                    map = jedis.hgetAll("goods:" + id);
                }
            }
        }
        jedis.close();
        s = JSON.toJSONString(map);
        return JSON.parseObject(s,Goods.class);
    }

    @Override
    public List<Goods> list() {
        GoodsServiceApi goodsServiceApi = Feign.builder().decoder(new GsonDecoder()).target(GoodsServiceApi.class,"192.168.0.1:8848");
        return goodsServiceApi.list();
    }

    @Override
    public void buy(Long id, Channel channel, Message message) throws IOException {
        try {
            System.out.println(id);
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        } catch (Exception e) {
            System.out.println("receiver false");
            channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, false);
        }
    }
}
