package com.tianxiao.faas.container.processor.manager;

import com.tianxiao.faas.runtime.processor.BeanDefinitionsAfterProcessor;
import com.tianxiao.faas.runtime.processor.manager.BeanDefinitionsProcessorManager;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

@Component
public class SpringBeanDefinitionsProcessorManager implements BeanDefinitionsProcessorManager {
    @Resource
    private List<BeanDefinitionsAfterProcessor> processors;
    @Override
    public List<BeanDefinitionsAfterProcessor> getAfterProcessors() {
        return processors;
    }
}