package org.example.service;

import org.example.core.AjaxResult;
import org.example.request.CreatePaperReq;
import org.springframework.web.multipart.MultipartFile;

public interface IPaperService {

    AjaxResult uploadPaperAndSetInfo(MultipartFile file, String chapterId, String num,String patterns, String info);

    AjaxResult uploadPaper(MultipartFile file);

    AjaxResult createPaper(CreatePaperReq req);


    AjaxResult meCreate();
}

