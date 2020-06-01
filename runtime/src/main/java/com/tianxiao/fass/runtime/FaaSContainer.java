package com.tianxiao.fass.runtime;

import com.tianxiao.fass.runtime.processor.manager.ServiceLoaderInvokeBeforeProcessorManager;

public class FaaSContainer {
    private final static FaaSContainer faasContainer = new FaaSContainer();

    private FaaSContainer() {}

    public static FaaSContainer getInstance() {
        return faasContainer;
    }

    public void start() {
        ServiceLoaderInvokeBeforeProcessorManager.init();
        ExecutorFactory.getInstance().init();
    }

    public void close() {
        ServiceLoaderInvokeBeforeProcessorManager.destroy();
        ExecutorFactory.getInstance().destroy();
    }


}
