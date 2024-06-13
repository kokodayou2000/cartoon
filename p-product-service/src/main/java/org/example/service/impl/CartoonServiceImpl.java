package org.example.service.impl;


import lombok.extern.slf4j.Slf4j;
import org.example.core.AjaxResult;
import org.example.feign.IUserServer;
import org.example.interceptor.TokenCheckInterceptor;
import org.example.model.BaseUser;
import org.example.model.CartoonDO;
import org.example.model.ChapterDO;
import org.example.repository.CartoonRepository;
import org.example.repository.ChapterRepository;
import org.example.request.cartoon.AddPatternsReq;
import org.example.request.CartoonSaleNumReq;
import org.example.request.cartoon.CreateCartoonReq;
import org.example.request.cartoon.UpdateCartoonReq;
import org.example.response.CartoonInfo;
import org.example.service.ICartoonService;
import org.example.utils.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


import java.util.*;

import static org.example.constant.CartoonConstant.*;

@Service
@Slf4j
public class CartoonServiceImpl implements ICartoonService {

    @Autowired
    private CartoonRepository cartoonRepository;

    @Autowired
    private ChapterRepository chapterRepository;


    @Autowired
    private IUserServer userServer;

    @Override
    public AjaxResult list(String order,int page, int size) {
        PageRequest pageRequest ;
        if (ORDER_FIELD.contains(order)){
            pageRequest  = PageRequest.of(page,size,Sort.by(order).descending());
        }else {
            pageRequest  = PageRequest.of(page,size);
        }

//        Page<CartoonDO> dos = cartoonRepository.findAll(pageRequest);
        Iterable<CartoonDO> all = cartoonRepository.findAll();

        return AjaxResult.success(all);
    }

    @Override
    public AjaxResult cartoonListByTag(String tag) {
        List<CartoonDO> cartoonDOS = cartoonRepository.queryAllByTagsContaining(tag);
        return AjaxResult.success(cartoonDOS);
    }


    @Override
    public AjaxResult createCartoon(CreateCartoonReq req) {
        BaseUser baseUser = TokenCheckInterceptor.tl.get();
        CartoonDO genCartoonDO = genCartoonDO();
        genCartoonDO.setCreateBy(baseUser.getId());

        HashSet<String> partnerList = new HashSet<>();
        partnerList.add(baseUser.getId());
        genCartoonDO.setPartners(partnerList);

        genCartoonDO.setTitle(req.getTitle());
        genCartoonDO.setPrice(req.getPrice());
        genCartoonDO.setIntroduction(req.getIntroduction());
        genCartoonDO.setTags(req.getTags());

        CartoonDO save = cartoonRepository.save(genCartoonDO);
        log.info("创建漫画 {}",save);
        return AjaxResult.success(save);
    }

    @Override
    public AjaxResult uploadCoverImg(String url, String id) {

        Optional<CartoonDO> byId = cartoonRepository.findById(id);
        if (byId.isEmpty()){
            return AjaxResult.error("查询漫画失败");
        }


        CartoonDO cartoonDO = byId.get();
        if (!checkCreateValidation(cartoonDO.getCreateBy())){
            return AjaxResult.error("权限错误");
        }


        log.info("上传漫画头像 {} ",id);
        cartoonDO.setCoverUrl(url);
        CartoonDO save = cartoonRepository.save(cartoonDO);
        return AjaxResult.success(save);
    }

    @Override
    public AjaxResult updateCartoonInfo(UpdateCartoonReq req) {

        Optional<CartoonDO> byId = cartoonRepository.findById(req.getId());
        if (byId.isEmpty()){
            return AjaxResult.error("查询漫画失败");
        }

        CartoonDO cartoonDO = byId.get();
        if (!checkCreateValidation(cartoonDO.getCreateBy())){
            return AjaxResult.error("权限错误");
        }

        cartoonDO.setTags(req.getTags());
        cartoonDO.setTitle(req.getTitle());
        cartoonDO.setIntroduction(req.getIntroduction());
        cartoonDO.setLastUpdateTime(new Date());

        CartoonDO save = cartoonRepository.save(cartoonDO);
        return AjaxResult.success(save);
    }

    @Override
    public AjaxResult addPattern(AddPatternsReq req) {
        String cartoonId = req.getCartoonId();
        Optional<CartoonDO> byId = cartoonRepository.findById(cartoonId);
        if (byId.isEmpty()){
            return AjaxResult.error("查询漫画失败");
        }
        CartoonDO cartoonDO = byId.get();
        if (!checkCreateValidation(cartoonDO.getCreateBy())){
            return AjaxResult.error("权限错误");
        }
        AjaxResult result = userServer.exist(req.getPatternId());
        if (!"200".equals(String.valueOf(result.get("code")))) {
            return AjaxResult.error("要添加的用户不存在");
        }

        Set<String> partners = cartoonDO.getPartners();
        partners.add(req.getPatternId());
        CartoonDO save = cartoonRepository.save(cartoonDO);
        return AjaxResult.success(save);
    }

    @Override
    public AjaxResult cartoonInfo(String cartoonId) {
        Optional<CartoonDO> byId = cartoonRepository.findById(cartoonId);
        if (byId.isEmpty()){
            return AjaxResult.error("查看失败");
        }
        CartoonDO cartoonDO = byId.get();
        String cartoonDOId = cartoonDO.getId();
        List<ChapterDO> chapterDOList = chapterRepository.findChapterDOSByCartoonId(cartoonDOId);

        CartoonInfo cartoonInfo = new CartoonInfo();
        cartoonInfo.setCartoonInfo(cartoonDO);
        cartoonInfo.setChapterList(chapterDOList);

        return AjaxResult.success(cartoonInfo);
    }

    @Override
    public AjaxResult chapterList(String cartoonId) {
        List<ChapterDO> chapterDOList = chapterRepository.queryAllByCartoonId(cartoonId);
        return AjaxResult.success(chapterDOList);
    }

    @Override
    public AjaxResult meJoin() {
        BaseUser baseUser = TokenCheckInterceptor.tl.get();
        String userId = baseUser.getId();
        Iterable<CartoonDO> cartoonRepositoryAll = cartoonRepository.findAll();
        ArrayList<CartoonDO> resList = new ArrayList<>();
        for (CartoonDO cartoonDO : cartoonRepositoryAll) {
            if (cartoonDO.getPartners().contains(userId)){
                resList.add(cartoonDO);
            }
        }
        return AjaxResult.success(resList);
    }

    @Override
    public AjaxResult meCreate() {
        BaseUser baseUser = TokenCheckInterceptor.tl.get();
        String userId = baseUser.getId();
        Iterable<CartoonDO> cartoonRepositoryAll = cartoonRepository.findAll();
        ArrayList<CartoonDO> resList = new ArrayList<>();
        for (CartoonDO cartoonDO : cartoonRepositoryAll) {
            if (cartoonDO.getCreateBy().equals(userId)){
                resList.add(cartoonDO);
            }
        }
        return AjaxResult.success(resList);
    }

    @Override
    public AjaxResult price(String cartoonId) {
        Optional<CartoonDO> byId = cartoonRepository.findById(cartoonId);
        if (byId.isEmpty()){
            return AjaxResult.error("查询漫画失败");
        }
        // 返回价格
        return AjaxResult.success(byId.get().getPrice());
    }

    @Override
    public AjaxResult sales(CartoonSaleNumReq req) {
        Optional<CartoonDO> byId = cartoonRepository.findById(req.getCartoonId());
        if (byId.isEmpty()){
            return AjaxResult.error("查询漫画失败");
        }
        CartoonDO cartoonDO = byId.get();
        Integer salesNum = cartoonDO.getSalesNum();
        cartoonDO.setSalesNum(req.getSales() + salesNum);
        CartoonDO save = cartoonRepository.save(cartoonDO);
        return AjaxResult.success(save);
    }

    @Override
    public AjaxResult cartoonPatternList(String cartoonId) {
        Optional<CartoonDO> byId = cartoonRepository.findById(cartoonId);
        if (byId.isEmpty()){
            return AjaxResult.error("查询漫画失败");
        }
        log.info("查询漫画参与者");
        CartoonDO cartoonDO = byId.get();
        Set<String> userIdList = cartoonDO.getPartners();
        AjaxResult result = userServer.batchSearch(new ArrayList<>(userIdList));
        return result;
    }

    @Override
    public Boolean checkCreate(String userId, String cartoonId) {
        Optional<CartoonDO> byId = cartoonRepository.findById(cartoonId);
        if (byId.isEmpty()){
            return false;
        }
        CartoonDO cartoonDO = byId.get();
        return Objects.equals(cartoonDO.getCreateBy(), userId);
    }



    /**
     *  校验合法性
     *  如果创建者和当前登录者相同，返回false
     * @param createBy
     * @return
     */
    public Boolean checkCreateValidation(String createBy){
        BaseUser baseUser = TokenCheckInterceptor.tl.get();
        return Objects.equals(createBy,baseUser.getId());
    }

    public CartoonDO genCartoonDO () {
        CartoonDO cartoonDO = new CartoonDO();
        cartoonDO.setId(CommonUtil.getRandomCode());

        cartoonDO.setCreateTime(new Date());
        cartoonDO.setStatus(STATUS_DOING);
        cartoonDO.setCoverUrl(DEFAULT_COVER);
        cartoonDO.setLastUpdateTime(new Date());
        cartoonDO.setSalesNum(0);
        return cartoonDO;
    }
}
