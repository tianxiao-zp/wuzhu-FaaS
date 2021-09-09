package com.tianxiao.faas.runtime;

import java.io.Serializable;
import java.util.List;

/**
 * 执行器上下文
 * @author tianxiao
 */
public class ExecutorContext implements Serializable {
    private static final long serialVersionUID = 2342150565826329476L;

    /**
     * 代码
     */
    private String code;

    /**
     * 方法名
     * 可不填写
     */
    private String methodName;

    /**
     * 方法入参
     */
    private List<Object> params;

    /**
     * 是否是debug，即没有缓存
     */
    private boolean debug;

    /**
     * 超时时间，毫秒级
     * 默认值3秒
     */
    private long timeout;

    /**
     * 服务名称
     */
    private String serviceName;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public List<Object> getParams() {
        return params;
    }

    public void setParams(List<Object> params) {
        this.params = params;
    }

    public boolean getDebug() {
        return debug;
    }

    public void setDebug(boolean debug) {
        this.debug = debug;
    }

    public long getTimeout() {
        return timeout;
    }

    public void setTimeout(long timeout) {
        this.timeout = timeout;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }
}
