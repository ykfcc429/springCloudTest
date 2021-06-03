package com.consumer81;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * 消费者启动类
 */
@SpringBootApplication(exclude= {DataSourceAutoConfiguration.class},
        scanBasePackages = {"com.commonTools","com.consumer81.*"})
@EnableEurekaClient
@EnableFeignClients(basePackages = {"com.commonTools.dist"})
public class ConsumerApplication {
    public static void main(String[] args) {
        SpringApplication.run(ConsumerApplication.class,args);
    }
}
