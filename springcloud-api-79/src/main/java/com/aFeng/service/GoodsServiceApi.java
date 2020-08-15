package com.aFeng.service;

import com.aFeng.pojo.Goods;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import java.util.List;

@FeignClient("provider")
public interface GoodsServiceApi {

    @PostMapping("/add")
    boolean add(Goods goods);

    @GetMapping("/get/{id}")
    Goods getGoodsById(@PathVariable("id") Long id);

    @GetMapping("/list")
    List<Goods> list();
}
