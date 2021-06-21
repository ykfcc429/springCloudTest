package com.consumer81.service.impl;

import com.commonTools.dist.GoodsServiceApi;
import com.commonTools.entity.Goods;
import com.consumer81.service.GoodsService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.amqp.core.Message;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.time.Duration;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class GoodsServiceImpl implements GoodsService {

    /**
     * redisTemplate 工具类
     */
    private final RedisTemplate<String, String> redisTool;

    @Resource(name = "redisTemplate")
    private ValueOperations<String, String> redisTemplate;
    /**
     * rpc 商品服务
     */
    private final GoodsServiceApi goodsServiceApi;

    /**
     * 如果有多个消费者服务,应采用redis的分布式锁
     * @param id 商品ID
     * @return 商品信息
     */
    @Override
    @SneakyThrows
    public Goods findById(Long id) {
        log.debug("enter findById , id is {}", id);
        String s = redisTemplate.get("goods:" + id);//直接在redis取数据
        ObjectMapper objectMapper = new ObjectMapper();//新建json序列化工具
        if (StringUtils.isEmpty(s)) {                   //判断redis里取出来的数据是否为空
            Boolean flag;                               //
            if ((flag = redisTemplate.setIfAbsent("findGoodById" + id, "1",Duration.ofSeconds(10)))  //利用redis 的 setNx命令添加分布式锁
                    != null && flag) {
                Goods goods = new Goods();
                try {
                    goods = goodsServiceApi.getGoodsById(id);  //使用feign rpc调用生产者服务的接口
                } catch (Exception e) {
                    log.error("GoodsServiceImpl.findById error ,id is {}",id,e);
                }
                s = objectMapper.writeValueAsString(goods);   // 查到的实体转成json字符串,就算rpc调用异常也能保证一个默认对象会被序列化
                redisTemplate.set("goods:" + id, s);  //存进redis
                int expire = 2*3600;                            //定义缓存过期时间
                if(goods.getId()==null)                         //如果没查到数据,过期时间设置成10秒 这一步是关键,缓存的目的就是为了让数据库的访问量减少,如果没查到数据,那立马
                    expire = 10;                                //去查也未必能查到,等于无故增加了数据库压力
                redisTool.expire("goods:"+id, Duration.ofSeconds(expire));  //设置过期时间
            }else {
                try {
                    Thread.sleep(1000);               //这里是获取分布式锁失败的时候会走的分支,不管有多少个线程同时抵达,只会有一个线程会进入数据库查询,其他的线程都会被分到这里
                    return findById(id);                    //然后休息一会,等待数据加载到redis,再自己调用自己
                }catch (InterruptedException i){
                    log.error("Thread.sleep error",i);
                }
            }
        }
        log.debug("exit findById, id is {}, goods is {}", id , s);
        return objectMapper.readValue(s,Goods.class);      //最后把redis里取到的字符串序列化成对象返回
    }

    @Override
    public List<Goods> list() {
        return goodsServiceApi.list();
    }

    @Override
    public void buy(Long id, Channel channel, Message message) throws IOException {
        try {
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        } catch (Exception e) {
            log.error("mq reserve error, info: id is {}, message is {}", id, message);
            channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, false);
        }
    }
}
