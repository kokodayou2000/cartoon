package org.example.model;

import lombok.Data;

@Data
public class SalesVolume {

    // 销售章节的数量
    private Integer num;
    // 总点数
    private Integer totalPoint;

    public SalesVolume(Integer num, Integer totalPoint) {
        this.num = num;
        this.totalPoint = totalPoint;
    }
}

