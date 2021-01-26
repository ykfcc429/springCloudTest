package com.consumer81.controller;

import com.aFeng.pojo.Goods;
import com.consumer81.service.GoodsService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/goods")
@SuppressWarnings("all")
public class GoodsController {

    private com.consumer81.service.GoodsService goodsService;

    private RabbitTemplate rabbitTemplate;

    @Autowired
    public void setRabbitTemplate(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Autowired
    public void setGoodsService(GoodsService goodsService) {
        this.goodsService = goodsService;
    }

//    @RequestMapping("/add")
//    public boolean add(Goods goods){
//        return goodsService
//    }

    @RequestMapping("/get/{id}")
    public Goods findById(@PathVariable("id")Long id) throws InterruptedException {
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
