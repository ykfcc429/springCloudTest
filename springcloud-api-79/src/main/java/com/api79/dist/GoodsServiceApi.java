package com.api79.dist;

import com.aFeng.pojo.Goods;
import feign.RequestLine;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import java.util.List;

@Component("goodsServiceApi")
@FeignClient("PROVIDER")
public interface GoodsServiceApi {

    @PostMapping("/goods/add")
    boolean add(Goods goods);

    @GetMapping("/goods/get/{id}")
    Goods getGoodsById(@PathVariable("id") Long id);

    @RequestLine("GET /goods/list")
    List<Goods> list();
}