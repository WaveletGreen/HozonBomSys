package com.connor.hozon.bom.resources.domain.model;

import com.connor.hozon.bom.common.util.user.UserInfo;
import com.connor.hozon.bom.resources.domain.dto.request.AddHzEbomReqDTO;
import sql.pojo.bom.HzMbomLineRecord;

import java.util.UUID;

/**
 * @Author: haozt
 * @Date: 2018/9/7
 * @Description:
 */
public class HzMbomRecordFactory {
    public static HzMbomLineRecord AddHzEbomReqDTOMbomRecord(AddHzEbomReqDTO reqDTO){
        HzMbomLineRecord hzMbomLineRecord = new HzMbomLineRecord();
        hzMbomLineRecord.setUpdateName(UserInfo.getUser().getUserName());
        hzMbomLineRecord.setCreateName(UserInfo.getUser().getUserName());
        hzMbomLineRecord.setpBomLinePartEnName(reqDTO.getpBomLinePartEnName());
        hzMbomLineRecord.setpBomOfWhichDept(reqDTO.getpBomOfWhichDept());
        hzMbomLineRecord.setpBomLinePartResource(reqDTO.getpBomLinePartResource());
        hzMbomLineRecord.setpBomLinePartName(reqDTO.getpBomLinePartName());
        hzMbomLineRecord.setpBomLinePartClass(reqDTO.getpBomLinePartClass());
        hzMbomLineRecord.setLineId(reqDTO.getLineId());
        hzMbomLineRecord.setPuid(UUID.randomUUID().toString());
        hzMbomLineRecord.setLinePuid(UUID.randomUUID().toString());
        return hzMbomLineRecord;
    }
}
