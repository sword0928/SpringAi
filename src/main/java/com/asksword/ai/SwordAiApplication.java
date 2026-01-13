package com.asksword.ai;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.asksword.ai.biz.mappers")
public class SwordAiApplication {

    public static void main(String[] args) {
        SpringApplication.run(SwordAiApplication.class, args);
    }

}
