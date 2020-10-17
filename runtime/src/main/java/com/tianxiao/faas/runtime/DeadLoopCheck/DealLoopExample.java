package com.tianxiao.faas.runtime.DeadLoopCheck;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author jianmiao.xu
 * @date 2020/10/17
 */
public class DealLoopExample {

    public void deadLoop() {
        while(true) {
            System.out.println("dead-loop");
            try {
                Thread.sleep(100L);
            } catch (InterruptedException e) {
                System.out.println("thread is dead");
            }
        }
    }

    public void healthLoop() {
        for (int i = 0; i < 1000; i++) {
            if (i % 100 == 0) {
                System.out.println(i);
            }
        }
        System.out.println("health-loop-is-over");
    }


}