package com.tianxiao.faas.container.runner;

import com.tianxiao.faas.runtime.ExecutorFactory;
import com.tianxiao.faas.runtime.processor.manager.BeanDefinitionsProcessorManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfig {

    @Bean(initMethod = "init", destroyMethod = "destroy")
    public ExecutorFactory faaSContainer(BeanDefinitionsProcessorManager beanDefinitionsProcessorManager) {
        ExecutorFactory instance = ExecutorFactory.getInstance();
        instance.setBeanDefinitionsProcessorManager(beanDefinitionsProcessorManager);
        return instance;
    }
}
