package com.tianxiao.faas.runtime.context;

public final class FaaSContextHolder {
    private final static ThreadLocal<FaaSContext> contexts = new ThreadLocal<>();

    public static void put(FaaSContext context) {
        contexts.set(context);
    }

    public static void remove() {
        contexts.remove();
    }

    public static FaaSContext get() {
        return contexts.get();
    }
}
