package org.example.service;

import org.example.core.AjaxResult;
import org.example.request.CreatePaperReq;
import org.springframework.web.multipart.MultipartFile;

public interface IPaperService {

    AjaxResult list(String chapterId);

    AjaxResult patternInfo(String cartoonId);
}

