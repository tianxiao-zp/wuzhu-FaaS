package com.tianxiao.faas.biz.aspect.system;

import com.tianxiao.faas.biz.aspect.FaaSAspect;
import com.tianxiao.faas.biz.aspect.context.FaaSAspectContext;
import org.springframework.stereotype.Component;

/**
 * 限流切面
 * 根据
 * @see StatisticsAspect
 * 统计的性能指标数据进行限流
 */
@Component
public class CurrentLimitingAspect implements FaaSAspect {
    @Override
    public int order() {
        return 0;
    }

    @Override
    public Object before(FaaSAspectContext context) {
        return null;
    }

    @Override
    public Object after(FaaSAspectContext context) {
        return null;
    }
}
