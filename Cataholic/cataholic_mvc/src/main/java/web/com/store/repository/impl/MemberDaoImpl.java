package web.com.store.repository.impl;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import web.com.store.model.MemberBean;
import web.com.store.repository.MemberDao;

@Repository
public class MemberDaoImpl implements MemberDao {

	@Autowired
	SessionFactory factory;
	
	public MemberDaoImpl() {
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean isIdExists(String id) {
		Session session = factory.getCurrentSession();
		String hql = "from MemberBean m where m.memberId=:id";
        List<MemberBean> list;
        list = session.createQuery(hql).setParameter("id", id).getResultList();

		return !list.isEmpty();
	}

	@Override
	public int insertMember(MemberBean memberBean) {
		Session session = factory.getCurrentSession();
		int insertResult = (int) session.save(memberBean);
		if (insertResult == 0) {
			return 0;
		}
		return 1;
	}

	@Override
	public int updateMember(String memberId, String column, String updateValue) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int deleteMember(MemberBean memberBean) {
		return 0;
	}

	@Override
	public MemberBean queryByMemberId(String memberId) {
		String hql = "from MemberBean m where m.memberId=:id";
		Session session = factory.getCurrentSession();
        MemberBean memberBean = (MemberBean) session.createQuery(hql).setParameter("id", memberId).getSingleResult();
		return memberBean;
	}

	@Override
	public MemberBean checkIdPassword(String memberId, String memberPassword) {
		
		Session session = factory.getCurrentSession();
		String hql = "from MemberBean m where m.memberId=:id and m.memberPassword=:password";
		MemberBean memberBean = null;
		memberBean = (MemberBean) session.createQuery(hql).setParameter("id", memberId).setParameter("password", memberPassword).getSingleResult();
		if (memberBean != null) {
            return memberBean;
        } else {
            return null;
        }
	}

}
