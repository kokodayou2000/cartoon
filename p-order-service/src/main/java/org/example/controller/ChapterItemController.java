package org.example.controller;


import lombok.extern.slf4j.Slf4j;
import org.example.core.AjaxResult;
import org.example.service.ChapterItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/chapterItem")
@Slf4j
public class ChapterItemController {

    @Autowired
    private ChapterItemService chapterItemService;

    /**
     * 获取本人购买的章节列表
     */
    @GetMapping("/buyList")
    public AjaxResult buyList(){
        return chapterItemService.buyList();
    }

    /**
     * 获取本章节是否被该 user 购买,当用户查看漫画的时候，进行可以进行权限查询
     */
    @GetMapping("/isBuy/{userId}/{chapterId}")
    public AjaxResult isBuy(
            @PathVariable("userId") String userId,
            @PathVariable("chapterId") String chapterId
    ){
        return chapterItemService.isBuy(userId,chapterId);
    }

    /**
     * 获取漫画的销量
     * 包含全部的 销量以及总点数
     */
    @GetMapping("/salesVolume/{cartoonId}")
    public AjaxResult isBuy(
            @PathVariable("cartoonId") String cartoonId
    ){
        return chapterItemService.salesVolume(cartoonId);
    }


    /**
     * 根据漫画id
     * 获取还未分成的item
     */
    @GetMapping("/payListByCartoonId/{cartoonId}")
    public AjaxResult payListByCartoonId(
            @PathVariable("cartoonId") String cartoonId
    ){
        return chapterItemService.payListByCartoonId(cartoonId);
    }

    /**
     * 根据章节id
     * 获取还未分成的item
     */
    @GetMapping("/payListByChapterId/{chapterId}")
    public AjaxResult payListByChapterId(
            @PathVariable("chapterId") String chapterId
    ){
        return chapterItemService.payListByChapterId(chapterId);
    }

    /**
     * 分配金额
     * 1 只有漫画作者能分配金额，当新增参与者的时候，建议先对现有销售的漫画进行分成
     * distribution benefits
     */
    @GetMapping("/benefits/{cartoonId}")
    public AjaxResult benefits(
            @PathVariable("cartoonId") String cartoonId
    ){
        return chapterItemService.benefits(cartoonId);
    }



}
