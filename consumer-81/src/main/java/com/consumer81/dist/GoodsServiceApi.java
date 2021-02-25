package com.consumer81.dist;

import com.aFeng.pojo.Goods;
import com.consumer81.bean.FeignClientConfig;
import feign.Param;
import feign.RequestLine;
import org.springframework.cloud.openfeign.FeignClient;

import java.util.List;

@FeignClient(value = "PROVIDER",configuration = FeignClientConfig.class)
public interface GoodsServiceApi {

    @RequestLine("POST /goods/add")
    boolean add(Goods goods);

    @RequestLine("GET /goods/get/{id}")
    Goods getGoodsById(@Param("id") Long id);

    @RequestLine("GET /goods/list")
    List<Goods> list();
}