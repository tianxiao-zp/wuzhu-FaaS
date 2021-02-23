package com.tianxiao.faas.biz.aspect.system;

import com.tianxiao.faas.biz.aspect.AspectOrder;
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
        return AspectOrder.CURRENT_LIMITING_ORDER;
    }

    @Override
    public AspectObject before(FaaSAspectContext context) {
        return null;
    }
}
