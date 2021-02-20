package com.tianxiao.faas.biz.aspect.context;

import com.tianxiao.faas.biz.domain.FaaSServiceDomain;

import java.util.List;

public interface FaaSAspectContext {
    /**
     * 参数名
     * @return
     */
    List<Object> params();

    /**
     * 服务
     * @return
     */
    FaaSServiceDomain service();
}
