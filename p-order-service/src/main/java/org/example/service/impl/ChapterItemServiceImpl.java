package org.example.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.example.core.AjaxResult;
import org.example.enums.ChapterItemStatus;
import org.example.enums.ProductOrderStateEnum;
import org.example.interceptor.TokenCheckInterceptor;
import org.example.mapper.ChapterItemMapper;
import org.example.model.BaseUser;
import org.example.model.ChapterItemDO;
import org.example.service.ChapterItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;


@Service
public class ChapterItemServiceImpl extends ServiceImpl<ChapterItemMapper, ChapterItemDO> implements ChapterItemService {

    @Autowired
    private ChapterItemMapper chapterItemMapper;

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
    public AjaxResult payList(String chapterId) {

        // 已经付款的
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
                .eq(ChapterItemDO::getChapterId, cartoonId)
                .eq(ChapterItemDO::getStatus, ChapterItemStatus.PAY.name());

        List<ChapterItemDO> chapterItemList = chapterItemMapper.selectList(queryWrapper);

        // 获取章节id
        // 更具章节id获取章节相关信息
        // 获取该章节的合作者的id
        // 假如章节售价是 70 有两个章节作者，就 35 35
        // 跟用户分成 35 35即可
        chapterItemList.forEach((item)->{
            String chapterId = item.getChapterId();



        });

        return null;
    }

    @Override
    public boolean changeStatus(String outTradeNo, String userId, String status) {

        // 查询用户支付成功的漫画章节列表
        Wrapper<ChapterItemDO> queryWrapper =
                new QueryWrapper<ChapterItemDO>()
                        .lambda()
                        .eq(ChapterItemDO::getUserId, userId)
                        .eq(ChapterItemDO::getStatus, ProductOrderStateEnum.PAY.name());

        List<ChapterItemDO> chapterList = chapterItemMapper.selectList(queryWrapper);
        chapterList.forEach((item)->{
            item.setStatus(status);
            chapterItemMapper.updateById(item);
        });
        return true;
    }

}
