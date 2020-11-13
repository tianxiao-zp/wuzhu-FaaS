package com.tianxiao.faas.runtime.context;

import com.tianxiao.faas.common.enums.context.Environment;

public interface FaaSContext {

    /**
     * 获取环境变量
     * @return
     */
    Environment getEnv();
}
