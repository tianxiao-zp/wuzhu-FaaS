package com.tianxiao.fass.runtime;

import com.tianxiao.fass.runtime.processor.manager.ServiceLoaderBeanProcessorManager;

public class FaaSContainer {
    private final static FaaSContainer faasContainer = new FaaSContainer();

    private FaaSContainer() {}

    public static FaaSContainer getInstance() {
        return faasContainer;
    }

    public void start() {
        ServiceLoaderBeanProcessorManager.init();
        ExecutorFactory.getInstance().init();
    }

    public void close() {
        ServiceLoaderBeanProcessorManager.destroy();
        ExecutorFactory.getInstance().destroy();
    }


}
