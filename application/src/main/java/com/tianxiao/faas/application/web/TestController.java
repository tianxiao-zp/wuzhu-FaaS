package com.tianxiao.faas.application.web;

import com.google.common.collect.Lists;
import com.tianxiao.faas.common.enums.ExecutorType;
import com.tianxiao.faas.common.exception.runtime.ExecuteException;
import com.tianxiao.faas.container.bean.UserBean;
import com.tianxiao.faas.runtime.Executor;
import com.tianxiao.faas.runtime.ExecutorContext;
import com.tianxiao.faas.runtime.ExecutorFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@RestController
public class TestController {
    @Resource
    private ExecutorFactory executorFactory;
    private com.tianxiao.faas.container.bean.UserBean UserBean;

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

    public static void main(String[] args) throws ExecuteException {
        String code = "package com.tianxiao.faas.application.web;\n" +
                "\n" +
                "import com.tianxiao.faas.container.bean.UserBean;\n" +
                "\n" +
                "public class Test {\n" +
                "\n" +
                "\n" +
                "    public Object test(UserBean name) {\n" +
                "        String userName = name.getUserName(\"1\");\n" +
                "        return userName;\n" +
                "    }\n" +
                "}\n";
        ExecutorFactory executorFactory = ExecutorFactory.getInstance();
        executorFactory.init();
        Executor executor = executorFactory.getExecutor(ExecutorType.GROOVY);
        ExecutorContext executeContext = new ExecutorContext();
        executeContext.setCode(code);
        executeContext.setDebug(true);

        executeContext.setParams(Lists.newArrayList(new UserBean()));
        executeContext.setMethodName("test");
        Object execute = executor.execute(executeContext);
        System.out.println(execute);
    }
}
