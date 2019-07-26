package com.connor.hozon.bom.resources.domain.dto.response;

import com.alibaba.fastjson.JSONArray;
import com.connor.hozon.bom.resources.domain.dto.BaseDTO;

public class HzEbomLevelRespDTO extends BaseDTO {
    private JSONArray jsonArray;

    public JSONArray getJsonArray() {
        return jsonArray;
    }

    public void setJsonArray(JSONArray jsonArray) {
        this.jsonArray = jsonArray;
    }

    /**
     *查找编号
     */
    private String findNum;
    /**
     * 项目id
     */
    private String projectId;

    /**
     * 调整层级的当前零件puid
     */
    private String puid;
    /**
     * 父层零件号(必填)
     */
    private String lineId;

    private Integer isHas;//判断是否有子层

    public Integer getIsHas() {
        return isHas;
    }

    public void setIsHas(Integer isHas) {
        this.isHas = isHas;
    }

    public String getFindNum() {
        return findNum;
    }

    public void setFindNum(String findNum) {
        this.findNum = findNum;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getPuid() {
        return puid;
    }

    public void setPuid(String puid) {
        this.puid = puid;
    }

    public String getLineId() {
        return lineId;
    }

    public void setLineId(String lineId) {
        this.lineId = lineId;
    }
}
