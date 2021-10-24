package com.nari.bidsystem.controller;

import com.nari.bidsystem.entity.PageElement;
import com.nari.bidsystem.entity.PeopleManage;
import com.nari.bidsystem.entity.User;
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
@CrossOrigin
public class PeopleManageController {

    @Autowired
    private PeopleManageServiceImpl peopleManageServiceImpl;

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    @ResponseBody
    public String getAll() {
        return peopleManageServiceImpl.selectAll();
    }

    @RequestMapping(value = "/all", method = RequestMethod.POST)
    @ResponseBody
    public String getAllPeople(@RequestBody PageElement pageElement) {
        return peopleManageServiceImpl.selectAllByPage(pageElement.getPage(), pageElement.getNum());
    }

    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    @ResponseBody
    public String insertPeople(@RequestBody PeopleManage peopleManage) {
        String res = peopleManageServiceImpl.insertPeople(peopleManage);
        return res;
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    public String deletePeople(@RequestBody PeopleManage peopleManage) {
        String res = peopleManageServiceImpl.deletePeople(peopleManage.getLoginName());
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
    public String updatePassword(@RequestBody User user) {
        String res = peopleManageServiceImpl.updatePassword(user.getName(), user.getPassword());
        return res;
    }

    @RequestMapping(value = "/select", method = RequestMethod.POST)
    @ResponseBody
    public String selectByCondition(@RequestBody PeopleManage peopleManage) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        String res = peopleManageServiceImpl.selectByCondition(peopleManage);
        return res;
    }

}
