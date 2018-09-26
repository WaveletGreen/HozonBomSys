package com.connor.hozon.bom.resources.domain.model;

import com.connor.hozon.bom.common.util.user.UserInfo;
import com.connor.hozon.bom.resources.domain.dto.request.AddHzEbomReqDTO;
import com.connor.hozon.bom.resources.domain.dto.response.HzMbomRecordRespDTO;
import com.connor.hozon.bom.resources.util.ListUtil;
import sql.pojo.bom.HzBomLineRecord;
import sql.pojo.bom.HzMbomLineRecord;
import sql.pojo.bom.HzPbomLineRecord;
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

    public static HzMbomLineRecord pBomRecordToMbomRecord(HzPbomLineRecord record){
        HzMbomLineRecord hzMbomLineRecord = new HzMbomLineRecord();
        hzMbomLineRecord.setPuid(UUID.randomUUID().toString());
        hzMbomLineRecord.setIsHas(record.getIsHas());
        hzMbomLineRecord.setBomDigifaxId(record.getBomDigifaxId());
        hzMbomLineRecord.seteBomPuid(record.geteBomPuid());
        hzMbomLineRecord.setIsDept(record.getIsDept());
        if(record.getLineId().endsWith("YY0")){
            hzMbomLineRecord.setLineId(record.getLineId().substring(0,record.getLineId().length()-3));
        }else {
            hzMbomLineRecord.setLineId(record.getLineId());
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
        hzMbomLineRecord.setSortNum(record.getSortNum());
        hzMbomLineRecord.setIsColorPart(record.getColorPart());
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
        hzMbomLineRecord.setIsColorPart(record.getColorPart());
        return hzMbomLineRecord;
    }

    /**
     * 移动部分BOM结构到当前的结构下
     * @param record 当前结构(MBOM)
     * @param records BOM结构
     * @return
     */
    public  List<HzMbomLineRecord> movePartBomStructureToThis(HzMbomLineRecord record,List<HzPbomLineRecord> records){
        int n=1;
        List<HzMbomLineRecord> recordList = new ArrayList<>();
        String lindIndex = record.getLineIndex();
        Double sortNum = Double.parseDouble(record.getSortNum());
        if(ListUtil.isNotEmpty(records)){
            int length = records.get(0).getLineIndex().split("\\.").length;
            for(int i = 1;i<records.size();i++){

                HzMbomLineRecord lineRecord = pBomRecordToMbomRecord(records.get(i));
                String childrenLineIndex = lineRecord.getLineIndex();
                String []lineIns = childrenLineIndex.split("\\.");
                int len = lineIns.length;
                if(len -length == 1){
                    lineRecord.setParentUid(record.geteBomPuid());
                }
                lineRecord.setSortNum(String.valueOf(sortNum+n*100));
                this.maxSortNum = sortNum+n*100;
                n++;
                StringBuffer stringBuffer = new StringBuffer();
                for(int j = length;j<len;j++){
                    stringBuffer.append("."+lineIns[j]);
                }
                lineRecord.setLineIndex(lindIndex+stringBuffer.toString());
                recordList.add(lineRecord);
            }
        }else {
            throw new RuntimeException("数据操作异常!");
        }

        return recordList;
    }


    public static HzMbomLineRecord generateSupMbom(HzPbomLineRecord record, int i, String projectId, List<HzConfigBomColorBean> beans){
        HzMbomLineRecord mbomLineRecord = pBomRecordToMbomRecord(record);
        String lineId = mbomLineRecord.getLineId();
        if(Integer.valueOf(1).equals(record.getColorPart())&&ListUtil.isNotEmpty(beans)){
            if(!beans.get(i).getColorCode().equals("-")){
                lineId = HzBomSysFactory.resultLineId(mbomLineRecord.getLineId(),projectId)+beans.get(i).getColorCode();
                mbomLineRecord.setColorId(beans.get(i).getColorUid());
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


//    public static void main(String[] a){
//        String s1 = "2.3.34.5.65.432.5.32";//原父
//        String s2 = "2.3.34.5.65.432.5.32.23.1.23.23";//原父的子孙
//        String s3 = "10.10.5";//新父
//        String s4 = "10.10.5.23.1.23.23";//期望的结果
//
//        int i1 = s1.split("\\.").length;
//        int i2 = s2.split("\\.").length;
//        String ss[] = s2.split("\\.");
//        StringBuffer stringBuffer = new StringBuffer();
//        for(int i =i1;i<i2;i++){
//            stringBuffer.append("."+ss[i]);
//        }
//        System.out.println(s3+stringBuffer.toString());
//    }
}
