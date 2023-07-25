package com.liuche.user.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

/**
 * @Author 刘彻
 * @Date 2023/7/25 17:57
 * @PackageName: com.liuche.user.util
 * @ClassName: SendMsgUtil
 * @Description: 发送邮箱信息工具类
 */
@Component
@Slf4j
public class SendMsgUtil {
    /**
     * Spring Boot 提供了一个发送邮件的简单抽象，直接注入即可使用
     */
    @Autowired
    private JavaMailSender mailSender;
    /**
     * 配置文件中的发送邮箱
     */
    @Value("${spring.mail.username}")
    private String from;

    public void sendMsg(String to, String subject, String msg) {
        SimpleMailMessage message = new SimpleMailMessage();
        // 发送者邮箱
        message.setFrom(from);
        // 接收者邮箱
        message.setTo(to);
        // 主题
        message.setSubject(subject);
        // 邮件内容
        message.setText(msg);
        // 发送
        mailSender.send(message);
        log.info("邮件已发送");
    }
}
