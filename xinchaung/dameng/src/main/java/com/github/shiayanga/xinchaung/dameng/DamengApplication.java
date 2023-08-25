package com.github.shiayanga.xinchaung.dameng;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;


@SpringBootApplication
@Configuration
@MapperScan(basePackages = "com.github.shiayanga.xinchaung.dameng.mapper")
public class DamengApplication {

    public static void main(String[] args) {
        SpringApplication.run(DamengApplication.class, args);
    }

}
