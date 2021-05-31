package com.consumer81.service.impl;

import com.aFeng.dist.GoodsServiceApi;
import com.commonTools.entity.Goods;
import com.consumer81.service.GoodsService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.amqp.core.Message;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.Duration;
import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class GoodsServiceImpl implements GoodsService {

    /**
     * redis 工具类
     */
    private final RedisTemplate<String, String> redisTool;

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
    public Goods findById(Long id) throws JsonProcessingException {
        log.debug("enter findById , id is {}", id);
        String s = redisTool.opsForValue().get("goods:" + id);
        ObjectMapper objectMapper = new ObjectMapper();
        if (StringUtils.isEmpty(s)) {
            Boolean flag;
            if ((flag = redisTool.opsForValue().setIfAbsent("findGoodById" + id, "1")) != null && flag) {
                Goods goods = new Goods();
                try {
                    goods = goodsServiceApi.getGoodsById(id);
                } catch (Exception e) {
                    log.error("GoodsServiceImpl.findById error ,id is {}",id,e);
                }
                s = objectMapper.writeValueAsString(goods);
                redisTool.opsForValue().set("goods:" + id, s);
                int expire = 2*3600;
                if(goods.getId()==null)
                    expire = 10;
                redisTool.expire("goods:"+id, Duration.ofSeconds(expire));
            }else {
                try {
                    Thread.sleep(1000);
                    findById(id);
                }catch (InterruptedException i){
                    log.error("Thread.sleep error",i);
                }
            }
        }
        log.debug("exit findById, id is {}, goods is {}", id , s);
        return objectMapper.readValue(s,Goods.class);
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
