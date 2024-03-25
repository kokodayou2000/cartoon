package org.example.controller;

import org.example.core.AjaxResult;
import org.example.request.AddPaperPatternReq;
import org.example.request.CreatePaperReq;
import org.example.service.IPaperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/paper")
public class PaperController {

    @Autowired
    private IPaperService paperService;


    /**
     * 根据章节id获取该章节下的全部页 获取 paper 完成的
     * 1. 章节是否收费
     * 2. 作者是否购买了该章节
     * 3. 是否是该漫画的创作成员
     * @param chapterId 章节id
     * @return
     */
    @GetMapping("/finishList/{chapterId}")
    public AjaxResult finishList(
            @PathVariable("chapterId") String chapterId
    ){
        return paperService.finishList(chapterId);
    }

    /**
     * 获取全部的
     * @param chapterId 章节id
     * @return
     */
    @GetMapping("/list/{chapterId}")
    public AjaxResult list(
            @PathVariable("chapterId") String chapterId
    ){
        return paperService.list(chapterId);
    }

    /**
     * 创建paper 创建paper的时候就创建一个对应的 RawPad
     * @param req 章节信息
     * @return
     */
    @PostMapping("/createPaper")
    public AjaxResult createPaper(
            @RequestBody CreatePaperReq req
    ) {
        return paperService.createPaper(req);
    }

    // 新增参与者
    @PostMapping("/addPaperPattern")
    public AjaxResult addPaperPattern(
            @RequestBody AddPaperPatternReq req
    ) {
        return paperService.addPaperPattern(req);
    }

    @GetMapping("/patternInfo/{cartoonId}")
    public AjaxResult patternInfo(@PathVariable("cartoonId") String cartoonId) {
        return paperService.patternInfo(cartoonId);
    }

}
