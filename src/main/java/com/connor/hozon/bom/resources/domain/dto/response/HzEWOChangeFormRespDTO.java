package com.connor.hozon.bom.resources.domain.dto.response;

import com.connor.hozon.bom.resources.domain.dto.BaseDTO;
import sql.pojo.bom.HzBomLineRecord;

/**
 * @Author: haozt
 * @Date: 2018/8/14
 * @Description:
 */
public class HzEWOChangeFormRespDTO extends BaseDTO {
    /**
     * 主键
     */
    private String puid;

    /**
     * 零件号
     */
    private String lineId;

    /**
     * 变更前
     */
    private HzBomLineRecord before;

    /**
     * 变更后
     */
    private HzBomLineRecord after;

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

    public HzBomLineRecord getBefore() {
        return before;
    }

    public void setBefore(HzBomLineRecord before) {
        this.before = before;
    }

    public HzBomLineRecord getAfter() {
        return after;
    }

    public void setAfter(HzBomLineRecord after) {
        this.after = after;
    }
}
