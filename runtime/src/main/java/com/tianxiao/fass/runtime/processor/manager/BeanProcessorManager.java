package com.tianxiao.fass.runtime.processor.manager;

import com.tianxiao.fass.runtime.processor.BeanDefinitionsAfterProcessor;

import java.util.List;

public interface BeanProcessorManager {
    List<BeanDefinitionsAfterProcessor> getAfterProcessors();
}
