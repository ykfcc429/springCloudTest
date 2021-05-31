package com.consumer81.controller;

import com.commonTools.entity.Goods;
import com.consumer81.service.GoodsService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/goods")
@AllArgsConstructor
public class GoodsController {

    private final GoodsService goodsService;

    @RequestMapping("/get/{id}")
    public Goods findById(@PathVariable("id")Long id) throws JsonProcessingException {
        return goodsService.findById(id);
    }

    @RequestMapping("/list")
    public List<Goods> list(){
        return goodsService.list();
    }

    @RequestMapping("buy/{id}")
    public String buy(@PathVariable("id")Long id){
        return "";
    }
}
