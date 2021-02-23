package com.tianxiao.faas.application.handler;

import com.tianxiao.faas.biz.domain.FaaSServiceDomain;
import com.tianxiao.faas.biz.domain.aggregate.FaaSAggregate;
import com.tianxiao.faas.biz.factory.FaaSAggregateFactory;
import com.tianxiao.faas.biz.infrastructure.repositories.FaaSServiceRepository;
import com.tianxiao.faas.common.enums.biz.FaaSServiceStatusEnum;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;
import java.util.List;

@Component
public class FaaSExecuteHandler {
    @Resource
    private FaaSServiceRepository faaSServiceRepository;
    @Resource
    private FaaSAggregateFactory faaSAggregateFactory;

    public Mono<Object> executeByServiceName(String name, FaaSServiceStatusEnum faaSServiceStatusEnum, List<Object> params){
        FaaSServiceDomain domain = faaSServiceRepository.get(name, faaSServiceStatusEnum);
        FaaSAggregate faaSAggregate = faaSAggregateFactory.build(domain);
        return Mono.justOrEmpty(faaSAggregate.execute(params));
    }
}
