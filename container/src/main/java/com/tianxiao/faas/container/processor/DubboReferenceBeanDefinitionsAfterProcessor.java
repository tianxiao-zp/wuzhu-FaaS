package com.tianxiao.faas.container.processor;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.util.concurrent.Striped;
import com.tianxiao.faas.common.constants.ContainerConstants;
import com.tianxiao.faas.common.exception.runtime.BeanDefinitionsAfterProcessorException;
import com.tianxiao.faas.common.util.StringUtils;
import com.tianxiao.faas.container.annotation.DubboReference;
import com.tianxiao.faas.container.invoker.DubboServiceInvoker;
import com.tianxiao.faas.runtime.processor.BeanDefinitionsAfterProcessor;
import org.apache.dubbo.config.ReferenceConfig;
import org.apache.dubbo.rpc.service.GenericService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;

@Component
public class DubboReferenceBeanDefinitionsAfterProcessor implements BeanDefinitionsAfterProcessor, InitializingBean {
    private Logger logger = Logger.getLogger(DubboReferenceBeanDefinitionsAfterProcessor.class);
    private static final Striped<Lock> striped = Striped.lazyWeakLock(127);
    private Cache<String, ReferenceConfig<GenericService>> cache;
    @Override
    public void process(Object object) throws BeanDefinitionsAfterProcessorException {
        if (object == null) {
            return;
        }
        Field[] declaredFields = object.getClass().getDeclaredFields();
        if (declaredFields == null || declaredFields.length <= 0) {
            return;
        }
        for (Field field : declaredFields) {
            field.setAccessible(true);
            DubboReference annotation = field.getAnnotation(DubboReference.class);
            if (annotation != null) {
                String interfaceName = annotation.interfaceName();
                String group = annotation.group();
                if (StringUtils.isEmpty(interfaceName)) {
                    throw new BeanDefinitionsAfterProcessorException("the dubbo reference interface name is required");
                }
                if (StringUtils.isEmpty(group)) {
                    throw new BeanDefinitionsAfterProcessorException("the dubbo reference group is required");
                }
                String protocol = annotation.protocol();
                String version = annotation.version();
                Class<?> type = field.getType();
                if (!DubboServiceInvoker.class.equals(type)) {
                    throw new BeanDefinitionsAfterProcessorException(String.format("the field %s, cannot use DubboReference annotation", field.getName()));
                }
                final String cacheKey = getCacheKey(interfaceName, group, version, protocol);
                ReferenceConfig<GenericService> service = cache.getIfPresent(cacheKey);

                GenericService genericService = null;
                try {
                    // 如果service == null 则需要创建，创建需要加锁，避免多次创建影响内存资源
                    if (service == null) {
                        final Lock lock = striped.get(cacheKey);
                        try {
                            lock.lock();
                            service = cache.getIfPresent(cacheKey);
                            if (service == null) {
                                service = buildReference(annotation);
                                cache.put(cacheKey, service);
                                genericService = service.get();
                            }
                        } finally {
                            lock.unlock();
                        }
                    } else {
                        genericService = service.get();
                    }
                    field.set(object, new DubboServiceInvoker(genericService));
                } catch (Exception e) {
                    throw new BeanDefinitionsAfterProcessorException(e);
                }
            }
        }
    }

    private ReferenceConfig<GenericService> buildReference(DubboReference annotation) {
        String protocol = annotation.protocol();
        String version = annotation.version();
        String interfaceName = annotation.interfaceName();
        String group = annotation.group();
        final int timeout = annotation.timeout();
        ReferenceConfig<GenericService> reference = new ReferenceConfig<GenericService>();
        reference.setProtocol(protocol);
        reference.setInterface(interfaceName);
        reference.setGroup(group);
        reference.setTimeout(timeout);
        if (StringUtils.isNotEmpty(version)) {
            reference.setVersion(version);
        }
        // 声明为泛化接口
        reference.setGeneric("true");
        logger.info("create service");
        return reference;
    }

    @Override
    public int order() {
        return 0;
    }

    private String getCacheKey(String interfaceName, String group, String version, String protocol) {
        StringBuilder builder = new StringBuilder();
        builder.append(interfaceName)
                .append(ContainerConstants.UNDER_LINE)
                .append(group)
                .append(ContainerConstants.UNDER_LINE);
                if (StringUtils.isNotEmpty(version)) {
                    builder.append(version);
                    builder.append(ContainerConstants.UNDER_LINE);
                }
                builder.append(protocol);
        return builder.toString();
    }

    /**
     * 加载缓存
     */
    private void loadCache() {
        cache = CacheBuilder.newBuilder()
                .expireAfterAccess(5L, TimeUnit.MINUTES)
                // 初始化缓存数量
                .initialCapacity(50)
                // 最大缓存数量，如果接近300，则会移除最近不经常使用的bean
                .maximumSize(300)
                .build();
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        loadCache();
    }
}
