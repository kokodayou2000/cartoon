package org.example.service;

import org.example.core.AjaxResult;
import org.example.request.chapter.AddChapterPatternReq;
import org.example.request.chapter.CreateChapterReq;
import org.example.request.chapter.UpdateChapterInfoReq;
import org.example.request.chapter.UpdateChapterStatusReq;

public interface IChapterService {
    AjaxResult chapterInfo(String chapterId);

    AjaxResult createChapter(CreateChapterReq req);

    AjaxResult updateChapterInfo(UpdateChapterInfoReq req);

    AjaxResult addChapterPattern(AddChapterPatternReq req);

    AjaxResult chapterPatternList(String chapterId);

    AjaxResult updateChapterStatus(UpdateChapterStatusReq req);

    String getPdfUrlById(String chapterId);

}
