package org.example.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.example.core.AjaxResult;
import org.example.model.ChapterItemDO;


public interface ChapterItemService extends IService<ChapterItemDO> {

    AjaxResult buyList();

    AjaxResult benefits(String cartoonId);

    AjaxResult isBuy(String userId, String chapterId);

    AjaxResult salesVolume(String cartoonId);

    AjaxResult payListByCartoonId(String cartoonId);

    AjaxResult payListByChapterId(String chapterId);
}
