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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *
 * @author ZhangXD
 */
@Service
public class BiddingServiceImpl extends ServiceImpl<BiddingMapper, Bidding>
        implements BiddingService{

    @Autowired
    private BiddingMapper biddingMapper;

    private static final Logger logger = LoggerFactory.getLogger(PeopleManageServiceImpl.class);

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
    public List<Bidding> selectAllByPage(int page, int num) {
        IPage<Bidding> peopleManageIPage = new Page<>(page, num);
        peopleManageIPage = biddingMapper.selectPage(peopleManageIPage, null);
        List<Bidding> res = peopleManageIPage.getRecords();
        logger.info("标书分页<" + page + "," + num + ">已查询");
        return res;
    }

}




