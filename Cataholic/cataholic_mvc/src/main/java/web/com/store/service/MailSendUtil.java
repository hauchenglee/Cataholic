package web.com.store.service;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import web.com.store.model.MailBean;
import web.com.store.model.MemberBean;

public class MailSendUtil implements Runnable {
    private MemberBean memberBean;
    private MailBean mailBean;

    public MailSendUtil(MemberBean memberBean, MailBean mailBean) {
        this.memberBean = memberBean;
        this.mailBean = mailBean;
    }

    @Override
    public void run() {
        // 1.创建连接对象javax.mail.Session
        // 2.创建邮件对象 javax.mail.Message
        // 3.发送一封激活邮件
        String senderEmail = "java010cataholic@gmail.com";// 发件人电子邮箱
        String senderPassword = "cataholiccataholic";// 发件人电子邮箱
        String hostMail = "smtp.gmail.com"; // 指定发送邮件的主机Gmail

        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", hostMail);
        properties.put("mail.smtp.port", "587");

        String memberId = memberBean.getMemberId();

        try {
            // 1.获取默认session对象
            Session session = Session.getDefaultInstance(properties, new Authenticator() {
                public PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(senderEmail, senderPassword); // 发件人邮箱账号、授权码
                }
            });

            // 2.创建邮件对象
            Message message = new MimeMessage(session);
            // 2.1设置发件人
            message.setFrom(new InternetAddress(senderEmail));
            // 2.2设置接收人
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(memberId));


            memberId = GlobalService.getMD5Encoding(memberId);
            String title = mailBean.getTitle();
            String content = mailBean.getContent();

            // 2.3设置邮件主题
            message.setSubject(title);
            // 2.4设置邮件内容
            message.setContent(content, "text/html;charset=UTF-8");

            // 3.发送邮件
            Transport.send(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
