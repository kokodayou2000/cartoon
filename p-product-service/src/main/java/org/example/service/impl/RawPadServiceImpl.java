package org.example.service.impl;

import org.example.core.AjaxResult;
import org.example.interceptor.TokenCheckInterceptor;
import org.example.model.BaseUser;
import org.example.model.RawPadDO;
import org.example.model.UpdateRawPadReq;
import org.example.repository.RawPadRepository;
import org.example.request.raw.CreateRawPadReq;
import org.example.service.IRawPadService;
import org.example.utils.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class RawPadServiceImpl implements IRawPadService {

    @Autowired
    private RawPadRepository rawPadRepository;



    @Override
    public AjaxResult createRawPad(CreateRawPadReq req) {
        BaseUser baseUser = TokenCheckInterceptor.tl.get();
        RawPadDO rawPadDO = rawPadRepository.queryByPaperIdAndUserId(req.getPaperId(), baseUser.getId());
        // 如果不为空，就不需要新创建
        if (rawPadDO != null){
            return AjaxResult.success("已经创建过了");
        }

        // 创建初始化的rawPad
        RawPadDO rawPad = new RawPadDO();
        rawPad.setId(CommonUtil.getRandomCode());
        rawPad.setPaperId(req.getPaperId());
        rawPad.setUserId(baseUser.getId());
        rawPad.setPenList(new ArrayList<>());
        RawPadDO insert = rawPadRepository.insert(rawPad);
        return AjaxResult.success(insert);
    }


    // 当用户创建完paper之后，获取该paperID下所有的笔迹
    // 一对多关系 paper 对应多个RawPadDO
    @Override
    public AjaxResult workRawPad(String paperId) {
        List<RawPadDO> rawPadDO = rawPadRepository.queryAllByPaperId(paperId);
        return AjaxResult.success(rawPadDO);
    }



    @Override
    public AjaxResult UpdateRawPadReq(UpdateRawPadReq req) {
        String paperId = req.getId();
        Optional<RawPadDO> byId = rawPadRepository.findById(paperId);
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
