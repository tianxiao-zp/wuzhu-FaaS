package com.tianxiao.faas.biz.aspect;

public interface AspectOrder {
    final static int MAX_ORDER = Integer.MAX_VALUE;

    final static int CACHE_ORDER = Integer.MAX_VALUE - 10;

    final static int CURRENT_LIMITING_ORDER = Integer.MAX_VALUE - 9;

    final static int STATISTICS_ORDER = Integer.MAX_VALUE - 8;

    final static int CONTEXT_ORDER = Integer.MAX_VALUE - 7;
}
