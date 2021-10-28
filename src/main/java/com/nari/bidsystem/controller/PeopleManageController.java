package com.nari.bidsystem.controller;

import com.nari.bidsystem.entity.Bidding;
import com.nari.bidsystem.entity.PageElement;
import com.nari.bidsystem.entity.PeopleManage;
import com.nari.bidsystem.entity.User;
import com.nari.bidsystem.service.impl.PeopleManageServiceImpl;
import com.nari.bidsystem.util.PageUtils;
import com.nari.bidsystem.util.ResultUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Set;

/**
 * @Author ZhangXD
 * @Date 2021/10/19 14:23
 * @Description
 */
@Controller
@RequestMapping("/people")
@CrossOrigin
@ResponseBody
@Api
public class PeopleManageController {

    private final PeopleManageServiceImpl peopleManageServiceImpl;

    @Autowired
    public PeopleManageController(PeopleManageServiceImpl peopleManageServiceImpl){
        this.peopleManageServiceImpl = peopleManageServiceImpl;
    }

    @ApiOperation(value = "简单查询所有人员信息")
    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public List<PeopleManage> getAll() {
        return peopleManageServiceImpl.selectAll();
    }

    @ApiOperation(value = "分页查询所有人员信息")
    @RequestMapping(value = "/all", method = RequestMethod.POST)
    public ResultUtil getAllPeople(@RequestBody PageElement pageElement) {
        ResultUtil resultUtil = new ResultUtil();
        PageUtils pageUtils = peopleManageServiceImpl.selectAllByPage(pageElement.getPage(), pageElement.getNum());
        return resultUtil.addData(pageUtils).success();
    }

    @ApiOperation(value = "插入一个人员信息")
    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    public ResultUtil insertPeople(@RequestBody PeopleManage peopleManage) {
        ResultUtil resultUtil = new ResultUtil();
        int res = peopleManageServiceImpl.insertPeople(peopleManage);
        if (res > 0) {
            return resultUtil.success();
        }else {
            return resultUtil.failure();
        }
    }

    @ApiOperation(value = "根据ID删除一个人员信息")
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public ResultUtil deletePeople(@RequestBody PeopleManage peopleManage) {
        ResultUtil resultUtil = new ResultUtil();
        int res = peopleManageServiceImpl.deletePeople(peopleManage.getLoginName());
        if (res > 0) {
            return resultUtil.success();
        }else{
            return resultUtil.failure();
        }
    }

    @ApiOperation(value = "根据ID删除一个人员信息")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ResultUtil updatePeople(@RequestBody PeopleManage peopleManage) {
        ResultUtil resultUtil = new ResultUtil();
        int res = peopleManageServiceImpl.updatePeople(peopleManage);
        if (res > 0) {
            return resultUtil.success();
        } else {
            return resultUtil.failure();
        }
    }

    @ApiOperation(value = "根据ID更新人员登录密码")
    @RequestMapping(value = "/updatePassword", method = RequestMethod.POST)
    public ResultUtil updatePassword(@RequestBody User user) {
        ResultUtil resultUtil = new ResultUtil();
        int res = peopleManageServiceImpl.updatePassword(user.getName(), user.getPassword());
        if (res > 0) {
            return resultUtil.success();
        }else {
            return resultUtil.failure();
        }
    }

    @ApiOperation(value = "根据ID查询一个人员的全部信息")
    @RequestMapping(value = "/selectOne")
    public ResultUtil selectOne(PeopleManage peopleManage){
        ResultUtil resultUtil = new ResultUtil();
        PeopleManage peopleManage1 = peopleManageServiceImpl.selectOne(peopleManage);
        if (peopleManage1 != null){
            return resultUtil.addData(peopleManage1).success();
        }else{
            return resultUtil.failure();
        }
    }

    @ApiOperation(value = "模糊查询所有符合条件的人员信息")
    @RequestMapping(value = "/select", method = RequestMethod.POST)
    public ResultUtil selectByCondition(@RequestBody PeopleManage peopleManage) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        ResultUtil resultUtil = new ResultUtil();
        List<PeopleManage> res = peopleManageServiceImpl.selectByCondition(peopleManage);
        if (res.size() > 0) {
            return resultUtil.addData(res).success();
        }else {
            return resultUtil.failure();
        }
    }

    @ApiOperation(value = "查询所有内部/供应商人员信息")
    @RequestMapping(value = "/selectIdentity", method = RequestMethod.POST)
    public ResultUtil selectIdentity(@RequestBody PeopleManage peopleManage){
        ResultUtil resultUtil = new ResultUtil();
        Set<String> res = peopleManageServiceImpl.selectIdentity(peopleManage);
        if (res.size() > 0) {
            return resultUtil.addData(res).success();
        }else {
            return resultUtil.failure();
        }
    }

}
