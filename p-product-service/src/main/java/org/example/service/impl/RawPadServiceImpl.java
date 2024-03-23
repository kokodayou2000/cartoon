package org.example.service.impl;

import org.example.core.AjaxResult;
import org.example.interceptor.TokenCheckInterceptor;
import org.example.model.BaseUser;
import org.example.model.RawPadDO;
import org.example.model.UpdateRawPadReq;
import org.example.repository.RawPadRepository;
import org.example.service.IRawPadService;
import org.example.utils.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RawPadServiceImpl implements IRawPadService {

    @Autowired
    private RawPadRepository rawPadRepository;


    @Override
    public AjaxResult create(String name) {
        RawPadDO rawPad = new RawPadDO();
        rawPad.setId(CommonUtil.getRandomCode());
        rawPad.setName(name);
        BaseUser baseUser = TokenCheckInterceptor.tl.get();
        rawPad.setUserId(baseUser.getId());
        rawPad.setPenList(new ArrayList<>());
        RawPadDO insert = rawPadRepository.insert(rawPad);
        return AjaxResult.success(insert);
    }

    @Override
    public AjaxResult list() {
        BaseUser baseUser = TokenCheckInterceptor.tl.get();
        String userId = baseUser.getId();
        Optional<List<RawPadDO>> optional = rawPadRepository.queryAllByUserId(userId);
        return optional.map(AjaxResult::success).orElseGet(() -> AjaxResult.success(new ArrayList<RawPadDO>()));
    }

    @Override
    public AjaxResult rawPad(String id) {
        Optional<RawPadDO> byId = rawPadRepository.findById(id);
        return byId.map(AjaxResult::success).orElseGet(() -> AjaxResult.error("没数据"));
    }

    @Override
    public AjaxResult UpdateRawPadReq(UpdateRawPadReq req) {
        Optional<RawPadDO> byId = rawPadRepository.findById(req.getId());
        if (byId.isEmpty()){
            return AjaxResult.error();
        }
        BaseUser baseUser = TokenCheckInterceptor.tl.get();
        RawPadDO rawPadDO = byId.get();
        if (!rawPadDO.getUserId().equals(baseUser.getId())){
            return AjaxResult.error("非法访问");
        }
        rawPadDO.setPenList(req.getPenList());
        RawPadDO save = rawPadRepository.save(rawPadDO);
        return AjaxResult.success(save);
    }


}
