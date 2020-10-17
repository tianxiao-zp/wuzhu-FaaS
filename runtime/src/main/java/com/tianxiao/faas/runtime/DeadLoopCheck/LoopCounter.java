package com.tianxiao.faas.runtime.DeadLoopCheck;

import java.util.HashMap;
import java.util.Map;

/**
 * @author jianmiao.xu
 * @date 2020/10/17
 */
public class LoopCounter {

    private static final ThreadLocal<ThreadLocalCounter> threadLocal = new ThreadLocal<>();

    // 单循环最大循环次数
    public static final long singleLoopMax = 10;

    /**
     * 检测循环体执行次数，如果执行次数超出设定的阈值，则抛出异常
     */
    public static void incr(String label) {

        // 判断线程执行是否被中止
        if (Thread.interrupted()) {
            throw new RuntimeException(new InterruptedException("Thread execution has been interrupted!"));
        }

        // 检查循环执行次数是否超出上限
        ThreadLocalCounter threadLocalCounter = threadLocal.get();
        if (threadLocalCounter != null) {
            if (threadLocalCounter.incrLabel(label) > singleLoopMax) {
                throw new RuntimeException(new Exception("Loop counter exceed limit"));
            }
        } else {
            threadLocal.set(new ThreadLocalCounter());
        }
    }

    // 标记类
    private static final class ThreadLocalCounter {

        // 记录每个label计数的次数
        private Map<String, Long> labelCounter = new HashMap<>();

        // 计数器+1
        long incrLabel(String label) {
            Long counter = labelCounter.get(label);
            if (counter == null) {
                labelCounter.put(label, 1L);
                return 1;
            }
            labelCounter.put(label, ++counter);
            return counter;
        }

        // 计数器清零
        void clearLabel() {
            this.labelCounter.clear();
        }
    }
}