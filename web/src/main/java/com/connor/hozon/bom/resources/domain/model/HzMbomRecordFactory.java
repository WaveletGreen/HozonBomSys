package com.connor.hozon.bom.resources.domain.model;

import com.connor.hozon.bom.resources.domain.dto.response.HzMbomRecordRespDTO;
import com.connor.hozon.bom.resources.util.DateUtil;
import com.connor.hozon.bom.resources.util.ListUtil;
import com.connor.hozon.bom.resources.util.StringUtil;
import com.connor.hozon.bom.sys.exception.HzBomException;
import sql.pojo.accessories.HzAccessoriesLibs;
import sql.pojo.bom.HzMbomLineRecord;
import sql.pojo.bom.HzPbomLineRecord;
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

    private double sortNum = 0.0;

    public double getSortNum() {
        return sortNum;
    }

    public void setSortNum(double sortNum) {
        this.sortNum = sortNum;
    }

    public void setMaxSortNum(double maxSortNum) {
        this.maxSortNum = maxSortNum;
    }


    public static HzMbomLineRecord pBomRecordToMbomRecord(HzPbomLineRecord record) {
        HzMbomLineRecord hzMbomLineRecord = new HzMbomLineRecord();
        hzMbomLineRecord.setPuid(UUID.randomUUID().toString());
        hzMbomLineRecord.setIsHas(record.getIsHas());
        hzMbomLineRecord.setBomDigifaxId(record.getBomDigifaxId());
        hzMbomLineRecord.seteBomPuid(record.geteBomPuid());
        hzMbomLineRecord.setIsDept(record.getIsDept());
        if (record.getLineId().endsWith("YY0")) {
            hzMbomLineRecord.setLineId(record.getLineId().substring(0, record.getLineId().length() - 3));
        } else {
            hzMbomLineRecord.setLineId(record.getLineId());
        }
        hzMbomLineRecord.setIsPart(record.getIsPart());
        hzMbomLineRecord.setIs2Y(record.getIs2Y());
        hzMbomLineRecord.setStatus(2);
        hzMbomLineRecord.setLineIndex(record.getLineIndex());
        hzMbomLineRecord.setParentUid(record.getParentUid());
        hzMbomLineRecord.setpBomLinePartClass(record.getpBomLinePartClass());
        hzMbomLineRecord.setpBomLinePartName(record.getpBomLinePartName());
        hzMbomLineRecord.setpBomOfWhichDept(record.getpBomOfWhichDept());
        hzMbomLineRecord.setpBomLinePartEnName(record.getpBomLinePartEnName());
        hzMbomLineRecord.setpBomLinePartResource(record.getpBomLinePartResource());
        hzMbomLineRecord.setSortNum(record.getSortNum());
        hzMbomLineRecord.setIsColorPart(record.getColorPart());
        hzMbomLineRecord.setpLouaFlag(record.getpLouaFlag());
        hzMbomLineRecord.setBuyType(record.getResource());
        return hzMbomLineRecord;
    }

    public static HzMbomRecordRespDTO mbomRecordToRespDTO(HzMbomLineRecord record) {
        HzMbomRecordRespDTO respDTO = new HzMbomRecordRespDTO();
        respDTO.setPuid(record.getPuid());
        respDTO.seteBomPuid(record.geteBomPuid());
        Integer is2Y = record.getIs2Y();
        Integer hasChildren = record.getIsHas();
        String lineIndex = record.getLineIndex();
        String[] strings = getLevelAndRank(lineIndex, is2Y, hasChildren);
        respDTO.setRank(strings[1]);
        respDTO.setLevel(strings[0]);//层级
//        respDTO.setLineNo(strings[2]);
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
        respDTO.setProcessRoute(record.getProcessRoute());
        respDTO.setChangeNum(record.getChangeNum());
        if (Integer.valueOf(1).equals(record.getpLouaFlag())) {
            respDTO.setpLouaFlag("LOU");
        } else if (Integer.valueOf(0).equals(record.getpLouaFlag())) {
            respDTO.setpLouaFlag("LOA");
        }
        if (Integer.valueOf(1).equals(record.getpBomType())) {
            respDTO.setpBomType("生产");
        } else if (Integer.valueOf(6).equals(record.getpBomType())) {
            respDTO.setpBomType("财务");
        } else {
            respDTO.setpBomType("");
        }
        if (StringUtil.isEmpty(record.getFactoryCode())) {
            respDTO.setpFactoryCode("1001");
        } else {
            respDTO.setpFactoryCode(record.getFactoryCode());
        }
        respDTO.setEffectTime(DateUtil.formatDefaultDate(record.getEffectTime()));
        respDTO.setStatus(record.getStatus());
        respDTO.setpStockLocation(record.getpStockLocation());
        respDTO.setColorId(record.getColorId());
        return respDTO;
    }


    /**
     * 移动部分BOM结构到当前的结构下
     *
     * @param record  当前结构(MBOM)
     * @param records BOM结构(PBOM)
     * @return
     */
    public List<HzMbomLineRecord> movePartBomStructureToThis(HzMbomLineRecord record, List<HzPbomLineRecord> records, int n) {
        List<HzMbomLineRecord> recordList = new ArrayList<>();
        String lindIndex = record.getLineIndex();
        if (ListUtil.isNotEmpty(records)) {
            int length = records.get(0).getLineIndex().split("\\.").length;
            for (int i = 1; i < records.size(); i++) {

                HzMbomLineRecord lineRecord = pBomRecordToMbomRecord(records.get(i));
                String childrenLineIndex = lineRecord.getLineIndex();
                String[] lineIns = childrenLineIndex.split("\\.");
                int len = lineIns.length;
                if (len - length == 1) {
                    lineRecord.setParentUid(record.geteBomPuid());
                }

//                lineRecord.setSortNum(String.valueOf(sortNum+n*100));
//                this.maxSortNum = sortNum+n*100;

                lineRecord.setSortNum(String.valueOf(100 + n * 100));
                this.maxSortNum = 100 + n * 100;
                n++;
                StringBuffer stringBuffer = new StringBuffer();
                for (int j = length; j < len; j++) {
                    stringBuffer.append("." + lineIns[j]);
                }
                lineRecord.setLineIndex(lindIndex + stringBuffer.toString());
                lineRecord.setColorId(record.getColorId());
                lineRecord.setpFactoryId(record.getpFactoryId());
                recordList.add(lineRecord);
            }
        } else {
            throw new HzBomException(1001L, "数据操作异常!");
        }

        return recordList;
    }


    /**
     * 产生超级MBOM
     *
     * @param record PBOM数据
     * @param i      颜色件集合下标
     * @param bean   颜色件信息
     * @param n      集合空间长度
     * @return
     */
    public HzMbomLineRecord generateSupMbom(HzPbomLineRecord record, int i, HzConfigBomColorBean bean, int n, String factoryId) {
        HzMbomLineRecord mbomLineRecord = pBomRecordToMbomRecord(record);
        String lineId = mbomLineRecord.getLineId();
        if (Integer.valueOf(1).equals(record.getColorPart()) && bean != null) {
            if (!bean.getColorCode().equals("-")) {
                lineId = HzBomSysFactory.resultLineId(lineId) + bean.getColorCode();

            }
        }
        if (bean != null) {
            mbomLineRecord.setColorId(bean.getColorUid());
            //TODO 替换bomline名称，将颜色名称作为前缀进行拼接
            if (bean.getColorName() != null)
                mbomLineRecord.setpBomLinePartName(bean.getColorName() + mbomLineRecord.getpBomLinePartName());
        }
        mbomLineRecord.setLineId(lineId);
        mbomLineRecord.setpFactoryId(factoryId);
        String lineIndex = record.getLineIndex();

        String firstIndex = lineIndex.split("\\.")[0];
        String othersIndex = lineIndex.substring(firstIndex.length(), lineIndex.length());
        Integer first = Integer.valueOf(firstIndex) + Integer.valueOf(firstIndex) * i;
        mbomLineRecord.setLineIndex(String.valueOf(first) + othersIndex);
        mbomLineRecord.setSortNum(String.valueOf(n * 100 + 100));//重新分配排序号
        this.sortNum++;
        return mbomLineRecord;
    }


    public static HzMbomLineRecord mbomLineRecordToMbomLineRecord(HzMbomLineRecord record) {
        HzMbomLineRecord mbomLineRecord = new HzMbomLineRecord();
        mbomLineRecord.setPuid(record.getPuid());
        mbomLineRecord.seteBomPuid(record.geteBomPuid());
        mbomLineRecord.setBomDigifaxId(record.getBomDigifaxId());
        mbomLineRecord.setIsHas(record.getIsHas());
        mbomLineRecord.setIs2Y(record.getIs2Y());
        mbomLineRecord.setLineIndex(record.getLineIndex());
        mbomLineRecord.setLineId(record.getLineId());
        mbomLineRecord.setpBomOfWhichDept(record.getpBomOfWhichDept());
        mbomLineRecord.setpBomLinePartClass(record.getpBomLinePartClass());
        mbomLineRecord.setpBomLinePartEnName(record.getpBomLinePartEnName());
        mbomLineRecord.setpBomLinePartName(record.getpBomLinePartName());
        mbomLineRecord.setpBomLinePartResource(record.getpBomLinePartResource());
        mbomLineRecord.setSparePart(record.getSparePart());
        mbomLineRecord.setSparePartNum(record.getSparePartNum());
        mbomLineRecord.setLaborHour(record.getLaborHour());
        mbomLineRecord.setRhythm(record.getRhythm());
        mbomLineRecord.setSolderJoint(record.getSolderJoint());
        mbomLineRecord.setMachineMaterial(record.getMachineMaterial());
        mbomLineRecord.setStandardPart(record.getStandardPart());
        mbomLineRecord.setTools(record.getTools());
        mbomLineRecord.setWasterProduct(record.getWasterProduct());
        mbomLineRecord.setChange(record.getChange());
        mbomLineRecord.setProcessRoute(record.getProcessRoute());
        mbomLineRecord.setChangeNum(record.getChangeNum());
        mbomLineRecord.setStatus(record.getStatus());
        mbomLineRecord.setpStockLocation(record.getpStockLocation());
        mbomLineRecord.setColorId(record.getColorId());
        mbomLineRecord.setOrderId(record.getOrderId());
        mbomLineRecord.setRevision(record.getRevision());
        mbomLineRecord.setParentUid(record.getParentUid());
        mbomLineRecord.setIsColorPart(record.getIsColorPart());
        mbomLineRecord.setpFactoryId(record.getpFactoryId());
        mbomLineRecord.setCreateName(record.getCreateName());
        mbomLineRecord.setSortNum(record.getSortNum());
        mbomLineRecord.setpLouaFlag(record.getpLouaFlag());
        mbomLineRecord.setEffectTime(record.getEffectTime());
        mbomLineRecord.setOrderId(record.getOrderId());
        mbomLineRecord.setRevision(record.getRevision());
        return mbomLineRecord;
    }


    /**
     * MBOM下面添加油漆物料
     * 产生油漆物料BOM
     *
     * @return
     */
    public static List<HzMbomLineRecord> generateMaterielPaint(HzPbomLineRecord pbomLineRecord, int size, String lineIndex, List<HzAccessoriesLibs> libs,
                                                               int i, String colorId, String factoryId) {
        List<HzMbomLineRecord> list = new ArrayList<>();
        if (ListUtil.isNotEmpty(libs)) {
            for (int j = 0; j < libs.size(); j++) {
                HzMbomLineRecord record = new HzMbomLineRecord();
                record.setParentUid(pbomLineRecord.geteBomPuid());
                record.setSortNum(String.valueOf(size * 100 + 100 * j));
                //油漆物料lineindex这里没做计算 这里取了极限值50000 不会超出int值的上限
                record.setLineIndex(lineIndex + "." + Integer.valueOf(50000 + j + i));
                record.setLineId(libs.get(j).getpMaterielCode());
                record.setpBomLinePartName(libs.get(j).getpMaterielName());
                record.setPuid(UUID.randomUUID().toString());
                record.setIsHas(0);
                record.setIs2Y(0);
                record.setStatus(1);
                record.setBomDigifaxId(pbomLineRecord.getBomDigifaxId());
                record.seteBomPuid(UUID.randomUUID().toString());
                record.setColorId(colorId);
                record.setpFactoryId(factoryId);
                list.add(record);
            }
        }
        return list;
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
