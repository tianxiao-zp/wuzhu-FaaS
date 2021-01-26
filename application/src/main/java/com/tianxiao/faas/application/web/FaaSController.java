package com.tianxiao.faas.application.web;

import com.tianxiao.faas.application.handler.FaaSExecuteHandler;
import com.tianxiao.faas.biz.domain.FaaSServiceDomain;
import com.tianxiao.faas.common.enums.biz.FaaSServiceStatusEnum;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;

@RestController
public class FaaSController {
    @Resource
    private FaaSExecuteHandler faaSExecuteHandler;

    @GetMapping(value = "/get")
    public Mono<FaaSServiceDomain> findCityById(@Param("serviceName") String serviceName) {
        return faaSExecuteHandler.getByServiceName(serviceName, FaaSServiceStatusEnum.OFFLINE);
    }
}
