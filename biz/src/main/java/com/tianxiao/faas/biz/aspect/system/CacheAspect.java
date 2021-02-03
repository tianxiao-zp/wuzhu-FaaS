package com.tianxiao.faas.biz.aspect.system;

import com.tianxiao.faas.biz.aspect.FaaSAspect;
import com.tianxiao.faas.biz.aspect.context.FaaSAspectContext;

/**
 * 缓存切面
 */
public class CacheAspect implements FaaSAspect {
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
