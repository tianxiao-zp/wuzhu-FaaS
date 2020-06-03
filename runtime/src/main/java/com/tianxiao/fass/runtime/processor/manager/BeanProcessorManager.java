package com.tianxiao.fass.runtime.processor.manager;

import com.tianxiao.fass.runtime.processor.BeanDefinitionsBeforeProcessor;

import java.util.List;

public interface BeanProcessorManager {
    List<BeanDefinitionsBeforeProcessor> getBeforeProcessors();
}
