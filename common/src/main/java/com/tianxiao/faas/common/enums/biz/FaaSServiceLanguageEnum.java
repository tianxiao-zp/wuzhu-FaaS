package com.tianxiao.faas.common.enums.biz;

import com.tianxiao.faas.common.enums.ExecutorType;

public enum FaaSServiceLanguageEnum {
    JAVA(0, ExecutorType.GROOVY),
    GROOVY(1, ExecutorType.GROOVY);

    private int language;

    private ExecutorType executorType;

    FaaSServiceLanguageEnum(int language, ExecutorType executorType) {
        this.language = language;
        this.executorType = executorType;
    }

    public int getLanguage() {
        return language;
    }

    public ExecutorType getExecutorType() {
        return executorType;
    }

    public static FaaSServiceLanguageEnum get(int language) {
        for (FaaSServiceLanguageEnum en : values()) {
            if (language == en.getLanguage()) {
                return en;
            }
        }
        return null;
    }
}
