package com.aFeng.service.impl;

import com.aFeng.mapper.GoodsMapper;
import com.aFeng.pojo.Goods;
import com.aFeng.service.GoodsService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@Slf4j
@AllArgsConstructor
public class GoodsServiceImpl implements GoodsService {

    private final GoodsMapper goodsMapper;

    public boolean add(Goods goods) {
        return goodsMapper.add(goods);
    }

    public Goods findById(Long id) {
        Goods goods = null;
        try{
            goods = goodsMapper.findById(id);
        }catch (Exception e){
            Throwable exception = e;
            while (exception.getCause()!=null){
                exception = exception.getCause();
            }
            log.error("goodsServiceImpl.findById error", exception);
        }
        return goods;
    }

    public List<Goods> list() {
        return goodsMapper.list();
    }

    public boolean delete(Long id) {
        return goodsMapper.delete(id);
    }
}
