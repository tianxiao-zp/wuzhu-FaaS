package com.tianxiao.faas.application.web;

import com.google.common.collect.Lists;
import com.tianxiao.faas.application.handler.FaaSExecuteHandler;
import com.tianxiao.faas.common.enums.biz.FaaSServiceStatusEnum;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
public class FaaSController {
    @Resource
    private FaaSExecuteHandler faaSExecuteHandler;

    @GetMapping(value = "/executeOnOfferLine")
    public Mono<Object> executeOnOfferLine(@Param("serviceName") String serviceName) {
        HttpServletRequest request = ((ServletRequestAttributes) (RequestContextHolder.currentRequestAttributes())).getRequest();
        Map<String, String[]> parameterMap = request.getParameterMap();
        return faaSExecuteHandler.executeByServiceName(serviceName, FaaSServiceStatusEnum.OFFLINE, Lists.newArrayList(parameterMap));
    }

    @GetMapping(value = "/executeOnLine")
    public Mono<Object> executeOnLine(@Param("serviceName") String serviceName) {
        HttpServletRequest request = ((ServletRequestAttributes) (RequestContextHolder.currentRequestAttributes())).getRequest();
        Map<String, String[]> parameterMap = request.getParameterMap();
        return faaSExecuteHandler.executeByServiceName(serviceName, FaaSServiceStatusEnum.ONLINE, Lists.newArrayList(parameterMap));
    }

    @GetMapping(value = "/executeOnPre")
    public Mono<Object> executeOnPre(@Param("serviceName") String serviceName) {
        HttpServletRequest request = ((ServletRequestAttributes) (RequestContextHolder.currentRequestAttributes())).getRequest();
        Map<String, String[]> parameterMap = request.getParameterMap();
        return faaSExecuteHandler.executeByServiceName(serviceName, FaaSServiceStatusEnum.PRE, Lists.newArrayList(parameterMap));
    }
}
