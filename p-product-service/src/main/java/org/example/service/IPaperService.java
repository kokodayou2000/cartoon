package org.example.service;

import org.example.core.AjaxResult;
import org.example.model.PaperDO;
import org.example.request.AddPaperPatternReq;
import org.example.request.CreatePaperReq;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IPaperService {

    List<PaperDO> finishList(String chapterId);

    AjaxResult patternInfo(String cartoonId);

    AjaxResult createPaper(CreatePaperReq req);

    AjaxResult addPaperPattern(AddPaperPatternReq req);

    AjaxResult checkCanJoin();

    List<PaperDO> list(String chapterId);
}

