package web.com.store.service;

import web.com.store.model.MemberBean;

public interface MailService {
    public void generateMail1(MemberBean memberBean, String code);

    public void generateMail2(MemberBean memberBean, String code);

    public void generateMail3(MemberBean memberBean, String code);

    public int doMailSendUtil(MemberBean memberBean);
}
