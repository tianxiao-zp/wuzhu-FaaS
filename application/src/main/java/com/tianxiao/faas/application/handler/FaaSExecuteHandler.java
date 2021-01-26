package com.tianxiao.faas.application.handler;

import com.tianxiao.faas.biz.domain.FaaSServiceDomain;
import com.tianxiao.faas.biz.infrastructure.repositories.FaaSServiceRepository;
import com.tianxiao.faas.common.enums.biz.FaaSServiceStatusEnum;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;

@Component
public class FaaSExecuteHandler {
    @Resource
    private FaaSServiceRepository faaSServiceRepository;

    public Mono<FaaSServiceDomain> getByServiceName(String name, FaaSServiceStatusEnum faaSServiceStatusEnum){
        FaaSServiceDomain domain = faaSServiceRepository.get(name, faaSServiceStatusEnum);
        return Mono.justOrEmpty(domain);
    }
}
