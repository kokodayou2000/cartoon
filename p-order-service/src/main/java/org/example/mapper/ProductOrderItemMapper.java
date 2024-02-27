package org.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.example.model.ProductOrderItemDO;

import java.util.List;


public interface ProductOrderItemMapper extends BaseMapper<ProductOrderItemDO> {


    /**
     * 批量插入
     * @param productOrderItemDOS
     */
    void insertBatch(@Param("orderItemList") List<ProductOrderItemDO> productOrderItemDOS);
}
