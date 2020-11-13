package com.tianxiao.faas.biz.domain;

import com.tianxiao.faas.biz.domain.event.PublishEvent;
import com.tianxiao.faas.biz.infrastructure.publisher.EventPublisher;
import com.tianxiao.faas.common.enums.biz.FaaSServiceLanguageEnum;
import com.tianxiao.faas.common.enums.biz.FaaSServiceStatusEnum;
import com.tianxiao.faas.common.exception.ParamAccessException;
import com.tianxiao.faas.common.exception.biz.BizException;
import com.tianxiao.faas.common.exception.runtime.CompileException;
import com.tianxiao.faas.common.util.StringUtils;
import com.tianxiao.faas.runtime.Executor;
import com.tianxiao.faas.runtime.ExecutorFactory;

import java.io.Serializable;

public class FaaSServiceDomain implements Serializable {

    private static final long serialVersionUID = -5740458254822011802L;

    private Integer id;

    private String serviceName;

    private String serviceDesc;

    private int overTime;

    private int maxQps;

    private String script;

    private int language;

    private String modifier;

    private String group;

    private int status;

    private int cacheTime;

    private int version;

    private ExecutorFactory executorFactory;

    private EventPublisher eventPublisher;

    public boolean save() {
        check();
        // 如果当前服务已经发布线上，则复制一份创建一份新的
        if (status == FaaSServiceStatusEnum.ONLINE.getStatus()) {
            this.id = null;
        }
        return true;
    }

    /**
     * 线下发布
     * @return
     */
    public boolean offlinePublish() {
        check();
        publishCheck();
        this.status = FaaSServiceStatusEnum.OFFLINE.getStatus();
        return true;
    }

    /**
     * 预发发布
     * @return
     */
    public boolean prePublish() {
        check();
        publishCheck();
        this.status = FaaSServiceStatusEnum.PRE.getStatus();
        return true;
    }

    /**
     * 线上发布
     * @return
     */
    public boolean publish() {
        check();
        publishCheck();
        this.status = FaaSServiceStatusEnum.ONLINE.getStatus();
        eventPublisher.publishEvent(new PublishEvent(this));
        return true;
    }

    public Integer getId() {
        return id;
    }

    public String getServiceName() {
        return serviceName;
    }

    public String getServiceDesc() {
        return serviceDesc;
    }

    public int getOverTime() {
        return overTime;
    }

    public int getMaxQps() {
        return maxQps;
    }

    public String getScript() {
        return script;
    }

    public int getLanguage() {
        return language;
    }

    public String getModifier() {
        return modifier;
    }

    public String getGroup() {
        return group;
    }

    public int getStatus() {
        return status;
    }

    public int getCacheTime() {
        return cacheTime;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public void setServiceDesc(String serviceDesc) {
        this.serviceDesc = serviceDesc;
    }

    public void setOverTime(int overTime) {
        this.overTime = overTime;
    }

    public void setMaxQps(int maxQps) {
        this.maxQps = maxQps;
    }

    public void setScript(String script) {
        this.script = script;
    }

    public void setLanguage(int language) {
        this.language = language;
    }

    public void setModifier(String modifier) {
        this.modifier = modifier;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setCacheTime(int cacheTime) {
        this.cacheTime = cacheTime;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    /**
     * 发布检查
     */
    private void publishCheck() {
        if (status == FaaSServiceStatusEnum.ONLINE.getStatus()) {
            throw new BizException("该状态下不能修改/发布");
        }
        if (executorFactory == null) {
            throw new ParamAccessException("执行器工厂为空，请检查FaaS领域模型构造是否异常");
        }
        FaaSServiceLanguageEnum languageEnum = FaaSServiceLanguageEnum.get(language);
        Executor executor = executorFactory.getExecutor(languageEnum.getExecutorType());
        try {
            executor.compile(script, true);
        } catch (CompileException e) {
            throw new BizException(e);
        }
    }

    /**
     * 参数检查
     */
    private void check() {
        if (StringUtils.isEmpty(serviceDesc)) {
            throw new BizException("服务描述不能为空");
        }
        if (StringUtils.isEmpty(serviceName)) {
            throw new BizException("服务名称不能为空");
        }
        if (StringUtils.isEmpty(script)) {
            throw new BizException("脚本内容不能为空");
        }
        if (StringUtils.isEmpty(modifier)) {
            throw new BizException("服务描述不能为空");
        }
        FaaSServiceLanguageEnum languageEnum = FaaSServiceLanguageEnum.get(language);
        if (languageEnum == null) {
            throw new BizException("暂不支持该语言");
        }
    }

    public static final class Builder {
        private Integer id;
        private String serviceName;
        private String serviceDesc;
        private int overTime;
        private int maxQps;
        private String script;
        private int language;
        private String modifier;
        private String group;
        private int status;
        private int cacheTime;
        private int version;
        private ExecutorFactory executorFactory;
        private EventPublisher eventPublisher;

        private Builder() {
        }

        public static Builder builder() {
            return new Builder();
        }

        public Builder id(Integer id) {
            this.id = id;
            return this;
        }

        public Builder serviceName(String serviceName) {
            this.serviceName = serviceName;
            return this;
        }

        public Builder serviceDesc(String serviceDesc) {
            this.serviceDesc = serviceDesc;
            return this;
        }

        public Builder overTime(int overTime) {
            this.overTime = overTime;
            return this;
        }

        public Builder maxQps(int maxQps) {
            this.maxQps = maxQps;
            return this;
        }

        public Builder script(String script) {
            this.script = script;
            return this;
        }

        public Builder language(int language) {
            this.language = language;
            return this;
        }

        public Builder modifier(String modifier) {
            this.modifier = modifier;
            return this;
        }

        public Builder group(String group) {
            this.group = group;
            return this;
        }

        public Builder status(int status) {
            this.status = status;
            return this;
        }

        public Builder cacheTime(int cacheTime) {
            this.cacheTime = cacheTime;
            return this;
        }

        public Builder version(int version) {
            this.version = version;
            return this;
        }

        public Builder executorFactory(ExecutorFactory executorFactory) {
            this.executorFactory = executorFactory;
            return this;
        }

        public Builder eventPublisher(EventPublisher eventPublisher) {
            this.eventPublisher = eventPublisher;
            return this;
        }

        public FaaSServiceDomain build() {
            FaaSServiceDomain faaSServiceDomain = new FaaSServiceDomain();
            faaSServiceDomain.setId(id);
            faaSServiceDomain.setServiceName(serviceName);
            faaSServiceDomain.setServiceDesc(serviceDesc);
            faaSServiceDomain.setOverTime(overTime);
            faaSServiceDomain.setMaxQps(maxQps);
            faaSServiceDomain.setScript(script);
            faaSServiceDomain.setLanguage(language);
            faaSServiceDomain.setModifier(modifier);
            faaSServiceDomain.setGroup(group);
            faaSServiceDomain.setStatus(status);
            faaSServiceDomain.setCacheTime(cacheTime);
            faaSServiceDomain.setVersion(version);
            faaSServiceDomain.eventPublisher = this.eventPublisher;
            faaSServiceDomain.executorFactory = this.executorFactory;
            return faaSServiceDomain;
        }
    }
}
