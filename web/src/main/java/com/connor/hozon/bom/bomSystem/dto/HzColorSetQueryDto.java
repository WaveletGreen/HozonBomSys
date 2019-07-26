package com.connor.hozon.bom.bomSystem.dto;

import com.connor.hozon.bom.common.base.entity.QueryBase;

public class HzColorSetQueryDto extends QueryBase {

    private String pColorOfSet;

    private String pColorName;

    private String pColorCode;

    private String pColorPlate;


    public String getpColorOfSet() {
        return pColorOfSet;
    }

    public void setpColorOfSet(String pColorOfSet) {
        this.pColorOfSet = pColorOfSet;
    }

    public String getpColorName() {
        return pColorName;
    }

    public void setpColorName(String pColorName) {
        this.pColorName = pColorName;
    }

    public String getpColorCode() {
        return pColorCode;
    }

    public void setpColorCode(String pColorCode) {
        this.pColorCode = pColorCode;
    }

    public String getpColorPlate() {
        return pColorPlate;
    }

    public void setpColorPlate(String pColorPlate) {
        this.pColorPlate = pColorPlate;
    }
}
