package com.tianxiao.fass.runtime;

import com.sun.tools.javac.util.ServiceLoader;
import com.tianxiao.fass.common.enums.ExecutorType;
import com.tianxiao.fass.runtime.processor.manager.InvokeBeforeProcessorManager;
import com.tianxiao.fass.runtime.processor.manager.ServiceLoaderInvokeBeforeProcessorManager;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class ExecutorFactory {

    private final static ExecutorFactory factory = new ExecutorFactory();

    private InvokeBeforeProcessorManager invokeBeforeProcessorManager = new ServiceLoaderInvokeBeforeProcessorManager();

    private static final Map<String, Executor> executorMap = new HashMap<String, Executor>(2);

    public static ExecutorFactory getInstance() {
        return factory;
    }

    public void init() {
        ServiceLoader<Executor> load = ServiceLoader.load(Executor.class);
        Iterator<Executor> iterator = load.iterator();
        while (iterator.hasNext()) {
            Executor next = iterator.next();
            next.beforeProcessManager(invokeBeforeProcessorManager);
            executorMap.put(next.type(), next);
        }
    }

    public void destroy() {
        executorMap.clear();
    }

    public Executor getExecutor(ExecutorType type) {
        Executor executor = executorMap.get(type.name());
        return executor;
    }
}
