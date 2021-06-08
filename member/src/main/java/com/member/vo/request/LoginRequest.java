package com.member.vo.request;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author yankaifeng
 * 创建日期 2021/6/8
 */
@Data
public class LoginRequest {

    @NotNull
    private String account;

    @NotNull
    private String password;
}
