package com.tianxiao.faas.application.web;

import com.google.common.collect.Lists;
import com.tianxiao.faas.container.annotation.DubboReference;
import com.tianxiao.faas.container.invoker.DubboServiceInvoker;

import java.util.HashMap;
import java.util.Map;

public class Test {
    @DubboReference(interfaceName = "com.yangt.finance.core.api.borrow.BorrowRecordQueryAPI",
            group = "20200427-tianxiao-himoney")
    private DubboServiceInvoker dubboServiceInvoker;

    public Object test() {
        Map<String, Object> result = new HashMap<>();
        final Object queryToReturn = dubboServiceInvoker.invoke("queryToReturn", Lists.newArrayList("java.lang.String"), Lists.newArrayList("1d004107e0ed4013b86dbfd604406e16"));
        result.put("result", queryToReturn);
        return result;
    }
}
