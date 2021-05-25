package com.consumer81.controller;

import com.commonTools.RedisTool;
import com.commonTools.entity.Goods;
import com.consumer81.service.GoodsService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.AllArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/goods")
@AllArgsConstructor
public class GoodsController {

    private final GoodsService goodsService;

    private final RabbitTemplate rabbitTemplate;

    private final RedisTool redisTool;

//    @RequestMapping("/add")
//    public boolean add(Goods goods){
//        return goodsService
//    }

    @RequestMapping("/get/{id}")
    public Goods findById(@PathVariable("id")Long id) throws JsonProcessingException {
        return goodsService.findById(id);
    }

    @RequestMapping("/list")
    public List<Goods> list(){
        return goodsService.list();
    }

    @RequestMapping("buy/{id}")
    @ResponseBody
    public String buy(@PathVariable("id")Long id){
        return "";
    }
}
