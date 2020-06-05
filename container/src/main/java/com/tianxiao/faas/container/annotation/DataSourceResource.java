package com.tianxiao.faas.container.annotation;

public @interface DataSourceResource {

    /**
     * 数据库名称
     * @return
     */
    String dataSource() default "";
}
