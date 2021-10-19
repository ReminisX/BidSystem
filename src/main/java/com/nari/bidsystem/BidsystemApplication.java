package com.nari.bidsystem;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(value = "com.nari.bidsystem.dao")
public class BidsystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(BidsystemApplication.class, args);
    }

}
