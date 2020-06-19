package com.tianxiao.faas.application.config;

import com.tianxiao.faas.container.bean.DubboApplicationConfig;
import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ConfigCenterConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DubboConfiguration {
    @Value("${dubbo.app.name}")
    private String appName;
    @Value("${dubbo.app.owner}")
    private String owner;
    @Value("${dubbo.register.address}")
    private String registerAddress;
    @Value("${dubbo.protocol.port}")
    private int port;
    @Value("${dubbo.register.timeout}")
    private int timeout;
    //@Value("${dubbo.group}")
    private String group;

    @Bean
    public DubboApplicationConfig dubboApplicationConfig() {
        DubboApplicationConfig dubboApplicationConfig = new DubboApplicationConfig();
        ApplicationConfig applicationConfig = new ApplicationConfig();
        applicationConfig.setName(appName);
        applicationConfig.setOwner(owner);
        dubboApplicationConfig.setApplicationConfig(applicationConfig);
        RegistryConfig registryConfig = new RegistryConfig();
        registryConfig.setPort(port);
        registryConfig.setAddress(registerAddress);
        registryConfig.setTimeout(timeout);
        registryConfig.setGroup(group);
        dubboApplicationConfig.setRegistryConfig(registryConfig);

        ConfigCenterConfig configCenterConfig = new ConfigCenterConfig();
        configCenterConfig.setAddress(registerAddress);
        configCenterConfig.setTimeout(Long.valueOf(timeout + ""));
        dubboApplicationConfig.setConfigCenterConfig(configCenterConfig);
        return dubboApplicationConfig;
    }
}
