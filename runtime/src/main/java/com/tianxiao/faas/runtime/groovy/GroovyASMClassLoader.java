package com.tianxiao.faas.runtime.groovy;

import groovy.lang.GroovyClassLoader;

public class GroovyASMClassLoader extends GroovyClassLoader {

    public GroovyASMClassLoader() {
        super(Thread.currentThread().getContextClassLoader(), new SimpleCompilerConfiguration());
    }
}
