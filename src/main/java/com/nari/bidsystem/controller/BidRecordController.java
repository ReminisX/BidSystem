package com.nari.bidsystem.controller;

import com.nari.bidsystem.entity.BidRecord;
import com.nari.bidsystem.entity.Bidding;
import com.nari.bidsystem.entity.PageElement;
import com.nari.bidsystem.entity.PeopleManage;
import com.nari.bidsystem.service.impl.BidRecordServiceImpl;
import com.nari.bidsystem.util.PageUtils;
import com.nari.bidsystem.util.ResultUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author ZhangXD
 * @Date 2021/10/26 9:47
 * @Description
 */
@RestController
@RequestMapping("/bidRecord")
@Api
public class BidRecordController {

    private BidRecordServiceImpl bidRecordServiceImpl;

    private final Logger logger = LoggerFactory.getLogger(BidRecordController.class);

    @Autowired
    public BidRecordController(BidRecordServiceImpl bidRecordServiceImpl) {
        this.bidRecordServiceImpl = bidRecordServiceImpl;
    }

    @ApiOperation(value = "添加新的投标记录")
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResultUtil addBidRecord(@RequestBody BidRecord bidRecord){
        ResultUtil resultUtil = new ResultUtil();
        int res = bidRecordServiceImpl.addBidRecord(bidRecord);
        if (res > 0){
            return resultUtil.success();
        }else{
            return resultUtil.failure();
        }
    }

    @ApiOperation(value = "根据ID删除对应的投标记录")
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public ResultUtil deleteBidRecord(@RequestBody BidRecord bidRecord){
        ResultUtil resultUtil = new ResultUtil();
        int res = bidRecordServiceImpl.deleteBidRecord(bidRecord);
        if (res > 0){
            return resultUtil.success();
        }else{
            return resultUtil.failure();
        }
    }

    @ApiOperation(value = "根据id更新对应的标书记录")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ResultUtil updateBidRecord(@RequestBody BidRecord bidRecord){
        ResultUtil resultUtil = new ResultUtil();
        int res = bidRecordServiceImpl.updateBidRecord(bidRecord);
        if (res > 0){
            return resultUtil.success();
        }else{
            return resultUtil.failure();
        }
    }

    @ApiOperation(value = "根据标书ID查询标书全部内容")
    @RequestMapping(value = "/select", method = RequestMethod.POST)
    public ResultUtil selectBidRecord(@RequestBody BidRecord bidRecord){
        ResultUtil resultUtil = new ResultUtil();
        BidRecord res = bidRecordServiceImpl.selectBidRecord(bidRecord);
        if (res != null){
            return resultUtil.success().addData(res);
        }else{
            return resultUtil.failure();
        }
    }

    @ApiOperation(value = "简单查询所有的投标记录")
    @RequestMapping(value = "/all", method = RequestMethod.POST)
    public ResultUtil selectAllBidRecord(){
        ResultUtil resultUtil = new ResultUtil();
        List<BidRecord> res = bidRecordServiceImpl.selectAll();
        if (res.size() > 0){
            return resultUtil.success().addData(res);
        }else{
            return resultUtil.failure();
        }
    }

    @ApiOperation(value = "分页查询所有的投标记录")
    @RequestMapping(value = "/selectPage", method = RequestMethod.POST)
    public ResultUtil selectByPage(@RequestBody PageElement pageElement) {
        ResultUtil resultUtil = new ResultUtil();
        PageUtils<BidRecord> pageUtils = bidRecordServiceImpl.selectPage(pageElement.getPage(), pageElement.getNum());
        if (pageUtils.getTotalCount() > 0){
            return resultUtil.addData(pageUtils).success();
        }else{
            return resultUtil.failure();
        }
    }

    @ApiOperation("根据标的ID查找所有投该标的标书")
    @PostMapping("/selectBidRecordsByBidId")
    public ResultUtil selectBidRecordsByBidId(@RequestBody Bidding bidding){
        ResultUtil resultUtil = new ResultUtil();
        PageUtils<BidRecord> pageUtils = bidRecordServiceImpl.selectBidRecordByBidId(bidding);
        if (pageUtils != null) {
            return resultUtil.addData(pageUtils).success();
        }else{
            return resultUtil.failure();
        }
    }

    @ApiOperation("批量评估标书")
    @PostMapping("/assBids")
    public ResultUtil assBids(@RequestBody List<BidRecord> bidRecords){
        logger.info("收到BidRecord数组:");
        System.out.println(bidRecords);
        ResultUtil resultUtil = new ResultUtil();
        Bidding bidding = bidRecordServiceImpl.assBidRecords(bidRecords);
        if (bidding != null){
            return resultUtil.addData(bidding).success();
        }else{
            return resultUtil.failure();
        }
    }

    @PostMapping("/hasBid")
    public ResultUtil hasBid(@RequestBody BidRecord bidRecord){
        ResultUtil resultUtil = new ResultUtil();
        Boolean b = bidRecordServiceImpl.hasBid(bidRecord);
        return resultUtil.addParam("has", b).success();
    }

    @PostMapping("/getCanBid")
    public ResultUtil getCanBid(@RequestBody PeopleManage peopleManage){
        ResultUtil resultUtil = new ResultUtil();
        PageUtils<Bidding> pageUtils = bidRecordServiceImpl.getCanBid(peopleManage);
        if (resultUtil != null){
            return resultUtil.addData(pageUtils).success();
        }else{
            return resultUtil.failure();
        }
    }
}
