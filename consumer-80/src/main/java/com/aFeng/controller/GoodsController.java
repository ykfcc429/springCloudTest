package com.aFeng.controller;

import com.aFeng.util.RedisUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/goods")
@SuppressWarnings("all")
public class GoodsController {

    RestTemplate restTemplate;

    RedisUtil redisUtil;

    private static final String URL = "http://localhost:8079";

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
        Object object = restTemplate.getForObject(URL+"/goods/get/"+id,Object.class);
        String jsonObject = JSON.toJSONString(object);
        redisUtil.getInstance().set("goods:"+id,jsonObject);
        return object;
    }
}
