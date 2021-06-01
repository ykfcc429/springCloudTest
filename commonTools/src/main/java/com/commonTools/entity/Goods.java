package com.commonTools.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 商品类
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class Goods implements Serializable {

    /**
     * 主键
     */
    private Long id;

    /**
     * 名称
     */
    @NotBlank
    private String name;

    /**
     * 库存
     */
    @NotNull
    private Long stock;

    /**
     * 价格
     */
    @NotNull
    private Double price;

    /**
     * 数据库来源
     */
    private String db_origin;

}
