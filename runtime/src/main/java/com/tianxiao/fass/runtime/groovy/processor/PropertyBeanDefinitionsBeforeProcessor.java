package com.tianxiao.fass.runtime.groovy.processor;

import com.tianxiao.fass.common.exception.runtime.ObjectInvokeProcessorException;
import com.tianxiao.fass.runtime.annotation.Property;
import com.tianxiao.fass.runtime.processor.BeanDefinitionsBeforeProcessor;

import java.lang.reflect.Field;

public class PropertyBeanDefinitionsBeforeProcessor implements BeanDefinitionsBeforeProcessor {

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
                    throw new ObjectInvokeProcessorException(e);
                }
            }
        }
    }

    public int order() {
        return 0;
    }
}
