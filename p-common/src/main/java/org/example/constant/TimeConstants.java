package org.example.constant;

public class TimeConstants {

    /**
     * 10分钟
     */
    public static final long EXPIRE_TIME_10 = 10 * 1000 * 60L;

    /**
     * 5分钟
     */
    public static final long EXPIRE_TIME_5 = 5 * 1000 * 60L;

    /**
     * 3分钟
     */
    public static final long EXPIRE_TIME_3 = 3 * 1000 * 60L;

    /**
     * 1分钟
     */
    public static final long EXPIRE_TIME_1= 1000 * 60L;


    /**
     * 以 / 分割的
     * 这个会在oss中自动创建文件
     * 2022year
     *  1mon
     *  2mon
     *    day-1
     *    day-2
     */
    public static final String TIME_FORMAT = "yyyy/MM/dd";
}
