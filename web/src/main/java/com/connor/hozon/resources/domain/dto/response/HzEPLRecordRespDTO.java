package com.connor.hozon.resources.domain.dto.response;


import com.alibaba.fastjson.JSONArray;
import cn.net.connor.hozon.common.entity.BaseDTO;

/**
 * Created by haozt on 2018/06/05
 * EPL管理全字段
 */
@Deprecated
public class HzEPLRecordRespDTO extends BaseDTO {

    private JSONArray jsonArray;

    public JSONArray getJsonArray() {
        return jsonArray;
    }

    public void setJsonArray(JSONArray jsonArray) {
        this.jsonArray = jsonArray;
    }
}
