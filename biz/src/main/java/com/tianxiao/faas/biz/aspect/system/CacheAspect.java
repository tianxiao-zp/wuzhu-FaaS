package com.tianxiao.faas.biz.aspect.system;

import com.tianxiao.faas.biz.aspect.AspectOrder;
import com.tianxiao.faas.biz.aspect.FaaSAspect;
import com.tianxiao.faas.biz.aspect.context.FaaSAspectContext;
import org.springframework.stereotype.Component;

/**
 * 缓存切面
 */
@Component
public class CacheAspect implements FaaSAspect {
    @Override
    public int order() {
        return AspectOrder.CACHE_ORDER;
    }

    @Override
    public AspectObject before(FaaSAspectContext context) {
        return null;
    }

    @Override
    public AspectObject after(FaaSAspectContext context, Object result) {
        return null;
    }
}
