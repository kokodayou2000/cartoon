package org.example.enums;

public enum CouponCategoryEnum {
    /**
     * 新用户卷
     */
    NEW_USER("NEW_USER"),

    /**
     * 营销卷
     */
    PROMOTION("PROMOTION"),

    /**
     * 任务卷
     */
    TASK("TASK");

    private String type;
    CouponCategoryEnum(String type){
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
