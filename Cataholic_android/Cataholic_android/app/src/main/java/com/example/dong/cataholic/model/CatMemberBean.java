package com.example.dong.cataholic.model;

import com.example.dong.cataholic.model.MemberBean;
import com.example.dong.cataholic.model.CatBean;

import java.io.Serializable;

public class CatMemberBean implements Serializable {
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
