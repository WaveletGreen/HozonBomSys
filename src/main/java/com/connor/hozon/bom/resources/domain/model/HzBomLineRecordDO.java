package com.connor.hozon.bom.resources.domain.model;

import com.connor.hozon.bom.resources.domain.dto.request.AddHzEbomReqDTO;
import sql.pojo.bom.HzBomLineRecord;

/**
 * @Author: haozt
 * @Date: 2018/9/6
 * @Description:
 */
public class HzBomLineRecordDO {
    private HzBomLineRecord hzBomLineRecord;

    public HzBomLineRecord getHzBomLineRecord() {
        return hzBomLineRecord;
    }

    public void setHzBomLineRecord(HzBomLineRecord hzBomLineRecord) {
        this.hzBomLineRecord = hzBomLineRecord;
    }

    public HzBomLineRecordDO (AddHzEbomReqDTO reqDTO){
        this.hzBomLineRecord = HzBomLineRecordFactory.addHzEbomDTOHzBomLineDO(reqDTO);
    }
}
