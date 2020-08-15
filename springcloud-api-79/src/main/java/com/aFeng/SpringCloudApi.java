package com.aFeng;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class SpringCloudApi {
    public static void main(String[] args) {
        SpringApplication.run(SpringCloudApi.class,args);
    }
}
