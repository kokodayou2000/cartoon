package org.example.controller;

import org.example.core.AjaxResult;
import org.example.model.ChapterDO;
import org.example.request.AddChapterPatternReq;
import org.example.request.CreateCartoonReq;
import org.example.request.CreateChapterReq;
import org.example.request.UpdateChapterReq;
import org.example.service.ICartoonService;
import org.example.service.IChapterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/chapter")
public class ChapterController {

    @Autowired
    private IChapterService chapterService;


    /**
     * 根据章节 id 获取该章节下包含的 页
     * 以及章节列表
     * @param chapterId 章节 id
     * @return ChapterInfo
     */
    @GetMapping("/chapterInfo/{chapterId}")
    public AjaxResult chapterInfo(
            @PathVariable("chapterId") String chapterId
    ){
        return chapterService.chapterInfo(chapterId);
    }


    /**
     * 创建章节
     * @param req 创建章节请求
     * @return
     */
    @PostMapping("/createChapter")
    public AjaxResult createChapter(
            @RequestBody CreateChapterReq req
            ){
        return chapterService.createChapter(req);
    }

    /**
     * 更新章节信息
     * 包含该章节的作者和章节的参与者才能更新章节信息
     * @param req 更新信息
     * @return
     */
    @PostMapping("/updateChapterInfo")
    public AjaxResult updateChapterInfo(
            @RequestBody UpdateChapterReq req
    ){
        return chapterService.updateChapterInfo(req);
    }

    /**
     * 新增章节参与者
     * 漫画创建者能新增
     * @param req 更新信息
     * @return
     */
    @PostMapping("/addChapterPattern")
    public AjaxResult addChapterPattern(
            @RequestBody AddChapterPatternReq req
    ){
        return chapterService.addChapterPattern(req);
    }

    /**
     * 查看本章节的参与者
     * @return
     */
    @GetMapping("/chapterPatternList/{chapterId}")
    public AjaxResult chapterPatternList(
            @PathVariable("chapterId") String chapterId
    ){
        return chapterService.chapterPatternList(chapterId);
    }


}
