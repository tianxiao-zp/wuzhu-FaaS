package com.tianxiao.fass.runtime.processor.manager;

import com.sun.tools.javac.util.ServiceLoader;
import com.tianxiao.fass.runtime.processor.ObjectInvokeBeforeProcessor;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ObjectInvokeBeforeProcessorManager {
    private final static List<ObjectInvokeBeforeProcessor> processors = new ArrayList<>();
    public static void init() {
        ServiceLoader<ObjectInvokeBeforeProcessor> beforeProcessors = ServiceLoader.load(ObjectInvokeBeforeProcessor.class);
        if (beforeProcessors != null) {
            Iterator<ObjectInvokeBeforeProcessor> iterator = beforeProcessors.iterator();
            while (iterator.hasNext()) {
                ObjectInvokeBeforeProcessor next = iterator.next();
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

    public static List<ObjectInvokeBeforeProcessor> getProcessors() {
        return processors;
    }
}
