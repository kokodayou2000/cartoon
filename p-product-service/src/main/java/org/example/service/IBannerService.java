package org.example.service;


import org.example.core.AjaxResult;
import org.example.model.BannerDO;
import org.example.request.ActiveBannerReq;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IBannerService {

    /**
     * 获取轮播图列表
     * @return 轮播图列表
     */
    List<BannerDO> list();

    AjaxResult uploadBanner(String url,String cartoonId);


    Boolean status(ActiveBannerReq req);

    List<BannerDO> listAll();
}
