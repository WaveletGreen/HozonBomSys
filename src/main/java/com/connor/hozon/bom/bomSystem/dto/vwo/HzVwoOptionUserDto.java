package com.connor.hozon.bom.bomSystem.dto.vwo;

public class HzVwoOptionUserDto {
    private Long selectedUserId;

    private String opiBomName;

    private String opiPmtName;

    private String opiProjName;

    private Long vwoId;

    private Integer selectId;

    public Long getSelectedUserId() {
        return selectedUserId;
    }

    public void setSelectedUserId(Long selectedUserId) {
        this.selectedUserId = selectedUserId;
    }

    public String getOpiBomName() {
        return opiBomName;
    }

    public void setOpiBomName(String opiBomName) {
        this.opiBomName = opiBomName;
    }

    public Long getVwoId() {
        return vwoId;
    }

    public void setVwoId(Long vwoId) {
        this.vwoId = vwoId;
    }

    public Integer getSelectId() {
        return selectId;
    }

    public void setSelectId(Integer selectId) {
        this.selectId = selectId;
    }

    public String getOpiPmtName() {
        return opiPmtName;
    }

    public void setOpiPmtName(String opiPmtName) {
        this.opiPmtName = opiPmtName;
    }

    public String getOpiProjName() {
        return opiProjName;
    }

    public void setOpiProjName(String opiProjName) {
        this.opiProjName = opiProjName;
    }
}
