# wuzhu-FaaS
五竹，庆余年中一个牛逼的机器人。

该项目旨在提供一种faas的解决方案，目前已经开发好了底层代码，可以运行groovy脚本及java脚本。
底层是采用groovy引擎来执行。
后续会持续更新。

下面是一个例子：
```
public class GroovyTest {
    public static void main(String[] args) throws ExecuteException, CompileException {
        String code = "import com.tianxiao.fass.runtime.annotation.Property;\n" +
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


设计：

![包结构](http://i1.fuimg.com/720396/50eaa2bd776c4a1d.png)
![模型设计](http://i1.fuimg.com/720396/ee1c05baa1645c8f.png)
![类图](http://i1.fuimg.com/720396/8ad495385b4e60c2.png)
