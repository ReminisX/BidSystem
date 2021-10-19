package com.nari.bidsystem.controller;

import com.nari.bidsystem.entity.PeopleManage;
import com.nari.bidsystem.service.impl.PeopleManageServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.InvocationTargetException;

/**
 * @Author ZhangXD
 * @Date 2021/10/19 14:23
 * @Description
 */
@Controller
@RequestMapping("/people")
public class PeopleManageController {

    @Autowired
    private PeopleManageServiceImpl peopleManageServiceImpl;

    @RequestMapping(value = "/all", method = RequestMethod.POST)
    @ResponseBody
    public String getAllPeople(@RequestParam("page") int page, @RequestParam("num") int num) {
        return peopleManageServiceImpl.selectAllByPage(page, num);
    }

    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    @ResponseBody
    public String insertPeople(@RequestBody PeopleManage peopleManage) {
        String res = peopleManageServiceImpl.insertPeople(peopleManage);
        return res;
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    public String deletePeople(@RequestParam("loginName") String loginName) {
        String res = peopleManageServiceImpl.deletePeople(loginName);
        return res;
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public String updatePeople(@RequestBody PeopleManage peopleManage) {
        String res = peopleManageServiceImpl.updatePeople(peopleManage);
        return res;
    }

    @RequestMapping(value = "/updatePassword", method = RequestMethod.POST)
    @ResponseBody
    public String updatePassword(@RequestParam("name") String name, @RequestParam("password") String password) {
        String res = peopleManageServiceImpl.updatePassword(name, password);
        return res;
    }

    @RequestMapping(value = "/select", method = RequestMethod.POST)
    @ResponseBody
    public String selectByCondition(@RequestBody PeopleManage peopleManage) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        String res = peopleManageServiceImpl.selectByCondition(peopleManage);
        return res;
    }

}
