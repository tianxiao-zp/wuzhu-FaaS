package com.tianxiao.faas.common;

import java.io.Serializable;

public class JSONResult implements Serializable {
    private static final long serialVersionUID = -7539749553868932437L;

    private String message;
    private boolean isSuccess;
    private Object data;
    private Long pageNo;
    private Long pageSize;
    private Long totalCount;
    private Long totalPage;

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

    public Long getPageNo() {
        return pageNo;
    }

    public void setPageNo(Long pageNo) {
        this.pageNo = pageNo;
    }

    public Long getPageSize() {
        return pageSize;
    }

    public void setPageSize(Long pageSize) {
        this.pageSize = pageSize;
    }

    public Long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Long totalCount) {
        this.totalCount = totalCount;
    }

    public Long getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(Long totalPage) {
        this.totalPage = totalPage;
    }


    public static final class Builder {
        private String message;
        private boolean isSuccess;
        private Object data;
        private Long pageNo;
        private Long pageSize;
        private Long totalCount;
        private Long totalPage;

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

        public Builder pageNo(Long pageNo) {
            this.pageNo = pageNo;
            return this;
        }

        public Builder pageSize(Long pageSize) {
            this.pageSize = pageSize;
            return this;
        }

        public Builder totalCount(Long totalCount) {
            this.totalCount = totalCount;
            return this;
        }

        public Builder totalPage(Long totalPage) {
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
