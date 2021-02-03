package com.tianxiao.faas.biz.aspect;

import com.tianxiao.faas.biz.aspect.context.FaaSAspectContext;

/**
 * FaaS切面设计
 */
public interface FaaSAspect {

    /**
     * 顺序
     * @return
     */
    int order();

    /**
     * 前置切面
     * @param context
     * @return
     */
    Object before(FaaSAspectContext context);

    /**
     * 后置切面
     * @param context
     * @return
     */
    Object after(FaaSAspectContext context);
}
