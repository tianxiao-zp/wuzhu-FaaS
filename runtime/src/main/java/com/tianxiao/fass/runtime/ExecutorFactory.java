package com.tianxiao.fass.runtime;

import com.sun.tools.javac.util.ServiceLoader;
import com.tianxiao.fass.common.enums.ExecutorType;
import com.tianxiao.fass.runtime.processor.manager.BeanProcessorManager;
import com.tianxiao.fass.runtime.processor.manager.ServiceLoaderBeanProcessorManager;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class ExecutorFactory {

    private final static ExecutorFactory factory = new ExecutorFactory();

    private BeanProcessorManager beanProcessorManager = new ServiceLoaderBeanProcessorManager();

    private static final Map<String, Executor> executorMap = new HashMap<String, Executor>();

    public static ExecutorFactory getInstance() {
        return factory;
    }

    public void init() {
        ServiceLoader<Executor> load = ServiceLoader.load(Executor.class);
        Iterator<Executor> iterator = load.iterator();
        while (iterator.hasNext()) {
            Executor next = iterator.next();
            next.processManager(beanProcessorManager);
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
