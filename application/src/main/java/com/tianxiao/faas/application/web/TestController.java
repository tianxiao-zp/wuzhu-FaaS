package com.tianxiao.faas.application.web;

import com.tianxiao.faas.container.bean.UserBean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class TestController {
    @Resource
    private UserBean userBean;

    @RequestMapping("/test")
    public void test() {
        String name = userBean.getUserName("name");
        System.out.println(name);
    }
}
