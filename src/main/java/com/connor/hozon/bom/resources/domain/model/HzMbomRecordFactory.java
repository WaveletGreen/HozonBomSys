package com.connor.hozon.bom.resources.domain.model;

import com.connor.hozon.bom.common.util.user.UserInfo;
import com.connor.hozon.bom.resources.domain.dto.request.AddHzEbomReqDTO;
import com.connor.hozon.bom.resources.domain.dto.response.HzMbomRecordRespDTO;
import sql.pojo.bom.HzBomLineRecord;
import sql.pojo.bom.HzMbomLineRecord;
import sql.pojo.epl.HzEPLManageRecord;
import sql.pojo.interaction.HzConfigBomColorBean;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.connor.hozon.bom.resources.domain.model.HzBomSysFactory.getLevelAndRank;

/**
 * @Author: haozt
 * @Date: 2018/9/7
 * @Description:
 */
public class HzMbomRecordFactory {
    private double maxSortNum = 0.0;

    public double getMaxSortNum() {
        return maxSortNum;
    }

    public void setMaxSortNum(double maxSortNum) {
        this.maxSortNum = maxSortNum;
    }

    public static HzMbomLineRecord addHzEbomReqDTOMbomRecord(AddHzEbomReqDTO reqDTO){
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

    public static HzMbomLineRecord bomLineRecordToMbomRecord(HzBomLineRecord record){
        HzMbomLineRecord hzMbomLineRecord = new HzMbomLineRecord();
        hzMbomLineRecord.setPuid(UUID.randomUUID().toString());
        hzMbomLineRecord.setIsHas(record.getIsHas());
        hzMbomLineRecord.setBomDigifaxId(record.getBomDigifaxId());
        hzMbomLineRecord.seteBomPuid(record.getPuid());
        hzMbomLineRecord.setIsDept(record.getIsDept());
        hzMbomLineRecord.setLineId(record.getLineID());
        hzMbomLineRecord.setIsPart(record.getIsPart());
        hzMbomLineRecord.setIs2Y(record.getIs2Y());
        hzMbomLineRecord.setLineIndex(record.getLineIndex());
        hzMbomLineRecord.setParentUid(record.getParentUid());
        hzMbomLineRecord.setpBomLinePartClass(record.getpBomLinePartClass());
        hzMbomLineRecord.setpBomLinePartName(record.getpBomLinePartName());
        hzMbomLineRecord.setpBomOfWhichDept(record.getpBomOfWhichDept());
        hzMbomLineRecord.setpBomLinePartEnName(record.getpBomLinePartEnName());
        hzMbomLineRecord.setpBomLinePartResource(record.getpBomLinePartResource());
        hzMbomLineRecord.setSortNum(record.getSortNum());
        return hzMbomLineRecord;
    }

    public static HzMbomRecordRespDTO mbomRecordToRespDTO(HzMbomLineRecord record){
        HzMbomRecordRespDTO respDTO  = new HzMbomRecordRespDTO();
        respDTO.setPuid(record.getPuid());
        respDTO.seteBomPuid(record.geteBomPuid());
        Integer is2Y = record.getIs2Y();
        Integer hasChildren = record.getIsHas();
        String lineIndex = record.getLineIndex();
        String[] strings = getLevelAndRank(lineIndex, is2Y, hasChildren);
        respDTO.setLevel(strings[0]);//层级
        respDTO.setLineNo(strings[2]);
        respDTO.setLineId(record.getLineId());
        respDTO.setpBomOfWhichDept(record.getpBomOfWhichDept());
        respDTO.setpBomLinePartClass(record.getpBomLinePartClass());
        respDTO.setpBomLinePartEnName(record.getpBomLinePartEnName());
        respDTO.setpBomLinePartName(record.getpBomLinePartName());
        respDTO.setpBomLinePartResource(record.getpBomLinePartResource());
        respDTO.setSparePart(record.getSparePart());
        respDTO.setSparePartNum(record.getSparePartNum());
        respDTO.setLaborHour(record.getLaborHour());
        respDTO.setRhythm(record.getRhythm());
        respDTO.setSolderJoint(record.getSolderJoint());
        respDTO.setMachineMaterial(record.getMachineMaterial());
        respDTO.setStandardPart(record.getStandardPart());
        respDTO.setTools(record.getTools());
        respDTO.setWasterProduct(record.getWasterProduct());
        respDTO.setChange(record.getChange());
        respDTO.setChangeNum(record.getChangeNum());
        if (Integer.valueOf(1).equals(record.getpLouaFlag())) {
            respDTO.setpLouaFlag("LOU");
        }
        if (Integer.valueOf(1).equals(record.getpBomType())) {
            respDTO.setpBomType("生产");
        } else if (Integer.valueOf(6).equals(record.getpBomType())) {
            respDTO.setpBomType("财务");
        } else {
            respDTO.setpBomType("");
        }
        respDTO.setStatus(record.getStatus());
        respDTO.setpStockLocation(record.getpStockLocation());
        return respDTO;
    }


    public static HzMbomLineRecord ebomRecordToMbomRecord(HzEPLManageRecord record){
        HzMbomLineRecord hzMbomLineRecord = new HzMbomLineRecord();
        hzMbomLineRecord.setPuid(UUID.randomUUID().toString());
        hzMbomLineRecord.setIsHas(record.getIsHas());
        hzMbomLineRecord.setBomDigifaxId(record.getBomDigifaxId());
        hzMbomLineRecord.seteBomPuid(record.getPuid());
        hzMbomLineRecord.setIsDept(record.getIsDept());
        if(record.getLineID().endsWith("YY0")){
            hzMbomLineRecord.setLineId(record.getLineID().substring(0,record.getLineID().length()-3));
        }else {
            hzMbomLineRecord.setLineId(record.getLineID());
        }
        hzMbomLineRecord.setIsPart(record.getIsPart());
        hzMbomLineRecord.setIs2Y(record.getIs2Y());
        hzMbomLineRecord.setLineIndex(record.getLineIndex());
        hzMbomLineRecord.setParentUid(record.getParentUid());
        hzMbomLineRecord.setpBomLinePartClass(record.getpBomLinePartClass());
        hzMbomLineRecord.setpBomLinePartName(record.getpBomLinePartName());
        hzMbomLineRecord.setpBomOfWhichDept(record.getpBomOfWhichDept());
        hzMbomLineRecord.setpBomLinePartEnName(record.getpBomLinePartEnName());
        hzMbomLineRecord.setpBomLinePartResource(record.getpBomLinePartResource());
        return hzMbomLineRecord;
    }

    /**
     * 移动部分BOM结构到当前的结构下
     * @param record 当前结构(MBOM)
     * @param records BOM结构
     * @return
     */
    public  List<HzMbomLineRecord> movePartBomStructureToThis(HzMbomLineRecord record,List<HzEPLManageRecord> records){
        int n=1;
        List<HzMbomLineRecord> recordList = new ArrayList<>();
        String lindIndex = record.getLineIndex();
        Double sortNum = Double.parseDouble(record.getSortNum());
        record.setIsHas(1);
        for(int i = 1;i<records.size();i++){
            HzMbomLineRecord lineRecord = ebomRecordToMbomRecord(records.get(i));
            String originalLineIndex = lineRecord.getLineIndex();
            String []lineIns = originalLineIndex.split("\\.");
            if(i ==1){
                lineRecord.setParentUid(record.geteBomPuid());
            }
            lineRecord.setSortNum(String.valueOf(sortNum+n*100));
            this.maxSortNum = sortNum+n*100;
            n++;
            StringBuffer stringBuffer = new StringBuffer();
            for(int j = lineIns.length-1;j>lineIns.length-1-i;j--){
                stringBuffer.append("."+lineIns[j]);
            }

            lineRecord.setLineIndex(lindIndex+stringBuffer.toString());
            recordList.add(lineRecord);
        }

        return recordList;
    }


    public static HzMbomLineRecord generateSupMbom(HzEPLManageRecord record, int i, String projectId, List<HzConfigBomColorBean> beans){
        HzMbomLineRecord mbomLineRecord = ebomRecordToMbomRecord(record);
        String lineId = HzBomSysFactory.resultLineId(mbomLineRecord.getLineId(),projectId);

        if(Integer.valueOf(1).equals(record.getColorPart())){
            if(i!=0){
                lineId = lineId+beans.get(i).getColorCode();
            }
        }

        mbomLineRecord.setLineId(lineId);

        String lineIndex = record.getLineIndex();
        String sortNum = record.getSortNum();
        String firstIndex = lineIndex.split("\\.")[0];
        String othersIndex = lineIndex.substring(firstIndex.length(),lineIndex.length());
        Integer first = Integer.valueOf(firstIndex+Integer.valueOf(firstIndex)*i);
        mbomLineRecord.setLineIndex(String.valueOf(first)+othersIndex);
        mbomLineRecord.setSortNum(String.valueOf(Double.parseDouble(sortNum)+100*i));
        return mbomLineRecord;
    }

}
