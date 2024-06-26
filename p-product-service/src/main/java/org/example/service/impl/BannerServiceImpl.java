package org.example.service.impl;

import org.example.core.AjaxResult;

import org.example.model.BannerDO;

import org.example.repository.BannerRepository;
import org.example.request.banner.ActiveBannerReq;
import org.example.service.IBannerService;
import org.example.utils.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class BannerServiceImpl implements IBannerService {

    @Autowired
    private BannerRepository bannerRepository;


    @Override
    public List<BannerDO> list() {
        return bannerRepository.findBannerDOByActive(true);
    }

    @Override
    public List<BannerDO> listAll() {
        List<BannerDO> active = list();
        List<BannerDO> noActive = bannerRepository.findBannerDOByActive(false);
        active.addAll(noActive);
        return active;
    }

    @Override
    public AjaxResult uploadBanner(String url,String cartoonId) {

        BannerDO bannerDO = new BannerDO();
        bannerDO.setId(CommonUtil.getRandomCode());
        bannerDO.setActive(false);
        bannerDO.setCartoonId(cartoonId);
        bannerDO.setCoverUrl(url);
        BannerDO save = bannerRepository.save(bannerDO);
        return AjaxResult.success(save);
    }

    @Override
    public Boolean status(ActiveBannerReq req) {
        List<BannerDO> activeBannerList = bannerRepository.findBannerDOByActive(true);
        if (req.getActive()){
            if(activeBannerList.size() < 5 ){
                // 将 active = false 的设置成 true
                List<BannerDO> noActiveBannerList = bannerRepository.findBannerDOByActive(false);
                for (BannerDO bannerDO : noActiveBannerList) {
                    if (Objects.equals(bannerDO.getId(), req.getId())) {
                        bannerDO.setActive(true);
                        bannerRepository.save(bannerDO);
                        return true;
                    }
                }
            }
        }else{
            // 将 active = true 的设置成 false
            for (BannerDO bannerDO : activeBannerList) {
                if (Objects.equals(bannerDO.getId(), req.getId())) {
                    bannerDO.setActive(false);
                    bannerRepository.save(bannerDO);
                    return true;
                }
            }
            return false;
        }
        return false;
    }

}
