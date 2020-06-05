package com.tianxiao.faas.common.exception.runtime;

public class ObjectInvokeProcessorException extends Exception {
    private static final long serialVersionUID = -119324811768332640L;

    public ObjectInvokeProcessorException() {
    }

    public ObjectInvokeProcessorException(String message) {
        super(message);
    }

    public ObjectInvokeProcessorException(String message, Throwable cause) {
        super(message, cause);
    }

    public ObjectInvokeProcessorException(Throwable cause) {
        super(cause);
    }

    public ObjectInvokeProcessorException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
