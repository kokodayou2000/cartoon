package org.example.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.example.core.AjaxResult;
import org.example.mapper.ChargeMapper;
import org.example.model.ChargeDO;
import org.example.service.ProductOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/charge")
@Slf4j
public class ChargeController {

    @Autowired
    private ChargeMapper chargeMapper;


    @GetMapping("/chargeList")
    public AjaxResult chargeList(){
        List<ChargeDO> chargeDOList = chargeMapper.selectList(new QueryWrapper<>());
        return AjaxResult.success(chargeDOList);
    }


}
