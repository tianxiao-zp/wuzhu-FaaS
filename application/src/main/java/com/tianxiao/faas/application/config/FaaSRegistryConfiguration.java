package com.tianxiao.faas.application.config;

import com.tianxiao.faas.biz.publisher.FaaSPublisher;
import com.tianxiao.faas.biz.registry.FaaSRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FaaSRegistryConfiguration {

    @Bean(initMethod = "init", destroyMethod = "destroy")
    public FaaSRegistry registry(FaaSPublisher publisher) {
        return new FaaSRegistry(publisher);
    }
}
