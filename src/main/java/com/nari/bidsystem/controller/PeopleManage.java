package com.nari.bidsystem.controller;

import com.nari.bidsystem.service.impl.PeopleManageServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Author ZhangXD
 * @Date 2021/10/19 14:23
 * @Description
 */
@Controller
@RequestMapping("/people")
public class PeopleManage {

    @Autowired
    private PeopleManageServiceImpl peopleManageServiceImpl;

    @RequestMapping(value = "/all", method = RequestMethod.POST)
    @ResponseBody
    public String getAllPeople(@RequestParam("page") int page, @RequestParam("num") int num) {
        return peopleManageServiceImpl.selectAllByPage(page, num);
    }

}
