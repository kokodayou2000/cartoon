package org.example.controller;

import org.example.core.AjaxResult;
import org.example.model.PaperDO;
import org.example.request.AddPaperPatternReq;
import org.example.request.CreatePaperReq;
import org.example.request.UpdateChapterStatusReq;
import org.example.service.IChapterService;
import org.example.service.IPaperService;
import org.example.service.IPdfService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/paper")
public class PaperController {

    @Autowired
    private IPaperService paperService;

    @Autowired
    private IPdfService pdfService;

    @Autowired
    private IChapterService chapterService;

    /**
     * 将图片转换成 pdf 文件
     * @return
     * @throws IOException
     */
//    @GetMapping("/toPdf/{chapterId}")
//    public AjaxResult toPdf(
//            @PathVariable("chapterId") String chapterId
//    )  {
//        // 获取该章节下 审核通过的 paper
//        List<PaperDO> list = paperService.finishList(chapterId);
//        // 将 paperDO -> urlList
//        List<String> urlList = list.stream().map(PaperDO::getUrl).collect(Collectors.toList());
//
//        String url = pdfService.createPdfFromImage(urlList);
//        // 将url保存到 chapterId;
//        chapterService.updateChapterStatus(new UpdateChapterStatusReq(chapterId,"finished",url));
//        return AjaxResult.success(url);
//    }

    @GetMapping("/getPdf/{chapterId}")
    public AjaxResult getPdf(
            @PathVariable("chapterId") String chapterId
    )  {
        String url = chapterService.getPdfUrlById(chapterId);
        return AjaxResult.success(url);
    }


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
        List<PaperDO> paperDOS = paperService.finishList(chapterId);
        return AjaxResult.success(paperDOS);
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
        List<PaperDO> list = paperService.list(chapterId);
        return AjaxResult.success(list);
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
