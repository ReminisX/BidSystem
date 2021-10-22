package com.nari.bidsystem.controller;

import com.nari.bidsystem.entity.Bidding;
import com.nari.bidsystem.service.impl.BiddingServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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
    public String addBid(@RequestBody Bidding bidding) {
        String res = biddingServiceImpl.insertBidding(bidding);
        return res;
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public String updateBid(@RequestBody Bidding bidding) {
        String res = biddingServiceImpl.updateBidding(bidding);
        return res;
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    public String deleteBid(@RequestParam("bidId") String bidId) {
        String res = biddingServiceImpl.deleteBidding(bidId);
        return res;
    }

    @RequestMapping(value = "/show", method = RequestMethod.POST)
    @ResponseBody
    public String searchBidByPage(int page, int num) {
        String res = biddingServiceImpl.selectAllByPage(page, num);
        return res;
    }

}
