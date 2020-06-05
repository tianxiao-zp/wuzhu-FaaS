package com.tianxiao.faas.container.processor;

import com.tianxiao.faas.container.annotation.SpringResource;
import com.tianxiao.faas.common.exception.runtime.ObjectInvokeProcessorException;
import com.tianxiao.faas.common.util.StringUtils;
import com.tianxiao.faas.runtime.processor.BeanDefinitionsAfterProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.lang.reflect.Field;

@Component
public class SpringBeanDefinitionsAfterProcessor implements BeanDefinitionsAfterProcessor {
    @Resource
    private ApplicationContext applicationContext;

    @Override
    public void process(Object object) throws ObjectInvokeProcessorException {
        if (object == null) {
            return;
        }
        Field[] declaredFields = object.getClass().getDeclaredFields();
        if (declaredFields == null || declaredFields.length <= 0) {
            return;
        }
        for (Field field : declaredFields) {
            field.setAccessible(true);
            SpringResource annotation = field.getAnnotation(SpringResource.class);
            if (annotation != null) {
                String name = annotation.name();
                if (name == null || StringUtils.isEmpty(name)) {
                    name = StringUtils.firstCharLowerCase(field.getType().getSimpleName());
                }
                try {
                    Object bean = applicationContext.getBean(name);
                    field.set(object, bean);
                } catch (IllegalAccessException e) {
                    throw new ObjectInvokeProcessorException(e);
                }
            }
        }
    }

    @Override
    public int order() {
        return 0;
    }
}
