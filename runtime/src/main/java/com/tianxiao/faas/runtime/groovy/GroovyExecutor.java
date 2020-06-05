package com.tianxiao.faas.runtime.groovy;

import com.tianxiao.faas.common.enums.ExecutorType;
import com.tianxiao.faas.common.exception.runtime.CompileException;
import com.tianxiao.faas.common.exception.runtime.ExecuteException;
import com.tianxiao.faas.common.exception.runtime.ObjectInvokeProcessorException;
import com.tianxiao.faas.common.util.ObjectUtils;
import com.tianxiao.faas.runtime.Executor;
import com.tianxiao.faas.runtime.ExecutorContext;
import com.tianxiao.faas.runtime.FaasBeanFactory;
import com.tianxiao.faas.runtime.processor.BeanDefinitionsAfterProcessor;
import com.tianxiao.faas.runtime.processor.manager.BeanDefinitionsProcessorManager;
import groovy.lang.GroovyClassLoader;
import groovy.lang.GroovyObject;
import org.codehaus.groovy.control.CompilationFailedException;

import java.util.List;

public class GroovyExecutor implements Executor {

    private BeanDefinitionsProcessorManager beanDefinitionsProcessorManager;

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

    @Override
    public void processManager(BeanDefinitionsProcessorManager beanDefinitionsProcessorManager) {
        this.beanDefinitionsProcessorManager = beanDefinitionsProcessorManager;
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
        List<BeanDefinitionsAfterProcessor> processors = beanDefinitionsProcessorManager.getAfterProcessors();
        if (processors != null) {
            for (BeanDefinitionsAfterProcessor processor : processors) {
                processor.process(object);
            }
        }
        return object;
    }

    public String type() {
        return ExecutorType.GROOVY.name();
    }
}
