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
     * 获取我购买的章节列表
     * @return
     */
    @GetMapping("/buyList")
    public AjaxResult buyList(){
        return chapterItemService.buyList();
    }

    /**
     * 根据章节id
     * 获取还未分成的item
     */
    @GetMapping("/payList/{chapterId}")
    public AjaxResult payList(
            @PathVariable("chapterId") String chapterId
    ){
        return chapterItemService.payList(chapterId);
    }

    /**
     * 分配金额
     * distribution benefits
     */
    @GetMapping("/benefits/{cartoonId}")
    public AjaxResult benefits(
            @PathVariable("cartoonId") String cartoonId
    ){
        return chapterItemService.benefits(cartoonId);
    }



}
