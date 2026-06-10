package com.iyuexian.wechat4j;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * WeChat4j Spring Boot Application
 * 
 * @author WeChat4j Team
 */
@SpringBootApplication
@EnableCaching
@EnableAsync
@EnableScheduling
public class Wechat4jApplication {

    public static void main(String[] args) {
        SpringApplication.run(Wechat4jApplication.class, args);
    }
}