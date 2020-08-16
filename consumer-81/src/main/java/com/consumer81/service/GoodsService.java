package com.consumer81.service;

import com.aFeng.pojo.Goods;
import org.springframework.cloud.openfeign.FeignClient;

import java.util.List;

public interface GoodsService {

    Goods findById(Long id) throws InterruptedException;

    List<Goods> list();

    String buy(Long id);
}
