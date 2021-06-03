package com.aFeng;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication(scanBasePackages =  {"com.commonTools","com.aFeng.*"})
@EnableEurekaClient
public class Provider8080 {

    public static void main(String[] args) {
        SpringApplication.run(Provider8080.class,args);
    }
}