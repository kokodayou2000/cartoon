package org.example.component.impl;

import lombok.extern.slf4j.Slf4j;
import org.example.component.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class MailServiceImpl implements MailService {

    /**
     * spring boot提供的发送邮箱的方法
     */
    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.from}")
    private String from;

    @Override
    public void sendMail(String to, String subject, String content) {
        //创建一个邮箱对象
        SimpleMailMessage msg = new SimpleMailMessage();
        //邮箱发送人
        msg.setFrom(from);

        msg.setCc(to);
        //邮箱接收人
        msg.setTo(to);
        //邮件主题
        msg.setSubject(subject);
        //邮件的内容
        msg.setText(content);

        mailSender.send(msg);
        log.info("邮件发送成功{}",msg.toString());


    }
}
