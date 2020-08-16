package com.aFeng.service.impl;

import com.aFeng.mapper.GoodsMapper;
import com.aFeng.pojo.Goods;
import com.aFeng.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
@Transactional
public class GoodsServiceImpl implements GoodsService {

//    @Autowired
    private GoodsMapper goodsMapper;

    @Autowired
    public void setGoodsMapper(GoodsMapper goodsMapper) {
        this.goodsMapper = goodsMapper;
    }

    public boolean add(Goods goods) {
        return goodsMapper.add(goods);
    }

    public Goods findById(Long id) {
        return goodsMapper.findById(id);
    }

    public List<Goods> list() {
        return goodsMapper.list();
    }

    public boolean delete(Long id) {
        return goodsMapper.delete(id);
    }
}
