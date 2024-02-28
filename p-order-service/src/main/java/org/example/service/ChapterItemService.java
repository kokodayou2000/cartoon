package org.example.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.example.core.AjaxResult;
import org.example.model.ChapterItemDO;


public interface ChapterItemService extends IService<ChapterItemDO> {

    AjaxResult buyList();

    boolean changeStatus(String outTradeNo,String userId,String status);

    AjaxResult payList(String chapterId);

    AjaxResult benefits(String cartoonId);
}
