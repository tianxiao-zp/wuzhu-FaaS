package com.tianxiao.faas.runtime;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public final class FaaSBeanFactory {

    private final static Map<String, Object> beans = new ConcurrentHashMap<>();

    public static Object getBean(String beanName) {
        Object o = beans.get(beanName);
        return o;
    }

    public static void cache(String beanName, Object bean) {
        beans.put(beanName, bean);
    }
}
