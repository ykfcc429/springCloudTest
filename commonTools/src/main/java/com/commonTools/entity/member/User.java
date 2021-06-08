package com.commonTools.entity.member;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

/**
 * @author yankaifeng
 * 创建日期 2021/6/8
 */
@Data
@Builder
public class User {

    private Integer id;

    private String name;

    private String account;

    private String password;

    private Date createDate;

    private Date updateDate;
}
