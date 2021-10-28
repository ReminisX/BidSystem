package com.nari.bidsystem.controller;

import com.nari.bidsystem.entity.Bidding;
import com.nari.bidsystem.entity.PageElement;
import com.nari.bidsystem.entity.PeopleManage;
import com.nari.bidsystem.service.impl.BiddingServiceImpl;
import com.nari.bidsystem.util.PageUtils;
import com.nari.bidsystem.util.ResultUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
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
@Api(tags = "Bidding")
public class BiddingController {

    private final Logger logger = LoggerFactory.getLogger(BiddingController.class);

    private final BiddingServiceImpl biddingServiceImpl;

    @Autowired
    public BiddingController(BiddingServiceImpl biddingServiceImpl){
        this.biddingServiceImpl = biddingServiceImpl;
    }

    @ApiOperation(value = "添加一个新标")

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResultUtil addBid( @RequestBody Bidding bidding) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        ResultUtil resultUtil = new ResultUtil();
        int res = biddingServiceImpl.insertBidding(bidding);
        if (res > 0) {
            return resultUtil.success();
        } else {
            return resultUtil.failure();
        }
    }

    @ApiOperation(value = "添加一个新文件")
    @RequestMapping(value = "/addFile", method = RequestMethod.POST)
    public ResultUtil addFile(@RequestParam("file") MultipartFile multipartFile){
        ResultUtil resultUtil = new ResultUtil();
        PageElement pageElement = biddingServiceImpl.addFile(multipartFile);
        String path = pageElement.getPath();
        String[] fileNames= path.split("\\\\");
        String fileName = fileNames[fileNames.length-1];
        if (pageElement.getPath() != null ) {
            return resultUtil.addParam("path", path).addParam("fileName", fileName).success();
        } else {
            return resultUtil.failure();
        }
    }

    @ApiOperation(value = "根据标的ID选择一个标书")
    @RequestMapping(value = "/select", method = RequestMethod.POST)
    public ResultUtil select(@RequestBody Bidding bidding){
        ResultUtil resultUtil = new ResultUtil();
        Bidding bidding1 = biddingServiceImpl.select(bidding.getBidId());
        if (bidding1 != null) {
            return resultUtil.addData(bidding1).success();
        }else{
            return resultUtil.failure();
        }
    }

    @ApiOperation(value = "根据ID更新标")
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

    @ApiOperation(value = "根据标的ID删除标")
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public ResultUtil deleteBid(@RequestBody Bidding bidding) {
        Integer bidId = bidding.getBidId();
        ResultUtil resultUtil = new ResultUtil();
        int res = biddingServiceImpl.deleteBidding(bidId);
        if (res > 0) {
            return resultUtil.success();
        }else {
            return resultUtil.failure();
        }
    }

    @ApiOperation(value = "上传page,num分页查询所有的标")
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

    @ApiOperation(value = "上传page,num分页查询所有的标")
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

    @ApiOperation(value = "根据条件模糊查询所有信息")
    @RequestMapping(value = "/selectByCondition", method = RequestMethod.POST)
    public ResultUtil selectByCondition(@RequestBody Bidding bidding) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        ResultUtil resultUtil = new ResultUtil();
        PageUtils<Bidding> res = biddingServiceImpl.selectByCondition(bidding);
        if (res != null){
            return resultUtil.addData(res).success();
        }else {
            return resultUtil.failure();
        }
    }

    @ApiOperation(value = "直接查询所有的标")
    @RequestMapping(value = "/all", method = RequestMethod.POST)
    public ResultUtil selectAll(){
        ResultUtil resultUtil = new ResultUtil();
        List<Bidding> res = biddingServiceImpl.selectAll();
        if (res.size() > 0){
            return resultUtil.addData(res).success();
        }else {
            return resultUtil.failure();
        }
    }

    @ApiOperation(value = "根据ID查询单个标书")
    @RequestMapping(value = "/selectBiddingById", method = RequestMethod.POST)
    public ResultUtil selectBiddingById(@RequestBody PeopleManage peopleManage){
        ResultUtil resultUtil = new ResultUtil();
        PageUtils<Bidding> pageUtils = biddingServiceImpl.selectBiddingById(peopleManage);
        if (pageUtils != null){
            return resultUtil.addData(pageUtils).success();
        }else{
            return resultUtil.failure();
        }
    }
}
