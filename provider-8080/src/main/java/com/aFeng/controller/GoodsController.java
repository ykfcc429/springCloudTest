package com.aFeng.controller;

import com.aFeng.service.GoodsService;
import com.commonTools.entity.Goods;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * 商品服务提供者
 * author:aFeng
 * date:2020/7/19
 */
@RestController("goodsController1")
@RequestMapping("/goods")
@AllArgsConstructor
@Valid
@Api(tags = "生产者商品控制器")
public class GoodsController {

    private final GoodsService goodsService;

    @ApiOperation("添加单个商品")
    @PostMapping("/add")
    public boolean add(@RequestBody @Validated Goods goods) throws ExecutionException, InterruptedException {
        return goodsService.add(goods).get();
    }

    /**
     * RestFul
     * @param id 商品ID
     * @return 商品全部信息
     */
    @ApiOperation("根据ID获取单个商品")
    @GetMapping("/get/{id}")
    public Goods getGoodsById(@PathVariable("id") Long id){
        return goodsService.findById(id);
    }

    /**
     * @return 获取所有商品所有信息,一般会有筛选条件和分页信息
     */
    @ApiOperation("获取全部商品")
    @GetMapping("/list")
    public List<Goods> list(){
        return goodsService.list();
    }

    /**
     * 删除单个商品
     * @param id 商品Id
     * @return 是否成功
     */
    @ApiOperation("根据Id删除商品")
    @GetMapping("/delete/{id}")
    public boolean delete(@PathVariable("id") Long id){
        return goodsService.delete(id);
    }
}
