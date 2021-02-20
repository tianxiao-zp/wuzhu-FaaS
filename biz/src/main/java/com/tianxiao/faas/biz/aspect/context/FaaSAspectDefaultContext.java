package com.tianxiao.faas.biz.aspect.context;

import com.tianxiao.faas.biz.domain.FaaSServiceDomain;

import java.util.List;

public class FaaSAspectDefaultContext implements FaaSAspectContext {

    private List<Object> params;

    private FaaSServiceDomain faaSServiceDomain;

    public FaaSAspectDefaultContext() {
    }

    public FaaSAspectDefaultContext(List<Object> params, FaaSServiceDomain faaSServiceDomain) {
        this.params = params;
        this.faaSServiceDomain = faaSServiceDomain;
    }

    @Override
    public List<Object> params() {
        return this.params;
    }

    @Override
    public FaaSServiceDomain service() {
        return this.faaSServiceDomain;
    }
}
