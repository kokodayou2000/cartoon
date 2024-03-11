package org.example.controller;

import org.example.core.AjaxResult;
import org.example.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@RestController
@RequestMapping("/api/v1/file")
public class FileController {

    @Autowired
    FileService fileService;

    @PostMapping("/upload")
    public AjaxResult upload(
            @RequestPart("file") MultipartFile file
    ){
        Object result = (Object) fileService.uploadToMinio(file);
        return AjaxResult.success(result);
    }

    @PostMapping("/upload1")
    public AjaxResult upload1(
            @RequestPart("file") MultipartFile file
    ){
        Object result = (Object) fileService.uploadToMinio(file);
        return AjaxResult.success(result);
    }

}
