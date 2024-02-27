package org.example.feign;

import org.example.core.AjaxResult;
import org.example.request.UserChargeReq;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "c-user-service")
public interface UserFeignService {

    // 充值
    @PostMapping("/api/v1/user/charge")
    public AjaxResult charge(
            @RequestBody UserChargeReq req
            );

    // 查看用户剩余 point
    @GetMapping("/api/v1/user/balance/{userId}")
    public AjaxResult balance(
            @PathVariable("userId") String userId
    );

    // 支付
    @PostMapping("/api/v1/user/pay")
    public AjaxResult pay(
            @RequestBody UserChargeReq req
    );
}
