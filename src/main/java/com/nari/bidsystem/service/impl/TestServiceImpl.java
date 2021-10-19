package com.nari.bidsystem.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nari.bidsystem.dao.Test;
import com.nari.bidsystem.service.TestService;
import com.nari.bidsystem.mapper.TestMapper;
import org.springframework.stereotype.Service;

/**
 *
 */
@Service
public class TestServiceImpl extends ServiceImpl<TestMapper, Test>
    implements TestService{

}




