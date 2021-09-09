package com.tianxiao.faas.runtime.groovy;

import com.tianxiao.faas.common.enums.ExecutorType;
import com.tianxiao.faas.common.enums.context.Environment;
import com.tianxiao.faas.common.exception.runtime.BeanDefinitionsAfterProcessorException;
import com.tianxiao.faas.common.exception.runtime.CompileException;
import com.tianxiao.faas.common.exception.runtime.ExecuteException;
import com.tianxiao.faas.common.util.ObjectUtils;
import com.tianxiao.faas.common.util.StringUtils;
import com.tianxiao.faas.runtime.BeanDefinitionsProcessorManagerFactory;
import com.tianxiao.faas.runtime.Executor;
import com.tianxiao.faas.runtime.ExecutorContext;
import com.tianxiao.faas.runtime.FaaSBeanFactory;
import com.tianxiao.faas.runtime.context.FaaSContext;
import com.tianxiao.faas.runtime.context.FaaSContextHolder;
import com.tianxiao.faas.runtime.processor.BeanDefinitionsAfterProcessor;
import groovy.lang.GroovyClassLoader;
import groovy.lang.GroovyObject;
import org.codehaus.groovy.control.CompilationFailedException;

import java.util.List;

public class GroovyExecutor implements Executor {

    public Object compile(ExecutorContext executeContext) throws CompileException {
        GroovyClassLoader instance = GroovyClassLoaderHolder.getInstance();
        Class parseClass = null;
        GroovyObject object;
        try {
            String code = executeContext.getCode();
            String serviceName = executeContext.getServiceName();
            if (StringUtils.isEmpty(serviceName)) {
                throw new CompileException("服务名称不能为空");
            }
            final boolean debug = executeContext.getDebug();
            if (debug) {
                parseClass = instance.parseClass(code);
                object = assemblyBean(parseClass);
            } else {
                Object bean = FaaSBeanFactory.getBean(serviceName);
                if (bean != null && (bean instanceof GroovyObject)) {
                    object = (GroovyObject) bean;
                } else {
                    parseClass = instance.parseClass(code);
                    object = assemblyBean(parseClass);
                    cache(serviceName, object);
                }
            }
        } catch (CompilationFailedException e) {
            throw new CompileException(e);
        } catch (IllegalAccessException e) {
            throw new CompileException(e);
        } catch (InstantiationException e) {
            throw new CompileException(e);
        } catch (BeanDefinitionsAfterProcessorException e) {
            throw new CompileException(e);
        }
        return object;
    }

    private void cache(String serviceName, GroovyObject object) {
        FaaSContext faaSContext = FaaSContextHolder.get();
        if (faaSContext != null && faaSContext.getEnv() == Environment.ONLINE) {
            FaaSBeanFactory.cache(serviceName, object);
        }
    }

    public Object execute(ExecutorContext executeContext) throws ExecuteException {
        ObjectUtils.checkNull(executeContext, "executor context require not null");
        String methodName = executeContext.getMethodName();
        List<Object> params = executeContext.getParams();
        try {
            GroovyObject object;
            Object result = null;
            object = (GroovyObject) compile(executeContext);
            if (object != null) {
                result = object.invokeMethod(methodName, params.toArray());
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
     * @throws BeanDefinitionsAfterProcessorException
     */
    private GroovyObject assemblyBean(Class parseClass) throws InstantiationException, IllegalAccessException, BeanDefinitionsAfterProcessorException {
        GroovyObject object = (GroovyObject) parseClass.newInstance();
        List<BeanDefinitionsAfterProcessor> processors = BeanDefinitionsProcessorManagerFactory.getInstance()
                .getManager()
                .getAfterProcessors();
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
