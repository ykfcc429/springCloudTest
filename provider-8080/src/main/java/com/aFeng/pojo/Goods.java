package com.aFeng.pojo;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

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
    private String name;

    /**
     * 库存
     */
    private Long stock;

    /**
     * 价格
     */
    private Double price;

    /**
     * 数据库来源
     */
    private String db_origin;

}
