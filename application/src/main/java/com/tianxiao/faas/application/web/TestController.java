package com.tianxiao.faas.application.web;

import com.tianxiao.faas.common.enums.ExecutorType;
import com.tianxiao.faas.common.exception.runtime.ExecuteException;
import com.tianxiao.faas.runtime.Executor;
import com.tianxiao.faas.runtime.ExecutorContext;
import com.tianxiao.faas.runtime.ExecutorFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class TestController {
    @Resource
    private ExecutorFactory executorFactory;

    @RequestMapping("/test")
    public Object test(@RequestParam String code, @RequestParam String method) throws ExecuteException {
        Executor executor = executorFactory.getExecutor(ExecutorType.GROOVY);
        ExecutorContext executeContext = new ExecutorContext();
        executeContext.setCode(code);
        executeContext.setDebug(true);
        executeContext.setMethodName(method);
        Object execute = executor.execute(executeContext);
        return execute;
    }
}
