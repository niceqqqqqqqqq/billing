package com.smallserver.pfmp.service.inter;

/**
 * Created by qi.wang on 18/2/22.
 *
 * 发送邮件服务
 *
 */
public interface EmailSenderService {


    boolean sendMail(String pickName, String emailAddress, String title, String content);



}
