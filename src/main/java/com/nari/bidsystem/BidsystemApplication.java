package com.nari.bidsystem;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
@MapperScan(value = "com.nari.bidsystem.mapper")
public class BidsystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(BidsystemApplication.class, args);
    }

}
