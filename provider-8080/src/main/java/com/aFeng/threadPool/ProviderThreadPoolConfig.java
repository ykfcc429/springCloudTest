package com.aFeng.threadPool;

import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties("thread-pool.provider")
@Setter
@Component
class ProviderThreadPoolConfig{
    protected int corePoolSize;
    protected int maximumPoolSize;
    protected int keepAliveTime;
}