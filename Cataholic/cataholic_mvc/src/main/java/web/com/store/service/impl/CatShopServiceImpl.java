package web.com.store.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import web.com.store.model.CatBean;
import web.com.store.model.MemberBean;
import web.com.store.repository.CatDao;
import web.com.store.service.CatShopService;

@Service
@Transactional
public class CatShopServiceImpl implements CatShopService {

	@Autowired
	CatDao catDao;

	public CatShopServiceImpl() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void insert(CatBean cb) {
		catDao.insert(cb);
	}

	@Override
	public void update(CatBean cb) {
		catDao.update(cb);
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
	public CatBean getRestaurantBean() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<CatBean> getAllCats() {
		return catDao.getAllCats();
	}

	@Override
	public List<CatBean> getPageCats() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getTotalPages() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setCatId(int catId) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setPageNo(int pageNo) {
		// TODO Auto-generated method stub

	}

	@Override
	public long getRecordCounts() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<CatBean> getCatsByMemberKey(MemberBean memberBean) {
		return catDao.getCatsByMemberKey(memberBean);
	}

}
