package com.nari.bidsystem;

import com.nari.bidsystem.service.TestService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class BidsystemApplicationTests {

    @Autowired
    TestService testService;

    @Test
    void contextLoads() {

        

    }

}
