package com.dy.dyweb;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.dy.dyweb.mapper")
public class DyWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(DyWebApplication.class, args);
    }

}
