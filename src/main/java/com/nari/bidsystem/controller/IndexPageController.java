package com.nari.bidsystem.controller;

import com.nari.bidsystem.entity.User;
import com.nari.bidsystem.service.impl.UserServiceImpl;
import com.nari.bidsystem.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.sql.ResultSet;
import java.util.List;

/**
 * @Author ZhangXD
 * @Date 2021/10/19 9:49
 * @Description
 */
@Controller
@RequestMapping("/index")
@CrossOrigin
public class IndexPageController {

    private final UserServiceImpl userServiceImpl;

    @Autowired
    public IndexPageController(UserServiceImpl userServiceImpl){
        this.userServiceImpl = userServiceImpl;
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public ResultUtil loginPage(@RequestBody User user) {
        ResultUtil resultUtil = new ResultUtil();
        List<User> res = userServiceImpl.loginIndexPage(user.getName(), user.getPassword());
        if (res.size() == 1) {
            return resultUtil.success().addParam("identify" ,res.get(0).getIdentity());
        }else if (res.size() > 1) {
            return resultUtil.addParam("error", "登录异常，有多个账号重复");
        }else{
            return resultUtil.failure();
        }
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    @ResponseBody
    public ResultUtil registerUser(@RequestParam(name = "name") String name, @RequestParam(name = "password") String password){
        ResultUtil resultUtil = new ResultUtil();
        int res = userServiceImpl.registerUser(name, password);
        if (res > 0) {
            return resultUtil.success();
        }else{
            return resultUtil.failure();
        }
    }

}
