package com.tianxiao.faas.runtime.DeadLoopCheck;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author jianmiao.xu
 * @date 2020/10/17
 */
public class Test {
    public static void main(String[] args)
            throws ClassNotFoundException, IllegalAccessException, InstantiationException, NoSuchMethodException,
                   InvocationTargetException {
        // 加载DealLoopExample，生成对应的class文件
        DealLoopExample dealLoopExample = new DealLoopExample();
        // 提前加载counter，防止在织入的时候出现问题
        LoopCounter loopCounter = new LoopCounter();

        // 加载对应的检测类，这里使用_test 后缀是因为之前已经加载过对应的检测类，如果使用同一个类名，回跳过自定义的类加载器
        // 由于class对象是由 class文件以及类加载器来唯一确定，因此在jvm会存在 "两个" 检测类的class对象
        ClassLoader classLoader = new DeadLoopCheckClassLoader();
        Class clazz = classLoader.loadClass("com.tianxiao.faas.runtime.DeadLoopCheck.DealLoopExample_test");
        Object demo = clazz.newInstance();
        Method testLoop = clazz.getMethod("deadLoop");
        testLoop.invoke(demo);

        //Method healthLoop = clazz.getMethod("healthLoop");
        //healthLoop.invoke(demo);
    }
}