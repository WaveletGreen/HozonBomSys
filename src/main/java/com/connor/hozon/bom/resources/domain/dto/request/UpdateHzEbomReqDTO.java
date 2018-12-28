package com.connor.hozon.bom.resources.domain.dto.request;

import com.connor.hozon.bom.resources.domain.dto.BaseDTO;

import java.util.Map;

/**
 * @Author: haozt
 * @Date: 2018/6/25
 * @Description:
 * @modify BOM 端只维护BOM属性 零部件的基本数据 移动至EPL中进行 维护
 */
public class UpdateHzEbomReqDTO extends BaseDTO {

    private static final long serialVersionUID = 8255335626223218095L;
    /**
     * 项目id
     */
    private String projectId;

    /**
     * 更改单车用量
     */
    private Map<String,Object> updateDosage;

    private String puid;

    private String lineNo;
    private String lineId;

    private String fna;

    private String pUpc;

    private String pFnaDesc;

    private String number;

    private String colorPart;

    /**
     * 单车用量
     */
    private String vehNum;

    public String getVehNum() {
        return vehNum;
    }

    public void setVehNum(String vehNum) {
        this.vehNum = vehNum;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public Map<String, Object> getUpdateDosage() {
        return updateDosage;
    }

    public void setUpdateDosage(Map<String, Object> updateDosage) {
        this.updateDosage = updateDosage;
    }

    public String getPuid() {
        return puid;
    }

    public void setPuid(String puid) {
        this.puid = puid;
    }

    public String getLineNo() {
        return lineNo;
    }

    public void setLineNo(String lineNo) {
        this.lineNo = lineNo;
    }

    public String getLineId() {
        return lineId;
    }

    public void setLineId(String lineId) {
        this.lineId = lineId;
    }

    public String getFna() {
        return fna;
    }

    public void setFna(String fna) {
        this.fna = fna;
    }

    public String getpUpc() {
        return pUpc;
    }

    public void setpUpc(String pUpc) {
        this.pUpc = pUpc;
    }

    public String getpFnaDesc() {
        return pFnaDesc;
    }

    public void setpFnaDesc(String pFnaDesc) {
        this.pFnaDesc = pFnaDesc;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getColorPart() {
        return colorPart;
    }

    public void setColorPart(String colorPart) {
        this.colorPart = colorPart;
    }
}
