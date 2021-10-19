package com.nari.bidsystem.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nari.bidsystem.entity.Status;
import com.nari.bidsystem.entity.User;
import com.nari.bidsystem.service.UserService;
import com.nari.bidsystem.mapper.UserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *
 * @author ZhangXD
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService{

    @Autowired
    private UserMapper userMapper;

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    public String loginIndexPage(String name, String password) {
        QueryWrapper<User> wrapper=new QueryWrapper<>();
        wrapper.eq("name", name).eq("password", password);
        List<User> users = userMapper.selectList(wrapper);
        if (users.size() > 0) {
            logger.info("用户<" + users.get(0).getName() + ">已登录");
            return "{ \"status\": \"" + Status.success + "\" }";
        }else {
            logger.info("用户不存在");
            return "{ status: \"" + Status.failure + "\" }";
        }
    }

    public String registerUser(String name, String password) {
        User user = new User();
        user.setName(name);
        user.setPassword(password);
        int res = userMapper.insert(user);
        if (res != 0) {
            logger.info("用户<" + user.getName() + ">已注册");
            return "{ \"status\": \"" + Status.success + "\" }";
        }else {
            logger.info("用户注册失败");
            return "{ status: \"" + Status.failure + "\" }";
        }
    }

}




