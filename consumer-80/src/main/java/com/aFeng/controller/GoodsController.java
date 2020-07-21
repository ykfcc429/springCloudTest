package com.aFeng.controller;

import com.aFeng.service.GoodsService;
import com.aFeng.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@RequestMapping("/goods")
@SuppressWarnings("all")
public class GoodsController {

    RestTemplate restTemplate;

    RedisUtil redisUtil;

    GoodsService goodsService;

    @Value("${provider.url}")
    private String URL ;

    @Autowired
    public void setGoodsService(GoodsService goodsService) {
        this.goodsService = goodsService;
    }

    @Autowired
    public void setRedisUtil(RedisUtil redisUtil) {
        this.redisUtil = redisUtil;
    }

    @Autowired
    public void setRestTemplate(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @PostMapping("/add")
    public boolean add(Object object){
        return restTemplate.postForObject(URL+"/goods/add",object,Boolean.class);
    }

    @GetMapping("/get/{id}")
    public Object findById(@PathVariable("id")Long id){
        return goodsService.findById(id);
    }

    @GetMapping("/list")
    public List list(){
        return goodsService.list();
    }
}
