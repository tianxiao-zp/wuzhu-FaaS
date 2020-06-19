package com.tianxiao.faas.common.util;

public final class StringUtils {
    public static final String EMPTY = "";

    private StringUtils() {
    }

    public static boolean isEmpty(String str) {
        if (str == null || EMPTY.equals(str.toLowerCase())) {
            return true;
        }
        return false;
    }

    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }

    /**
     * 首字母转小写
     * @param str
     * @return
     */
    public static String firstCharLowerCase(String str) {
        if (str == null) {
            return null;
        }
        char[]chars = str.toCharArray();

        chars[0] += 32;
        str = String.valueOf(chars);
        return str;
    }
}
