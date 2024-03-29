import com.tianxiao.faas.common.enums.ExecutorType;
import com.tianxiao.faas.common.enums.context.Environment;
import com.tianxiao.faas.common.exception.runtime.CompileException;
import com.tianxiao.faas.common.exception.runtime.ExecuteException;
import com.tianxiao.faas.runtime.Executor;
import com.tianxiao.faas.runtime.ExecutorContext;
import com.tianxiao.faas.runtime.ExecutorFactory;
import com.tianxiao.faas.runtime.FaaSContainer;
import com.tianxiao.faas.runtime.context.FaaSContext;
import com.tianxiao.faas.runtime.context.FaaSContextHolder;

import java.util.ArrayList;

/**
 * class：GroovyTest
 * desc：
 * create time ：2020/3/25 10:58 上午
 * author：tianxiao
 */
public class GroovyTest {
    public static void main(String[] args) throws ExecuteException, CompileException {
        String code = "import com.tianxiao.faas.runtime.annotation.Property;\n" +
                "\n" +
                "public class Test {\n" +
                "    @Property(\"11\")" +
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
        // 构建执行上下文
        ExecutorContext executeContext = new ExecutorContext();
        // 设置代码
        executeContext.setCode(code);
        // 设置调用方法
        executeContext.setMethodName("test");
        executeContext.setServiceName("test");
        // 代码编译，并初始化bean(对象)
        FaaSContextHolder.put(() -> Environment.ONLINE);
        executor.compile(executeContext);
        // 设置方法入参
        ArrayList<Object> params = new ArrayList<>();
        params.add("zhang san");
        executeContext.setParams(params);
        // 方法执行，并获取执行结果
        Object execute = executor.execute(executeContext);
        System.out.println(execute);
        // 容器关闭
        FaaSContainer.getInstance().close();
    }
}
