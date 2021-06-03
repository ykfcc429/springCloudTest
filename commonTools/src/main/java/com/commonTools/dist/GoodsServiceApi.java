package com.commonTools.dist;

import com.commonTools.config.FeignClientConfig;
import com.commonTools.entity.Goods;
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