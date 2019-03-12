package com.example.dong.cataholic;

public class Evolution {
    int evolGenerate;
    int evolCatMoney;
    int evolCatAEffectTime;

    public Evolution(int evolGenerate, int evolCatMoney, int evolCatAEffectTime) {
        this.evolGenerate = evolGenerate;
        this.evolCatMoney = evolCatMoney;
        this.evolCatAEffectTime = evolCatAEffectTime;
    }

    public int getEvolGenerate() {
        return evolGenerate;
    }

    public void setEvolGenerate(int evolGenerate) {
        this.evolGenerate = evolGenerate;
    }

    public int getEvolCatMoney() {
        return evolCatMoney;
    }

    public void setEvolCatMoney(int evolCatMoney) {
        this.evolCatMoney = evolCatMoney;
    }

    public int getEvolCatAEffectTime() {
        return evolCatAEffectTime;
    }

    public void setEvolCatAEffectTime(int evolCatAEffectTime) {
        this.evolCatAEffectTime = evolCatAEffectTime;
    }
}
