package com.tianxiao.faas.runtime;

import com.tianxiao.faas.runtime.processor.manager.BeanDefinitionsProcessorManager;

public final class BeanDefinitionsProcessorManagerFactory {

    private static final BeanDefinitionsProcessorManagerFactory factory = new BeanDefinitionsProcessorManagerFactory();

    public void init(BeanDefinitionsProcessorManager beanDefinitionsProcessorManager) {
        this.beanDefinitionsProcessorManager = beanDefinitionsProcessorManager;
    }

    public static BeanDefinitionsProcessorManagerFactory getInstance() {
        return factory;
    }

    private BeanDefinitionsProcessorManager beanDefinitionsProcessorManager;

    public BeanDefinitionsProcessorManager getManager() {
        return beanDefinitionsProcessorManager;
    }


}
