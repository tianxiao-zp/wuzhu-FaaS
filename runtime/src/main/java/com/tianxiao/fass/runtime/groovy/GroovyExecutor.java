package com.tianxiao.fass.runtime.groovy;

import com.tianxiao.fass.common.enums.ExecutorType;
import com.tianxiao.fass.common.exception.runtime.CompileException;
import com.tianxiao.fass.common.exception.runtime.ExecuteException;
import com.tianxiao.fass.common.exception.runtime.ObjectInvokeProcessorException;
import com.tianxiao.fass.common.util.ObjectUtils;
import com.tianxiao.fass.runtime.Executor;
import com.tianxiao.fass.runtime.ExecutorContext;
import com.tianxiao.fass.runtime.FaasBeanFactory;
import com.tianxiao.fass.runtime.processor.ObjectInvokeBeforeProcessor;
import com.tianxiao.fass.runtime.processor.manager.ObjectInvokeBeforeProcessorManager;
import groovy.lang.GroovyClassLoader;
import groovy.lang.GroovyObject;
import org.codehaus.groovy.control.CompilationFailedException;

import java.util.List;

public class GroovyExecutor implements Executor {

    public Object compile(String code) throws CompileException {
        GroovyClassLoader instance = GroovyClassLoaderHolder.getInstance();
        Class parseClass = null;
        GroovyObject object;
        try {
            parseClass = instance.parseClass(code);
            Object bean = FaasBeanFactory.getBean(parseClass.getName());
            if (bean != null && (bean instanceof GroovyObject)) {
                object = (GroovyObject) bean;
            } else {
                object = assemblyBean(parseClass);
                FaasBeanFactory.cache(parseClass.getName(), object);
            }
        } catch (CompilationFailedException e) {
            throw new CompileException(e);
        } catch (IllegalAccessException e) {
            throw new CompileException(e);
        } catch (InstantiationException e) {
            throw new CompileException(e);
        } catch (ObjectInvokeProcessorException e) {
            throw new CompileException(e);
        }
        return object;
    }

    public Object execute(ExecutorContext executeContext) throws ExecuteException {
        ObjectUtils.checkNull(executeContext, "executor context require not null");
        Class parseClass;
        String code = executeContext.getCode();
        String methodName = executeContext.getMethodName();
        Object params = executeContext.getParams();
        try {
            GroovyObject object;
            Object result = null;
            object = (GroovyObject) compile(code);
            if (object != null) {
                result = object.invokeMethod(methodName, params);
            }
            return result;
        } catch (CompileException e) {
            throw new ExecuteException(e);
        }
    }

    /**
     * 创建并组装bean
     * @param parseClass
     * @return
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @throws ObjectInvokeProcessorException
     */
    private GroovyObject assemblyBean(Class parseClass) throws InstantiationException, IllegalAccessException, ObjectInvokeProcessorException {
        GroovyObject object;
        object = (GroovyObject) parseClass.newInstance();
        List<ObjectInvokeBeforeProcessor> processors = ObjectInvokeBeforeProcessorManager.getProcessors();
        if (processors != null) {
            for (ObjectInvokeBeforeProcessor processor : processors) {
                processor.process(object);
            }
        }
        return object;
    }

    public String type() {
        return ExecutorType.GROOVY.name();
    }
}
