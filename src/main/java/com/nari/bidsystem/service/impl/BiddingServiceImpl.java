package com.nari.bidsystem.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nari.bidsystem.entity.Bidding;
import com.nari.bidsystem.entity.PeopleManage;
import com.nari.bidsystem.entity.Status;
import com.nari.bidsystem.service.BiddingService;
import com.nari.bidsystem.mapper.BiddingMapper;
import com.nari.bidsystem.util.PageUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author ZhangXD
 */
@Service
public class BiddingServiceImpl extends ServiceImpl<BiddingMapper, Bidding>
        implements BiddingService{

    private final BiddingMapper biddingMapper;

    @Autowired
    public BiddingServiceImpl(BiddingMapper biddingMapper){
        this.biddingMapper = biddingMapper;
    }

    private static final Logger logger = LoggerFactory.getLogger(BiddingServiceImpl.class);

    /**
     * 插入新标书
     * @param bidding 标书对象
     * @return 状态码（success/failure）
     */
    public int insertBidding(Bidding bidding) {
        int res = biddingMapper.insert(bidding);
        if (res > 0) {
            logger.info("新标<" + bidding.getBidId() + ">已添加");
        }else {
            logger.info("新标<" + bidding.getBidId() + ">添加失败");
        }
        return res;
    }

    /**
     * 标书更新
     * @param bidding 标书对象
     * @return 状态码（success/failure）
     */
    public int updateBidding(Bidding bidding) {
        QueryWrapper<Bidding> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("login_name", bidding.getBidId());
        int res = biddingMapper.update(bidding, queryWrapper);
        if (res > 0) {
            logger.info("标书<" + bidding.getBidId() + ">已更新");
        }else {
            logger.info("标书<" + bidding.getBidId() + ">更新失败");
        }
        return res;
    }

    /**
     * 标书删除
     * @param bidId 标书唯一ID
     * @return 删除结果（success/failure）
     */
    public int deleteBidding(String bidId) {
        QueryWrapper<Bidding> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("bid_id", bidId);
        int res = biddingMapper.delete(queryWrapper);
        if (res > 0) {
            logger.info("标书<" + bidId + ">已删除");
        }else {
            logger.info("标书<" + bidId + ">删除失败");
        }
        return res;
    }

    /**
     * 标书查询，按页分页查询
     * @param page 页数
     * @param num 一页上显示的个数
     * @return json格式的查询结果
     */
    public PageUtils<Bidding> selectAllByPage(int page, int num) {
        IPage<Bidding> biddingIPage = new Page<>(page, num);
        biddingIPage = biddingMapper.selectPage(biddingIPage, null);
        PageUtils<Bidding> pageUtils = new PageUtils(biddingIPage);
        logger.info("标书分页<" + page + "," + num + ">已查询");
        return pageUtils;
    }

    /**
     * 所有正在招标的项目
     * @param page
     * @param num
     * @return
     */
    public PageUtils<Bidding> selectAllBidding(int page, int num) {
        IPage<Bidding> biddingIPage = new Page<>(page, num);
        QueryWrapper<Bidding> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("state", 1);
        biddingIPage = biddingMapper.selectPage(biddingIPage, queryWrapper);
        PageUtils<Bidding> pageUtils = new PageUtils(biddingIPage);
        logger.info("标书分页<" + page + "," + num + ">已查询");
        return pageUtils;
    }

    /**
     * 依据反射原理获取对象的所有属性值
     * @param object
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     * @return Map 属性名以及属性值
     */
    public Map<String, Object> searchMethod(Object object) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Field[] field = object.getClass().getDeclaredFields();
        Map<String, Object> map = new HashMap<>(16);
        for (int i = 0; i < field.length; i++) {
            String fieldName = field[i].getName();
            String methodStr=fieldName.substring(0,1).toUpperCase()+fieldName.substring(1);
            Method getterMethod = object.getClass().getMethod("get"+methodStr);
            Object res = getterMethod.invoke(object);

            String name = fieldName.replaceAll("[A-Z]", "_$0").toLowerCase();
            map.put(name, res);
        }
        return map;
    }

    public List<Bidding> selectByCondition(Bidding bidding) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        QueryWrapper<Bidding> queryWrapper = new QueryWrapper<>();
        Map<String, Object> map = searchMethod(bidding);
        StringBuilder s = new StringBuilder();
        for(Map.Entry<String, Object> entry:map.entrySet()){
            if (entry.getValue() == null || entry.getValue() == "") {
                continue;
            }else {
                s.append(entry.getKey() + "->" +entry.getValue() + ";");
                queryWrapper.like(entry.getKey(), entry.getValue());
            }
        }
        List<Bidding> res = biddingMapper.selectList(queryWrapper);
        logger.info("查询<" + s + ">成功" + "，返回<" + res.size() + ">条数据");
        return res;
    }

    /**
     * 根据bidId选择标书
     * @param bidId
     * @return
     */
    public Bidding select(int bidId) {
        QueryWrapper<Bidding> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("bid_id", bidId);
        Bidding bidding = biddingMapper.selectOne(queryWrapper);
        if (bidding != null){
            logger.info("查询标书<" + bidId + ">成功");
        }else {
            logger.info("查询标书<" + bidId + ">失败");
        }
        return bidding;
    }

    /**
     * 文件传输
     * @param multipartFile
     * @return
     */
    public int addFile(Integer bidId, MultipartFile multipartFile) {
        String filePath = "E:\\Fork\\Files";
        if (multipartFile.isEmpty()) {
            logger.warn(bidId + "=>保存文件失败，文件为空");
            return 0;
        }
        String multipartFileName = multipartFile.getOriginalFilename();
        File dest = new File(new File(filePath).getAbsolutePath()+ "/" + multipartFileName);
        if (!dest.getParentFile().exists()) {
            dest.getParentFile().mkdirs();
        }
        try {
            multipartFile.transferTo(dest);
            logger.info(bidId + "=>保存文件成功");
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
}




