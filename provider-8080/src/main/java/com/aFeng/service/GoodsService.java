package com.aFeng.service;

import com.aFeng.pojo.Goods;

import java.util.List;

public interface GoodsService {

    boolean add(Goods goods);

    Goods findById(Long id);

    List<Goods> list();

    boolean delete(Long id);
}
