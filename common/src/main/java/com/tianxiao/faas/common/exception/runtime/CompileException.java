package com.tianxiao.faas.common.exception.runtime;

/**
 * 编译异常
 */
public class CompileException extends Exception {
    private static final long serialVersionUID = -2773804049674140436L;

    public CompileException() {
    }

    public CompileException(String message) {
        super(message);
    }

    public CompileException(String message, Throwable cause) {
        super(message, cause);
    }

    public CompileException(Throwable cause) {
        super(cause);
    }

    public CompileException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
