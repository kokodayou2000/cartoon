package org.example.controller;

import org.example.core.AjaxResult;
import org.example.model.UpdateRawPadReq;
import org.example.request.CreateRawPadReq;
import org.example.service.IPaperService;
import org.example.service.IRawPadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

// rawPad 记录
@RestController
@RequestMapping("/api/v1/rawPad")
public class RawPadController {

    @Autowired
    private IRawPadService RawPadService;


    // 获取paper下对应的RawPad数据
    // 这个会获取多个 RawPad, 因为一个 paper 映射多个 rawPad
    @GetMapping("/workRawPad/{paperId}")
    public AjaxResult workRawPad(@PathVariable("paperId") String paperId){
        return RawPadService.workRawPad(paperId);
    }


    // 一个 paper 关联多个 rawPad
    // 但是每个用户和paper只能对应一个rawPad
    @PostMapping("/createRawPad")
    public AjaxResult createRawPad(
            @RequestBody CreateRawPadReq padReq
    ){
        return RawPadService.createRawPad(padReq);
    }

    @PostMapping("/update")
    public AjaxResult updateRawPad(
            @RequestBody UpdateRawPadReq req
    ){
        return RawPadService.UpdateRawPadReq(req);
    }

}
