package org.example.feign;


import org.example.core.AjaxResult;
import org.example.request.CartoonSaleNumReq;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@FeignClient(name = "c-product-service")
public interface ProductFeignService {

    @GetMapping("/price/{cartoonId}")
    public AjaxResult price(
            @PathVariable("cartoonId") String cartoonId
    );

    @PostMapping("/sales")
    public AjaxResult sales(
            @RequestBody CartoonSaleNumReq req
    );

    @GetMapping("/api/v1/chapter/chapterInfo/{chapterId}")
    public AjaxResult chapterInfo(
            @PathVariable("chapterId") String chapterId
    );
}
