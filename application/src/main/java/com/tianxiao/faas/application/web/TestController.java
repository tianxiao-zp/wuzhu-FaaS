package com.tianxiao.faas.application.web;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
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
                "import java.util.Map;\n" +
                "import java.util.Set;\n" +
                "\n" +
                "public class Tests {\n" +
                "    public String execute(Map<String, String[]> map) {\n" +
                "        StringBuilder result = new StringBuilder();\n" +
                "\n" +
                "        Set<String> strings = map.keySet();\n" +
                "        for (String key : strings) {\n" +
                "            result.append(key + \"|\").append(map.get(key)).append(\"-\");\n" +
                "        }\n" +
                "        User user = new User();\n" +
                "        user.sayHello();\n" +
                "\n" +
                "        while (true) {\n" +
                "            System.out.println(1);\n" +
                "        }\n" +
                "\n" +
                "    }\n" +
                "\n" +
                "    public static class User {\n" +
                "        public void sayHello() {\n" +
                "            System.out.println(\"hello\");\n" +
                "        }\n" +
                "    }\n" +
                "}";
        ExecutorFactory executorFactory = ExecutorFactory.getInstance();
        executorFactory.init();
        Executor executor = executorFactory.getExecutor(ExecutorType.GROOVY);
        ExecutorContext executeContext = new ExecutorContext();
        executeContext.setCode(code);
        executeContext.setDebug(true);

        executeContext.setParams(Lists.newArrayList(Maps.newHashMap()));
        executeContext.setMethodName("execute");
        Object execute = executor.execute(executeContext);
        System.out.println(execute);
    }
}
