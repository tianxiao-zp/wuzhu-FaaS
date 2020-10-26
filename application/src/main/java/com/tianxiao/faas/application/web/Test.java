package com.tianxiao.faas.application.web;

import com.tianxiao.faas.container.bean.UserBean;

public class Test {


    public Object test(UserBean name, String st) {
        String userName = name.getUserName("1");
        return userName + st;
    }
}
