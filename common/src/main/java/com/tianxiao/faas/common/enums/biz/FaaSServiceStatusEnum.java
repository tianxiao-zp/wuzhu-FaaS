package com.tianxiao.faas.common.enums.biz;

public enum FaaSServiceStatusEnum {
    WRITING(0, "编写中"),
    SAVE(1, "保存"),
    OFFLINE(2, "线下发布"),
    PRE(3, "预发发布"),
    ONLINE(4, "线上发布");

    private int status;

    private String desc;

    FaaSServiceStatusEnum(int status, String desc) {
        this.status = status;
        this.desc = desc;
    }

    public int getStatus() {
        return status;
    }

    public String getDesc() {
        return desc;
    }
}
