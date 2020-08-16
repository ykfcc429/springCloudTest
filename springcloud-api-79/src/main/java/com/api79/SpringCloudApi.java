package com.api79;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class},scanBasePackages = "com.api79.*")
@EnableFeignClients(basePackages = {"com.aFeng.*"})
@EnableEurekaClient
public class SpringCloudApi {
    public static void main(String[] args) {
        SpringApplication.run(SpringCloudApi.class,args);
    }
}
