package com.tianxiao.faas.biz.infrastructure.handler;

import com.tianxiao.faas.biz.domain.FaaSServiceDomain;
import com.tianxiao.faas.biz.domain.event.PublishEvent;
import com.tianxiao.faas.biz.publisher.FaaSPublisher;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

import javax.annotation.Resource;

@Component
public class FaaSDomainEventHandler {
    @Resource
    private FaaSPublisher faaSPublisher;

    @TransactionalEventListener
    public void handlePublishEvent(PublishEvent publishEvent) {
        FaaSServiceDomain serviceDomain = publishEvent.getServiceDomain();
        faaSPublisher.publish(serviceDomain.getServiceName());
    }
}
