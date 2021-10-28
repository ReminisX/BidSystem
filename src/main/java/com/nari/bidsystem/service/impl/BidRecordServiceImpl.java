package com.nari.bidsystem.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nari.bidsystem.entity.BidRecord;
import com.nari.bidsystem.entity.Bidding;
import com.nari.bidsystem.entity.PeopleManage;
import com.nari.bidsystem.mapper.BiddingMapper;
import com.nari.bidsystem.mapper.PeopleManageMapper;
import com.nari.bidsystem.service.BidRecordService;
import com.nari.bidsystem.mapper.BidRecordMapper;
import com.nari.bidsystem.util.PageUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 投标记录服务
 * @author ZhangXD
 */
@Service
public class BidRecordServiceImpl extends ServiceImpl<BidRecordMapper, BidRecord>
    implements BidRecordService{

    private final BidRecordMapper bidRecordMapper;

    private final PeopleManageMapper peopleManageMapper;

    private final BiddingMapper biddingMapper;

    private final Logger logger = LoggerFactory.getLogger(BidRecordServiceImpl.class);

    @Autowired
    public BidRecordServiceImpl(BidRecordMapper bidRecordMapper, PeopleManageMapper peopleManageMapper, BiddingMapper biddingMapper){
        this.bidRecordMapper = bidRecordMapper;
        this.peopleManageMapper = peopleManageMapper;
        this.biddingMapper = biddingMapper;
    }

    public BidRecord addUuid(BidRecord bidRecord){
        String uuid = bidRecord.getBidId() + "_" + bidRecord.getUserId() + "_" + bidRecord.getBidCount();
        bidRecord.setUuid(uuid);
        return bidRecord;
    }

    public int addBidRecord(BidRecord bidRecord) {
        bidRecord = addUuid(bidRecord);
        bidRecord.setUpdateTime(new Date());
        bidRecord.setCreateTime(new Date());
        logger.info(String.valueOf(bidRecord));
        int res = bidRecordMapper.insert(bidRecord);
        if (res > 0) {
            logger.info("标书记录<" + bidRecord.getUuid() + ">已插入");
        }else {
            logger.info("标书记录<" + bidRecord.getUuid() + ">插入失败");
        }
        return res;
    }

    public int deleteBidRecord(BidRecord bidRecord) {
        QueryWrapper<BidRecord> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("uuid", bidRecord.getUuid());
        int res = bidRecordMapper.delete(queryWrapper);
        if (res > 0) {
            logger.info("标书记录<" + bidRecord.getUuid() + ">删除成功");
        }else {
            logger.info("标书记录<" + bidRecord.getUuid() + ">删除失败");
        }
        return res;
    }

    public double getScore(int rank){
        int res;
        switch (rank){
            case 1:
                res = 100;
                break;
            case 2:
                res = 80;
                break;
            case 3:
                res = 60;
                break;
            case 4:
                res = 40;
                break;
            case 5:
                res = 20;
                break;
            default:
                res = 0;
        }
        return res;
    }

    public BidRecord assBid(BidRecord bidRecord){
        int serviceRank, priceRank, qualityRank;
        Double k = 0.5;
        if (bidRecord.getServiceRanking() == null || bidRecord.getPriceRanking() == null || bidRecord.getQualityRanking() == null){
            return bidRecord;
        }else {
            serviceRank = bidRecord.getServiceRanking();
            priceRank = bidRecord.getPriceRanking();
            qualityRank = bidRecord.getQualityRanking();
            double serviceScore = getScore(serviceRank);
            double priceScore = getScore(priceRank);
            double qualityScore = getScore(qualityRank);
            bidRecord.setServiceScore(serviceScore);
            bidRecord.setPriceScore(priceScore);
            bidRecord.setQualityScore(qualityScore);
            double totalScore = (serviceScore + priceScore + qualityScore) / 3;
            bidRecord.setTotalScore(totalScore);
        }
        return bidRecord;
    }

    public int updateBidRecord(BidRecord bidRecord){
        bidRecord = assBid(bidRecord);
        bidRecord = addUuid(bidRecord);
        bidRecord.setUpdateTime(new Date());
        QueryWrapper<BidRecord> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("uuid", bidRecord.getUuid());
        int res = bidRecordMapper.update(bidRecord, queryWrapper);
        if (res > 0) {
            logger.info("标书记录<" + bidRecord.getUuid() + ">更新成功");
        }else {
            logger.info("标书记录<" + bidRecord.getUuid() + ">更新失败");
        }
        return res;
    }

    public BidRecord selectBidRecord(BidRecord bidRecord) {
        QueryWrapper<BidRecord> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("uuid", bidRecord.getUuid());
        BidRecord bidRecord1 = bidRecordMapper.selectOne(queryWrapper);
        if (bidRecord1 != null) {
            logger.info("标书记录<" + bidRecord.getUuid() + ">查询成功");
        }else {
            logger.warn("标书记录<" + bidRecord.getUuid() + ">查询失败");
        }
        return bidRecord1;
    }

    public List<BidRecord> selectAll(){
        List<BidRecord> res = bidRecordMapper.selectList(null);
        if (res.size() > 0) {
            logger.info("标书记录查询=>查询到" + res.size() + "条数据");
        }else{
            logger.warn("标书记录查询=>查询到" + res.size() + "条数据");
        }
        return res;
    }

    public PageUtils<BidRecord> selectPage(int page, int num){
        IPage<BidRecord> bidRecordIPage = new Page<>(page, num);
        QueryWrapper<BidRecord> queryWrapper = new QueryWrapper<>();
        bidRecordIPage = bidRecordMapper.selectPage(bidRecordIPage, queryWrapper);
        PageUtils<BidRecord> pageUtils = new PageUtils(bidRecordIPage);
        logger.info("标书记录分页<" + page + "," + num + ">已查询");
        return pageUtils;
    }

    public PageUtils<BidRecord> selectBidRecordByBidId(Bidding bidding) {
        Integer bidId = bidding.getBidId();
        QueryWrapper<BidRecord> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("bid_id", bidId);
        List<BidRecord> res = bidRecordMapper.selectList(queryWrapper);
        PageUtils<BidRecord> pageUtils = new PageUtils<>(res, res.size(), 0, 0);
        return pageUtils;
    }

    public Bidding assBidRecords(List<BidRecord> bidRecords) {
        if (bidRecords.size() <= 0){
            logger.info("传入空值");
            return new Bidding();
        }
        Integer bidId = Integer.parseInt(bidRecords.get(0).getBidId());
        String winCompany, winUser = "";
        BigDecimal winMoney = null;
        Double maxScore = 0.0;
        for (int i=0; i < bidRecords.size(); i++) {
            if (bidRecords.get(i).getBidId() == null){
                logger.warn("输入不规范");
                return new Bidding();
            }
            bidRecords.set(i, assBid(bidRecords.get(i)));
            QueryWrapper<BidRecord> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("uuid", bidRecords.get(i).getUuid());
            int res = bidRecordMapper.update(bidRecords.get(i), queryWrapper);
            if (maxScore < bidRecords.get(i).getTotalScore()){
                maxScore = bidRecords.get(i).getTotalScore();
                winUser = bidRecords.get(i).getUserId();
                winMoney = bidRecords.get(i).getQuotedPrice();
            }
        }
        QueryWrapper<PeopleManage> peopleManageQueryWrapper = new QueryWrapper<>();
        peopleManageQueryWrapper.eq("login_name", winUser);
        PeopleManage peopleManage = peopleManageMapper.selectOne(peopleManageQueryWrapper);
        winCompany = peopleManage.getCompanyName();

        QueryWrapper<Bidding> biddingQueryWrapper = new QueryWrapper<>();
        biddingQueryWrapper.eq("bid_id", bidId);
        Bidding bidding = biddingMapper.selectOne(biddingQueryWrapper);
        bidding.setWinCompany(winCompany);
        bidding.setWinMoney(winMoney.doubleValue());
        int res = biddingMapper.update(bidding, biddingQueryWrapper);
        logger.info("评标完成");
        bidding.setState("已结束");
        int r = biddingMapper.update(bidding, biddingQueryWrapper);
        return bidding;
    }

    public Boolean hasBid(BidRecord bidRecord) {
        QueryWrapper<BidRecord> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("bid_id", bidRecord.getBidId()).eq("user_id", bidRecord.getUserId());
        BidRecord bidRecord1 = bidRecordMapper.selectOne(queryWrapper);
        return bidRecord1 != null;
    }

    public PageUtils<Bidding> getCanBid(PeopleManage peopleManage) {
        String companyName = "";
        if (peopleManage.getCompanyName() != null){
            companyName = peopleManage.getCompanyName();
        }else{
            QueryWrapper<PeopleManage> peopleManageQueryWrapper = new QueryWrapper<>();
            peopleManageQueryWrapper.eq("login_name", peopleManage.getLoginName());
            PeopleManage peopleManage1 = peopleManageMapper.selectOne(peopleManageQueryWrapper);
            companyName = peopleManage1.getCompanyName();
        }
        QueryWrapper<Bidding> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("bid_company", companyName);
        List<Bidding> res = biddingMapper.selectList(queryWrapper);
        return new PageUtils<Bidding>(res, res.size(), 0, 0);
    }
}




