package com.tianxiao.faas.container.bean;

import org.springframework.stereotype.Service;

@Service
public class UserBean {

    public String getUserName(String name) {
        return name;
    }
}
