package org.example.request;

import lombok.Data;

@Data
public class ActiveBannerReq {

    private String bannerId;

    private Boolean active;
}
