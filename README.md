# wuzhu-FaaS
五竹，庆余年中一个牛逼的机器人。

该项目旨在提供一种faas的解决方案，目前已经开发好了底层代码，可以运行groovy脚本及java脚本。
底层是采用groovy引擎来执行。
后续会持续更新。

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
```

下面是一个普通调用例子：
```
public class GroovyTest {
    public static void main(String[] args) throws ExecuteException, CompileException {
        String code = "import Property;\n" +
                "\n" +
                "public class Test {\n" +
                "    @Property(\"18\")\n" +
                "    private int age;\n" +
                "\n" +
                "    public Object test(String name) {\n" +
                "        return \"test, my name is \" + name + \" and age is \" + age;\n" +
                "    }\n" +
                "}";
        // 容器初始化
        FaaSContainer.getInstance().start();
        // 根据执行器类型获取执行器
        Executor executor = ExecutorFactory.getInstance().getExecutor(ExecutorType.GROOVY);
        // 代码编译，并初始化bean(对象)
        executor.compile(code);
        // 构建执行上下文
        ExecutorContext executeContext = new ExecutorContext();
        // 设置代码
        executeContext.setCode(code);
        // 设置调用方法
        executeContext.setMethodName("test");
        // 设置方法入参
        executeContext.setParams("zhang san");
        // 方法执行，并获取执行结果
        Object execute = executor.execute(executeContext);
        System.out.println(execute);
        // 容器关闭
        FaaSContainer.getInstance().close();
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

设计：

![包结构](http://i1.fuimg.com/720396/50eaa2bd776c4a1d.png)
![模型设计](http://i1.fuimg.com/720396/ee1c05baa1645c8f.png)
![类图](http://i1.fuimg.com/720396/8ad495385b4e60c2.png)
![时序图](http://i2.tiimg.com/720396/7b158bc1503600dd.png)
