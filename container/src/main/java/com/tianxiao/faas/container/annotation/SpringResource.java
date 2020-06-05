package com.tianxiao.faas.container.annotation;

public @interface SpringResource {

    /**
     * bean的名字
     * @return
     */
    String name() default "";
}
