package com.aFeng.service;

import com.aFeng.pojo.Goods;

import java.util.ArrayList;
import java.util.List;

public interface GoodsService {

    Goods findById(Long id);

    List<Goods> list();
}
