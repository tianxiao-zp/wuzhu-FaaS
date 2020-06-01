package com.tianxiao.fass.runtime;

import com.tianxiao.fass.runtime.processor.manager.ObjectInvokeBeforeProcessorManager;

public class FaasContainer {
    private final static FaasContainer faasContainer = new FaasContainer();

    private FaasContainer() {}

    public static FaasContainer getInstance() {
        return faasContainer;
    }

    public void start() {
        ObjectInvokeBeforeProcessorManager.init();
        ExecutorFactory.init();
    }

    public void close() {
        ObjectInvokeBeforeProcessorManager.destroy();
        ExecutorFactory.destroy();
    }


}
