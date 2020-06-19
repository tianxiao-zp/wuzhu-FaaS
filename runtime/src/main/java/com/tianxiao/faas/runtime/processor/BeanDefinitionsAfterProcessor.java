package com.tianxiao.faas.runtime.processor;

import com.tianxiao.faas.common.exception.runtime.BeanDefinitionsAfterProcessorException;

public interface BeanDefinitionsAfterProcessor {

    void process(Object object) throws BeanDefinitionsAfterProcessorException;

    int order();
}
