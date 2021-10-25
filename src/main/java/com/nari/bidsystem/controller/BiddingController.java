package com.nari.bidsystem.controller;

import com.nari.bidsystem.entity.Bidding;
import com.nari.bidsystem.entity.PageElement;
import com.nari.bidsystem.service.impl.BiddingServiceImpl;
import com.nari.bidsystem.util.PageUtils;
import com.nari.bidsystem.util.ResultUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * @Author ZhangXD
 * @Date 2021/10/20 11:36
 * @Description
 */
@Controller
@RequestMapping("/bidding")
@CrossOrigin
@ResponseBody
public class BiddingController {

    private final Logger logger = LoggerFactory.getLogger(BiddingController.class);

    private final BiddingServiceImpl biddingServiceImpl;

    @Autowired
    public BiddingController(BiddingServiceImpl biddingServiceImpl){
        this.biddingServiceImpl = biddingServiceImpl;
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResultUtil addBid( @RequestBody Bidding bidding) {
        ResultUtil resultUtil = new ResultUtil();
        int res = biddingServiceImpl.insertBidding(bidding);
        if (res > 0) {
            return resultUtil.success();
        } else {
            return resultUtil.failure();
        }
    }

    @RequestMapping(value = "/addFile/{id}", method = RequestMethod.POST)
    public ResultUtil addFile(@RequestParam("file") MultipartFile multipartFile, @PathVariable("id") Integer bidId){
        ResultUtil resultUtil = new ResultUtil();
        if (multipartFile != null){
            logger.info(bidId + "接收到file");
        }else{
            logger.info(bidId + "未接收到文件");
        }
        int res = biddingServiceImpl.addFile(bidId, multipartFile);
        if (res > 0) {
            return resultUtil.success();
        } else {
            return resultUtil.failure();
        }
    }

    @RequestMapping(value = "select", method = RequestMethod.POST)
    public ResultUtil select(@RequestBody Bidding bidding){
        ResultUtil resultUtil = new ResultUtil();
        Bidding bidding1 = biddingServiceImpl.select(bidding.getBidId());
        if (bidding1 != null) {
            return resultUtil.addData(bidding1).success();
        }else{
            return resultUtil.failure();
        }
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ResultUtil updateBid(@RequestBody Bidding bidding) {
        ResultUtil resultUtil = new ResultUtil();
        int res = biddingServiceImpl.updateBidding(bidding);
        if (res > 0) {
            return resultUtil.success();
        }else {
            return resultUtil.failure();
        }
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public ResultUtil deleteBid(@RequestParam("bidId") String bidId) {
        ResultUtil resultUtil = new ResultUtil();
        int res = biddingServiceImpl.deleteBidding(bidId);
        if (res > 0) {
            return resultUtil.success();
        }else {
            return resultUtil.failure();
        }
    }

    @RequestMapping(value = "/show", method = RequestMethod.POST)
    public ResultUtil searchBidByPage(@RequestBody PageElement pageElement) {
        ResultUtil resultUtil = new ResultUtil();
        PageUtils<Bidding> res = biddingServiceImpl.selectAllByPage(pageElement.getPage(), pageElement.getNum());
        if (res.getTotalCount() > 0) {
            return resultUtil.addData(res).success();
        }else{
            return resultUtil.failure();
        }
    }

    @RequestMapping(value = "/showBidding", method = RequestMethod.POST)
    public ResultUtil showBidding(@RequestBody PageElement pageElement){
        ResultUtil resultUtil = new ResultUtil();
        PageUtils<Bidding> res = biddingServiceImpl.selectAllBidding(pageElement.getPage(), pageElement.getNum());
        if (res.getTotalCount() > 0) {
            return resultUtil.addData(res).success();
        }else{
            return resultUtil.failure();
        }
    }

    @RequestMapping(value = "selectByCondition", method = RequestMethod.POST)
    public ResultUtil selectByCondition(@RequestBody Bidding bidding) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        ResultUtil resultUtil = new ResultUtil();
        List<Bidding> res = biddingServiceImpl.selectByCondition(bidding);
        if (res.size() > 0){
            return resultUtil.addData(res).success();
        }else {
            return resultUtil.failure();
        }
    }

}
