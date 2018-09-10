package com.connor.hozon.bom.resources.domain.dto.response;


import com.alibaba.fastjson.JSONArray;

/**
 * Created by haozt on 2018/06/05
 * EPL管理全字段
 */
public class HzEPLRecordRespDTO {

    private JSONArray jsonArray;

    public JSONArray getJsonArray() {
        return jsonArray;
    }

    public void setJsonArray(JSONArray jsonArray) {
        this.jsonArray = jsonArray;
    }
}
