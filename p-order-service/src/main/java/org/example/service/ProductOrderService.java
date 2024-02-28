package org.example.service;


import com.baomidou.mybatisplus.extension.service.IService;
import org.example.core.AjaxResult;
import org.example.enums.ProductOrderPayTypeEnum;
import org.example.model.OrderMessage;
import org.example.model.ProductOrderDO;
import org.example.request.ChargeReq;
import org.example.request.UserBuyChapterRequest;

import java.util.Map;


public interface ProductOrderService extends IService<ProductOrderDO> {


    AjaxResult handlerOrderCallbackMsg(ProductOrderPayTypeEnum productOrderPayTypeEnum, Map<String, String> paramsToMap);

    AjaxResult userBuy(UserBuyChapterRequest req);

    AjaxResult charge(ChargeReq req);

    boolean closeProductOrder(OrderMessage orderMessage);

    AjaxResult orderState(String orderNO);
}
