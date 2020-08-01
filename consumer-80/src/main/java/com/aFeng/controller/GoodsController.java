package com.aFeng.controller;

import com.aFeng.pojo.Goods;
import com.aFeng.service.GoodsService;
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

    GoodsService goodsService;

    @Value("${provider.url}")
    private String URL ;

    @Autowired
    public void setGoodsService(GoodsService goodsService) {
        this.goodsService = goodsService;
    }

    @Autowired
    public void setRestTemplate(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @RequestMapping("/add")
    public boolean add(Goods goods){
        return restTemplate.postForObject(URL+"/goods/add",goods,Boolean.class);
    }

    @RequestMapping("/get/{id}")
    public Goods findById(@PathVariable("id")Long id){
        return goodsService.findById(id);
    }

    @RequestMapping("/list")
    public List<Goods>  list(){
        return goodsService.list();
    }
}
