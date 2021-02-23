package com.consumer81.service;

import com.aFeng.pojo.Goods;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.cloud.openfeign.FeignClient;

import java.io.IOException;
import java.util.List;

public interface GoodsService {

    Goods findById(Long id) throws InterruptedException;

    List<Goods> list();

    @RabbitListener(queues = {"TestDirectQueue"})
    @RabbitHandler
    void buy(Long id, Channel channel, Message message) throws IOException;
}