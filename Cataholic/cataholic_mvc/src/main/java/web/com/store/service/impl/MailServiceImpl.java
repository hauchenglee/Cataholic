package web.com.store.service.impl;

import web.com.store.model.MailBean;
import web.com.store.model.MemberBean;
import web.com.store.service.Base64Security;
import web.com.store.service.MailSendUtil;
import web.com.store.service.MailService;

public class MailServiceImpl implements MailService {
    private MailBean mailBean;
    private String title;
    private String content;
    private String requestURI = "http://localhost:8080/cataholic_z/mail/controller/ReceiveMailServlet" + "?";

    // 註冊激活郵件
    @Override
    public void generateMail1(MemberBean memberBean, String code) {
        String memberId = memberBean.getMemberId();
        memberId = Base64Security.valueEncoder(memberId);

        // 樣板化郵件內容
        String queryString = "state=1&id=" + memberId + "&code=" + code;
        String requestURL = requestURI + queryString;

        // 郵件標題與內容
        title = "激活郵件";
        content = "<html><head></head><body><h2>請點擊連接以進行下一步操作</h2><h2><a href='" + requestURL + "'>點我</href></h2></body></html>";

        // 存入mailBean裡
        mailBean = new MailBean(1, title, content, code);
    }

    // 忘記密碼郵件
    @Override
    public void generateMail2(MemberBean memberBean, String code) {
        String memberId = memberBean.getMemberId();
        memberId = Base64Security.valueEncoder(memberId);

        // 樣板化郵件內容
        String queryString = "state=2&id=" + memberId + "&code=" + code;
        String requestURL = requestURI + queryString;
        java.sql.Timestamp timestamp = new java.sql.Timestamp(System.currentTimeMillis());

        // 郵件標題與內容
        title = "忘記密碼認證信";
        content = "<html><head></head><body><h2>親愛的用戶，您在：" + timestamp.toString() + " 時提交了修改密碼的請求，若這是您本人提出，請點擊連接以進行下一步操作</h2><h2><a href='" + requestURL + "'>點我</href></h2></body></html>";

        // 存入mailBean裡
        mailBean = new MailBean(2, title, content, code);
    }

    // 修改密碼通知郵件
    @Override
    public void generateMail3(MemberBean memberBean, String code) {
    }

    @Override
    public int doMailSendUtil(MemberBean memberBean) {
        try {
            new Thread(new MailSendUtil(memberBean, mailBean)).start();
            return 1;
        } catch (Exception e) {
            return 0;
        }
    }
}
