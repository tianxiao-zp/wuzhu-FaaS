package com.tianxiao.faas.container.bean;

import org.springframework.stereotype.Service;

/**
 * 测试的bean
 */
@Service
public class UserBean {

    public String getUserName(String name) {
        return name;
    }
}
