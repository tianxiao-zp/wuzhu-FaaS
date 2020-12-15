package com.tianxiao.faas.biz.factory;

import com.tianxiao.faas.biz.command.FaaSServiceSaveCommand;
import com.tianxiao.faas.biz.domain.FaaSServiceDomain;
import com.tianxiao.faas.biz.infrastructure.publisher.EventPublisher;
import com.tianxiao.faas.mapper.model.FaaSServiceModel;
import com.tianxiao.faas.runtime.ExecutorFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;

@Component
public class FaaSServiceDomainFactory {
    @Resource
    private EventPublisher eventPublisher;
    @Resource
    private ExecutorFactory executorFactory;

    public FaaSServiceDomain build(FaaSServiceSaveCommand command) {
        if (command == null) {
            return null;
        }
        return FaaSServiceDomain.Builder.builder()
                .serviceName(command.getServiceName())
                .serviceDesc(command.getServiceDesc())
                .script(command.getScript())
                .overTime(command.getOverTime())
                .maxQps(command.getMaxQps())
                .modifier(command.getModifier())
                .language(command.getLanguage())
                .group(command.getGroup())
                .id(command.getId())
                .executorFactory(executorFactory)
                .eventPublisher(eventPublisher)
                .cacheTime(command.getCacheTime())
                .build();
    }

    public FaaSServiceDomain build(FaaSServiceModel faaSServiceModel) {
        if (faaSServiceModel == null) {
            return null;
        }
        return FaaSServiceDomain.Builder.builder().cacheTime(faaSServiceModel.getCacheTime())
                .eventPublisher(eventPublisher)
                .executorFactory(executorFactory)
                .group(faaSServiceModel.getGroup())
                .id(faaSServiceModel.getId())
                .language(faaSServiceModel.getLanguage())
                .maxQps(faaSServiceModel.getMaxQps())
                .modifier(faaSServiceModel.getModifier())
                .overTime(faaSServiceModel.getOverTime())
                .script(faaSServiceModel.getScript())
                .serviceDesc(faaSServiceModel.getServiceDesc())
                .serviceName(faaSServiceModel.getServiceName())
                .status(faaSServiceModel.getStatus())
                .version(faaSServiceModel.getVersion())
                .build();
    }

    public FaaSServiceModel build(FaaSServiceDomain faaSServiceDomain) {
        FaaSServiceModel faaSServiceModel = new FaaSServiceModel();
        faaSServiceModel.setCacheTime(faaSServiceDomain.getCacheTime());
        faaSServiceModel.setCreateTime(new Date());
        faaSServiceModel.setGroup(faaSServiceDomain.getGroup());
        faaSServiceModel.setIsDeleted(0);
        faaSServiceModel.setLanguage(faaSServiceDomain.getLanguage());
        faaSServiceModel.setMaxQps(faaSServiceDomain.getMaxQps());
        faaSServiceModel.setModifier(faaSServiceDomain.getModifier());
        faaSServiceModel.setOverTime(faaSServiceDomain.getOverTime());
        faaSServiceModel.setScript(faaSServiceDomain.getScript());
        faaSServiceModel.setServiceDesc(faaSServiceDomain.getServiceDesc());
        faaSServiceModel.setServiceName(faaSServiceDomain.getServiceName());
        faaSServiceModel.setStatus(faaSServiceDomain.getStatus());
        return faaSServiceModel;
    }
}
