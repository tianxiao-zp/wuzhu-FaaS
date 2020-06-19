package com.tianxiao.faas.common.exception.container;

public class InvokerException extends RuntimeException {
    private static final long serialVersionUID = -1667147854600585999L;

    public InvokerException() {
    }

    public InvokerException(String message) {
        super(message);
    }

    public InvokerException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvokerException(Throwable cause) {
        super(cause);
    }

    public InvokerException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
