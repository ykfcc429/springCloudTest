package com.aFeng.controller;

import com.aFeng.pojo.Goods;
import com.aFeng.service.GoodsService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 商品服务提供者
 * author:aFeng
 * date:2020/7/19
 */
@RestController
@RequestMapping("/goods")
@AllArgsConstructor
public class GoodsController {

    private final GoodsService goodsService;

    @PostMapping("/add")
    public boolean add(Goods goods){
        return goodsService.add(goods);
    }

    /**
     * RestFul
     * @param id 商品ID
     * @return 商品全部信息
     */
    @GetMapping("/get/{id}")
    @HystrixCommand(fallbackMethod = "getGoodsByIdHystrix")
    public Goods getGoodsById(@PathVariable("id") Long id){
        return goodsService.findById(id);
    }

    public Goods getGoodsByIdHystrix(@PathVariable("id")Long id){
        return new Goods().setId(0L).setName("不存在的id:"+id);
    }

    /**
     * @return 获取所有商品所有信息,一般会有筛选条件和分页信息
     */
    @GetMapping("/list")
    public List<Goods> list(){
        return goodsService.list();
    }

    /**
     * 删除单个商品
     * @param id 商品Id
     * @return 是否成功
     */
    @GetMapping("/delete/{id}")
    public boolean delete(@PathVariable("id") Long id){
        return goodsService.delete(id);
    }
}
