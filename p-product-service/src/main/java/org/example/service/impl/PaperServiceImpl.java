package org.example.service.impl;


import org.example.core.AjaxResult;
import org.example.enums.status.CartoonStatus;
import org.example.interceptor.TokenCheckInterceptor;
import org.example.model.BaseUser;
import org.example.model.ChapterDO;
import org.example.model.PaperDO;
import org.example.model.PartnerInfo;
import org.example.repository.ChapterRepository;
import org.example.repository.PaperRepository;
import org.example.request.AddPaperPatternReq;
import org.example.request.CreatePaperReq;
import org.example.request.CreateRawPadReq;
import org.example.service.IPaperService;
import org.example.utils.CommonUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class PaperServiceImpl implements IPaperService {


    @Autowired
    private PaperRepository paperRepository;

    @Autowired
    private ChapterRepository chapterRepository;

    @Autowired
    private RawPadServiceImpl rawPadService;

    @Override
    public AjaxResult addPaperPattern(AddPaperPatternReq req) {
        Optional<PaperDO> byId = paperRepository.findById(req.getPaperId());
        if (byId.isEmpty()){
            return AjaxResult.error();
        }
        PaperDO paperDO = byId.get();
        Set<String> partners = paperDO.getPartners();
        partners.add(req.getUserId());
        paperDO.setPartners(partners);
        paperRepository.save(paperDO);
        return AjaxResult.success();
    }


    @Override
    public AjaxResult checkCanJoin() {
        BaseUser baseUser = TokenCheckInterceptor.tl.get();
//        if (baseUser.getId())
        return null;
    }




    @Override
    public List<PaperDO> finishList(String chapterId) {
        List<PaperDO> paperList = paperRepository.findAllByChapterIdAndStatus(chapterId,CartoonStatus.PAPER_STATUS_FINISH.name());
        paperList.sort(Comparator.comparingInt(PaperDO::getNum));
        return paperList;
    }
    @Override
    public List<PaperDO>  list(String chapterId) {
        List<PaperDO> paperList = paperRepository.findAllByChapterId(chapterId);
        paperList.sort(Comparator.comparingInt(PaperDO::getNum));
        return paperList;
    }

    @Override
    public AjaxResult patternInfo(String cartoonId) {
        HashMap<String, Integer> hashMap = new HashMap<>();

        // 根据漫画id，获取该漫画下所有完成的章节
        List<ChapterDO> list = chapterRepository.findAllByCartoonIdAndStatus(cartoonId, CartoonStatus.CARTOON_STATUS_FINISH.name());
        // 获取全部的章节id
        List<String> idList = list.stream().map(ChapterDO::getId).collect(Collectors.toList());
        for (String chapterId : idList) {
            List<PaperDO> paperList = paperRepository.findAllByChapterId(chapterId);
            // 统计每页的创作者，key是用户id，value的该创作者画的页
            for (PaperDO paperDO : paperList) {
                String user = paperDO.getCreateBy();
                hashMap.put(user,hashMap.getOrDefault(user,0)+1);
            }
        }
        List<PartnerInfo> partnerInfoList = new ArrayList<>();
        hashMap.forEach((k,v)->{
            PartnerInfo partnerInfo = new PartnerInfo();
            partnerInfo.setUserId(k);
            partnerInfo.setPaperNum(v);
            partnerInfoList.add(partnerInfo);
        });
        return AjaxResult.success(partnerInfoList);
    }


    @Override
    public AjaxResult createPaper(CreatePaperReq req) {
        PaperDO paperDO = new PaperDO();
        BeanUtils.copyProperties(req,paperDO);
        paperDO.setId(CommonUtil.getRandomCode());
        paperDO.setStatus(CartoonStatus.PAPER_STATUS_DOING.name());
        paperRepository.save(paperDO);
        // 创建对应的raw对象
        CreateRawPadReq createRawPadReq = new CreateRawPadReq();
        createRawPadReq.setPaperId(paperDO.getId());
        rawPadService.createRawPad(createRawPadReq);
        return AjaxResult.success();
    }


}
