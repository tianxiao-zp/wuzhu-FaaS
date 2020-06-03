package com.tianxiao.fass.runtime;

import com.tianxiao.fass.common.exception.runtime.CompileException;
import com.tianxiao.fass.common.exception.runtime.ExecuteException;
import com.tianxiao.fass.runtime.processor.manager.BeanProcessorManager;

/**
 * 代码执行器
 * @author tianxiao
 */

public interface Executor {

    /**
     * 代码编译
     * @param code
     */
    Object compile(String code) throws CompileException;

    /**
     * 代码执行
     * @param executeContext
     * @return
     */
    Object execute(ExecutorContext executeContext) throws ExecuteException;

    void processManager(BeanProcessorManager beanProcessorManager);

    /**
     * 获取执行器名称
     * 根据该名称
     * @return
     */
    String type();
}
