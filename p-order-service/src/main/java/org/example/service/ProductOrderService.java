package org.example.service;


import com.baomidou.mybatisplus.extension.service.IService;
import org.example.core.AjaxResult;
import org.example.enums.ProductOrderPayTypeEnum;
import org.example.model.OrderMessage;
import org.example.model.ProductOrderDO;
import org.example.request.ChargeReq;
import org.example.request.ConfirmOrderRequest;
import org.example.request.UserChargeReq;
import org.example.utils.JsonData;

import java.util.Map;


public interface ProductOrderService extends IService<ProductOrderDO> {


    AjaxResult handlerOrderCallbackMsg(ProductOrderPayTypeEnum productOrderPayTypeEnum, Map<String, String> paramsToMap);

    AjaxResult confirmOrder(ConfirmOrderRequest req);

    AjaxResult charge(ChargeReq req);

    boolean closeProductOrder(OrderMessage orderMessage);

}
