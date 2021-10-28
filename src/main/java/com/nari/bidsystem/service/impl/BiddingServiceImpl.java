package com.nari.bidsystem.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nari.bidsystem.entity.Bidding;
import com.nari.bidsystem.entity.PageElement;
import com.nari.bidsystem.entity.PeopleManage;
import com.nari.bidsystem.mapper.PeopleManageMapper;
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
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 标服务集
 * @author ZhangXD
 */
@Service
public class BiddingServiceImpl extends ServiceImpl<BiddingMapper, Bidding>
        implements BiddingService{

    private final BiddingMapper biddingMapper;

    private final PeopleManageMapper peopleManageMapper;

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    @Autowired
    public BiddingServiceImpl(BiddingMapper biddingMapper, PeopleManageMapper peopleManageMapper){
        this.biddingMapper = biddingMapper;
        this.peopleManageMapper = peopleManageMapper;
    }

    private static final Logger logger = LoggerFactory.getLogger(BiddingServiceImpl.class);

    public PageUtils<Bidding> dateFormat(SimpleDateFormat sdf, PageUtils<Bidding> pageUtils){
        List<Bidding> list = pageUtils.getList();
        for (int i = 0; i < list.size(); i++){
            Date startTime = list.get(i).getStartTime();
            Date endTime = list.get(i).getEndTime();
            Date updateTime = list.get(i).getUpdateTime();
            String startTimeStr = sdf.format(startTime);
            String endTimeStr = sdf.format(endTime);
            String updateTimeStr = sdf.format(updateTime);
            pageUtils.getList().get(i).setStartTimeString(startTimeStr);
            pageUtils.getList().get(i).setEndTimeString(endTimeStr);
            pageUtils.getList().get(i).setUpdateTimeString(updateTimeStr);
        }
        return pageUtils;
    }

    /**
     * 插入新标书
     * @param bidding 标书对象
     * @return 状态码（success/failure）
     */
    public int insertBidding(Bidding bidding) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        String path = bidding.getTechnicalAttachments();
        if (path != null && path != ""){
            String[] fileNames= path.split("\\\\");
            String fileName = fileNames[fileNames.length-1];
            bidding.setTechnicalAttachmentsName(fileName);
        }
        bidding.setUpdateTime(new Date());
        bidding.setEndTime(bidding.getDeliveryDate() == null ? new Date() : bidding.getDeliveryDate());
        Map<String, Object> map = searchMethod(bidding);
        StringBuilder s = new StringBuilder();
        for(Map.Entry<String, Object> entry:map.entrySet()){
            if (entry.getValue() == null || entry.getValue() == "") {
                continue;
            }else {
                s.append(entry.getKey() + "->" +entry.getValue() + ";");
            }
        }
        logger.info(String.valueOf(map));
        int res = biddingMapper.insert(bidding);
        if (res > 0) {
            logger.info("新标<" + s + ">已添加");
        }else {
            logger.info("新标<" + s + ">添加失败");
        }
        return res;
    }

    /**
     * 标书更新
     * @param bidding 标书对象
     * @return 状态码（success/failure）
     */
    public int updateBidding(Bidding bidding) {
        bidding.setUpdateTime(new Date());
        bidding.setEndTime(bidding.getDeliveryDate() == null ? new Date() : bidding.getDeliveryDate());
        String path = bidding.getTechnicalAttachments();
        if (path != null && path != ""){
            String[] fileNames= path.split("\\\\");
            String fileName = fileNames[fileNames.length-1];
            bidding.setTechnicalAttachmentsName(fileName);
        }
        QueryWrapper<Bidding> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("bid_id", bidding.getBidId());
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
    public int deleteBidding(Integer bidId) {
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
        pageUtils = dateFormat(sdf, pageUtils);
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
        pageUtils = dateFormat(sdf, pageUtils);
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
        for (int i = 0; i < field.length - 1; i++) {
            String fieldName = field[i].getName();
            String methodStr=fieldName.substring(0,1).toUpperCase()+fieldName.substring(1);
            Method getterMethod = object.getClass().getMethod("get"+methodStr);
            Object res = getterMethod.invoke(object);
            String name = fieldName.replaceAll("[A-Z]", "_$0").toLowerCase();
            map.put(name, res);
        }
        return map;
    }

    public PageUtils<Bidding> selectByCondition(Bidding bidding) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        QueryWrapper<Bidding> queryWrapper = new QueryWrapper<>();
        Map<String, Object> map = searchMethod(bidding);
        StringBuilder s = new StringBuilder();
        for(Map.Entry<String, Object> entry:map.entrySet()){
            if (entry.getValue() == null || entry.getValue() == "") {
                continue;
            }else {
                s.append(entry.getKey() + "->" + entry.getValue() + ";");
                if (entry.getKey() != "num" && entry.getKey() != "page"){
                    queryWrapper.like(entry.getKey(), entry.getValue());
                }
            }
        }
        logger.info("标查询=><" + s.toString() + ">进行中...");
        if (bidding.getPage() != null && bidding.getNum() != null){
            IPage<Bidding> iPage = new Page<>(bidding.getPage(), bidding.getNum());
            iPage = biddingMapper.selectPage(iPage, queryWrapper);
            PageUtils<Bidding> pageUtils = new PageUtils<>(iPage);
            if (pageUtils.getTotalCount() > 0){
                logger.info("仓储查询=>总共查询到<" + pageUtils.getTotalCount() + ">个仓库");
            }else {
                logger.info("仓储查询=>暂无仓库");
            }
            return pageUtils;
        }else{
            List<Bidding> res = biddingMapper.selectList(queryWrapper);
            if (res.size() > 0){
                logger.info("仓储查询=>总共查询到<" + res.size() + ">个仓库");
            }else {
                logger.info("仓储查询=>暂无仓库");
            }
            PageUtils<Bidding> pageUtils = new PageUtils(res, res.size(), 5, 1);
            return pageUtils;
        }
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
    public PageElement addFile(MultipartFile multipartFile) {
        String filePath = "E:\\Fork\\Files\\";
        PageElement pageElement = new PageElement();
        if (multipartFile.isEmpty()) {
            logger.warn("保存文件失败，文件为空");
            return null;
        }
        String multipartFileName = multipartFile.getOriginalFilename();
        File dest = new File(new File(filePath).getAbsolutePath()+ "/" + multipartFileName);
        if (!dest.getParentFile().exists()) {
            dest.getParentFile().mkdirs();
        }
        try {
            multipartFile.transferTo(dest);
            logger.info("保存文件成功");
            pageElement.setPath(filePath + multipartFileName);
            return pageElement;
        } catch (Exception e) {
            e.printStackTrace();
            return pageElement;
        }
    }

    public List<Bidding> selectAll() {
        List<Bidding> res = biddingMapper.selectList(null);
        if (res.size() > 0){
            logger.info("查询全部标书成功");
        }else {
            logger.info("查询全部标书失败");
        }
        return res;
    }

    /**
     * 通过loginName确定该用户可投的标书情况
     * @param peopleManage 用户封装的数据集，包括分页信息
     * @return 分页查询结果集
     */
    public PageUtils<Bidding> selectBiddingById(PeopleManage peopleManage) {
        QueryWrapper<PeopleManage> peopleManageQueryWrapper = new QueryWrapper<>();
        peopleManageQueryWrapper.eq("login_name", peopleManage.getLoginName());
        PeopleManage peopleManage1 = peopleManageMapper.selectOne(peopleManageQueryWrapper);
        String company = peopleManage1.getCompanyName();
        QueryWrapper<Bidding> biddingQueryWrapper = new QueryWrapper<>();
        biddingQueryWrapper.like("bid_company", company);
        if (peopleManage.getPage() != null && peopleManage.getNum() != null){
            IPage<Bidding> iPage = new Page<>(peopleManage.getPage(), peopleManage.getNum());
            iPage = biddingMapper.selectPage(iPage, biddingQueryWrapper);
            PageUtils<Bidding> pageUtils = new PageUtils<>(iPage);
            if (pageUtils.getTotalCount() > 0){
                logger.info(peopleManage1.getLoginName() + "=>查询到<" + pageUtils.getTotalCount() + ">可投标书");
            }else {
                logger.info(peopleManage1.getLoginName() + "=>无可投标书");
            }
            return pageUtils;
        }else{
            List<Bidding> res = biddingMapper.selectList(biddingQueryWrapper);
            if (res.size() > 0){
                logger.info(peopleManage1.getLoginName() + "=>查询可投标书成功");
            }else {
                logger.info(peopleManage1.getLoginName() + "=>无可投标书");
            }
            PageUtils<Bidding> pageUtils = new PageUtils(res, res.size(), 5, 1);
            return pageUtils;
        }
    }
}




