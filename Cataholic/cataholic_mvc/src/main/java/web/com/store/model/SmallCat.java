package web.com.store.model;

import java.io.Serializable;

public class SmallCat implements Serializable{

	private static final long serialVersionUID = 1L;
	int smallCatMoney;
    int smallCatId;
    int smallCatVisibility;

    public SmallCat(int smallCatMoney, int smallCatId, int smallCatVisibility) {
        this.smallCatMoney = smallCatMoney;
        this.smallCatId = smallCatId;
        this.smallCatVisibility = smallCatVisibility;
    }

    public int getSmallCatMoney() {
        return smallCatMoney;
    }

    public void setSmallCatMoney(int smallCatMoney) {
        this.smallCatMoney = smallCatMoney;
    }

    public int getSmallCatId() {
        return smallCatId;
    }

    public void setSmallCatId(int smallCatId) {
        this.smallCatId = smallCatId;
    }

    public int getSmallCatVisibility() {
        return smallCatVisibility;
    }

    public void setSmallCatVisibility(int smallCatVisibility) {
        this.smallCatVisibility = smallCatVisibility;
    }

}
