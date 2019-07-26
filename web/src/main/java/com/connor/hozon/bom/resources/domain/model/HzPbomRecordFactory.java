package com.connor.hozon.bom.resources.domain.model;

import com.connor.hozon.bom.bomSystem.dao.bom.HzBomMainRecordDao;
import com.connor.hozon.bom.common.util.user.UserInfo;
import com.connor.hozon.bom.resources.domain.constant.BOMTransConstants;
import com.connor.hozon.bom.resources.domain.dto.request.AddHzEbomReqDTO;
import com.connor.hozon.bom.resources.domain.dto.request.UpdateHzEbomReqDTO;
import com.connor.hozon.bom.resources.domain.dto.response.HzPbomLineRespDTO;
import com.connor.hozon.bom.resources.enumtype.ChangeTableNameEnum;
import com.connor.hozon.bom.sys.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import sql.pojo.bom.HzBomLineRecord;
import sql.pojo.bom.HzImportEbomRecord;
import sql.pojo.bom.HzPbomLineRecord;
import sql.pojo.epl.HzEPLManageRecord;
import sql.pojo.epl.HzEPLRecord;

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
     * @param record EPL 信息
     * @param bomRecord  EBOM 信息  业务变更较大 代码兼容，类名未改
     *                    {@link HzEPLManageRecord} 现阶段的EBOM字段 多余的字段废弃
     * @return HzPbomLineRecord
     */
    public static HzPbomLineRecord editEbomToPbomRecord(HzEPLRecord record, HzEPLManageRecord bomRecord){
        HzPbomLineRecord hzPbomLineRecord = new HzPbomLineRecord();
        User user = UserInfo.getUser();
        hzPbomLineRecord.setUpdateName(user.getUserName());
        hzPbomLineRecord.setCreateName(user.getUserName());
        hzPbomLineRecord.setpBomLinePartEnName(record.getPartEnName());
        hzPbomLineRecord.setpBomOfWhichDept(record.getPartOfWhichDept());
        hzPbomLineRecord.setpBomLinePartResource(record.getPartResource());
        hzPbomLineRecord.setpBomLinePartName(record.getPartName());
        hzPbomLineRecord.setpBomLinePartClass(record.getPartClass());
        hzPbomLineRecord.setLineId(record.getPartId());
        hzPbomLineRecord.setPuid(UUID.randomUUID().toString());
        hzPbomLineRecord.setIsNewPart(0);
        hzPbomLineRecord.seteBomPuid(bomRecord.getPuid());
        hzPbomLineRecord.setIs2Y(bomRecord.getIs2Y());
        hzPbomLineRecord.setIsHas(bomRecord.getIsHas());
        hzPbomLineRecord.setBomDigifaxId(bomRecord.getBomDigifaxId());
        hzPbomLineRecord.setTableName(ChangeTableNameEnum.HZ_PBOM.getTableName());
        hzPbomLineRecord.setColorPart(bomRecord.getColorPart());
        hzPbomLineRecord.setLineIndex(bomRecord.getLineIndex());
        hzPbomLineRecord.setSortNum(bomRecord.getSortNum());
        hzPbomLineRecord.setStatus(2);
        return hzPbomLineRecord;
    }



    /**
     * ebom新增数据 同步至pbom
     * @param record EPL 信息
     * @param reqDTO  前端传入EBOM 信息
     * @return HzPbomLineRecord
     */
    public static HzPbomLineRecord editEbomToPbomRecord(HzEPLRecord record, AddHzEbomReqDTO reqDTO){
        HzPbomLineRecord hzPbomLineRecord = new HzPbomLineRecord();
        User user = UserInfo.getUser();
        hzPbomLineRecord.setUpdateName(user.getUserName());
        hzPbomLineRecord.setCreateName(user.getUserName());
        hzPbomLineRecord.setpBomLinePartEnName(record.getPartEnName());
        hzPbomLineRecord.setpBomOfWhichDept(record.getPartOfWhichDept());
        hzPbomLineRecord.setpBomLinePartResource(record.getPartResource());
        hzPbomLineRecord.setpBomLinePartName(record.getPartName());
        hzPbomLineRecord.setpBomLinePartClass(record.getPartClass());
        hzPbomLineRecord.setLineId(record.getPartId());
        hzPbomLineRecord.setPuid(UUID.randomUUID().toString());
        hzPbomLineRecord.setIsNewPart(0);
        hzPbomLineRecord.setTableName(ChangeTableNameEnum.HZ_PBOM.getTableName());
        hzPbomLineRecord.setColorPart(BOMTransConstants.constantStringToInteger(reqDTO.getColorPart()));
        hzPbomLineRecord.setStatus(2);
        return hzPbomLineRecord;
    }


    /**
     * ebom更新数据 同步至pbom
     * @param reqDTO  前端传入EBOM 信息
     * @return HzPbomLineRecord
     */
    public static HzPbomLineRecord editEbomToPbomRecord(UpdateHzEbomReqDTO reqDTO){
        HzPbomLineRecord hzPbomLineRecord = new HzPbomLineRecord();
        User user = UserInfo.getUser();
        hzPbomLineRecord.setCreateName(user.getUserName());
        hzPbomLineRecord.setPuid(UUID.randomUUID().toString());
        hzPbomLineRecord.setIsNewPart(0);
        hzPbomLineRecord.seteBomPuid(reqDTO.getPuid());
        hzPbomLineRecord.setTableName(ChangeTableNameEnum.HZ_PBOM.getTableName());
        hzPbomLineRecord.setColorPart(BOMTransConstants.constantStringToInteger(reqDTO.getColorPart()));
        hzPbomLineRecord.setStatus(2);
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
//        hzPbomLineRecord.setIsPart(record.getIsPart());
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
        hzPbomLineRecord.setStatus(2);
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
        respDTO.setType(BOMTransConstants.integerToYNString(type));
        respDTO.setBuyUnit(BOMTransConstants.integerToYNString(buyUnit));
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
        r.setEffectTime(record.getEffectTime());
        r.setRevision(record.getRevision());
        r.setOrderId(record.getOrderId());
        return  r;
    }
}