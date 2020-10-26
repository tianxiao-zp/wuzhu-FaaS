package com.tianxiao.faas.runtime;

import com.tianxiao.faas.common.enums.ExecutorType;
import com.tianxiao.faas.runtime.processor.manager.ServiceLoaderBeanDefinitionsProcessorManager;
import com.tianxiao.faas.runtime.processor.manager.BeanDefinitionsProcessorManager;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.ServiceLoader;

public class ExecutorFactory {

    private final static ExecutorFactory factory = new ExecutorFactory();

    private BeanDefinitionsProcessorManager beanDefinitionsProcessorManager;

    private static final Map<String, Executor> executorMap = new HashMap<String, Executor>();

    public static ExecutorFactory getInstance() {
        return factory;
    }

    public void init() {
        if (beanDefinitionsProcessorManager == null) {
            beanDefinitionsProcessorManager = new ServiceLoaderBeanDefinitionsProcessorManager();
        }
        BeanDefinitionsProcessorManagerFactory.getInstance().init(beanDefinitionsProcessorManager);
        ServiceLoader<Executor> load = ServiceLoader.load(Executor.class);
        Iterator<Executor> iterator = load.iterator();
        while (iterator.hasNext()) {
            Executor next = iterator.next();
            executorMap.put(next.type(), next);
        }
    }

    public void setBeanDefinitionsProcessorManager(BeanDefinitionsProcessorManager beanDefinitionsProcessorManager) {
        this.beanDefinitionsProcessorManager = beanDefinitionsProcessorManager;
    }

    public void destroy() {
        executorMap.clear();
    }

    public Executor getExecutor(ExecutorType type) {
        Executor executor = executorMap.get(type.name());
        return executor;
    }
}
