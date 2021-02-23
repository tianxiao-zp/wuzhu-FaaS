package com.tianxiao.faas.biz.aspect.system;

import com.tianxiao.faas.biz.aspect.AspectOrder;
import com.tianxiao.faas.biz.aspect.FaaSAspect;
import com.tianxiao.faas.biz.aspect.context.FaaSAspectContext;
import com.tianxiao.faas.common.enums.biz.FaaSServiceStatusEnum;
import com.tianxiao.faas.common.enums.context.Environment;
import com.tianxiao.faas.runtime.context.FaaSContextHolder;
import org.springframework.stereotype.Component;

@Component
public class ContextAspect implements FaaSAspect {
    @Override
    public int order() {
        return AspectOrder.CONTEXT_ORDER;
    }

    @Override
    public AspectObject before(FaaSAspectContext context) {
        if (context != null && context.service() != null) {
            if (context.service().getStatus() == FaaSServiceStatusEnum.ONLINE.getStatus()) {
                FaaSContextHolder.put(() -> Environment.ONLINE);
            }
        }
        return null;
    }

    @Override
    public AspectObject after(FaaSAspectContext context, Object result) {
        if (context != null && context.service() != null) {
            if (context.service().getStatus() == FaaSServiceStatusEnum.ONLINE.getStatus()) {
                FaaSContextHolder.remove();
            }
        }
        return null;
    }
}
