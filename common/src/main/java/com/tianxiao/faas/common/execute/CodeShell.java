package com.tianxiao.faas.common.execute;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicInteger;

public class CodeShell {
    private static final Logger logger = LoggerFactory.getLogger(CodeShell.class);
    private ExecutorService executorService;

    private int corePoolSize;

    private int maxPoolSize;

    private int queueSize;

    public void init() {
        corePoolSize = corePoolSize <= 0 ? 20 : corePoolSize;
        maxPoolSize = maxPoolSize <= 0 ? 100 : maxPoolSize;
        queueSize = queueSize <= 0 ? 50 : queueSize;
        executorService = new ThreadPoolExecutor(corePoolSize, maxPoolSize,
                0L, TimeUnit.MILLISECONDS,
                new ArrayBlockingQueue<>(queueSize),
                new CodeShellThreadFactory());
    }

    public void destroy() {
        executorService.shutdown();
    }

    public List<Object> invoke(DataSource dataSource) {
        return invoke(0, dataSource);
    }

    public List<Object> invoke(int timeout, DataSource dataSource) {
        if (dataSource == null) {
            return null;
        }
        List<Caller> callers = dataSource.getCallers();
        if (callers == null || callers.size() <= 0) {
            return null;
        }
        List<Object> results = new ArrayList<>();
        List<Future<Object>> futures = new ArrayList<>();
        for (Caller caller : callers) {
            Future<Object> submit = executorService.submit(() -> caller.call());
            futures.add(submit);
        }
        for (Future<Object> future : futures) {
            try {
                if (timeout <= 0) {
                    results.add(future.get());
                } else {
                    results.add(future.get(timeout, TimeUnit.SECONDS));
                }
            } catch (InterruptedException e) {
                results.add(null);
                logger.error("code-shell-invoker-error", e);
            } catch (ExecutionException e) {
                results.add(null);
                logger.error("code-shell-invoker-error", e);
            } catch (TimeoutException e) {
                results.add(null);
                logger.error("code-shell-invoker-error", e);
            }
        }
        return results;
    }

    public static class DataSource {
        private List<Caller> callers;

        public static DataSource builder() {
            return new DataSource();
        }

        public DataSource add(Caller caller) {
            if (callers == null) {
                callers = new ArrayList<>();
            }
            callers.add(caller);
            return this;
        }

        public List<Caller> getCallers() {
            return callers;
        }
    }

    public interface Caller {
        Object call();
    }

    static class CodeShellThreadFactory implements ThreadFactory {
        protected static final AtomicInteger POOL_COUNTER = new AtomicInteger(1);
        protected final AtomicInteger mThreadCounter;
        protected final String mPrefix;
        protected final boolean mDaemon;
        protected final ThreadGroup mGroup;

        public CodeShellThreadFactory() {
            this("CodeShell-pool-" + POOL_COUNTER.getAndIncrement(), false);
        }

        public CodeShellThreadFactory(String prefix, boolean daemon) {
            this.mThreadCounter = new AtomicInteger(1);
            this.mPrefix = prefix + "-thread-";
            this.mDaemon = daemon;
            SecurityManager s = System.getSecurityManager();
            this.mGroup = s == null ? Thread.currentThread().getThreadGroup() : s.getThreadGroup();
        }
        @Override
        public Thread newThread(Runnable runnable) {
            String name = this.mPrefix + this.mThreadCounter.getAndIncrement();
            Thread ret = new Thread(this.mGroup, runnable, name, 0L);
            ret.setDaemon(this.mDaemon);
            return ret;
        }
    }
}
