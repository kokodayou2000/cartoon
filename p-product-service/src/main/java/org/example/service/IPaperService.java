package org.example.service;

import org.example.core.AjaxResult;
import org.example.request.AddPaperPatternReq;
import org.example.request.CreatePaperReq;
import org.springframework.web.multipart.MultipartFile;

public interface IPaperService {

    AjaxResult finishList(String chapterId);

    AjaxResult patternInfo(String cartoonId);

    AjaxResult createPaper(CreatePaperReq req);

    AjaxResult addPaperPattern(AddPaperPatternReq req);

    AjaxResult checkCanJoin();

    AjaxResult list(String chapterId);
}

