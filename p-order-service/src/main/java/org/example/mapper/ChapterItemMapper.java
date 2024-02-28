package org.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.example.model.ChapterItemDO;

import java.util.List;


public interface ChapterItemMapper extends BaseMapper<ChapterItemDO> {


    /**
     * 批量插入
     */
    void insertBatch(@Param("orderItemList") List<ChapterItemDO> chapterItemDOS);
}
