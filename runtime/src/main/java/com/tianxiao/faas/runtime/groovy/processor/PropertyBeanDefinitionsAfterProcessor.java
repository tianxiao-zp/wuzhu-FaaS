package com.tianxiao.faas.runtime.groovy.processor;

import com.tianxiao.faas.common.exception.runtime.BeanDefinitionsAfterProcessorException;
import com.tianxiao.faas.runtime.annotation.Property;
import com.tianxiao.faas.runtime.processor.BeanDefinitionsAfterProcessor;

import java.lang.reflect.Field;

public class PropertyBeanDefinitionsAfterProcessor implements BeanDefinitionsAfterProcessor {

    public void process(Object object) throws BeanDefinitionsAfterProcessorException {
        if (object == null) {
            return;
        }
        Field[] declaredFields = object.getClass().getDeclaredFields();
        if (declaredFields == null || declaredFields.length <= 0) {
            return;
        }
        for (Field field : declaredFields) {
            field.setAccessible(true);
            Property annotation = field.getAnnotation(Property.class);
            if (annotation != null) {
                String value = annotation.value();
                try {
                    Class<?> type = field.getType();
                    if (String.class.equals(type)) {
                        field.set(object, value);
                    } else if (int.class.equals(type)) {
                        field.set(object, Integer.valueOf(value).intValue());
                    }

                } catch (IllegalAccessException e) {
                    throw new BeanDefinitionsAfterProcessorException(e);
                }
            }
        }
    }

    public int order() {
        return 0;
    }
}
