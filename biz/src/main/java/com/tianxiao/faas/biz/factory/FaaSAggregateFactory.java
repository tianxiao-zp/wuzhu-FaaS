package com.tianxiao.faas.biz.factory;

import com.tianxiao.faas.biz.aspect.FaaSAspect;
import com.tianxiao.faas.biz.domain.FaaSServiceDomain;
import com.tianxiao.faas.biz.domain.aggregate.FaaSAggregate;
import com.tianxiao.faas.runtime.ExecutorFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class FaaSAggregateFactory {
    @Resource
    private ExecutorFactory executorFactory;
    @Resource
    private List<FaaSAspect> faaSAspectList;

    public FaaSAggregate build(FaaSServiceDomain domain) {
        if (domain == null) {
            return null;
        }
        List<FaaSAspect> faaSAspects = null;
        if (faaSAspectList != null) {
            faaSAspects = faaSAspectList.stream()
                    .sorted((o1, o2) -> o1.order() > o2.order() ? 1 : -1).collect(Collectors.toList());
        }
        return FaaSAggregate.Builder.builder()
                .executorFactory(executorFactory)
                .faaSAspectList(faaSAspects)
                .faaSServiceDomain(domain)
                .build();
    }
}
