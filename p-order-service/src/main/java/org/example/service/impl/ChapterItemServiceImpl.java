package org.example.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.example.core.AjaxResult;
import org.example.enums.ChapterItemStatus;
import org.example.enums.ProductOrderStateEnum;
import org.example.feign.ProductFeignService;
import org.example.feign.UserFeignService;
import org.example.interceptor.TokenCheckInterceptor;
import org.example.mapper.ChapterItemMapper;
import org.example.model.*;
import org.example.request.ChargeReq;
import org.example.request.UserChargeReq;
import org.example.service.ChapterItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
public class ChapterItemServiceImpl extends ServiceImpl<ChapterItemMapper, ChapterItemDO> implements ChapterItemService {

    @Autowired
    private ChapterItemMapper chapterItemMapper;

    @Autowired
    private ProductFeignService productFeignService;

    @Autowired
    private UserFeignService userFeignService;

    @Override
    public AjaxResult buyList() {
        BaseUser baseUser = TokenCheckInterceptor.tl.get();
        String userId = baseUser.getId();
        // 查询用户支付成功的漫画章节列表
        Wrapper<ChapterItemDO> queryWrapper =
                new QueryWrapper<ChapterItemDO>()
                        .lambda()
                        .eq(ChapterItemDO::getUserId, userId);

        List<ChapterItemDO> chapterList = chapterItemMapper.selectList(queryWrapper);
        chapterList.sort((o1, o2) -> o2.getCreateTime().compareTo(o1.getCreateTime()));
        return AjaxResult.success(chapterList);
    }
    @Override
    public AjaxResult isBuy(String userId, String chapterId) {
        LambdaQueryWrapper<ChapterItemDO> eq = new QueryWrapper<ChapterItemDO>()
                .lambda()
                .eq(ChapterItemDO::getUserId, userId)
                .eq(ChapterItemDO::getChapterId, chapterId);
        ChapterItemDO chapterItemDO = chapterItemMapper.selectOne(eq);
        if (chapterItemDO!= null){
            return AjaxResult.success("用户已经购买了该章节");
        }
        return AjaxResult.error("未购买该章节");
    }

    @Override
    public AjaxResult salesVolume(String cartoonId) {
        Wrapper<ChapterItemDO> eq =
                new QueryWrapper<ChapterItemDO>()
                        .lambda()
                        .eq(ChapterItemDO::getCartoonId,cartoonId);
        List<ChapterItemDO> chapterList = chapterItemMapper.selectList(eq);


        Integer num = new Integer(0);
        Integer totalPoint = new Integer(0);
        for (ChapterItemDO chapterItemDO : chapterList) {
            num += 1;
            totalPoint += chapterItemDO.getPrice();
        }
        SalesVolume salesVolume = new SalesVolume(num,totalPoint);
        return AjaxResult.success(salesVolume);
    }

    @Override
    public AjaxResult payListByCartoonId(String cartoonId) {

        // 已经付款的,但是还未分成的
        Wrapper<ChapterItemDO> queryWrapper =
                new QueryWrapper<ChapterItemDO>()
                        .lambda()
                        .eq(ChapterItemDO::getStatus, ChapterItemStatus.PAY.name())
                        .eq(ChapterItemDO::getCartoonId,cartoonId);

        List<ChapterItemDO> chapterList = chapterItemMapper.selectList(queryWrapper);
        chapterList.sort((o1, o2) -> o2.getCreateTime().compareTo(o1.getCreateTime()));

        return AjaxResult.success(chapterList);
    }

    @Override
    public AjaxResult payListByChapterId(String chapterId) {

        // 已经付款的,但是还未分成的
        Wrapper<ChapterItemDO> queryWrapper =
                new QueryWrapper<ChapterItemDO>()
                        .lambda()
                        .eq(ChapterItemDO::getStatus, ChapterItemStatus.PAY.name())
                        .eq(ChapterItemDO::getChapterId,chapterId);

        List<ChapterItemDO> chapterList = chapterItemMapper.selectList(queryWrapper);
        chapterList.sort((o1, o2) -> o2.getCreateTime().compareTo(o1.getCreateTime()));

        return AjaxResult.success(chapterList);
    }

    @Override
    public AjaxResult benefits(String cartoonId) {


        LambdaQueryWrapper<ChapterItemDO> queryWrapper = new QueryWrapper<ChapterItemDO>()
                .lambda()
                .eq(ChapterItemDO::getCartoonId, cartoonId)
                .eq(ChapterItemDO::getStatus, ChapterItemStatus.PAY.name());

        List<ChapterItemDO> chapterItemList = chapterItemMapper.selectList(queryWrapper);

        // 统计该漫画赚到了多少钱
        // 远程获取该漫画的参与者
        // 该参与者的信息包含了
        // 用户id，该用户画的页数
        // 百分比分成
        Integer totalPoint= 0;
        for (ChapterItemDO chapterItemDO : chapterItemList) {
            totalPoint += chapterItemDO.getPrice();
        }
        AjaxResult result = productFeignService.patternInfo(cartoonId);

        if (!Objects.equals(String.valueOf(result.get("code")), "200")){
            return AjaxResult.error();
        }
        Object outData = result.get("data");

        List<PartnerInfo> partnerInfoList = new ArrayList<>();
        if (outData instanceof List<?>){
            List<?> list = (List<?>) outData;
            for (Object element : list) {
                if (element instanceof Map) {
                    LinkedHashMap<String, Object> map = (LinkedHashMap<String, Object>) element;
                    String uid = (String) map.get("userId");
                    Integer paperNum = (Integer) map.get("paperNum");
                    PartnerInfo info = new PartnerInfo(uid,paperNum);
                    partnerInfoList.add(info);
                }
            }
        }
        if (partnerInfoList.size() <= 0){
            return AjaxResult.error();
        }
        Integer totalPaperNum = new Integer(0);
        for (PartnerInfo partnerInfo : partnerInfoList) {
            totalPaperNum += partnerInfo.getPaperNum();
        }
        // 单页分成价格
        int singlePaperPrice = totalPoint / totalPaperNum;

        for (PartnerInfo partnerInfo : partnerInfoList) {
            String userId = partnerInfo.getUserId();
            Integer paperNum = partnerInfo.getPaperNum();
            Integer point = singlePaperPrice * paperNum;
            UserChargeReq userChargeReq = new UserChargeReq();
            userChargeReq.setUserId(userId);
            userChargeReq.setPoint(point);
            AjaxResult ajaxResult = userFeignService.charge(userChargeReq);
            if (!Objects.equals(String.valueOf(ajaxResult.get("code")), "200")){
                // TODO 本次分成失败，需要利用分布式事务取消掉之前的操作
            }
        }
        // 将本次的分成 字段设置为 FINISHED
        for (ChapterItemDO chapterItemDO : chapterItemList) {
            chapterItemDO.setStatus(ChapterItemStatus.FINISH.name());
            chapterItemMapper.updateById(chapterItemDO);
        }

        return AjaxResult.success("分成完毕");
    }

}
