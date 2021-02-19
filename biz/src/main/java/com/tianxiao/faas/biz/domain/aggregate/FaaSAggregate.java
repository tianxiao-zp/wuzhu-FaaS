package com.tianxiao.faas.biz.domain.aggregate;

import com.tianxiao.faas.biz.aspect.FaaSAspect;
import com.tianxiao.faas.biz.aspect.context.FaaSAspectDefaultContext;
import com.tianxiao.faas.biz.domain.FaaSServiceDomain;
import com.tianxiao.faas.common.enums.biz.FaaSServiceLanguageEnum;
import com.tianxiao.faas.common.exception.biz.BizException;
import com.tianxiao.faas.common.exception.runtime.ExecuteException;
import com.tianxiao.faas.runtime.Executor;
import com.tianxiao.faas.runtime.ExecutorContext;
import com.tianxiao.faas.runtime.ExecutorFactory;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * 聚合根
 */
public class FaaSAggregate {
    private final static String DEFAULT_METHOD_NAME = "execute";
    /**
     * service 领域模型
     */
    private FaaSServiceDomain faaSServiceDomain;

    /**
     * faaS切面集合
     */
    private List<FaaSAspect> faaSAspectList;

    private ExecutorFactory executorFactory;

    public Object execute(List<Object> params) {
        if (faaSServiceDomain == null || StringUtils.isEmpty(faaSServiceDomain.getScript())) {
            throw new BizException("FaaS script is empty");
        }
        if (executorFactory == null) {
            throw new BizException("FaaS executor factory is null");
        }
        FaaSServiceLanguageEnum languageEnum = FaaSServiceLanguageEnum.get(faaSServiceDomain.getLanguage());
        if (languageEnum == null) {
            throw new BizException("not support the language");
        }
        Executor executor = executorFactory.getExecutor(languageEnum.getExecutorType());
        if (executor == null) {
            throw new BizException("not support the language");
        }
        if (CollectionUtils.isEmpty(faaSAspectList)) {
            return execute(params, executor);
        }
        for (FaaSAspect faaSAspect : faaSAspectList) {
            FaaSAspect.AspectObject before = faaSAspect.before(new FaaSAspectDefaultContext());

        }
        return null;
    }

    private Object execute(List<Object> params, Executor executor) {
        try {
            ExecutorContext executeContext = new ExecutorContext();
            executeContext.setCode(faaSServiceDomain.getScript());
            executeContext.setMethodName(DEFAULT_METHOD_NAME);
            executeContext.setParams(params);
            executeContext.setTimeout(faaSServiceDomain.getOverTime());
            return executor.execute(executeContext);
        } catch (ExecuteException e) {
            throw new BizException(e);
        }
    }
}
