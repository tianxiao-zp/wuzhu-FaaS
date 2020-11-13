package com.tianxiao.faas.biz.domain.event;

import com.tianxiao.faas.biz.domain.FaaSServiceDomain;

public class PublishEvent extends DomainEvent {
    private static final long serialVersionUID = -8981012085416105475L;

    /**
     * 服务领域模型
     */
    private FaaSServiceDomain serviceDomain;

    public PublishEvent(FaaSServiceDomain serviceDomain) {
        this.serviceDomain = serviceDomain;
    }

    public PublishEvent() {
    }

    public FaaSServiceDomain getServiceDomain() {
        return serviceDomain;
    }

    public void setServiceDomain(FaaSServiceDomain serviceDomain) {
        this.serviceDomain = serviceDomain;
    }
}
