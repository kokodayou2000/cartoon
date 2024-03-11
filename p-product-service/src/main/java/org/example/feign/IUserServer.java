package org.example.feign;


import org.example.core.AjaxResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@FeignClient(
        name = "c-user-service"
)
public interface IUserServer {

    @GetMapping(value =  "/api/v1/user/exist/{userId}")
    AjaxResult exist(
            @PathVariable("userId") String userId
    );


    @PostMapping(value =  "/api/v1/user/batchSearch")
    AjaxResult batchSearch(
            @RequestBody List<String> userIdList
    );
}
