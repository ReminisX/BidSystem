package com.nari.bidsystem.controller;

import com.nari.bidsystem.entity.Bidding;
import com.nari.bidsystem.service.impl.BiddingServiceImpl;
import com.nari.bidsystem.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author ZhangXD
 * @Date 2021/10/20 11:36
 * @Description
 */
@Controller
@RequestMapping("/bidding")
@CrossOrigin
public class BiddingController {

    @Autowired
    private BiddingServiceImpl biddingServiceImpl;

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public ResultUtil addBid(@RequestBody Bidding bidding) {
        ResultUtil resultUtil = new ResultUtil();
        int res = biddingServiceImpl.insertBidding(bidding);
        if (res > 0) {
            return resultUtil.success();
        } else {
            return resultUtil.failure();
        }
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
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
    @ResponseBody
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
    @ResponseBody
    public ResultUtil searchBidByPage(int page, int num) {
        ResultUtil resultUtil = new ResultUtil();
        List<Bidding> res = biddingServiceImpl.selectAllByPage(page, num);
        if (res.size() > 0) {
            return resultUtil.addData(res).success();
        }else{
            return resultUtil.failure();
        }
    }

}
