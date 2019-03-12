package com.example.dong.cataholic.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

import java.io.Serializable;
import java.sql.Blob;
import java.sql.Timestamp;
import java.util.List;

@Entity
public class MemberBean implements Serializable {
    private static final long serialVersionUID = 1L;
    @PrimaryKey
    private Integer memberKey;
    private String memberId;
    private String memberPassword;
    private String memberDisplayName;
    @Ignore
    private Blob memberImage;
    @Ignore
    private Timestamp registerTime;

    private String memberMailCode;
    private String memberMailChecked;
    private String memberAuthorization;
    private String memberForgotPasswordCode;

    @Ignore
    private List<CatBean> catList;

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

    public Integer getMemberKey() {
        return memberKey;
    }

    public void setMemberKey(Integer memberKey) {
        this.memberKey = memberKey;
    }

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