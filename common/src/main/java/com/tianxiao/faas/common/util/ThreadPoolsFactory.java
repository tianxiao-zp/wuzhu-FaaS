package com.tianxiao.faas.common.util;

import java.util.concurrent.ExecutorService;

public class ThreadPoolsFactory {

    private static ExecutorService messageHandlerPoll = NamedThreadPools.newFixedThreadPool(NamedThreadPools.ioPoolSize(), "io-handle-thread");

    public static ExecutorService getIoThreadPoll() {
        return messageHandlerPoll;
    }
}
