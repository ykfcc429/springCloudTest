package com.aFeng.controller;

import com.aFeng.pojo.Goods;
import com.aFeng.service.GoodsService;
import lombok.AllArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import java.util.List;

@RestController
@RequestMapping("/goods")
@SuppressWarnings("all")
@AllArgsConstructor
public class GoodsController {

    private final RestTemplate restTemplate;

    private final GoodsService goodsService;

    private final RabbitTemplate rabbitTemplate;

    @Value("${provider.url}")
    private String URL ;

    @RequestMapping("/add")
    public boolean add(Goods goods){
        return restTemplate.postForObject(URL+"/goods/add",goods,Boolean.class);
    }

    @RequestMapping("/get/{id}")
    public Goods findById(@PathVariable("id")Long id){
        return goodsService.findById(id);
    }

    @RequestMapping("/list")
    public List<Goods> list(){
        return goodsService.list();
    }

    @RequestMapping("buy/{id}")
    @ResponseBody
    public String buy(@PathVariable("id")Long id){
        return goodsService.buy(id);
    }
}
