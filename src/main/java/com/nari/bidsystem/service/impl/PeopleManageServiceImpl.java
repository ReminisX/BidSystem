package com.nari.bidsystem.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nari.bidsystem.entity.PeopleManage;
import com.nari.bidsystem.entity.Status;
import com.nari.bidsystem.entity.User;
import com.nari.bidsystem.mapper.UserMapper;
import com.nari.bidsystem.service.PeopleManageService;
import com.nari.bidsystem.mapper.PeopleManageMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 人员管理页面服务
 * @author ZhangXD
 */
@Service
public class PeopleManageServiceImpl extends ServiceImpl<PeopleManageMapper, PeopleManage>
    implements PeopleManageService{

    @Autowired
    private PeopleManageMapper peopleManageMapper;

    @Autowired
    private UserMapper userMapper;

    private static final Logger logger = LoggerFactory.getLogger(PeopleManageServiceImpl.class);

    /**
     * 普通的查询，未分页
     * @return
     */
    public String selectAll() {
        QueryWrapper<PeopleManage> queryWrapper = new QueryWrapper<>();
        List<PeopleManage> res = peopleManageMapper.selectList(queryWrapper);
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        for (int i = 0; i < res.size(); i++) {
            sb.append("\"" + i + "\":" + JSON.toJSONString(res.get(i)));
            if (i < res.size()-1) {
                sb.append(",");
            }
        }
        sb.append("}");
        return sb.toString();
    }

    /**
     * 分页查询所有人员信息
     * @param page 需要查询的页数
     * @param num 需要查询的数量
     * @return JSON格式的查询结果
     */
    public String selectAllByPage(int page, int num) {
        IPage<PeopleManage> peopleManageIPage = new Page<>(page, num);
        peopleManageIPage = peopleManageMapper.selectPage(peopleManageIPage, null);
        List<PeopleManage> res = peopleManageIPage.getRecords();
        logger.info("人员管理分页<" + page + "," + num + ">已查询");
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        int index = (page - 1) * num;
        for (int i = 0; i < res.size(); i++) {
            index++;
            sb.append("\"" + index + "\":" + JSON.toJSONString(res.get(i)));
            if (i < res.size()-1) {
                sb.append(",");
            }
        }
        sb.append("}");
        return sb.toString();
    }

    /**
     * 插入一个人员信息
     * @param peopleManage 人员信息对象
     * @return success or failure
     */
    public String insertPeople(PeopleManage peopleManage) {
        int res = peopleManageMapper.insert(peopleManage);
        if (res > 0) {
            logger.info("用户<" + peopleManage.getName() + ">已插入");
            return "{ \"status\": \"" + Status.success + "\" }";
        }else {
            logger.info("用户<" + peopleManage.getName() + ">插入失败");
            return "{ status: \"" + Status.failure + "\" }";
        }
    }

    /**
     * 根据loginName删除对象
     * @param loginName
     * @return
     */
    public String deletePeople(String loginName) {
        QueryWrapper<PeopleManage> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("login_name", loginName);
        int res = peopleManageMapper.delete(queryWrapper);
        if (res > 0) {
            logger.info("用户<" + loginName + ">已删除");
            return "{ \"status\": \"" + Status.success + "\" }";
        }else {
            logger.info("用户<" + loginName + ">删除失败");
            return "{ status: \"" + Status.failure + "\" }";
        }
    }

    /**
     * 更新用户信息
     * @param peopleManage 用户类实体
     * @return
     */
    public String updatePeople(PeopleManage peopleManage) {
        QueryWrapper<PeopleManage> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("login_name", peopleManage.getLoginName());
        int res = peopleManageMapper.update(peopleManage, queryWrapper);
        if (res > 0) {
            logger.info("用户<" + peopleManage.getLoginName() + ">已更新");
            return "{ \"status\": \"" + Status.success + "\" }";
        }else {
            logger.info("用户<" + peopleManage.getLoginName() + ">更新失败");
            return "{ status: \"" + Status.failure + "\" }";
        }
    }

    /**
     * 更新用户密码
     * @param name 用户名
     * @param password 用户密码
     * @return 返回处理结果
     */
    public String updatePassword(String name, String password) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name", name);
        User user = new User();
        user.setName(name);
        user.setPassword(password);
        int res = userMapper.update(user, queryWrapper);
        if (res > 0) {
            logger.info("用户<" + name + ">密码已更新");
            return "{ \"status\": \"" + Status.success + "\" }";
        }else {
            logger.info("用户<" + name + ">密码更新失败");
            return "{ status: \"" + Status.failure + "\" }";
        }
    }

}




