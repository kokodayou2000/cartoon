package org.example.controller;

import org.example.core.AjaxResult;
import org.example.model.UpdateRawPadReq;
import org.example.service.IPaperService;
import org.example.service.IRawPadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/rawPad")
public class RawPadController {

    @Autowired
    private IRawPadService RawPadService;

    @GetMapping("/create/{name}")
    public AjaxResult create(
            @PathVariable("name") String name
    ){
        return RawPadService.create(name);
    }

    @GetMapping("/list")
    public AjaxResult list(){
        return RawPadService.list();
    }

    @PostMapping("/update")
    public AjaxResult updateRawPad(
            @RequestBody UpdateRawPadReq req
    ){
        return RawPadService.UpdateRawPadReq(req);
    }

    @GetMapping("/rawPad/{id}")
    public AjaxResult rawPad(
            @PathVariable("id") String id
    ){
        return RawPadService.rawPad(id);
    }
}
