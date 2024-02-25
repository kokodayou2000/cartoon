package org.example.component;

public interface MailService {
    /**
     * 发送邮件
     * @param to 发送的对象
     * @param subject 主题
     * @param content 内容
     */
    void sendMail(String to,String subject,String content);
}
