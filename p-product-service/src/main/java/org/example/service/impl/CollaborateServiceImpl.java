package org.example.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.core.AjaxResult;
import org.example.enums.status.CartoonStatus;
import org.example.feign.IFileServer;
import org.example.interceptor.TokenCheckInterceptor;
import org.example.model.*;
import org.example.repository.*;
import org.example.request.CreateCollaborateReq;
import org.example.service.ICartoonService;
import org.example.service.ICollaborateService;
import org.example.utils.CommonUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

@Service
public class CollaborateServiceImpl implements ICollaborateService {

    @Autowired
    private CollaborateRepository collaborateRepository;

    @Autowired
    private TempStorageRepository tempStorageRepository;

    @Autowired
    private PaperRepository paperRepository;

    @Autowired
    private ICartoonService cartoonService;

    @Autowired
    private IFileServer fileServer;

    @Override
    public AjaxResult uploadPaperTemp(MultipartFile file, String info) {
        AjaxResult result = fileServer.upload1(file);
        if (!Objects.equals(String.valueOf(result.get("code")), "200")){
            return AjaxResult.error("上传图片失败");
        }
        Object imageDOMap = (Object) (result.get("data"));
        ObjectMapper objectMapper = new ObjectMapper();
        ImageDO imageDO = objectMapper.convertValue(imageDOMap, ImageDO.class);
        TempStorageDO tempStorageDO = new TempStorageDO();
        tempStorageDO.setId(CommonUtil.getRandomCode());
        tempStorageDO.setUploadTime(new Date());
        tempStorageDO.setImgUrl(imageDO.getUrl());
        String userId = TokenCheckInterceptor.tl.get().getId();
        tempStorageDO.setUserId(userId);
        tempStorageDO.setInfo(info);
        TempStorageDO insert = tempStorageRepository.insert(tempStorageDO);
        return AjaxResult.success(insert);
    }

    @Override
    public AjaxResult meCreateTemp() {
        String userId = TokenCheckInterceptor.tl.get().getId();
        List<TempStorageDO> list = tempStorageRepository.queryAllByUserId(userId);
        list.sort(Comparator.comparing(TempStorageDO::getUploadTime));
        return AjaxResult.success(list);
    }

    @Override
    public AjaxResult meCreateCollaborate() {
        String userId = TokenCheckInterceptor.tl.get().getId();
        List<CollaborateDO> list = collaborateRepository.queryAllByPatternId(userId);
        list.sort(Comparator.comparing(CollaborateDO::getUploadTime));
        return AjaxResult.success(list);
    }

    @Override
    public AjaxResult toPaper(String collaborateId) {
        Optional<CollaborateDO> byId = collaborateRepository.findById(collaborateId);
        if (byId.isEmpty()){
            return AjaxResult.error("通过失败");
        }

        CollaborateDO collaborateDO = byId.get();
        String userId = TokenCheckInterceptor.tl.get().getId();
        String cartoonId = collaborateDO.getCartoonId();
        // 检测该用户是否是漫画的创作者
        Boolean res = cartoonService.checkCreate(userId,cartoonId);
        if (!res){
            return AjaxResult.error("没有权限通过该页");
        }


        String chapterId = collaborateDO.getChapterId();

        PaperDO paperDO = new PaperDO();
        paperDO.setChapterId(chapterId);
        paperDO.setUrl(collaborateDO.getImgUrl());
        paperDO.setNum(collaborateDO.getNum());
        paperDO.setInfo(collaborateDO.getInfo());
        paperDO.setCreateBy(collaborateDO.getPatternId());
        paperDO.setId(CommonUtil.getRandomCode());
        // 设定成完成状态
        paperDO.setStatus(CartoonStatus.PAPER_STATUS_FINISH.name());
        PaperDO insert = paperRepository.insert(paperDO);
        collaborateDO.setPass(true);
        // 将本 collaborate设置未通过
        collaborateRepository.save(collaborateDO);
        return AjaxResult.success(insert);
    }

    @Override
    public AjaxResult collaborateList(String cartoonId) {
        String userId = TokenCheckInterceptor.tl.get().getId();
        // 检测该用户是否是漫画的创作者
        Boolean res = cartoonService.checkCreate(userId,cartoonId);
        if (!res){
            return AjaxResult.error("没有权限获取该漫画未通过审核的页");
        }
        // 未通过，并且未删除的 collaborate
        List<CollaborateDO> list = collaborateRepository.queryAllByCartoonIdAndPassAndDel(cartoonId, false, false);
        list.sort(Comparator.comparing(CollaborateDO::getUploadTime));
        return AjaxResult.success(list);
    }

    @Override
    public AjaxResult toCollaborate(CreateCollaborateReq req) {
        CollaborateDO collaborateDO = new CollaborateDO();
        
        BeanUtils.copyProperties(req,collaborateDO);
        collaborateDO.setDel(false);
        collaborateDO.setPatternId(TokenCheckInterceptor.tl.get().getId());
        collaborateDO.setUploadTime(new Date());
        collaborateDO.setPass(false);
        collaborateDO.setId(CommonUtil.getRandomCode());

        CollaborateDO insert = collaborateRepository.insert(collaborateDO);
        return AjaxResult.success(insert);
    }


}
