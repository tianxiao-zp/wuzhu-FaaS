package com.tianxiao.faas.container.bean;

import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ConfigCenterConfig;
import org.apache.dubbo.config.RegistryConfig;

public class DubboApplicationConfig {

    private ApplicationConfig applicationConfig;

    private RegistryConfig registryConfig;

    private ConfigCenterConfig configCenterConfig;

    public ConfigCenterConfig getConfigCenterConfig() {
        return configCenterConfig;
    }

    public void setConfigCenterConfig(ConfigCenterConfig configCenterConfig) {
        this.configCenterConfig = configCenterConfig;
    }

    public ApplicationConfig getApplicationConfig() {
        return applicationConfig;
    }

    public void setApplicationConfig(ApplicationConfig applicationConfig) {
        this.applicationConfig = applicationConfig;
    }

    public RegistryConfig getRegistryConfig() {
        return registryConfig;
    }

    public void setRegistryConfig(RegistryConfig registryConfig) {
        this.registryConfig = registryConfig;
    }
}
