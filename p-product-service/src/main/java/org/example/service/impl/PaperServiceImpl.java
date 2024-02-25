package org.example.service.impl;

import org.example.constant.CartoonConstant;
import org.example.core.AjaxResult;
import org.example.feign.IFileServer;
import org.example.interceptor.TokenCheckInterceptor;
import org.example.model.BaseUser;
import org.example.model.ChapterDO;
import org.example.model.PaperDO;
import org.example.repository.PaperRepository;
import org.example.request.CreatePaperReq;
import org.example.service.IPaperService;
import org.example.utils.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.awt.print.Paper;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class PaperServiceImpl implements IPaperService {

    @Autowired
    private IFileServer fileServer;

    @Autowired
    private PaperRepository paperRepository;

    @Override
    public AjaxResult uploadPaperAndSetInfo(MultipartFile file, String chapterId, String num, String patterns,String info) {
        AjaxResult result = fileServer.uploadAvatar(file);
        if (!Objects.equals(String.valueOf(result.get("code")), "200")){
            return AjaxResult.error("上传失败");
        }
        String[] list = patterns.split("-");

        String url = (String) result.get("data");
        PaperDO paperDO = genPaper();
        paperDO.setUrl(url);
        paperDO.setNum(Integer.parseInt(num));
        paperDO.setChapterId(chapterId);
        // TODO 校验
        paperDO.setPartners(Arrays.stream(list).collect(Collectors.toSet()));
        paperDO.setInfo(info);
        PaperDO save = paperRepository.save(paperDO);

        return AjaxResult.success(save);
    }

    @Override
    public AjaxResult uploadPaper(MultipartFile file) {
        return fileServer.uploadAvatar(file);
    }

    @Override
    public AjaxResult createPaper(CreatePaperReq req) {
        PaperDO paperDO = genPaper();

        paperDO.setInfo(req.getInfo());
        paperDO.setUrl(req.getUrl());
        paperDO.setNum(req.getNum());
        paperDO.setChapterId(req.getChapterId());
        paperDO.setPartners(req.getPartners());
        PaperDO save = paperRepository.save(paperDO);
        return AjaxResult.success(save);
    }

    @Override
    public AjaxResult meCreate() {
        BaseUser baseUser = TokenCheckInterceptor.tl.get();
        String userId = baseUser.getId();
        List<PaperDO> arrayList = new ArrayList<>();
        for (PaperDO paperDO : paperRepository.findAll()) {
            if (paperDO.getPartners().contains(userId)){
                arrayList.add(paperDO);
            }
        }
        return AjaxResult.success(arrayList);
    }


    private PaperDO genPaper() {
        PaperDO paperDO = new PaperDO();
        paperDO.setId(CommonUtil.getRandomCode());
        paperDO.setStatus(CartoonConstant.STATUS_DOING);
        return paperDO;
    }
}
