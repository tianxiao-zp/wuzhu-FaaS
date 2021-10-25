package com.tianxiao.faas.biz.registry;

import com.tianxiao.faas.biz.publisher.FaaSPublisher;
import com.tianxiao.faas.common.util.IpUtils;

public class FaaSRegistry {
    private FaaSPublisher faaSPublisher;

    public FaaSRegistry(FaaSPublisher faaSPublisher) {
        this.faaSPublisher = faaSPublisher;
    }

    public void init() {
        faaSPublisher.register(IpUtils.getLocalHostAddress());
    }

    public void destroy() {
        faaSPublisher.offline(IpUtils.getLocalHostAddress());
    }
}
