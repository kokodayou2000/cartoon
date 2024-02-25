package org.example.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.security.MessageDigest;
import java.util.UUID;

@Slf4j
public class CommonUtil {

    /**
     * 获取ip
     * @param request
     * @return
     */
    public static String getIpAddr(HttpServletRequest request) {
        String ipAddress = null;
        try {
            ipAddress = request.getHeader("x-forwarded-for");
            if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
                ipAddress = request.getHeader("Proxy-Client-IP");
            }
            if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
                ipAddress = request.getHeader("WL-Proxy-Client-IP");
            }
            if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
                ipAddress = request.getRemoteAddr();
                if (ipAddress.equals("127.0.0.1")) {
                    // 根据网卡取本机配置的IP
                    InetAddress inet = null;
                    try {
                        inet = InetAddress.getLocalHost();
                    } catch (UnknownHostException e) {
                        e.printStackTrace();
                    }
                    ipAddress = inet.getHostAddress();
                }
            }
            // 对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
            if (ipAddress != null && ipAddress.length() > 15) {
                // "***.***.***.***".length()
                // = 15
                if (ipAddress.indexOf(",") > 0) {
                    ipAddress = ipAddress.substring(0, ipAddress.indexOf(","));
                }
            }
        } catch (Exception e) {
            ipAddress="";
        }
        return ipAddress;
    }


    /**
     * MD5加密
     * @param data
     * @return
     */
    public static String MD5(String data)  {
        try {
            java.security.MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] array = md.digest(data.getBytes("UTF-8"));
            StringBuilder sb = new StringBuilder();
            for (byte item : array) {
                sb.append(Integer.toHexString((item & 0xFF) | 0x100).substring(1, 3));
            }

            return sb.toString().toUpperCase();
        } catch (Exception exception) {
        }
        return null;
    }

    /**
     * 获取随机数
     * @param len 验证码长度
     * @return 返回随机获取的验证码
     */
    public static String getRandomCode(Integer len){
        return RandomStringUtils.randomNumeric(len);
    }

    public static String getRandomCode(){
        return getRandomCode(10);
    }

    /**
     * 随机生成文件名
     * @return 返回随机文件名
     */
    public static String getRandomFileName(){
        return getRandomFileName(10);
    }

    /**
     * 随机生成文件名
     * @param len 文件名长度
     * @return 返回随机获取的验证码
     */
    public static String getRandomFileName(Integer len){
        return RandomStringUtils.randomAlphanumeric(len);
    }

    /**
     * 获取当前时间戳
     * @return 系统时间戳
     */
    public static long getCurrentTimestamp(){
        return System.currentTimeMillis();
    }

    /**
     * 生成32位的uuid
     * @return uuid
     */
    public static String generateUUID(){
        return UUID.randomUUID().toString().replaceAll("-","").substring(0,32);
    }

    /**
     * 响应json数据给前端 通过response的方式
     * @param response 响应对象
     * @param obj 数据
     */
    public static void sendJsonMessage(HttpServletResponse response,Object obj){
        //用来做序列化的
        ObjectMapper objectMapper = new ObjectMapper();
        response.setContentType("application/json;charset=utf-8");

        //获取到写对象 ，放到这边，能自动执行AutoCloseable
        try (PrintWriter writer =response.getWriter()){
            //执行写
            writer.print(objectMapper.writeValueAsString(obj));

            response.flushBuffer();
        } catch (IOException e) {
            log.warn("响应数据给前端异常");
        }

    }
}
