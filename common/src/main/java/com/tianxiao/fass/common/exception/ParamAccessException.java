package com.tianxiao.fass.common.exception;

/**
 * 入参异常
 */
public class ParamAccessException extends RuntimeException {
    private static final long serialVersionUID = -541917991749060377L;

    public ParamAccessException() {
    }

    public ParamAccessException(String message) {
        super(message);
    }

    public ParamAccessException(String message, Throwable cause) {
        super(message, cause);
    }

    public ParamAccessException(Throwable cause) {
        super(cause);
    }

    public ParamAccessException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
