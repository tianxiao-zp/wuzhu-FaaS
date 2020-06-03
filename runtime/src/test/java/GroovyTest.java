import com.tianxiao.fass.common.enums.ExecutorType;
import com.tianxiao.fass.common.exception.runtime.CompileException;
import com.tianxiao.fass.common.exception.runtime.ExecuteException;
import com.tianxiao.fass.runtime.Executor;
import com.tianxiao.fass.runtime.ExecutorContext;
import com.tianxiao.fass.runtime.ExecutorFactory;
import com.tianxiao.fass.runtime.FaaSContainer;

/**
 * class：GroovyTest
 * desc：
 * create time ：2020/3/25 10:58 上午
 * author：tianxiao
 */
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
