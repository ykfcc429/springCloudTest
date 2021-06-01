package com.aFeng.service;

import com.commonTools.entity.Goods;

import java.util.List;
import java.util.concurrent.Future;

public interface GoodsService {

    Future<Boolean> add(Goods goods);

    Goods findById(Long id);

    List<Goods> list();

    boolean delete(Long id);
}
