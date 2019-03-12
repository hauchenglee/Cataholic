package web.com.store.repository.impl;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import web.com.store.model.CatBean;
import web.com.store.repository.CatDao;


@Repository
public class CatDaoImpl implements CatDao {

	@Autowired
	SessionFactory factory;
	
	@SuppressWarnings("unused")
	private Integer catId;
	
	public CatDaoImpl() {

	}

	@Override
	public void insert(CatBean cb) {
		Session session = factory.getCurrentSession();
		session.save(cb);
	}

	@Override
	public void update(CatBean cb) {
		Session session = factory.getCurrentSession();
		session.saveOrUpdate(cb);
	}

	@Override
	public List<CatBean> getPageCats() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getRecordCounts() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getTotalPages() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void delete(CatBean cb) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public CatBean findByPrimaryKey(int key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CatBean getCatBean() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setPageNo(int pageNo) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setCatId(Integer catId) {
		// TODO Auto-generated method stub
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CatBean> getAllCats() {
		String hql = "FROM CatBean";
		Session session = factory.getCurrentSession();
		return session.createQuery(hql).list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CatBean> getCatsByMemberKey(web.com.store.model.MemberBean memberBean) {
		String hql = "FROM CatBean WHERE memberBean = :member";
		Session session = factory.getCurrentSession();
		return session.createQuery(hql).setParameter("member", memberBean).getResultList();
	}

	
	
	

}
