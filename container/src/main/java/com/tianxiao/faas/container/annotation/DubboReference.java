package com.tianxiao.faas.container.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * dubbo消费者注解
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.METHOD})
public @interface DubboReference {
    /**
     * 接口名
     * @return
     */
    String interfaceName() default "";

    /**
     * 接口版本
     * @return
     */
    String version() default "";

    /**
     * 分组名
     * @return
     */
    String group() default "";

    /**
     * 协议名
     * @return
     */
    String protocol() default "";
}
