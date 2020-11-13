package com.tianxiao.faas.biz.infrastructure.publisher;


import com.tianxiao.faas.biz.domain.event.DomainEvent;

public interface EventPublisher {

    void publishEvent(DomainEvent domainEvent);
}
