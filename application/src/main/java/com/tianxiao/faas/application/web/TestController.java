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
    public void test() throws ExecuteException {
        String code = "import com.tianxiao.faas.container.bean.UserBean;\n" +
                "\n" +
                "import javax.annotation.Resource;" +
                "import org.springframework.beans.factory.annotation.Autowired\n" +
                "\n" +
                "public class Test {\n" +
                "    @Autowired\n" +
                "    private UserBean userBean;\n" +
                "\n" +
                "    public String test(String name) {\n" +
                "        return userBean.getUserName(name);\n" +
                "    }\n" +
                "}\n";
        Executor executor = executorFactory.getExecutor(ExecutorType.GROOVY);
        ExecutorContext executeContext = new ExecutorContext();
        executeContext.setCode(code);
        executeContext.setMethodName("test");
        executeContext.setParams("zhang san");
        executor.execute(executeContext);
    }
}
