package com.nari.bidsystem.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nari.bidsystem.entity.Status;
import com.nari.bidsystem.entity.User;
import com.nari.bidsystem.service.UserService;
import com.nari.bidsystem.mapper.UserMapper;
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

    public String loginIndexPage(String name, String password) {
        QueryWrapper<User> wrapper=new QueryWrapper<>();
        wrapper.eq("name", name).eq("password", password);
        List<User> users = userMapper.selectList(wrapper);
        if (users.size() > 0) {
            return "{ \"status\": \"" + Status.success + "\" }";
        }else {
            return "{ status: \"" + Status.failure + "\" }";
        }
    }

    public String registerUser(String name, String password) {
        User user = new User();
        user.setName(name);
        user.setPassword(password);
        int res = userMapper.insert(user);
        if (res != 0) {
            return "{ \"status\": \"" + Status.success + "\" }";
        }else {
            return "{ status: \"" + Status.failure + "\" }";
        }
    }

}




