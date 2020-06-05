package com.tianxiao.faas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
public class FaaSApplication {

    public static void main(String[] args) {
        SpringApplication.run(FaaSApplication.class);
    }
}
