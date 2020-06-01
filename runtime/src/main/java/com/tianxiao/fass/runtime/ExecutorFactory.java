package com.tianxiao.fass.runtime;

import com.sun.tools.javac.util.ServiceLoader;
import com.tianxiao.fass.common.enums.ExecutorType;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class ExecutorFactory {

    private static final Map<String, Executor> executorMap = new HashMap<String, Executor>(2);

    public static void init() {
        ServiceLoader<Executor> load = ServiceLoader.load(Executor.class);
        Iterator<Executor> iterator = load.iterator();
        while (iterator.hasNext()) {
            Executor next = iterator.next();
            executorMap.put(next.type(), next);
        }
    }

    public static void destroy() {
        executorMap.clear();
    }

    public static Executor getExecutor(ExecutorType type) {
        Executor executor = executorMap.get(type.name());
        return executor;
    }
}
