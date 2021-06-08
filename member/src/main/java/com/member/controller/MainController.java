package com.member.controller;

import com.commonTools.entity.Result;
import com.commonTools.util.ServletUtil;
import com.member.service.MainService;
import com.member.vo.request.LoginRequest;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.ServletRequest;
import javax.validation.Valid;
import java.util.concurrent.TimeUnit;

/**
 * @author yankaifeng
 * 创建日期 2021/6/8
 */
@RestController
@RequestMapping("/member")
@Api(value = "", tags = "")
@RequiredArgsConstructor
@Validated
public class MainController {

    private final MainService mainService;

    private final ServletUtil servletUtil;

    @Resource(name = "redisTemplate")
    private ValueOperations<String, String> valueOperations;

    @PostMapping("/signIn")
    public Result<String> login(@Valid @RequestBody LoginRequest request, ServletRequest servletRequest){
        if(!mainService.checkLoginAccount(request.getAccount(), request.getPassword()))
            return Result.error(5001,"账户或密码错误!");
        String token = mainService.getToken(request.getAccount(), request.getPassword(),
                servletUtil.getRemoteAddress(servletRequest));
        valueOperations.set(request.getAccount(), token, 30, TimeUnit.DAYS);
        return Result.success(token);
    }
}
