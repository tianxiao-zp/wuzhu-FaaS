package com.tianxiao.faas.biz.aspect.system;

import com.tianxiao.faas.biz.aspect.FaaSAspect;
import com.tianxiao.faas.biz.aspect.context.FaaSAspectContext;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 数据统计切面
 * 用来统计接口的qps，rt等性能指标
 */
@Component
public class StatisticsAspect implements FaaSAspect {
    private static final Map<String, AtomicLong> qps = new ConcurrentHashMap<>();

    @Override
    public int order() {
        return 0;
    }

    @Override
    public AspectObject before(FaaSAspectContext context) {

        return null;
    }
}
