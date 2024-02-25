package org.example.controller;

import org.example.core.AjaxResult;
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
     * 上传页
     * @param file 文件
     * @return 文件地址
     */
    @PostMapping("/uploadPaper")
    public AjaxResult uploadPaper(
            @RequestPart("file") MultipartFile file
    ) {
        return paperService.uploadPaper(file);
    }

    /**
     * 创建页
     * @param req 创建请求
     * @return
     */
    @PostMapping("/createPaper")
    public AjaxResult createPaper(
            @RequestBody CreatePaperReq req
    ){
        return paperService.createPaper(req);
    }


    /**
     * 上传页
     * @param file 文件
     * @param chapterId 图片所属章节
     * @param num 下标
     * @param info 关于该图片的信息
     * @return 文件地址
     */
    @PostMapping("/uploadPaperAndSetInfo")
    public AjaxResult uploadPaperAndSetInfo(
            @RequestPart("file") MultipartFile file,
            @RequestPart("chapterId") String chapterId,
            @RequestPart("num") String num,
            @RequestPart("patterns") String patterns,
            @RequestPart("info") String info
    ) {
        return paperService.uploadPaperAndSetInfo(file,chapterId,num,patterns,info);
    }



    /**
     * 获取本人创建的所有图片
     * @return PaperDO
     */
    @GetMapping("/meCreate")
    public AjaxResult meCreate() {
        return paperService.meCreate();
    }



}
