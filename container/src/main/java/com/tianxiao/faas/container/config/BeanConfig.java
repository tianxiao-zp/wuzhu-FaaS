package com.tianxiao.faas.container.config;

import com.tianxiao.faas.runtime.ExecutorFactory;
import com.tianxiao.faas.runtime.processor.manager.BeanDefinitionsProcessorManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

@Configuration
public class BeanConfig {

    @DependsOn({"springBeanDefinitionsProcessorManager"})
    @Bean(initMethod = "init", destroyMethod = "destroy")
    public ExecutorFactory faaSContainer(BeanDefinitionsProcessorManager beanDefinitionsProcessorManager) {
        ExecutorFactory instance = ExecutorFactory.getInstance();
        instance.setBeanDefinitionsProcessorManager(beanDefinitionsProcessorManager);
        return instance;
    }
}
