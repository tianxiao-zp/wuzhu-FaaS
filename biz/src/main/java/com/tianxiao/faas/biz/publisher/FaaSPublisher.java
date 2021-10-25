package com.tianxiao.faas.biz.publisher;

import java.util.List;

// 服务的注册中心
public interface FaaSPublisher {
    // 注册服务ip
    void register(String ip);

    // 服务下线/重启
    void offline(String ip);

    // 获取所有服务
    List<String> servers();

    // 发布服务
    void publish(String serviceName);

    // 根据注册中心地址及服务刷新本地脚本缓存
    void refreshCache();
}
