package com.tianxiao.faas.biz.service;

import com.tianxiao.faas.biz.command.FaaSServiceSaveCommand;
import com.tianxiao.faas.biz.domain.FaaSServiceDomain;
import com.tianxiao.faas.biz.factory.FaaSServiceDomainFactory;
import com.tianxiao.faas.biz.infrastructure.repositories.FaaSServiceRepository;
import com.tianxiao.faas.common.enums.biz.FaaSServiceStatusEnum;
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

    @Transactional
    public boolean offlinePublish(Integer id) {
        FaaSServiceDomain faaSServiceDomain = faaSServiceRepository.get(id);
        faaSServiceDomain.offlinePublish();
        return faaSServiceRepository.save(faaSServiceDomain);
    }

    @Transactional
    public boolean prePublish(Integer id) {
        FaaSServiceDomain faaSServiceDomain = faaSServiceRepository.get(id);
        faaSServiceDomain.prePublish();
        FaaSServiceDomain current = faaSServiceRepository.get(faaSServiceDomain.getServiceName(), FaaSServiceStatusEnum.ONLINE);
        if (current != null) {
            
        }
        return faaSServiceRepository.save(faaSServiceDomain);
    }

    /**
     * 发布
     * 比如发布id为1
     * 当前如果线上没有服务，则只需要把当前的id=1的服务状态改为发布状态就ok了。
     * 如果线上已经存在服务了，则需要把线上的服务归档，并把id=1的服务状态改为发布状态
     * save方法里面已经是乐观更新了。
     * @param id
     * @return
     */
    @Transactional
    public boolean online(Integer id) {
        FaaSServiceDomain faaSServiceDomain = faaSServiceRepository.get(id);
        faaSServiceDomain.publish();
        FaaSServiceDomain current = faaSServiceRepository.get(faaSServiceDomain.getServiceName(), FaaSServiceStatusEnum.ONLINE);
        if (current != null) {
            current.archive();
            boolean save = faaSServiceRepository.save(current);
            if (!save) {
                return false;
            }
        }
        return faaSServiceRepository.save(faaSServiceDomain);
    }

    /**
     * 回滚
     * 原服务归档，目标服务发布
     * @param id
     * @param toId
     * @return
     */
    @Transactional
    public boolean rollback(Integer id, Integer toId) {
        FaaSServiceDomain from = faaSServiceRepository.get(id);
        FaaSServiceDomain to = faaSServiceRepository.get(toId);
        from.archive();
        to.publish();
        boolean save = faaSServiceRepository.save(from);
        boolean save1 = faaSServiceRepository.save(to);
        return save && save1;
    }
}
