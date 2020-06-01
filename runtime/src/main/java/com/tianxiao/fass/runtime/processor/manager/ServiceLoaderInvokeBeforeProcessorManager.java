package com.tianxiao.fass.runtime.processor.manager;

import com.sun.tools.javac.util.ServiceLoader;
import com.tianxiao.fass.runtime.processor.InvokeBeforeProcessor;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ServiceLoaderInvokeBeforeProcessorManager implements InvokeBeforeProcessorManager {
    private final static List<InvokeBeforeProcessor> processors = new ArrayList<>();
    public static void init() {
        ServiceLoader<InvokeBeforeProcessor> beforeProcessors = ServiceLoader.load(InvokeBeforeProcessor.class);
        if (beforeProcessors != null) {
            Iterator<InvokeBeforeProcessor> iterator = beforeProcessors.iterator();
            while (iterator.hasNext()) {
                InvokeBeforeProcessor next = iterator.next();
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

    public List<InvokeBeforeProcessor> getProcessors() {
        return processors;
    }
}
