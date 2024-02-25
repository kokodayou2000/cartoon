package org.example.feign;


import org.example.config.MultiPartSupportConfiguration;
import org.example.core.AjaxResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;


@FeignClient(
        name = "c-file-service",
        configuration = MultiPartSupportConfiguration.class
)
public interface IFileServer {

    @PostMapping(
    value =  "/api/v1/file/upload",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    AjaxResult uploadAvatar(
            @RequestPart("file") MultipartFile file
    );

    @GetMapping(
            value =  "/api/v1/file/upload1",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    AjaxResult upload1(
            @RequestPart("file") MultipartFile file
    );
}
