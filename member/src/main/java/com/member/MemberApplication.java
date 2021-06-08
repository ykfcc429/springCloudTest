package com.member;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * @author yankaifeng
 * 创建日期 2021/6/8
 */
@SpringBootApplication(scanBasePackages = {"com.commonTools","com.member"})
@EnableEurekaClient
public class MemberApplication {

    public static void main(String[] args) {
        SpringApplication.run(MemberApplication.class,args);
    }
}
