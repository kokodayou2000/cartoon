package org.example.controller;

import org.example.core.AjaxResult;
import org.example.model.CollaborateDO;
import org.example.request.CreateCollaborateReq;
import org.example.service.ICollaborateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

// collaborate 是中间的状态，用户绘画之后，生成图片，并将图片发送给管理员
@RestController
@RequestMapping("/api/v1/collaborate")
public class CollaborateController {

    @Autowired
    private ICollaborateService collaborateService;

    /**
     * 上传文件，保存文件url，以及简单的信息
     */
    @PostMapping("/uploadPaperTemp")
    public AjaxResult uploadPaperTemp(
            @RequestPart("file") MultipartFile file,
            @RequestPart("info") String info
    ) {
        return collaborateService.uploadPaperTemp(file,info);
    }

    /**
     * 获取本人上传的临时图片and排序
     */
    @GetMapping("/meCreateTemp")
    public AjaxResult meCreateTemp() {
        return collaborateService.meCreateTemp();
    }

    /**
     * 上传到 Collaborate，可以通过审核了
     */
    @PostMapping("/toCollaborate")
    public AjaxResult toCollaborate(
            @RequestBody CreateCollaborateReq req
    ) {
        return collaborateService.toCollaborate(req);
    }

    /**
     * 获取本人上传的Collaborate
     */
    @GetMapping("/meCreateCollaborate")
    public AjaxResult meCreateCollaborate() {
        return collaborateService.meCreateCollaborate();
    }

    /**
     * 获取该漫画下的未通过审核的 collaborate
     */
    @GetMapping("/collaborateList/{cartoonId}")
    public AjaxResult collaborateList(@PathVariable("cartoonId") String cartoonId) {
        return collaborateService.collaborateList(cartoonId);
    }

    /**
     * 将用户上传的 collaborate 转正
     */
    @PostMapping("/toPaper/{collaborateId}")
    public AjaxResult toPaper(
            @PathVariable("collaborateId") String collaborateId
            ) {
        return collaborateService.toPaper(collaborateId);
    }


}
