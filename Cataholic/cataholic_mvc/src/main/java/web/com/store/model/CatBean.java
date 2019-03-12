package web.com.store.model;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "Cat_Table")
public class CatBean implements Serializable {
	private static final long serialVersionUID = 1L;
	Integer catId;
	String catName;//
	String imageId;
	String catNarrative;//
	Integer catPrice;
	Integer catInterest;
	Integer catDuration;//
	Integer catSpeed;//
	Integer catUnitMoney;//
	@JsonIgnore
	MemberBean memberBean;
	Integer memberKey;

	public CatBean() {

	}

	
	

	public CatBean(Integer catId, String catName, String imageId, String catNarrative, Integer catPrice,
			Integer catInterest, Integer catDuration, Integer catSpeed, Integer catUnitMoney, MemberBean memberBean,
			Integer memberKey) {
		super();
		this.catId = catId;
		this.catName = catName;
		this.imageId = imageId;
		this.catNarrative = catNarrative;
		this.catPrice = catPrice;
		this.catInterest = catInterest;
		this.catDuration = catDuration;
		this.catSpeed = catSpeed;
		this.catUnitMoney = catUnitMoney;
		this.memberBean = memberBean;
		this.memberKey = memberKey;
	}




	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Integer getCatId() {
		return catId;
	}


	public void setCatId(Integer catId) {
		this.catId = catId;
	}


	public Integer getMemberKey() {
		return memberKey;
	}


	public void setMemberKey(Integer memberKey) {
		this.memberKey = memberKey;
	}


	public String getCatName() {
		return catName;
	}


	public void setCatName(String catName) {
		this.catName = catName;
	}


	public String getImageId() {
		return imageId;
	}


	public void setImageId(String imageId) {
		this.imageId = imageId;
	}


	public String getCatNarrative() {
		return catNarrative;
	}


	public void setCatNarrative(String catNarrative) {
		this.catNarrative = catNarrative;
	}


	public Integer getCatPrice() {
		return catPrice;
	}


	public void setCatPrice(Integer catPrice) {
		this.catPrice = catPrice;
	}


	public Integer getCatInterest() {
		return catInterest;
	}


	public void setCatInterest(Integer catInterest) {
		this.catInterest = catInterest;
	}


	public Integer getCatDuration() {
		return catDuration;
	}


	public void setCatDuration(Integer catDuration) {
		this.catDuration = catDuration;
	}


	public Integer getCatSpeed() {
		return catSpeed;
	}


	public void setCatSpeed(Integer catSpeed) {
		this.catSpeed = catSpeed;
	}


	public Integer getCatUnitMoney() {
		return catUnitMoney;
	}


	public void setCatUnitMoney(Integer catUnitMoney) {
		this.catUnitMoney = catUnitMoney;
	}

	
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="FK_memberBean")
	public MemberBean getMemberBean() {
		return memberBean;
	}


	public void setMemberBean(MemberBean memberBean) {
		this.memberBean = memberBean;
	}


	
	
}
