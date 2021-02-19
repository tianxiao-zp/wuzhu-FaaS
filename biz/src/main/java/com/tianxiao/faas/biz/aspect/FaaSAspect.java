package com.tianxiao.faas.biz.aspect;

import com.tianxiao.faas.biz.aspect.context.FaaSAspectContext;

/**
 * FaaS切面设计
 */
public interface FaaSAspect {

    /**
     * 顺序
     * @return
     */
    int order();

    /**
     * 前置切面
     * @param context
     * @return
     */
    AspectObject before(FaaSAspectContext context);

    /**
     * 后置切面
     * @param context
     * @return
     */
    AspectObject after(FaaSAspectContext context, Object result);

    public static class AspectObject {
        private Object object;

        private boolean isReturn;

        private Throwable throwable;

        public AspectObject() {
        }

        public AspectObject(Object object, boolean isReturn) {
            this.object = object;
            this.isReturn = isReturn;
        }

        public Object getObject() {
            return object;
        }

        public void setObject(Object object) {
            this.object = object;
        }

        public boolean isReturn() {
            return isReturn;
        }

        public void setReturn(boolean aReturn) {
            isReturn = aReturn;
        }

        public Throwable getThrowable() {
            return throwable;
        }

        public void setThrowable(Throwable throwable) {
            this.throwable = throwable;
        }
    }
}
