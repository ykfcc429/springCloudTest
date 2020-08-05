package com.aFeng.service;

import com.aFeng.pojo.Goods;

import java.util.List;

public interface GoodsService {

    Goods findById(Long id);

    List<Goods> list();

    String buy(Long id);
}
