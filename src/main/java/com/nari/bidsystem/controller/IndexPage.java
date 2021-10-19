package com.nari.bidsystem.controller;

import com.nari.bidsystem.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Author ZhangXD
 * @Date 2021/10/19 9:49
 * @Description
 */
@Controller
public class IndexPage {

    @Autowired
    private UserServiceImpl userServiceImpl;

    @RequestMapping(value = "/index/login", method = RequestMethod.POST)
    @ResponseBody
    public String loginPage(@RequestParam(name = "name") String name, @RequestParam(name = "password") String password) {
        return userServiceImpl.loginIndexPage(name, password);
    }

    @RequestMapping(value = "/index/register", method = RequestMethod.POST)
    @ResponseBody
    public String registerUser(@RequestParam(name = "name") String name, @RequestParam(name = "password") String password){
        return userServiceImpl.registerUser(name, password);
    }

}
