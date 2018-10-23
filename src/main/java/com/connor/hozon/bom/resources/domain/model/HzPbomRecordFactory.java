package com.connor.hozon.bom.resources.domain.model;

import com.connor.hozon.bom.bomSystem.dao.bom.HzBomMainRecordDao;
import com.connor.hozon.bom.common.util.user.UserInfo;
import com.connor.hozon.bom.resources.domain.dto.request.AddHzEbomReqDTO;
import com.connor.hozon.bom.resources.domain.dto.request.UpdateHzEbomReqDTO;
import org.springframework.beans.factory.annotation.Autowired;
import sql.pojo.bom.HzBomLineRecord;
import sql.pojo.bom.HzImportEbomRecord;
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
    public static HzPbomLineRecord addHzEbomReqDTOPbomRecord(AddHzEbomReqDTO reqDTO){
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
        hzPbomLineRecord.setIsNewPart(0);
        return hzPbomLineRecord;
    }

    public static HzPbomLineRecord updateHzEbomReqDTOPbomRecord(UpdateHzEbomReqDTO reqDTO){
        HzPbomLineRecord hzPbomLineRecord = new HzPbomLineRecord();
        hzPbomLineRecord.setUpdateName(UserInfo.getUser().getUserName());
        hzPbomLineRecord.setpBomLinePartEnName(reqDTO.getpBomLinePartEnName());
        hzPbomLineRecord.setpBomOfWhichDept(reqDTO.getpBomOfWhichDept());
        hzPbomLineRecord.setpBomLinePartResource(reqDTO.getpBomLinePartResource());
        hzPbomLineRecord.setpBomLinePartName(reqDTO.getpBomLinePartName());
        hzPbomLineRecord.setpBomLinePartClass(reqDTO.getpBomLinePartClass());
        hzPbomLineRecord.setLinePuid(UUID.randomUUID().toString());
        hzPbomLineRecord.setLineId(reqDTO.getLineId());
        if(reqDTO.getColorPart().equals("Y")){
            hzPbomLineRecord.setColorPart(1);
        }else if(reqDTO.getColorPart().equals("N")){
            hzPbomLineRecord.setColorPart(0);
        }else {
            hzPbomLineRecord.setColorPart(null);
        }
        return hzPbomLineRecord;
    }


    public static HzPbomLineRecord ImportEbomRecordToPbomRecord(HzImportEbomRecord record){
        HzPbomLineRecord hzPbomLineRecord = new HzPbomLineRecord();
        hzPbomLineRecord.setUpdateName(UserInfo.getUser().getUserName());
        hzPbomLineRecord.setCreateName(UserInfo.getUser().getUserName());
        hzPbomLineRecord.setPuid(UUID.randomUUID().toString());
        hzPbomLineRecord.setIsHas(record.getIsHas());
        hzPbomLineRecord.setBomDigifaxId(record.getBomDigifaxId());
        hzPbomLineRecord.seteBomPuid(record.getPuid());
        hzPbomLineRecord.setLineId(record.getLineId());
        hzPbomLineRecord.setIsPart(record.getIsPart());
        hzPbomLineRecord.setIs2Y(record.getIs2Y());
        hzPbomLineRecord.setLineIndex(record.getLineIndex());
        hzPbomLineRecord.setParentUid(record.getParentId());
        hzPbomLineRecord.setpBomLinePartClass(record.getpBomLinePartClass());
        hzPbomLineRecord.setpBomLinePartName(record.getpBomLinePartName());
        hzPbomLineRecord.setpBomOfWhichDept(record.getpBomOfWhichDept());
        hzPbomLineRecord.setpBomLinePartEnName(record.getpBomLinePartEnName());
        hzPbomLineRecord.setpBomLinePartResource(record.getpBomLinePartResource());
        hzPbomLineRecord.setSortNum(record.getSortNum());
        hzPbomLineRecord.setColorPart(record.getColorPart());
        hzPbomLineRecord.setIsNewPart(0);
        return hzPbomLineRecord;
    }


    public static HzPbomLineRecord bomLineRecordToPbomRecord(HzBomLineRecord record){
        HzPbomLineRecord hzPbomLineRecord = new HzPbomLineRecord();
        hzPbomLineRecord.setUpdateName(UserInfo.getUser().getUserName());
        hzPbomLineRecord.setLineId(record.getLineID());
        hzPbomLineRecord.setBomDigifaxId(record.getBomDigifaxId());
        hzPbomLineRecord.setpBomLinePartClass(record.getpBomLinePartClass());
        hzPbomLineRecord.setpBomLinePartName(record.getpBomLinePartName());
        hzPbomLineRecord.setpBomOfWhichDept(record.getpBomOfWhichDept());
        hzPbomLineRecord.setpBomLinePartEnName(record.getpBomLinePartEnName());
        hzPbomLineRecord.setpBomLinePartResource(record.getpBomLinePartResource());
        hzPbomLineRecord.setColorPart(record.getColorPart());
        hzPbomLineRecord.setSingleVehDosage(record.getSingleVehDosage());
        hzPbomLineRecord.setIsNewPart(0);
        return hzPbomLineRecord;
    }
}
