package com.nari.bidsystem;

import com.nari.bidsystem.service.impl.PeopleManageServiceImpl;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@MapperScan(value = "com.nari.bidsystem.mapper")
class BidsystemApplicationTests {

    @Autowired
    private PeopleManageServiceImpl peopleManageServiceImpl;

    @Test
    void contextLoads() {
        System.out.println(peopleManageServiceImpl.selectAllByPage(2, 1));
    }

}
