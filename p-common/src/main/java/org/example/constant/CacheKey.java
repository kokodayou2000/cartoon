package org.example.constant;


/**
 * 验证码模板
 */
public class CacheKey {
    /**
     * 注册验证码
     * param1 类型
     * param2 接收号码
     */
    public static final String CHECK_CODE_KEY = "code:%s:%s";

    /**
     * 购物车唯一标识 key是用户唯一标识
     */
    public static final String CART_CODE_KEY = "cart:%s";

    /**
     * 提交表单的 token key 一个用户只能有同时提交一个订单
     */
    public static final String SUBMIT_ORDER_TOKEN_KEY = "order:submit:%s";
}
