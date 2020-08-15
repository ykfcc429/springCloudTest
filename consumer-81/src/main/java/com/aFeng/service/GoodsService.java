package com.aFeng.service;

import com.aFeng.pojo.Goods;
import org.springframework.cloud.openfeign.FeignClient;

import java.util.List;

@FeignClient("dinc")
public interface GoodsService {

    Goods findById(Long id);

    List<Goods> list();

    String buy(Long id);
}
