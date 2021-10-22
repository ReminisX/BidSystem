package com.nari.bidsystem.controller;

import com.nari.bidsystem.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * @Author ZhangXD
 * @Date 2021/10/19 9:49
 * @Description
 */
@Controller
@RequestMapping("/index")
@CrossOrigin
public class IndexPageController {

    @Autowired
    private UserServiceImpl userServiceImpl;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public String loginPage(@RequestParam(name = "name") String name, @RequestParam(name = "password") String password) {
        return userServiceImpl.loginIndexPage(name, password);
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    @ResponseBody
    public String registerUser(@RequestParam(name = "name") String name, @RequestParam(name = "password") String password){
        return userServiceImpl.registerUser(name, password);
    }

}
