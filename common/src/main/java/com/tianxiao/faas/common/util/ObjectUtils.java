package com.tianxiao.faas.common.util;

import com.tianxiao.faas.common.exception.ParamAccessException;

public class ObjectUtils {

    public static void checkNull(Object obj, String errorMsg) {
        if (obj == null) {
            throw new ParamAccessException(errorMsg);
        }
    }
}
