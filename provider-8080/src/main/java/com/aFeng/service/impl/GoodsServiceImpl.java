package com.aFeng.service.impl;

import com.aFeng.mapper.GoodsMapper;
import com.aFeng.service.GoodsService;
import com.commonTools.entity.Goods;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.Future;

@Service("GoodsServiceImpl1")
@Transactional
public class GoodsServiceImpl implements GoodsService {

    private GoodsMapper goodsMapper;

    @Autowired
    public void setGoodsMapper(GoodsMapper goodsMapper) {
        this.goodsMapper = goodsMapper;
    }

    @Async("providerThreadPool")
    public Future<Boolean> add(Goods goods) {
        System.out.println(Thread.currentThread().getName());
        return new AsyncResult<>(goodsMapper.add(goods));
    }

    public Goods findById(Long id) {
        Goods goods;
        return (goods = goodsMapper.findById(id))==null?new Goods():goods;
    }

    public List<Goods> list() {
        return goodsMapper.list();
    }

    public boolean delete(Long id) {
        return goodsMapper.delete(id);
    }
}
