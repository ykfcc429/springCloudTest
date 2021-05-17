package com.aFeng.controller;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author ykf
 * @version 2021/3/30
 */
@RestControllerAdvice
public class ControllerAdvice {

    @ExceptionHandler(Exception.class)
    public void handle(Exception e){
        Throwable exception = e;
        while (exception.getCause()!=null)
            exception = exception.getCause();
        System.out.println("controllerAdvice捕获到异常---"+exception.getMessage());
    }
}
