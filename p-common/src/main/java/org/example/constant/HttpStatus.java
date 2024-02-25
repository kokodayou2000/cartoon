package org.example.constant;

public class HttpStatus {
    public static int SUCCESS = 200;

    /**
     * 参数列表问题
     */
    public static int BAD_REQUEST =400;

    /**
     * 权限错误
     */
    public static int UNAUTHORIZED =401;
    /**
     * 访问受限
     */
    public static int FORBIDDEN =403;
    /**
     * 找不到资源
     */
    public static int NOT_FOUND =404;

    /**
     * 系统内部错误
     */
    public static final int ERROR = 500;


}
