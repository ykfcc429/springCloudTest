package com.consumer81.bean;

import feign.Contract;
import feign.Feign;
import feign.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author ykf
 * @version 2021/2/5
 */
public class FeignClientConfig {

    @Bean
    public Contract feignContract() {
        return new Contract.Default();
    }

    @Bean
    public Logger.Level getLevel(){
        return Logger.Level.NONE;
    }
}
