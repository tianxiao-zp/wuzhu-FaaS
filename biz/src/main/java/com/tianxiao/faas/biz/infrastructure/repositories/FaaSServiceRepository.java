package com.tianxiao.faas.biz.infrastructure.repositories;

import com.tianxiao.faas.biz.domain.FaaSServiceDomain;
import com.tianxiao.faas.biz.factory.FaaSServiceDomainFactory;
import com.tianxiao.faas.common.enums.biz.FaaSServiceStatusEnum;
import com.tianxiao.faas.common.exception.ParamAccessException;
import com.tianxiao.faas.mapper.dao.FaaSServiceModelMapper;
import com.tianxiao.faas.mapper.model.FaaSServiceModel;
import com.tianxiao.faas.mapper.model.FaaSServiceModelExample;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Repository
public class FaaSServiceRepository {

    @Resource
    private FaaSServiceModelMapper faaSServiceModelMapper;
    @Resource
    private FaaSServiceDomainFactory faaSServiceDomainFactory;

    public boolean save(FaaSServiceDomain faaSServiceDomain) {
        if (faaSServiceDomain == null) {
            throw new ParamAccessException("服务模型不能为空");
        }
        FaaSServiceModel faaSServiceModel = faaSServiceDomainFactory.build(faaSServiceDomain);

        Integer id = faaSServiceDomain.getId();
        int line = 0;
        if (id == null || id <= 0) {
            faaSServiceModel.setCreator(faaSServiceDomain.getModifier());
            line =faaSServiceModelMapper.insertSelective(faaSServiceModel);
        } else {
            int version = faaSServiceDomain.getVersion();
            faaSServiceModel.setId(id);
            faaSServiceModel.setEditTime(new Date());
            faaSServiceModel.setVersion(version + 1);
            faaSServiceDomain.setVersion(faaSServiceModel.getVersion());
            FaaSServiceModelExample faaSServiceModelExample = new FaaSServiceModelExample();
            faaSServiceModelExample.createCriteria().andIdEqualTo(id).andVersionEqualTo(version);
            line = faaSServiceModelMapper.updateByExampleSelective(faaSServiceModel, faaSServiceModelExample);
        }
        return line > 0;
    }

    public FaaSServiceDomain get(String serviceName, FaaSServiceStatusEnum faaSServiceStatusEnum) {
        FaaSServiceModelExample example = new FaaSServiceModelExample();
        example.createCriteria().andServiceNameEqualTo(serviceName).andStatusEqualTo(faaSServiceStatusEnum.getStatus());
        List<FaaSServiceModel> faaSServiceModels = faaSServiceModelMapper.selectByExample(example);
        if (CollectionUtils.isEmpty(faaSServiceModels)) {
            return null;
        }
        return faaSServiceDomainFactory.build(faaSServiceModels.get(0));
    }

    public FaaSServiceDomain get(Integer id) {
        FaaSServiceModelExample example = new FaaSServiceModelExample();
        example.createCriteria().andIdEqualTo(id);
        List<FaaSServiceModel> faaSServiceModels = faaSServiceModelMapper.selectByExample(example);
        if (CollectionUtils.isEmpty(faaSServiceModels)) {
            return null;
        }
        return faaSServiceDomainFactory.build(faaSServiceModels.get(0));
    }
}
