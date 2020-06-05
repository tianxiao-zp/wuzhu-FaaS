package com.tianxiao.faas.runtime;

import com.tianxiao.faas.runtime.processor.manager.ServiceLoaderBeanDefinitionsProcessorManager;

public class FaaSContainer {
    private final static FaaSContainer faasContainer = new FaaSContainer();

    private FaaSContainer() {}

    public static FaaSContainer getInstance() {
        return faasContainer;
    }

    public void start() {
        ServiceLoaderBeanDefinitionsProcessorManager.init();
        ExecutorFactory.getInstance().init();
    }

    public void close() {
        ServiceLoaderBeanDefinitionsProcessorManager.destroy();
        ExecutorFactory.getInstance().destroy();
    }


}
