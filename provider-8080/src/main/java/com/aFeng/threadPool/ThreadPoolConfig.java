package com.aFeng.threadPool;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author yankaifeng
 * 创建日期 2021/6/1
 */
@Configuration
@EnableAsync
@AllArgsConstructor
public class ThreadPoolConfig {

    private final ProviderThreadPoolConfig providerThreadPool;

    @Bean("providerThreadPool")
    public Executor providerThread(){
        ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
        threadPoolTaskExecutor.setCorePoolSize(providerThreadPool.corePoolSize);
        threadPoolTaskExecutor.setMaxPoolSize(providerThreadPool.maximumPoolSize);
        threadPoolTaskExecutor.setKeepAliveSeconds(providerThreadPool.keepAliveTime);
        threadPoolTaskExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        threadPoolTaskExecutor.initialize();
        return new ThreadPoolExecutor(providerThreadPool.corePoolSize,providerThreadPool.maximumPoolSize
                ,providerThreadPool.keepAliveTime, TimeUnit.SECONDS,new ArrayBlockingQueue<>(5,true)
        ,new ThreadPoolExecutor.CallerRunsPolicy());
    }

}
