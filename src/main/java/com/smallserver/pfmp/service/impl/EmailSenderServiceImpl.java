package com.smallserver.pfmp.service.impl;

import com.smallserver.pfmp.service.inter.EmailSenderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

/**
 * Created by qi.wang on 18/2/22.
 */
@Service("emailSenderService")
public class EmailSenderServiceImpl implements EmailSenderService {

    public static Logger LOGGER = LoggerFactory.getLogger(EmailSenderServiceImpl.class);

    private static JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();

    private static String EMAIL_SERVER_HOST = "smtp.163.com";
    private static String EMAIL_SERVER_USER_NAME = "wq18457147477@163.com";
    private static String EMAIL_SERVER_PASSWORD = "wq5978630";
    private static Integer EMAIL_SERVER_PORT = 25;

    static {
        javaMailSender.setHost(EMAIL_SERVER_HOST);//指定用来发送Email的邮件服务器主机名
        javaMailSender.setPort(EMAIL_SERVER_PORT);//默认端口，标准的SMTP端口
        javaMailSender.setUsername(EMAIL_SERVER_USER_NAME);//用户名
        javaMailSender.setPassword(EMAIL_SERVER_PASSWORD);//密码
    }

    @Override
    public boolean sendMail(String pickName, String emailAddress, String title, String content) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();//消息构造器
            message.setFrom(EMAIL_SERVER_USER_NAME);//发件人
            message.setTo(emailAddress);//收件人
            message.setSubject(title);//主题
            message.setText(content);//正文
            javaMailSender.send(message);
            return true;
        } catch (Exception e){
            return false;
        }
    }
}
