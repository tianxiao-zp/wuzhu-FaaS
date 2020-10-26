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
        if (processors == null) {
            return null;
        }
        processors.sort((o1, o2) -> {
            int i = o1.order() - o2.order();
            if (i >= 0) {
                return -1;
            } else {
                return 1;
            }
        });
        return processors;
    }
}
