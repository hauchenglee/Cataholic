package web.com.store.service;

import java.util.List;

import web.com.store.model.CatBean;
import web.com.store.model.MemberBean;



public interface CatShopService {
	void insert(CatBean rb);

	void update(CatBean rb);

	void delete(CatBean rb);

	CatBean findByPrimaryKey(int key);

	CatBean getRestaurantBean();

	List<CatBean> getAllCats();

	List<CatBean> getPageCats();
	
	int getTotalPages();

	void setCatId(int catId);
	
	void setPageNo(int pageNo);
	
	long getRecordCounts();
	
	List<CatBean> getCatsByMemberKey(MemberBean memberBean);
	

}
