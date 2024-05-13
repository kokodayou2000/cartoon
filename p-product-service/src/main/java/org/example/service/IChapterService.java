package org.example.service;

import org.example.core.AjaxResult;
import org.example.request.AddChapterPatternReq;
import org.example.request.CreateChapterReq;
import org.example.request.UpdateChapterInfoReq;
import org.example.request.UpdateChapterStatusReq;

public interface IChapterService {
    AjaxResult chapterInfo(String chapterId);

    AjaxResult createChapter(CreateChapterReq req);

    AjaxResult updateChapterInfo(UpdateChapterInfoReq req);

    AjaxResult addChapterPattern(AddChapterPatternReq req);

    AjaxResult chapterPatternList(String chapterId);

    AjaxResult updateChapterStatus(UpdateChapterStatusReq req);

    String getPdfUrlById(String chapterId);

}
