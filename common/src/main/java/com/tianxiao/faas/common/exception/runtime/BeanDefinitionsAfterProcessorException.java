package com.tianxiao.faas.common.exception.runtime;

public class BeanDefinitionsAfterProcessorException extends Exception {
    private static final long serialVersionUID = -119324811768332640L;

    public BeanDefinitionsAfterProcessorException() {
    }

    public BeanDefinitionsAfterProcessorException(String message) {
        super(message);
    }

    public BeanDefinitionsAfterProcessorException(String message, Throwable cause) {
        super(message, cause);
    }

    public BeanDefinitionsAfterProcessorException(Throwable cause) {
        super(cause);
    }

    public BeanDefinitionsAfterProcessorException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
