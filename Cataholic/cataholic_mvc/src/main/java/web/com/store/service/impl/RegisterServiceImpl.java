package web.com.store.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import web.com.store.model.MemberBean;
import web.com.store.repository.MemberDao;
import web.com.store.repository.impl.MemberDaoImpl;
import web.com.store.service.RegisterService;

@Transactional
@Service
public class RegisterServiceImpl implements RegisterService {
    @Autowired
	private MemberDao memberDao;

    public RegisterServiceImpl() {
        memberDao = new MemberDaoImpl();
    }

    @Override
    public boolean isIdExists(String id) {
        return memberDao.isIdExists(id);
    }

    @Override
    public int insertMember(MemberBean memberBean) {
        return memberDao.insertMember(memberBean);
    }

    @Override
    public int updateMember(String memberId, String column, String updateValue) {
        return memberDao.updateMember(memberId, column, updateValue);
    }

    @Override
    public int deleteMember(MemberBean memberBean) {
        return memberDao.deleteMember(memberBean);
    }

    @Override
    public MemberBean queryByMemberId(String memberId) {
        return memberDao.queryByMemberId(memberId);
    }

    @Override
    public MemberBean checkIdPassword(String memberId, String memberPassword) {
        return memberDao.checkIdPassword(memberId, memberPassword);
    }
}
