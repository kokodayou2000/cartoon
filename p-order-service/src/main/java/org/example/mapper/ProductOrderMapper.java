package org.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.example.model.ProductOrderDO;


public interface ProductOrderMapper extends BaseMapper<ProductOrderDO> {
    void updateOrderPayState(
            @Param("outTradeNo") String outTradeNo,
            @Param("newState") String newState,
            @Param("oldState") String oldState);
}
