package com.tianxiao.faas.runtime.groovy;

import groovy.lang.GroovyClassLoader;

public class GroovyASMClassLoader extends GroovyClassLoader {

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        return super.findClass(name);
    }
}
