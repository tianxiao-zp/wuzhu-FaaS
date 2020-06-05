package com.tianxiao.faas.runtime.groovy;

import groovy.lang.GroovyClassLoader;

/**
 * groovy class loader 单例持有者
 * 懒加载单例模式
 */
public class GroovyClassLoaderHolder {

    private GroovyClassLoaderHolder() {
    }

    public static GroovyClassLoader getInstance() {
        return Instance.loader;
    }

    private static class Instance {
        private static GroovyClassLoader loader = new GroovyClassLoader();
    }
}
