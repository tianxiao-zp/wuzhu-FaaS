package com.tianxiao.faas.common.util;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * tianxiao
 */
public class NamedThreadPools {

    public static ExecutorService newFixedThreadPool(int nThreads, String name) {
        return newFixedThreadPool(nThreads, new NamedThreadFactory(name));
    }


    public static ExecutorService newFixedThreadPool(int nThreads, ThreadFactory threadFactory) {
        return new ThreadPoolExecutor(nThreads, nThreads,
                0L, TimeUnit.MILLISECONDS,
                new ArrayBlockingQueue<>(150),
                threadFactory);
    }


    public static ExecutorService newCachedThreadPool(String name) {
        return newCachedThreadPool(new NamedThreadFactory(name));
    }


    public static ExecutorService newCachedThreadPool(ThreadFactory threadFactory) {
        return new ThreadPoolExecutor(0, Integer.MAX_VALUE,
                60L, TimeUnit.SECONDS,
                new SynchronousQueue<Runnable>(),
                threadFactory);
    }


    public static ScheduledExecutorService newScheduledThreadPool(int corePoolSize, String name) {
        return newScheduledThreadPool(corePoolSize, new NamedThreadFactory(name));
    }


    public static ScheduledExecutorService newScheduledThreadPool(
            int corePoolSize, ThreadFactory threadFactory) {
        return new ScheduledThreadPoolExecutor(corePoolSize, threadFactory);
    }

    /**
     * Each tasks blocks 90% of the time, and works only 10% of its
     *  lifetime. That is, I/O intensive pool
     * @return io intensive Thread pool size
     */
    public static int ioPoolSize() {
        double blockingCoefficient = 0.9;
        return poolSize(blockingCoefficient);
    }

    /**
     *
     * Number of threads = Number of Available Cores / (1 - Blocking
     * Coefficient) where the blocking coefficient is between 0 and 1.
     *
     * A computation-intensive task has a blocking coefficient of 0, whereas an
     * IO-intensive task has a value close to 1,
     * so we don't have to worry about the value reaching 1.
     *  @param blockingCoefficient the coefficient
     *  @return Thread pool size
     */
    public static int poolSize(double blockingCoefficient) {
        int numberOfCores = Runtime.getRuntime().availableProcessors();
        int poolSize = (int) (numberOfCores / (1 - blockingCoefficient));
        return poolSize;
    }
}
