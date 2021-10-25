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
import com.nari.bidsystem.util.PageUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;

/**
 * 人员管理页面服务
 * @author ZhangXD
 */
@Service
public class PeopleManageServiceImpl extends ServiceImpl<PeopleManageMapper, PeopleManage>
    implements PeopleManageService{

    private final PeopleManageMapper peopleManageMapper;

    private final UserMapper userMapper;

    @Autowired
    public PeopleManageServiceImpl(UserMapper userMapper, PeopleManageMapper peopleManageMapper){
        this.peopleManageMapper = peopleManageMapper;
        this.userMapper = userMapper;
    }

    private static final Logger logger = LoggerFactory.getLogger(PeopleManageServiceImpl.class);

    /**
     * 普通的查询，未分页
     * @return
     */
    public List<PeopleManage> selectAll() {
        QueryWrapper<PeopleManage> queryWrapper = new QueryWrapper<>();
        List<PeopleManage> res = peopleManageMapper.selectList(queryWrapper);
        return res;
    }

    /**
     * 分页查询所有人员信息
     * @param page 需要查询的页数
     * @param num 需要查询的数量
     * @return JSON格式的查询结果
     */
    public PageUtils<PeopleManage> selectAllByPage(int page, int num) {
        IPage<PeopleManage> peopleManageIPage = new Page<>(page, num);
        peopleManageIPage = peopleManageMapper.selectPage(peopleManageIPage, null);
        PageUtils<PeopleManage> pageUtils = new PageUtils<>(peopleManageIPage);
        logger.info("人员管理分页<" + page + "," + num + ">已查询");
        return pageUtils;
    }

    /**
     * 插入一个人员信息
     * @param peopleManage 人员信息对象
     * @return success or failure
     */
    public int insertPeople(PeopleManage peopleManage) {
        int res = peopleManageMapper.insert(peopleManage);
        if (res > 0) {
            logger.info("用户<" + peopleManage.getLoginName() + ">已插入");
        }else {
            logger.info("用户<" + peopleManage + ">插入失败");
        }
        return res;
    }

    /**
     * 根据loginName删除对象
     * @param loginName
     * @return
     */
    public int deletePeople(String loginName) {
        QueryWrapper<PeopleManage> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("login_name", loginName);
        int res = peopleManageMapper.delete(queryWrapper);
        if (res > 0) {
            logger.info("删除" + loginName + "成功");
        }else{
            logger.info("删除" + loginName + "失败");
        }
        return res;
    }

    /**
     * 更新用户信息
     * @param peopleManage 用户类实体
     * @return
     */
    public int updatePeople(PeopleManage peopleManage) {
        QueryWrapper<PeopleManage> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("login_name", peopleManage.getLoginName());
        int res = peopleManageMapper.update(peopleManage, queryWrapper);
        if (res > 0) {
            logger.info("用户<" + peopleManage.getLoginName() + ">已更新");
        }else {
            logger.info("用户<" + peopleManage.getLoginName() + ">更新失败");
        }
        return res;
    }

    /**
     * 更新用户密码
     * @param name 用户名
     * @param password 用户密码
     * @return 返回处理结果
     */
    public int updatePassword(String name, String password) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name", name);
        User user = new User();
        user.setName(name);
        user.setPassword(password);
        int res = userMapper.update(user, queryWrapper);
        if (res > 0) {
            logger.info("用户<" + name + ">密码已更新");
        }else {
            logger.info("用户<" + name + ">密码更新失败");
        }
        return res;
    }

    /**
     * 依据反射原理获取对象的所有属性值
     * @param object
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     * @return Map 属性名以及属性值
     */
    public Map<String, String> searchMethod(Object object) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Field[] field = object.getClass().getDeclaredFields();
        Map<String, String> map = new HashMap<>(16);
        for (int i = 0; i < field.length - 1; i++) {
            String fieldName = field[i].getName();
            String methodStr=fieldName.substring(0,1).toUpperCase()+fieldName.substring(1);
            Method getterMethod = object.getClass().getMethod("get"+methodStr);
            String res = (String)getterMethod.invoke(object);
            String name = fieldName.replaceAll("[A-Z]", "_$0").toLowerCase();
            map.put(name, res);
        }
        return map;
    }

    /**
     * 根据提供的不同的属性值进行查询
     * @param peopleManage 需要查询的对象
     * @return 返回查询的列表
     */
    public List<PeopleManage> selectByCondition(PeopleManage peopleManage) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        QueryWrapper<PeopleManage> queryWrapper = new QueryWrapper<>();
        Map<String, String> map = searchMethod(peopleManage);
        StringBuilder s = new StringBuilder();
        for(Map.Entry<String, String> entry:map.entrySet()){
            if (entry.getValue() == null || entry.getValue() == "") {
                continue;
            }else {
                s.append(entry.getKey() + "->" +entry.getValue() + ";");
                queryWrapper.like(entry.getKey(), entry.getValue());
            }
        }
        List<PeopleManage> res = peopleManageMapper.selectList(queryWrapper);
        logger.info("查询<" + s + ">成功" + "，返回<" + res.size() + ">条数据");
        return res;
    }

}




