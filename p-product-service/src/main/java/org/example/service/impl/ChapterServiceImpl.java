package org.example.service.impl;


import org.example.core.AjaxResult;
import org.example.enums.status.CartoonStatus;
import org.example.feign.IUserServer;
import org.example.interceptor.TokenCheckInterceptor;
import org.example.model.BaseUser;
import org.example.model.CartoonDO;
import org.example.model.ChapterDO;
import org.example.model.PaperDO;
import org.example.repository.CartoonRepository;
import org.example.repository.ChapterRepository;
import org.example.repository.PaperRepository;
import org.example.request.AddChapterPatternReq;
import org.example.request.CreateChapterReq;
import org.example.request.UpdateChapterInfoReq;
import org.example.request.UpdateChapterStatusReq;
import org.example.service.IChapterService;
import org.example.utils.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
public class ChapterServiceImpl implements IChapterService {

    @Autowired
    private ChapterRepository chapterRepository;

    @Autowired
    private CartoonRepository cartoonRepository;

    @Autowired
    private PaperRepository paperRepository;

    @Autowired
    private IUserServer userServer;

    @Override
    public AjaxResult chapterInfo(String chapterId) {
        Optional<ChapterDO> byId = chapterRepository.findById(chapterId);
        if (byId.isEmpty()){
            return AjaxResult.error("查询失败");
        }
        ChapterDO chapterDO = byId.get();
        // 如果是免费的
        if (chapterDO.getFree()){
            List<PaperDO> allByChapterId = paperRepository.findAllByChapterId(chapterDO.getId());
            return AjaxResult.success(allByChapterId);
        }
        return AjaxResult.error("收费章节");
    }

    @Override
    public AjaxResult createChapter(CreateChapterReq req) {

        // 只有漫画作者和漫画的参与者才能创建
        String cartoonId = req.getCartoonId();
        Optional<CartoonDO> byId = cartoonRepository.findById(cartoonId);
        if (byId.isEmpty()){
            return AjaxResult.error("创建失败");
        }
        CartoonDO cartoonDO = byId.get();
        if (!checkValidation1(cartoonDO)){
            return AjaxResult.error("创建失败");
        }

        ChapterDO genChapter = genChapter();
        genChapter.setCartoonId(req.getCartoonId());
        genChapter.setTitle(req.getTitle());
        genChapter.setFree(req.getFree());
        genChapter.setStatus(CartoonStatus.CHAPTER_STATUS_DOING.name());
        genChapter.setNum(req.getNum());
        Set<String> set = new HashSet<>();
        set.add(TokenCheckInterceptor.tl.get().getId());
        // 将创建者添加到 partner 中
        genChapter.setPartners(set);
        chapterRepository.save(genChapter);
        return AjaxResult.success(genChapter);
    }


    @Override
    public AjaxResult updateChapterInfo(UpdateChapterInfoReq req) {
        String chapterId = req.getId();
        Optional<ChapterDO> byId = chapterRepository.findById(chapterId);
        if (byId.isEmpty()){
            return AjaxResult.error("查询章节失败");
        }
        ChapterDO chapterDO = byId.get();

        if (!checkValidation(chapterDO)){
            return AjaxResult.error("更新失败");
        }
        chapterDO.setTitle(req.getTitle());
        chapterDO.setNum(req.getNum());
        ChapterDO save = chapterRepository.save(chapterDO);
        return AjaxResult.success(save);
    }

    @Override
    public AjaxResult updateChapterStatus(UpdateChapterStatusReq req) {

        String chapterId = req.getChapterId();
        Optional<ChapterDO> byId = chapterRepository.findById(chapterId);
        if (byId.isEmpty()){
            return AjaxResult.error("查询章节失败");
        }
        ChapterDO chapterDO = byId.get();

        if (!checkValidation(chapterDO)){
            return AjaxResult.error("更新失败");
        }
        if ("finished".equals(req.getStatus()) || "doing".equals(req.getStatus())){
            chapterDO.setStatus(req.getStatus());
            chapterDO.setFinishedPDFUrl(req.getUrl());
            ChapterDO save = chapterRepository.save(chapterDO);
            return AjaxResult.success(save);
        }
        return AjaxResult.error("状态只有doing or finished");
    }

    @Override
    public String getPdfUrlById(String chapterId) {
        Optional<ChapterDO> byId = chapterRepository.findById(chapterId);
        // todo 合法性校验
        ChapterDO chapterDO = byId.get();
        return chapterDO.getFinishedPDFUrl();
    }


    @Override
    public AjaxResult addChapterPattern(AddChapterPatternReq req) {

        // 检查用户是否存在
        AjaxResult exist = userServer.exist(req.getPatternId());
        if (!Objects.equals(String.valueOf(exist.get("code")), "200")){
            return AjaxResult.error("不存在该用户");
        }

        // 这个人必须是漫画的创作者 才行
        String chapterId = req.getChapterId();
        Optional<ChapterDO> byId = chapterRepository.findById(chapterId);
        if (byId.isEmpty()){
            return AjaxResult.error("查找章节失败");
        }

        ChapterDO chapterDO = byId.get();
        String cartoonId = chapterDO.getCartoonId();
        Optional<CartoonDO> cartoonDOOptional = cartoonRepository.findById(cartoonId);
        if (cartoonDOOptional.isEmpty()){
            return AjaxResult.error("查找漫画失败");
        }

        CartoonDO cartoonDO = cartoonDOOptional.get();
        if(!checkCartoonCreate(cartoonDO)){
            return AjaxResult.error("只有漫画的创作者才能添加章节的参与者");
        }

        Set<String> partners = chapterDO.getPartners();
        partners.add(req.getPatternId());
        // set 去重
        cartoonDO.setPartners(partners);
        ChapterDO save = chapterRepository.save(chapterDO);
        return AjaxResult.success(save);
    }

    @Override
    public AjaxResult chapterPatternList(String chapterId) {
        Optional<ChapterDO> byId = chapterRepository.findById(chapterId);
        if (byId.isEmpty()){
            return AjaxResult.error("查询失败");
        }
        ChapterDO chapterDO = byId.get();
        return userServer.batchSearch(new ArrayList<>(chapterDO.getPartners()));
    }



    // 漫画的作者和漫画参与者
    private boolean checkValidation1(CartoonDO cartoonDO) {
        BaseUser baseUser = TokenCheckInterceptor.tl.get();
        String userId = baseUser.getId();
        if (!checkCartoonCreate(cartoonDO)){
            // 如果不是作者
            // 检查是不是参与者
            return cartoonDO.getPartners().contains(userId);
        }
        return true;

    }

    // 检查是不是漫画的作者
    private boolean checkCartoonCreate(CartoonDO cartoonDO) {
        BaseUser baseUser = TokenCheckInterceptor.tl.get();
        String userId = baseUser.getId();
        return Objects.equals(cartoonDO.getCreateBy(), userId);
    }

    // 修改章节，漫画的作者能修改，章节的参与者能修改，但是其他人不可以
    private Boolean checkValidation(ChapterDO chapterDO){
        String cartoonId = chapterDO.getCartoonId();
        Optional<CartoonDO> byId = cartoonRepository.findById(cartoonId);
        if (byId.isEmpty()){
            return false;
        }
        // 章节的参与者能修改章节
        if (chapterDO.getPartners().contains(TokenCheckInterceptor.tl.get().getId())){
            return true;
        }
        CartoonDO cartoonDO = byId.get();
        // 该章节的漫画，漫画作者 or 参与者能修改漫画
        return checkCartoonCreate(cartoonDO);

    }
    private ChapterDO genChapter() {
        ChapterDO chapterDO = new ChapterDO();
        chapterDO.setId(CommonUtil.getRandomCode());
        return chapterDO;
    }
}
