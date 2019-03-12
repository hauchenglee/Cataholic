package web.com.store.model;

import java.io.Serializable;
import java.sql.Blob;
import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "Member_Table")
public class MemberBean implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer memberKey;
    private String memberId;
    private String memberPassword;
    private String memberDisplayName;
    private Blob memberImage;
    private Timestamp registerTime;
    private String memberMailCode;
    private String memberMailChecked;
    private String memberAuthorization;
    private String memberForgotPasswordCode;
    
    
    private transient List<CatBean> catList;

    public MemberBean() {
    }

    public MemberBean(Integer memberKey, String memberId, String memberPassword, String memberDisplayName, Blob memberImage, Timestamp registerTime, String memberMailCode, String memberMailChecked, String memberAuthorization, String memberForgotPasswordCode) {
        this.memberKey = memberKey;
        this.memberId = memberId;
        this.memberPassword = memberPassword;
        this.memberDisplayName = memberDisplayName;
        this.memberImage = memberImage;
        this.registerTime = registerTime;
        this.memberMailCode = memberMailCode;
        this.memberMailChecked = memberMailChecked;
        this.memberAuthorization = memberAuthorization;
        this.memberForgotPasswordCode = memberForgotPasswordCode;
    }

    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getMemberKey() {
		return memberKey;
	}

	public void setMemberKey(Integer memberKey) {
		this.memberKey = memberKey;
	}

	@OneToMany(fetch = FetchType.EAGER, mappedBy="memberBean")
	public List<CatBean> getCatList() {
		return catList;
	}

	public void setCatList(List<CatBean> catList) {
		this.catList = catList;
	}

	
    

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getMemberPassword() {
        return memberPassword;
    }

    public void setMemberPassword(String memberPassword) {
        this.memberPassword = memberPassword;
    }

    public String getMemberDisplayName() {
        return memberDisplayName;
    }

    public void setMemberDisplayName(String memberDisplayName) {
        this.memberDisplayName = memberDisplayName;
    }

    public Timestamp getRegisterTime() {
        return registerTime;
    }

    public void setRegisterTime(Timestamp registerTime) {
        this.registerTime = registerTime;
    }

    public String getMemberMailCode() {
        return memberMailCode;
    }

    public void setMemberMailCode(String memberMailCode) {
        this.memberMailCode = memberMailCode;
    }

    public String getMemberMailChecked() {
        return memberMailChecked;
    }

    public void setMemberMailChecked(String memberMailChecked) {
        this.memberMailChecked = memberMailChecked;
    }

    public String getMemberAuthorization() {
        return memberAuthorization;
    }

    public void setMemberAuthorization(String memberAuthorization) {
        this.memberAuthorization = memberAuthorization;
    }

    public String getMemberForgotPasswordCode() {
        return memberForgotPasswordCode;
    }

    public void setMemberForgotPasswordCode(String memberForgotPasswordCode) {
        this.memberForgotPasswordCode = memberForgotPasswordCode;
    }

    public Blob getMemberImage() {
        return memberImage;
    }

    public void setMemberImage(Blob memberImage) {
        this.memberImage = memberImage;
    }
}
