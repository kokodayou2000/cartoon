package org.example.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PartnerInfo {

    // 用户
    private String userId;

    public PartnerInfo(String userId, Integer paperNum) {
        this.userId = userId;
        this.paperNum = paperNum;
    }

    // 该作者画的页面数量
    private Integer paperNum;
}

