package com.nari.bidsystem;

import org.junit.jupiter.api.Test;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@MapperScan(value = "com.nari.bidsystem.mapper")
class BidsystemApplicationTests {

    @Test
    void contextLoads() {


    }

}
