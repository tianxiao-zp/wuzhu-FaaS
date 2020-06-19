package com.tianxiao.faas.container.invoker;

import com.tianxiao.faas.common.exception.container.InvokerException;
import org.apache.dubbo.rpc.service.GenericService;

import java.util.List;

/**
 * dubbo接口的调用类
 */
public class DubboServiceInvoker {
    private GenericService genericService;

    public DubboServiceInvoker(GenericService genericService) {
        this.genericService = genericService;
    }

    public Object invoke(String method, List<String> parameterTypes, List<Object> args) {
        try {
            if (genericService != null) {
                String[] params = null;
                if (parameterTypes != null) {
                    params = new String[parameterTypes.size()];
                    for (int i = 0; i < parameterTypes.size(); i++) {
                        params[i] = parameterTypes.get(i);
                    }
                }
                return genericService.$invoke(method, params, args.toArray());
            }
        } catch (Exception e) {
            throw new InvokerException(e);
        }
        return null;
    }
}
