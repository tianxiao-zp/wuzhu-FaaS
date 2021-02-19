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
}
