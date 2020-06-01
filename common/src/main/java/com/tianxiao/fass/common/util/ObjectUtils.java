package com.tianxiao.fass.common.util;

import com.tianxiao.fass.common.exception.ParamAccessException;

public class ObjectUtils {

    public static void checkNull(Object obj, String errorMsg) {
        if (obj == null) {
            throw new ParamAccessException(errorMsg);
        }
    }
}
