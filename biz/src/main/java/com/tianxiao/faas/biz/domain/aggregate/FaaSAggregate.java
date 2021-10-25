package com.tianxiao.faas.biz.domain.aggregate;

import com.tianxiao.faas.biz.aspect.FaaSAspect;
import com.tianxiao.faas.biz.aspect.context.FaaSAspectDefaultContext;
import com.tianxiao.faas.biz.domain.FaaSServiceDomain;
import com.tianxiao.faas.common.enums.biz.FaaSServiceLanguageEnum;
import com.tianxiao.faas.common.enums.biz.FaaSServiceStatusEnum;
import com.tianxiao.faas.common.exception.biz.BizException;
import com.tianxiao.faas.common.exception.runtime.CompileException;
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
        FaaSAspectDefaultContext context = new FaaSAspectDefaultContext(params, faaSServiceDomain);
        for (FaaSAspect faaSAspect : faaSAspectList) {
            FaaSAspect.AspectObject before = faaSAspect.before(context);
            if (before == null) {
                continue;
            }
            if (before.isReturn()) {
                return before.getObject();
            }
            if (before.getThrowable() != null) {
                throw new BizException(before.getThrowable());
            }
        }
        Object result = execute(params, executor);
        for (int i = faaSAspectList.size() - 1; i >= 0; i--) {
            FaaSAspect faaSAspect = faaSAspectList.get(i);
            FaaSAspect.AspectObject after = faaSAspect.after(context, result);
            if (after == null) {
                continue;
            }
            if (after.isReturn()) {
                return after.getObject();
            }
            if (after.getThrowable() != null) {
                throw new BizException(after.getThrowable());
            }
            if (after.getObject() != null) {
                result = after.getObject();
            }
        }
        return result;
    }

    public void refresh() {
        FaaSServiceLanguageEnum languageEnum = FaaSServiceLanguageEnum.get(faaSServiceDomain.getLanguage());
        if (languageEnum == null) {
            throw new BizException("not support the language");
        }
        Executor executor = executorFactory.getExecutor(languageEnum.getExecutorType());
        ExecutorContext executeContext = new ExecutorContext();
        executeContext.setCode(faaSServiceDomain.getScript());
        executeContext.setDebug(true);
        executeContext.setServiceName(faaSServiceDomain.getServiceName());
        try {
            executor.compile(executeContext);
        } catch (CompileException e) {
            throw new BizException(e);
        }
    }

    private Object execute(List<Object> params, Executor executor) {
        try {
            ExecutorContext executeContext = new ExecutorContext();
            executeContext.setCode(faaSServiceDomain.getScript());
            executeContext.setMethodName(DEFAULT_METHOD_NAME);
            executeContext.setParams(params);
            int status = faaSServiceDomain.getStatus();
            executeContext.setServiceName(this.faaSServiceDomain.getServiceName());
            if (status != FaaSServiceStatusEnum.ONLINE.getStatus()) {
                executeContext.setDebug(true);
            }
            executeContext.setTimeout(faaSServiceDomain.getOverTime());
            return executor.execute(executeContext);
        } catch (ExecuteException e) {
            throw new BizException(e);
        }
    }



    public FaaSServiceDomain getFaaSServiceDomain() {
        return faaSServiceDomain;
    }

    public List<FaaSAspect> getFaaSAspectList() {
        return faaSAspectList;
    }

    public ExecutorFactory getExecutorFactory() {
        return executorFactory;
    }

    public static final class Builder {
        private FaaSServiceDomain faaSServiceDomain;
        private List<FaaSAspect> faaSAspectList;
        private ExecutorFactory executorFactory;

        private Builder() {
        }

        public static Builder builder() {
            return new Builder();
        }

        public Builder faaSServiceDomain(FaaSServiceDomain faaSServiceDomain) {
            this.faaSServiceDomain = faaSServiceDomain;
            return this;
        }

        public Builder faaSAspectList(List<FaaSAspect> faaSAspectList) {
            this.faaSAspectList = faaSAspectList;
            return this;
        }

        public Builder executorFactory(ExecutorFactory executorFactory) {
            this.executorFactory = executorFactory;
            return this;
        }

        public FaaSAggregate build() {
            FaaSAggregate faaSAggregate = new FaaSAggregate();
            faaSAggregate.executorFactory = this.executorFactory;
            faaSAggregate.faaSServiceDomain = this.faaSServiceDomain;
            faaSAggregate.faaSAspectList = this.faaSAspectList;
            return faaSAggregate;
        }
    }
}
