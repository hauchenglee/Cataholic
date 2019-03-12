package web.com.store.model;

public class CatMemberBean {
	private MemberBean memberBean;
    private CatBean catBean;

    public CatMemberBean() {
    }

    public CatMemberBean(MemberBean memberBean, CatBean catBean) {
        this.memberBean = memberBean;
        this.catBean = catBean;
    }

    public MemberBean getMemberBean() {
        return memberBean;
    }

    public void setMemberBean(MemberBean memberBean) {
        this.memberBean = memberBean;
    }

    public CatBean getCatBean() {
        return catBean;
    }

    public void setCatBean(CatBean catBean) {
        this.catBean = catBean;
    }

}
