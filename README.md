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
        FaasContainer.getInstance().start();
        Executor executor = ExecutorFactory.getExecutor(ExecutorType.GROOVY);
        executor.compile(code);
        ExecutorContext executeContext = new ExecutorContext();
        executeContext.setCode(code);
        executeContext.setMethodName("test");
        executeContext.setParams("zhang san");
        Object execute = executor.execute(executeContext);
        System.out.println(execute);
    }
}
```
这里code，可以是网络传输来的脚本，可以是存储在数据库的脚本，可以是文件读取的脚本。


设计：

![包结构](http://i1.fuimg.com/720396/50eaa2bd776c4a1d.png)
![模型设计](http://i1.fuimg.com/720396/ee1c05baa1645c8f.png)
