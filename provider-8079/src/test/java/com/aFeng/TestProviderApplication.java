package com.aFeng;

import com.aFeng.pojo.Goods;
import com.aFeng.service.GoodsService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProviderApplication.class)
public class TestProviderApplication {

    GoodsService goodsService;

    @Autowired
    public void setGoodsService(GoodsService goodsService) {
        this.goodsService = goodsService;
    }

    @Test
    public void add(){
        Goods goods = new Goods();
        goods.setName("鼠标").setPrice(200d).setStock(2000L);
        System.out.println(goodsService.add(goods));
    }
}
