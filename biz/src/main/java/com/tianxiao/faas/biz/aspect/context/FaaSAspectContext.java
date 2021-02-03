package com.tianxiao.faas.biz.aspect.context;

import com.tianxiao.faas.biz.domain.FaaSServiceDomain;

import java.util.Map;

public interface FaaSAspectContext {
    /**
     * 参数名
     * @return
     */
    Map<String, Object> params();

    /**
     * 服务名
     * @return
     */
    String serviceName();

    /**
     * 服务
     * @return
     */
    FaaSServiceDomain service();

    /**
     * 是否返回
     * 如果前置切面为里将该字段设置成true，则不再执行服务、后置切面及后续的其他切面
     * @return
     */
    boolean isReturn();

    /**
     * 设置return值
     */
    void setIsReturn();
}
