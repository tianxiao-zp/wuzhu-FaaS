package com.tianxiao.faas.biz.infrastructure.handler;

import com.tianxiao.faas.biz.domain.FaaSServiceDomain;
import com.tianxiao.faas.biz.domain.event.PublishEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
public class FaaSDomainEventHandler {


    @TransactionalEventListener
    public void handlePublishEvent(PublishEvent publishEvent) {
        FaaSServiceDomain serviceDomain = publishEvent.getServiceDomain();

    }
}
