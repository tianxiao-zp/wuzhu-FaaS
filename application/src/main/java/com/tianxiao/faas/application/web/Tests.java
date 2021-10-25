package com.tianxiao.faas.application.web;
import java.util.Map;
import java.util.Set;

public class Tests {
    public String execute(Map<String, String[]> map) {
        StringBuilder result = new StringBuilder();

        Set<String> strings = map.keySet();
        for (String key : strings) {
            result.append(key + "|").append(map.get(key)).append("-");
        }
        User user = new User();
        user.sayHello();

        while (true) {
            System.out.println(1);
        }

    }

    public static class User {
        public void sayHello() {
            System.out.println("hello");
        }
    }
}