package com.member.controller;

import com.commonTools.entity.Result;
import com.commonTools.util.ServletUtil;
import com.member.service.MainService;
import com.member.vo.request.LoginRequest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.servlet.ServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.Email;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * @author yankaifeng
 * 创建日期 2021/6/8
 */
@RestController
@RequestMapping("/member")
@Api(value = "", tags = "member主控制器")
@RequiredArgsConstructor
@Validated
@Slf4j
public class MainController {

    private final MainService mainService;

    private final ServletUtil servletUtil;

    @SuppressWarnings("All")
    @Resource(name = "redisTemplate")
    private ValueOperations<String, String> valueOperations;

    @PostMapping("/signIn")
    @ApiOperation("登录接口,没什么说法")
    public Result<String> login(@Valid @RequestBody LoginRequest request, ServletRequest servletRequest){
        if(!mainService.checkLoginAccount(request.getAccount(), request.getPassword()))
            return Result.error(5001,"账户或密码错误!");
        String token = mainService.getToken(request.getAccount(), request.getPassword(),
                servletUtil.getRemoteAddress(servletRequest));
        valueOperations.set("token:"+request.getAccount(), token, 30, TimeUnit.MINUTES);
        return Result.success(token);
    }

    @GetMapping("/sendVerificationCode/{receiver}")
    @ApiOperation("获取验证码的接口,单个邮箱地址1分钟可以获取一次验证码")
    public Result<?> sendVerificationCode(@PathVariable("receiver")@Email String receiver){
        try {
            mainService.sendVerificationCode(receiver);
            return Result.success("success");
        }catch (MessagingException e){
            return Result.error(4002,e.getMessage());
        }catch (Throwable var1){
            log.error("send email error",var1);
            return Result.error(4001,"send email error");
        }
    }


}
