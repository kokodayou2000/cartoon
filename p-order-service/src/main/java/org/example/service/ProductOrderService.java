package org.example.service;


import com.baomidou.mybatisplus.extension.service.IService;
import org.example.core.AjaxResult;
import org.example.enums.ProductOrderPayTypeEnum;
import org.example.model.ProductOrderDO;
import org.example.request.ConfirmOrderRequest;
import org.example.request.UserPointReq;

import java.util.Map;


public interface ProductOrderService extends IService<ProductOrderDO> {


    AjaxResult handlerOrderCallbackMsg(ProductOrderPayTypeEnum productOrderPayTypeEnum, Map<String, String> paramsToMap);

    AjaxResult confirmOrder(ConfirmOrderRequest req);

    AjaxResult charge(UserPointReq req);
}
