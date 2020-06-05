package com.tianxiao.faas.runtime.processor.manager;

import com.tianxiao.faas.runtime.processor.BeanDefinitionsAfterProcessor;

import java.util.List;

public interface BeanDefinitionsProcessorManager {
    List<BeanDefinitionsAfterProcessor> getAfterProcessors();
}
