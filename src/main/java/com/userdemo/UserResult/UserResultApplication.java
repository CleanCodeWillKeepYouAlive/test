package com.userdemo.UserResult;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching(proxyTargetClass=true)
public class UserResultApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserResultApplication.class, args);
    }
}
