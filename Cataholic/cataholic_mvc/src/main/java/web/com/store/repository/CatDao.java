package web.com.store.repository;

import java.util.List;

import web.com.store.model.CatBean;
import web.com.store.model.MemberBean;


public interface CatDao {

	void insert(CatBean cb);

	void update(CatBean cb);
	
	List<CatBean> getPageCats();
	
	long getRecordCounts();
	
	int getTotalPages();

	void delete(CatBean cb);

	CatBean findByPrimaryKey(int key);

	CatBean getCatBean();
	
	void setPageNo(int pageNo);

	void setCatId(Integer catId);

	List<CatBean> getAllCats();
	
	List<CatBean> getCatsByMemberKey(MemberBean memberBean);

}