import com.tianxiao.fass.common.enums.ExecutorType;
import com.tianxiao.fass.common.exception.runtime.CompileException;
import com.tianxiao.fass.common.exception.runtime.ExecuteException;
import com.tianxiao.fass.runtime.Executor;
import com.tianxiao.fass.runtime.ExecutorContext;
import com.tianxiao.fass.runtime.ExecutorFactory;
import com.tianxiao.fass.runtime.FaasContainer;
import com.tianxiao.fass.runtime.processor.manager.ObjectInvokeBeforeProcessorManager;
import groovy.lang.GroovyObject;

import javax.annotation.Resource;
import java.lang.reflect.Field;

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
