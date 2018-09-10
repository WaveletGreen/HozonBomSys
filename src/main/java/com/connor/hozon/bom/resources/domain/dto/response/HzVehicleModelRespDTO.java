package com.connor.hozon.bom.resources.domain.dto.response;

/**
 * @Author: haozt
 * @Date: 2018/6/28
 * @Description:
 */
public class HzVehicleModelRespDTO {

    /**
     * puid
     */
    private String cfg0ModelRecordId;
    /**
     * 车型名称
     */
    private String objectName;

    public String getCfg0ModelRecordId() {
        return cfg0ModelRecordId;
    }

    public void setCfg0ModelRecordId(String cfg0ModelRecordId) {
        this.cfg0ModelRecordId = cfg0ModelRecordId;
    }

    public String getObjectName() {
        return objectName;
    }

    public void setObjectName(String objectName) {
        this.objectName = objectName;
    }
}
