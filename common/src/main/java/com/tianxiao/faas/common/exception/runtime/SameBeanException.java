package com.tianxiao.faas.common.exception.runtime;

public class SameBeanException extends RuntimeException {
    private static final long serialVersionUID = -1849875823471460894L;

    public SameBeanException() {
    }

    public SameBeanException(String message) {
        super(message);
    }

    public SameBeanException(String message, Throwable cause) {
        super(message, cause);
    }

    public SameBeanException(Throwable cause) {
        super(cause);
    }

    public SameBeanException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
