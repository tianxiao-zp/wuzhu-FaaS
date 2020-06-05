package com.tianxiao.faas.runtime.processor.manager;

import com.tianxiao.faas.runtime.processor.BeanDefinitionsAfterProcessor;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ServiceLoader;

public class ServiceLoaderBeanDefinitionsProcessorManager implements BeanDefinitionsProcessorManager {
    private final static List<BeanDefinitionsAfterProcessor> processors = new ArrayList<>();
    public static void init() {
        ServiceLoader<BeanDefinitionsAfterProcessor> beforeProcessors = ServiceLoader.load(BeanDefinitionsAfterProcessor.class);
        if (beforeProcessors != null) {
            Iterator<BeanDefinitionsAfterProcessor> iterator = beforeProcessors.iterator();
            while (iterator.hasNext()) {
                BeanDefinitionsAfterProcessor next = iterator.next();
                processors.add(next);
            }
            processors.sort((o1, o2) -> {
                int i = o1.order() - o2.order();
                if (i >= 0) {
                    return -1;
                } else {
                    return 1;
                }
            });
        }
    }

    public static void destroy() {
        processors.clear();
    }

    public List<BeanDefinitionsAfterProcessor> getAfterProcessors() {
        return processors;
    }
}
