package com.nari.bidsystem.controller;

import com.nari.bidsystem.entity.PageElement;
import com.nari.bidsystem.entity.PeopleManage;
import com.nari.bidsystem.entity.User;
import com.nari.bidsystem.service.impl.PeopleManageServiceImpl;
import com.nari.bidsystem.util.PageUtils;
import com.nari.bidsystem.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * @Author ZhangXD
 * @Date 2021/10/19 14:23
 * @Description
 */
@Controller
@RequestMapping("/people")
@CrossOrigin
public class PeopleManageController {

    private final PeopleManageServiceImpl peopleManageServiceImpl;

    @Autowired
    public PeopleManageController(PeopleManageServiceImpl peopleManageServiceImpl){
        this.peopleManageServiceImpl = peopleManageServiceImpl;
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    @ResponseBody
    public List<PeopleManage> getAll() {
        return peopleManageServiceImpl.selectAll();
    }

    @RequestMapping(value = "/all", method = RequestMethod.POST)
    @ResponseBody
    public ResultUtil getAllPeople(@RequestBody PageElement pageElement) {
        ResultUtil resultUtil = new ResultUtil();
        PageUtils pageUtils = peopleManageServiceImpl.selectAllByPage(pageElement.getPage(), pageElement.getNum());
        return resultUtil.addData(pageUtils).success();
    }

    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    @ResponseBody
    public ResultUtil insertPeople(@RequestBody PeopleManage peopleManage) {
        ResultUtil resultUtil = new ResultUtil();
        int res = peopleManageServiceImpl.insertPeople(peopleManage);
        if (res > 0) {
            return resultUtil.success();
        }else {
            return resultUtil.failure();
        }
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    public ResultUtil deletePeople(@RequestBody PeopleManage peopleManage) {
        ResultUtil resultUtil = new ResultUtil();
        int res = peopleManageServiceImpl.deletePeople(peopleManage.getLoginName());
        if (res > 0) {
            return resultUtil.success();
        }else{
            return resultUtil.failure();
        }
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public ResultUtil updatePeople(@RequestBody PeopleManage peopleManage) {
        ResultUtil resultUtil = new ResultUtil();
        int res = peopleManageServiceImpl.updatePeople(peopleManage);
        if (res > 0) {
            return resultUtil.success();
        } else {
            return resultUtil.failure();
        }
    }

    @RequestMapping(value = "/updatePassword", method = RequestMethod.POST)
    @ResponseBody
    public ResultUtil updatePassword(@RequestBody User user) {
        ResultUtil resultUtil = new ResultUtil();
        int res = peopleManageServiceImpl.updatePassword(user.getName(), user.getPassword());
        if (res > 0) {
            return resultUtil.success();
        }else {
            return resultUtil.failure();
        }
    }

    @RequestMapping(value = "/select", method = RequestMethod.POST)
    @ResponseBody
    public ResultUtil selectByCondition(@RequestBody PeopleManage peopleManage) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        ResultUtil resultUtil = new ResultUtil();
        List<PeopleManage> res = peopleManageServiceImpl.selectByCondition(peopleManage);
        if (res.size() > 0) {
            return resultUtil.addData(res).success();
        }else {
            return resultUtil.failure();
        }
    }

}
