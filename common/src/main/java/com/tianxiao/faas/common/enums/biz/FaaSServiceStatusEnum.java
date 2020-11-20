package com.tianxiao.faas.common.enums.biz;

import com.google.common.collect.Lists;

import java.util.List;

public enum FaaSServiceStatusEnum {
    WRITING(0, "编写中"),
    SAVE(1, "开发环境"),
    OFFLINE(2, "线下发布"),
    PRE(3, "预发发布"),
    ONLINE(4, "线上发布"),
    HISTORY_VERSION(5, "历史版本"),
    ABANDON(6, "下线/废弃");

    private static final List<FaaSServiceStatusEnum> FINAL_STATUS = Lists.newArrayList(FaaSServiceStatusEnum.ONLINE, FaaSServiceStatusEnum.HISTORY_VERSION);

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

    public static FaaSServiceStatusEnum get(Integer status) {
        if (status == null) {
            return null;
        }
        for (FaaSServiceStatusEnum statusEnum : values()) {
            if (status.equals(statusEnum.getStatus())) {
                return statusEnum;
            }
        }
        return null;
    }

    public static boolean canModified(Integer status) {
        FaaSServiceStatusEnum statusEnum = get(status);
        return !FINAL_STATUS.contains(statusEnum);
    }

    public static boolean canModified(FaaSServiceStatusEnum status) {
        return !FINAL_STATUS.contains(status);
    }
}
