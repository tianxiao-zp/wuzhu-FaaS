package com.tianxiao.fass.runtime.processor;

import com.tianxiao.fass.common.exception.runtime.ObjectInvokeProcessorException;

public interface InvokeBeforeProcessor {

    void process(Object object) throws ObjectInvokeProcessorException;

    int order();
}
