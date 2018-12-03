package com.connor.hozon.bom.resources.domain.model;

import com.connor.hozon.bom.bomSystem.dao.bom.HzBomMainRecordDao;
import com.connor.hozon.bom.common.util.user.UserInfo;
import com.connor.hozon.bom.resources.domain.dto.request.AddHzEbomReqDTO;
import com.connor.hozon.bom.resources.domain.dto.request.UpdateHzEbomReqDTO;
import com.connor.hozon.bom.resources.domain.dto.response.HzPbomLineRespDTO;
import org.springframework.beans.factory.annotation.Autowired;
import sql.pojo.bom.HzBomLineRecord;
import sql.pojo.bom.HzImportEbomRecord;
import sql.pojo.bom.HzPbomLineRecord;

import javax.xml.ws.soap.Addressing;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.connor.hozon.bom.resources.domain.model.HzBomSysFactory.getLevelAndRank;

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
        if("Y".equals(reqDTO.getColorPart())){
            hzPbomLineRecord.setColorPart(1);
        }else if("N".equals(reqDTO.getColorPart())){
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

    /**
     * 临时用
     * @param record
     * @return
     */
    @Deprecated
    public static HzPbomLineRecord bomLineAllToPbomRecord(HzBomLineRecord record){
        HzPbomLineRecord hzPbomLineRecord = new HzPbomLineRecord();
        hzPbomLineRecord.setUpdateName(UserInfo.getUser().getUserName());
        hzPbomLineRecord.setCreateName(UserInfo.getUser().getUserName());
        hzPbomLineRecord.setPuid(UUID.randomUUID().toString());
        hzPbomLineRecord.setIsHas(record.getIsHas());
        hzPbomLineRecord.setBomDigifaxId(record.getBomDigifaxId());
        hzPbomLineRecord.seteBomPuid(record.getPuid());
        hzPbomLineRecord.setLineId(record.getLineID());
        hzPbomLineRecord.setIsPart(record.getIsPart());
        hzPbomLineRecord.setIs2Y(record.getIs2Y());
        hzPbomLineRecord.setLineIndex(record.getLineIndex());
        hzPbomLineRecord.setParentUid(record.getParentUid());
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

    public static HzPbomLineRespDTO bomLineRecordToRespDTO(HzPbomLineRecord record){
        HzPbomLineRespDTO respDTO = new HzPbomLineRespDTO();
        Integer is2Y = record.getIs2Y();
        Integer hasChildren = record.getIsHas();
        String lineIndex = record.getLineIndex();
        String[] strings = getLevelAndRank(lineIndex, is2Y, hasChildren);
        respDTO.setLevel(strings[0]);
        respDTO.setRank(strings[1]);
        respDTO.setLineId(record.getLineId());
        respDTO.setpBomOfWhichDept(record.getpBomOfWhichDept());

        respDTO.setpBomLinePartClass(record.getpBomLinePartClass());
        respDTO.setpBomLinePartResource(record.getpBomLinePartResource());
        respDTO.setResource(record.getResource());
        Integer type = record.getType();
        Integer buyUnit = record.getBuyUnit();
        if (Integer.valueOf(0).equals(type)) {
            respDTO.setType("N");
        } else if (Integer.valueOf(1).equals(type)) {
            respDTO.setType("Y");
        } else {
            respDTO.setType("");
        }
        if (Integer.valueOf(0).equals(buyUnit)) {
            respDTO.setBuyUnit("N");
        } else if (Integer.valueOf(1).equals(buyUnit)) {
            respDTO.setBuyUnit("Y");
        } else {
            respDTO.setBuyUnit("");
        }
        respDTO.seteBomPuid(record.geteBomPuid());
        respDTO.setPuid(record.getPuid());
        respDTO.setProductLine(record.getProductLine());
        respDTO.setWorkShop1(record.getWorkShop1());
        respDTO.setWorkShop2(record.getWorkShop2());
        respDTO.setMouldType(record.getMouldType());
        respDTO.setOuterPart(record.getOuterPart());
        respDTO.setStation(record.getStation());
        respDTO.setpBomLinePartName(record.getpBomLinePartName());
        respDTO.setpBomLinePartEnName(record.getpBomLinePartEnName());
        respDTO.setStatus(record.getStatus());
        return  respDTO;
    }


    public static HzPbomLineRecord bomLineRecordToBomRecord(HzPbomLineRecord record){
        HzPbomLineRecord r = new HzPbomLineRecord();
        r.setParentUid(record.getParentUid());
        r.setIs2Y(record.getIs2Y());
        r.setIsHas(record.getIsHas());
        r.setLineIndex(record.getLineIndex());
        r.setLineId(record.getLineId());
        r.setpBomOfWhichDept(record.getpBomOfWhichDept());
        r.setBomDigifaxId(record.getBomDigifaxId());
        r.setpBomLinePartClass(record.getpBomLinePartClass());
        r.setpBomLinePartResource(record.getpBomLinePartResource());
        r.setResource(record.getResource());
        r.setType(record.getType());
        r.setBuyUnit(record.getBuyUnit());
        r.seteBomPuid(record.geteBomPuid());
        r.setPuid(record.getPuid());
        r.setProductLine(record.getProductLine());
        r.setWorkShop1(record.getWorkShop1());
        r.setWorkShop2(record.getWorkShop2());
        r.setMouldType(record.getMouldType());
        r.setOuterPart(record.getOuterPart());
        r.setStation(record.getStation());
        r.setSortNum(record.getSortNum());
        r.setpBomLinePartName(record.getpBomLinePartName());
        r.setpBomLinePartEnName(record.getpBomLinePartEnName());
        r.setStatus(record.getStatus());
        r.setUpdateName(record.getUpdateName());
        r.setCreateName(record.getCreateName());
        r.setIsNewPart(record.getIsNewPart());
        return  r;
    }
}
