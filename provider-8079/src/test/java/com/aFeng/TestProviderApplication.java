package com.aFeng;

import com.aFeng.service.GoodsService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import java.util.HashMap;
import java.util.Map;

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
        Map<String,String> map = new HashMap<>();
        map.put("A","200.0");
        System.out.println(map);
    }
}
