package com.connor.hozon.bom.resources.domain.model;

import com.connor.hozon.bom.bomSystem.dao.bom.HzBomMainRecordDao;
import com.connor.hozon.bom.common.util.user.UserInfo;
import com.connor.hozon.bom.resources.domain.dto.request.AddHzEbomReqDTO;
import org.springframework.beans.factory.annotation.Autowired;
import sql.pojo.bom.HzPbomLineRecord;

import javax.xml.ws.soap.Addressing;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @Author: haozt
 * @Date: 2018/9/7
 * @Description:
 */
public class HzPbomRecordFactory {
    /**
     * ebom新增数据 同步至pbom
     * @param reqDTO
     * @return
     */
    public static HzPbomLineRecord AddHzEbomReqDTOPbomRecord(AddHzEbomReqDTO reqDTO){
        HzPbomLineRecord hzPbomLineRecord = new HzPbomLineRecord();
        hzPbomLineRecord.setLinePuid(UUID.randomUUID().toString());
        hzPbomLineRecord.setUpdateName(UserInfo.getUser().getUserName());
        hzPbomLineRecord.setCreateName(UserInfo.getUser().getUserName());
        hzPbomLineRecord.setpBomLinePartEnName(reqDTO.getpBomLinePartEnName());
        hzPbomLineRecord.setpBomOfWhichDept(reqDTO.getpBomOfWhichDept());
        hzPbomLineRecord.setpBomLinePartResource(reqDTO.getpBomLinePartResource());
        hzPbomLineRecord.setpBomLinePartName(reqDTO.getpBomLinePartName());
        hzPbomLineRecord.setpBomLinePartClass(reqDTO.getpBomLinePartClass());
        hzPbomLineRecord.setLineId(reqDTO.getLineId());
        hzPbomLineRecord.setPuid(UUID.randomUUID().toString());
        return hzPbomLineRecord;
    }
}
