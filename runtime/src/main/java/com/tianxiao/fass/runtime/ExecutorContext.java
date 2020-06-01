package com.tianxiao.fass.runtime;

import java.io.Serializable;

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
    private Object params;

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

    public Object getParams() {
        return params;
    }

    public void setParams(Object params) {
        this.params = params;
    }
}
