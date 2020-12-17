package com.tianxiao.faas.common;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

import java.io.Serializable;

public class JSONResult implements Serializable {
    private static final long serialVersionUID = -7539749553868932437L;

    private String message;
    private boolean isSuccess;
    private Object data;
    private int pageNo;
    private int pageSize;
    private int totalCount;
    private int totalPage;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean getIsSuccess() {
        return isSuccess;
    }

    public void setIsSuccess(boolean success) {
        isSuccess = success;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

    public static final class Builder {
        private String message;
        private boolean isSuccess;
        private Object data;
        private int pageNo;
        private int pageSize;
        private int totalCount;
        private int totalPage;

        private Builder() {
        }

        public static Builder builder() {
            return new Builder();
        }

        public Builder message(String message) {
            this.message = message;
            return this;
        }

        public Builder isSuccess(boolean isSuccess) {
            this.isSuccess = isSuccess;
            return this;
        }

        public Builder data(Object data) {
            this.data = data;
            return this;
        }

        public Builder pageNo(int pageNo) {
            this.pageNo = pageNo;
            return this;
        }

        public Builder pageSize(int pageSize) {
            this.pageSize = pageSize;
            return this;
        }

        public Builder totalCount(int totalCount) {
            this.totalCount = totalCount;
            return this;
        }

        public Builder totalPage(int totalPage) {
            this.totalPage = totalPage;
            return this;
        }

        public JSONResult build() {
            JSONResult jSONResult = new JSONResult();
            jSONResult.setMessage(message);
            jSONResult.setIsSuccess(isSuccess);
            jSONResult.setData(data);
            jSONResult.setPageNo(pageNo);
            jSONResult.setPageSize(pageSize);
            jSONResult.setTotalCount(totalCount);
            jSONResult.setTotalPage(totalPage);
            return jSONResult;
        }
    }
}
