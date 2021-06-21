package com.consumer81.controller;

import com.commonTools.entity.Goods;
import com.commonTools.entity.Result;
import com.consumer81.service.GoodsService;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/goods")
@AllArgsConstructor
@Api(value = "商品控制器", tags = "消费者商品控制器")
@ApiResponses({@ApiResponse(code = 200, message = "成功"),@ApiResponse(code = 404,message = "没找着")})
public class GoodsController {

    private final GoodsService goodsService;

    @ApiOperation(value = "查询单个实体", httpMethod = "GET")
    @GetMapping("/get/{id}")
    @ApiResponse(code = 40001, message = "参数错误")
    public Result<Goods> findById(@PathVariable("id")Long id) throws JsonProcessingException {
        return Result.success(goodsService.findById(id));
    }

    @ApiOperation("查询所有实体")
    @GetMapping("/list")
    public Result<List<Goods>> list(){
        return Result.success(goodsService.list());
    }

    @ApiOperation("已废弃")
    @GetMapping("buy/{id}")
    @Deprecated(since = "6")
    public String buy(@PathVariable("id")Long id){
        return "";
    }
}
