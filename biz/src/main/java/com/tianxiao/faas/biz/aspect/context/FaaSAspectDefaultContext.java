package com.tianxiao.faas.biz.aspect.context;

import com.tianxiao.faas.biz.domain.FaaSServiceDomain;

import java.util.Map;

public class FaaSAspectDefaultContext implements FaaSAspectContext {

    private Map<String, Object> params;

    private String serviceName;

    private FaaSServiceDomain faaSServiceDomain;

    @Override
    public Map<String, Object> params() {
        return this.params;
    }

    @Override
    public String serviceName() {
        return this.serviceName;
    }

    @Override
    public FaaSServiceDomain service() {
        return this.faaSServiceDomain;
    }
}
