package com.tianxiao.faas.container.processor;

import com.tianxiao.faas.common.exception.runtime.ObjectInvokeProcessorException;
import com.tianxiao.faas.common.util.StringUtils;
import com.tianxiao.faas.runtime.processor.BeanDefinitionsAfterProcessor;
import org.springframework.beans.factory.annotation.Autowired;
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
            Resource resource = field.getAnnotation(Resource.class);
            Autowired autowired = field.getAnnotation(Autowired.class);
            if (resource != null) {
                String name = resource.name();
                if (name == null || StringUtils.isEmpty(name)) {
                    name = StringUtils.firstCharLowerCase(field.getName());
                }
                try {
                    Object bean = applicationContext.getBean(name);
                    field.set(object, bean);
                } catch (IllegalAccessException e) {
                    throw new ObjectInvokeProcessorException(e);
                }
            } else if (autowired != null) {
                try {
                    boolean required = autowired.required();
                    Class<?> type = field.getType();
                    Object bean = applicationContext.getBean(type);
                    if (required && bean == null) {
                        throw new ObjectInvokeProcessorException(type.getName() + " is required");
                    }
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
