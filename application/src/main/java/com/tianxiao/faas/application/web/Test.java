package com.tianxiao.faas.application.web;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class Test implements Callable {
    private int a,b;

    public Test(int a, int b) {
        this.a = a;
        this.b = b;
    }

    @Override
    public Integer call() throws Exception {
        Integer result = a + b;
        while (result > 0) {
            Thread.sleep(1000L);
            System.out.println(result);
        }
        return result;
    }

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        //JDK目前为止返回的都是FutureTask的实例
        Future<Integer> future = executor.submit(new Test(1, 2));
        try {
            Integer result = future.get(5, TimeUnit.SECONDS);// 只有当future的状态是已完成时(future.isDone() = true),get()方法才会返回
            System.out.println(result);
        } catch (TimeoutException e) {
            System.out.println(e);
        }
    }
}
