package com.tianxiao.faas.biz.infrastructure.publisher;


import com.tianxiao.faas.biz.domain.event.DomainEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class SpringEventPublisher implements EventPublisher {
    @Autowired
    private ApplicationContext applicationContext;
    @Override
    public void publishEvent(DomainEvent domainEvent) {
        applicationContext.publishEvent(domainEvent);
    }
}
