package com.api79.dist;

import com.aFeng.pojo.Goods;
import com.api79.bean.FeignClientConfig;
import feign.RequestLine;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Component("goodsServiceApi")
@FeignClient(name = "PROVIDER",configuration=FeignClientConfig.class)
public interface GoodsServiceApi {

    @RequestLine("POST /goods/add")
    boolean add(Goods goods);

    @RequestLine("GET /goods/get/{id}")
    Goods getGoodsById(@PathVariable("id") Long id);

    @RequestLine("GET /goods/list")
    List<Goods> list();
}