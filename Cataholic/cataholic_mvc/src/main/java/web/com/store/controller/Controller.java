package web.com.store.controller;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import web.com.store.model.CatBean;
import web.com.store.model.CatMemberBean;
import web.com.store.model.MemberBean;
import web.com.store.service.CatShopService;
import web.com.store.service.CodeUtil;
import web.com.store.service.GlobalService;
import web.com.store.service.MailService;
import web.com.store.service.RegisterService;
import web.com.store.service.impl.MailServiceImpl;

@RestController
public class Controller {

	@Autowired
	CatShopService catShopService;
	@Autowired
	RegisterService registerService;


	@RequestMapping("/catShop.do")
	public List<CatBean> getListCats() {
		return catShopService.getAllCats();
	}

	@RequestMapping("/login.do")
	public MemberBean loginMemberBean(@RequestBody MemberBean memberBean) {
		String status;

		String userId = memberBean.getMemberId();
		String userPassword = memberBean.getMemberPassword();
		userPassword = GlobalService.getMD5Encoding(GlobalService.encryptString(userPassword));
		MemberBean memberBeanLogin = null;
		try {
			memberBeanLogin = registerService.checkIdPassword(userId, userPassword);
			if (memberBeanLogin != null) {
				// 賬號密碼正確
				status = "checkOK";
			} else {
				status = "checkError";
			}
		} catch (RuntimeException e) {
			status = "unexpectedException";
		}

		// 確認激活
		if (status.equals("checkOK")) {
			if (memberBeanLogin.getMemberMailChecked().equals("0")) {
				status = "isActiveFalse";
			} else {
				return memberBeanLogin;
			}
		}
		return null;
	}

	@RequestMapping("/register.do")
	public MemberBean registerMemberBean(@RequestBody MemberBean memberBean) {

		// 存入執行結果
		int sendResult = 0;
		String status;

		// 確認id是否重複
		if (registerService.isIdExists(memberBean.getMemberId())) {
			// 表示已經存在此用戶
			status = "idExist";
		} else {
			// 表示不存在此用戶
			status = "idNotExist";
		}

		// 如果賬號沒有重複
		if (status.equals("idNotExist")) {
			// 將密碼加密兩次
			memberBean.setMemberPassword(
					GlobalService.getMD5Encoding(GlobalService.encryptString(memberBean.getMemberPassword())));

			// 獲得現在系統時間，為註冊的時間
			Timestamp timestamp = new Timestamp(System.currentTimeMillis());

			// 產生激活碼
			String code = CodeUtil.generateUniqueCode();

			// 將會員資料包裝成一個包裹
			memberBean.setRegisterTime(timestamp);
			memberBean.setMemberAuthorization("1");
			memberBean.setMemberMailChecked("1");

			// 存入資料庫
			int insertResult = registerService.insertMember(memberBean);

			// 發送激活郵件
			if (insertResult == 1) {
				MailService mailService = new MailServiceImpl();
				mailService.generateMail1(memberBean, code);
				sendResult = mailService.doMailSendUtil(memberBean);
			} else {
				status = "insertError";
			}
		}
		// 發送執行結果透過ajax到前端
		if (sendResult == 1 && registerService.queryByMemberId(memberBean.getMemberId()) != null) {
			return memberBean;
		}
		return null;
	}

	@RequestMapping("/adopt.do")
	public MemberBean adopt(@RequestBody CatMemberBean catMemberBean) {
		MemberBean memberBean = catMemberBean.getMemberBean();
		CatBean catBean = catMemberBean.getCatBean();
		catBean.setMemberBean(memberBean);
		catShopService.insert(catBean);
		MemberBean mb = catBean.getMemberBean();
		return mb;
	}
	
	@RequestMapping("/manage.do")
	public List<CatBean> manage(@RequestBody MemberBean memberBean) {
		
		return catShopService.getCatsByMemberKey(memberBean);
	}

	@RequestMapping("/feed.do")
	public MemberBean feed(@RequestBody CatMemberBean catMemberBean) {
		MemberBean memberBean = catMemberBean.getMemberBean();
		CatBean catBean = catMemberBean.getCatBean();
		catBean.setMemberBean(memberBean);
		catShopService.update(catBean);
		MemberBean mb = catBean.getMemberBean();
		return mb;
	}
	

	
}
