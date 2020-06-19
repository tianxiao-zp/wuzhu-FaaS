package com.tianxiao.faas.container.annotation;

import com.tianxiao.faas.container.enums.RPCType;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Target({FIELD, METHOD})
@Retention(RUNTIME)
public @interface DubboReference {
    /**
     * 协议名称
     * @return
     */
    String protocol() default "dubbo";

    String rpcType() default RPCType.DUBBO_TYPE;

    String interfaceName();

    String group();

    String version() default "";

    int timeout() default 3000;
}
