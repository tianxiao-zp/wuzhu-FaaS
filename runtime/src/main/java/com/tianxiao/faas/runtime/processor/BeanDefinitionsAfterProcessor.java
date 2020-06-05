package com.tianxiao.faas.runtime.processor;

import com.tianxiao.faas.common.exception.runtime.ObjectInvokeProcessorException;

public interface BeanDefinitionsAfterProcessor {

    void process(Object object) throws ObjectInvokeProcessorException;

    int order();
}
