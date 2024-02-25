package org.example.service;

import org.example.core.AjaxResult;
import org.example.request.AddChapterPatternReq;
import org.example.request.CreateChapterReq;
import org.example.request.UpdateChapterReq;

public interface IChapterService {
    AjaxResult chapterInfo(String chapterId);

    AjaxResult createChapter(CreateChapterReq req);

    AjaxResult updateChapterInfo(UpdateChapterReq req);

    AjaxResult addChapterPattern(AddChapterPatternReq req);
}
