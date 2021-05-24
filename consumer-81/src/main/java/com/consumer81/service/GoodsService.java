package com.consumer81.service;

import com.commonTools.entity.Goods;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;

import java.io.IOException;
import java.util.List;

public interface GoodsService {

    Goods findById(Long id) throws JsonProcessingException;

    List<Goods> list();

    @RabbitListener(queues = {"TestDirectQueue"})
    @RabbitHandler
    void buy(Long id, Channel channel, Message message) throws IOException;
}