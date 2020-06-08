package com.tianxiao.faas.application.web;

import com.tianxiao.faas.common.enums.ExecutorType;
import com.tianxiao.faas.common.exception.runtime.ExecuteException;
import com.tianxiao.faas.runtime.Executor;
import com.tianxiao.faas.runtime.ExecutorContext;
import com.tianxiao.faas.runtime.ExecutorFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class TestController {
    @Resource
    private ExecutorFactory executorFactory;

    @RequestMapping("/test")
    public Object test() throws ExecuteException {
        String code = "import com.tianxiao.faas.container.bean.UserBean;" +
                "import com.ql.util.express.DefaultContext;\n" +
                "import com.ql.util.express.ExpressRunner;\n" +
                "\n" +
                "import javax.annotation.Resource;" +
                "import org.springframework.beans.factory.annotation.Autowired\n" +
                "\n" +
                "public class Test {\n" +
                "    @Autowired\n" +
                "    private UserBean userBean;\n" +
                "\n" +
                "    public String test(String name) {\n" +
                "ExpressRunner runner = new ExpressRunner();\n" +
                "        DefaultContext<String, Object> context = new DefaultContext<String, Object>();\n" +
                "        context.put(\"a\",1);\n" +
                "        context.put(\"b\",2);\n" +
                "        context.put(\"c\",3);\n" +
                "        String express = \"a+b*c\";\n" +
                "        Object r = runner.execute(express, context, null, true, false);\n" +
                "        System.out.println(r);        " +
                "return userBean.getUserName(name);\n" +
                "    }\n" +
                "}\n";
        Executor executor = executorFactory.getExecutor(ExecutorType.GROOVY);
        ExecutorContext executeContext = new ExecutorContext();
        executeContext.setCode(code);
        executeContext.setMethodName("test");
        executeContext.setParams("zhang san");
        Object execute = executor.execute(executeContext);
        return execute;
    }
}
