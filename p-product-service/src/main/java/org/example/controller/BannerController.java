package org.example.controller;

import org.example.core.AjaxResult;
import org.example.request.banner.ActiveBannerReq;
import org.example.request.base.UploadReq;
import org.example.service.IBannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/banner")
public class BannerController {


    @Autowired
    private IBannerService bannerService;

    /**
     * 获取活动的轮播图列表
     * @return 轮播图漫画列表
     */
    @GetMapping("/list")
    public AjaxResult list() {
        return AjaxResult.success(bannerService.list());
    }

    /**
     * 获取全部的轮播图列表
     * @return 轮播图漫画列表
     */
    @GetMapping("/listAll")
    public AjaxResult listAll() {
        return AjaxResult.success(bannerService.listAll());
    }

    /**
     * 获取轮播图的漫画列表
     * @return 轮播图漫画列表
     */
    @PostMapping("/uploadBanner")
    public AjaxResult uploadBanner(
            @RequestBody UploadReq req
    ) {
        Assert.notNull(req.getUrl(),"链接不能为空");
        Assert.notNull(req.getId(),"漫画id不能为空");

        return bannerService.uploadBanner(req.getUrl(),req.getId());

    }

    /**
     * 将某个 banner 设置成活动的
     * 活跃的状态图只能有 5 个，当数量超过5的时候，继续设置活跃banner会禁止
     * @return 轮播图漫画列表
     */
    @PostMapping("/status")
    public AjaxResult status(
            @RequestBody ActiveBannerReq req
            ) {
        return AjaxResult.success(bannerService.status(req));
    }

}
