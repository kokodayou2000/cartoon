package org.example.controller;

import org.example.core.AjaxResult;
import org.example.model.UpdateRawPadReq;
import org.example.request.CreateRawPadReq;
import org.example.service.IRawPadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

// rawPad 记录
@RestController
@RequestMapping("/api/v1/rawPad")
public class RawPadController {

    @Autowired
    private IRawPadService RawPadService;


    // 一个 paper 关联多个 rawPad
    // 一个userId 关联多个 PaperID

    /**
     * 每个用户可以创建一个PawPad, userId绑定 PaperId,PaperID对应一个 rawPadId
     * @param padReq  paper id
     * @return
     */
    @PostMapping("/createRawPad")
    public AjaxResult createRawPad(
            @RequestBody CreateRawPadReq padReq
    ){
        return RawPadService.createRawPad(padReq);
    }

    // 获取raw数据 根据 paperID
    @GetMapping("/workRawPad/{paperId}")
    public AjaxResult workRawPad(@PathVariable("paperId") String paperId){
        return RawPadService.workRawPad(paperId);
    }

    // 更新该用户创建的 rawPad
    /**
     * 根据 rawPadId,来定位到 rawPad,根据 userID 进行鉴权
     * @param req rawPadId 笔迹
     * @return 更新数据
     */
    @PostMapping("/update")
    public AjaxResult updateRawPad(
            @RequestBody UpdateRawPadReq req
    ){
        return RawPadService.UpdateRawPadReq(req);
    }


}
