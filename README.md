# wuzhu-FaaS

该项目旨在提供一种faas的解决方案，目前已经开发好了底层代码，可以运行groovy脚本及java脚本。
底层是采用groovy引擎来执行。
后续会持续更新。

有简单的设计文档faas文档.docx，也有一些后续的简单规划。
目前切面的基本设计已经开发完成，缓存、限流、脚本执行统计切面还没有实现。

GroovyExecutor
执行器里面是有本地缓存的，为防止编译导致的性能损耗。如果正式上线需要考虑分布式刷新缓存的问题，
可以通过zk、redis做缓存刷新操作，这里不能简单的就把脚本编译好的类/对象存入redis中，这里有序列化的问题，还是需要利用本地缓存，
通过通知的方式把本地缓存刷入.
目前分布式刷新缓存问题基于redis的方案已经实现，具体实现见：com.tianxiao.faas.biz.publisher.FaaSPublisher
设计方案：https://www.yuque.com/wuyan-jkoan/kb/wl8fb2

系统已经实现了脚本死循环检测机制，LoopCounter 类中限制了脚本中的循环次数，目前限制为1000次，超过就会抛出异常。
实现原理，参考：https://www.yuque.com/markylumin/ikl7q2/gyqc2o

目前已经投入使用的公司：
海派客-对账系统，使用内核container及其依赖，发布和开发页面是海拍客单独实现。

dubbo配置如下：
```
dubbo.app.name=faas
dubbo.app.owner=tianxiao
dubbo.protocol.port=28889
dubbo.register.address=配置自己zk地址
dubbo.register.timeout=3000
```
下面是一个dubbo 泛化调用的例子：
```
import com.google.common.collect.Lists;
import com.tianxiao.faas.container.annotation.DubboReference;
import com.tianxiao.faas.container.invoker.DubboServiceInvoker;

import java.util.HashMap;
import java.util.Map;

public class Test {
    @DubboReference(interfaceName = "com.***.***.***.api.borrow..***",
            group = "20200427-tianxiao-himoney")
    private DubboServiceInvoker dubboServiceInvoker;

    public Object test() {
        Map<String, Object> result = new HashMap<>();
        final Object queryToReturn = dubboServiceInvoker.invoke("queryToReturn", Lists.newArrayList("java.lang.String"), Lists.newArrayList("1d004107e0ed4013b86dbfd604406e16"));
        result.put("result", queryToReturn);
        return result;
    }
}
```
1. FaaSApplication 启动应用
2. 调用localhost:8888/test?code=上面的脚本&method=脚本里面的方法
3. 发送请求即可，这样就可以脚本的形式调用dubbo接口

下面是一个普通调用例子：
```
public class GroovyTest {
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
```
这里code，可以是网络传输来的脚本，可以是存储在数据库的脚本，可以是文件读取的脚本。

使用spring 容器的例子：
```
        String code =
                "import com.tianxiao.faas.container.bean.UserBean;" +
                "import com.ql.util.express.DefaultContext;" +
                "import com.ql.util.express.ExpressRunner;" +
                "import javax.annotation.Resource;" +
                "import org.springframework.beans.factory.annotation.Autowired" +
                "public class Test {" +
                "    @Autowired" +
                "    private UserBean userBean;" +
                "    public String test(String name) {" +
                "        ExpressRunner runner = new ExpressRunner();\n" +
                "        DefaultContext<String, Object> context = new DefaultContext<String, Object>();\n" +
                "        context.put(\"a\",1);\n" +
                "        context.put(\"b\",2);\n" +
                "        context.put(\"c\",3);\n" +
                "        String express = \"a+b*c\";\n" +
                "        Object r = runner.execute(express, context, null, true, false);\n" +
                "        System.out.println(r);        " +
                "        return userBean.getUserName(name);\n" +
                "    }\n" +
                "}\n";
        Executor executor = executorFactory.getExecutor(ExecutorType.GROOVY);
        ExecutorContext executeContext = new ExecutorContext();
        executeContext.setCode(code);
        executeContext.setMethodName("test");
        executeContext.setParams("zhang san");
        Object execute = executor.execute(executeContext);
```

