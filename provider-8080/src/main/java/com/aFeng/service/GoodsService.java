package com.aFeng.service;

import com.commonTools.entity.Goods;

import java.util.List;

public interface GoodsService {

    boolean add(Goods goods);

    Goods findById(Long id);

    List<Goods> list();

    boolean delete(Long id);
}
