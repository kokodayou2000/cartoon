package org.example.model;

import lombok.Data;

import java.util.List;

@Data
public class UpdateRawPadReq {

    private String id;

    // 笔画
    private List<Pen> penList;
}
