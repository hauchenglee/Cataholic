package com.example.dong.cataholic.model;

import java.io.Serializable;

public class AllCannedBean implements Serializable {
    CannedBean cannedBean01,cannedBean02,cannedBean03;

    public AllCannedBean() {
    }

    public CannedBean getCannedBean01() {
        return cannedBean01;
    }

    public void setCannedBean01(CannedBean cannedBean01) {
        this.cannedBean01 = cannedBean01;
    }

    public CannedBean getCannedBean02() {
        return cannedBean02;
    }

    public void setCannedBean02(CannedBean cannedBean02) {
        this.cannedBean02 = cannedBean02;
    }

    public CannedBean getCannedBean03() {
        return cannedBean03;
    }

    public void setCannedBean03(CannedBean cannedBean03) {
        this.cannedBean03 = cannedBean03;
    }
}
