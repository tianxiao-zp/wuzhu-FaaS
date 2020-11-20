package com.tianxiao.faas.biz.service;

import com.tianxiao.faas.biz.command.FaaSServiceSaveCommand;
import com.tianxiao.faas.biz.domain.FaaSServiceDomain;
import com.tianxiao.faas.biz.factory.FaaSServiceDomainFactory;
import com.tianxiao.faas.biz.infrastructure.repositories.FaaSServiceRepository;
import com.tianxiao.faas.common.exception.ParamAccessException;
import com.tianxiao.faas.common.exception.biz.BizException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
public class FaaSServiceCommandService {
    @Resource
    private FaaSServiceRepository faaSServiceRepository;
    @Resource
    private FaaSServiceDomainFactory faaSServiceDomainFactory;

    @Transactional
    public boolean save(FaaSServiceSaveCommand command) {
        if (command == null) {
            throw new ParamAccessException("参数不能为空");
        }
        FaaSServiceDomain faaSServiceDomain = faaSServiceDomainFactory.build(command);
        if (faaSServiceDomain == null) {
            throw new BizException("系统异常");
        }
        boolean save = faaSServiceDomain.save();
        faaSServiceRepository.save(faaSServiceDomain);
        return save;
    }
}
