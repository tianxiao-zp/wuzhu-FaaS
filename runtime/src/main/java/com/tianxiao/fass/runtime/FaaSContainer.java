package com.tianxiao.fass.runtime;

import com.tianxiao.fass.runtime.processor.manager.ServiceLoaderBeanDefinitionsProcessorManager;

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
