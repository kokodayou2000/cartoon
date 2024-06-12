package org.example.controller;

import org.example.core.AjaxResult;
import org.example.service.IFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@RestController
@RequestMapping("/api/v1/file")
public class FileController {

    @Autowired
    IFileService fileService;

    @PostMapping("/upload")
    public AjaxResult upload(
            @RequestPart("file") MultipartFile file
    ){
        Object result = fileService.uploadToMinio(file);
        return AjaxResult.success(result);
    }

    @GetMapping("/test")
    public AjaxResult test(){
        return AjaxResult.success(Thread.currentThread().getName());
    }

    @PostMapping("/updateAliyun")
    public AjaxResult updateAliyun(
            @RequestPart("file") MultipartFile file
    ){
        Object result = (Object) fileService.uploadToAliyun(file);
        return AjaxResult.success(result);
    }

}
