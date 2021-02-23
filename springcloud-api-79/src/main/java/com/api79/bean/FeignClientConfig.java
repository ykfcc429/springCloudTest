package com.api79.bean;

import feign.Contract;
import org.springframework.context.annotation.Bean;

/**
 * @author ykf
 * @version 2021/2/5
 */
public class FeignClientConfig {

    @Bean
    public Contract feignContract() {
        return new feign.Contract.Default();
    }

}
