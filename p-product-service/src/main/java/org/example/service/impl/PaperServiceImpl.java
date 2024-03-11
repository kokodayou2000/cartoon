package org.example.service.impl;


import org.example.core.AjaxResult;
import org.example.enums.CartoonStatus;
import org.example.model.ChapterDO;
import org.example.model.PaperDO;
import org.example.model.PartnerInfo;
import org.example.repository.CartoonRepository;
import org.example.repository.ChapterRepository;
import org.example.repository.PaperRepository;
import org.example.service.IPaperService;
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

    @Override
    public AjaxResult list(String chapterId) {
        List<PaperDO> paperList = paperRepository.findAllByChapterId(chapterId);
        paperList.sort(Comparator.comparingInt(PaperDO::getNum));
        return AjaxResult.success(paperList);
    }

    @Override
    public AjaxResult patternInfo(String cartoonId) {
        HashMap<String, Integer> hashMap = new HashMap<>();

        // 根据漫画id，获取该漫画下所有完成的章节
        List<ChapterDO> list = chapterRepository.findAllByCartoonIdAndStatus(cartoonId, CartoonStatus.FINISH.name());
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
}
