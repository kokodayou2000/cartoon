package org.example.feign;


import org.example.config.MultiPartSupportConfiguration;
import org.example.core.AjaxResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;


@FeignClient(
        name = "c-user-service"
)
public interface IUserServer {

    @GetMapping(value =  "/api/v1/user/exist/{userId}")
    AjaxResult exist(
            @PathVariable("userId") String userId
    );
}
