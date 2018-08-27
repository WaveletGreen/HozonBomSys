package com.connor.hozon.bom.resources.service.file.impl;

import com.alibaba.fastjson.JSON;
import com.connor.hozon.bom.bomSystem.dao.bom.HzBomMainRecordDao;
import com.connor.hozon.bom.bomSystem.dao.impl.bom.HzBomLineRecordDaoImpl;
import com.connor.hozon.bom.common.util.user.UserInfo;
import com.connor.hozon.bom.resources.dto.response.OperateResultMessageRespDTO;
import com.connor.hozon.bom.resources.mybatis.bom.HzEbomRecordDAO;
import com.connor.hozon.bom.resources.service.file.FileUploadService;
import com.connor.hozon.bom.resources.util.ExcelUtil;
import com.connor.hozon.bom.resources.util.ListUtil;
import com.connor.hozon.bom.resources.util.PrivilegeUtil;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import sql.pojo.bom.HZBomMainRecord;
import sql.pojo.bom.HzImportEbomRecord;

import java.math.BigDecimal;
import java.util.*;

/**
 * @Author: haozt
 * @Date: 2018/8/22
 * @Description:
 */
@Service("FileUploadService")
public class FileUploadServiceImpl implements FileUploadService {

    @Autowired
    private HzBomLineRecordDaoImpl hzBomLineRecordDao;

    @Autowired
    private HzBomMainRecordDao hzBomMainRecordDao;

    @Autowired
    private HzEbomRecordDAO hzEbomRecordDAO;

    private int errorCount = 0;

    private int lineIndexFirst =10;
    @Override
    public OperateResultMessageRespDTO UploadEbomToDB(MultipartFile file,String projectId) {
        try {
            //判断权限
            boolean b = PrivilegeUtil.writePrivilege();
            if(!b){
                return OperateResultMessageRespDTO.getFailPrivilege();
            }
            //判断文件格式
            boolean preCheck = ExcelUtil.preReadCheck(file.getOriginalFilename());
            if(!preCheck){
                return OperateResultMessageRespDTO.fileIsNotExcel();
            }
            //判断文件大小
            boolean suitSize = ExcelUtil.checkFileSize(file.getSize());
            if(!suitSize){
                return OperateResultMessageRespDTO.fileSizeOverFlow();
            }
            ExcelUtil.preReadCheck(file.getOriginalFilename());
            //上传文件到服务器
            String fileUrl = ExcelUtil.uploadFileToLocation(file.getBytes(),file.getOriginalFilename());
            //String textUtl = "D:\\file\\upload\\测试测试.xlsx";
            //读取excel文件
            Workbook workbook = ExcelUtil.getWorkbook(fileUrl);
            this.errorCount = 0;
            String errorMsg = errorLogInfo(workbook);
            if(this.errorCount!=0){
                OperateResultMessageRespDTO respDTO = new OperateResultMessageRespDTO();
                respDTO.setErrMsg(errorMsg);
                respDTO.setErrCode(OperateResultMessageRespDTO.FAILED_CODE);
                return  respDTO;
            }

            List<List<HzImportEbomRecord>> list1 = new ArrayList<>();

            HZBomMainRecord hzBomMainRecord = hzBomMainRecordDao.selectByProjectPuid(projectId);

            for(int numSheet = 0; numSheet < workbook.getNumberOfSheets(); numSheet++ ){
                Sheet sheet = workbook.getSheetAt(numSheet);
                String sheetName = workbook.getSheetName(numSheet);//EBOM 表名 就一张表的话 没什么卵用
                int i = 1;
                while (i<=sheet.getLastRowNum()){
                    List<HzImportEbomRecord> list = new ArrayList<>();
                    int high = 0;
                    boolean add2Y = true;
                    for(int rowNum = i; rowNum <= sheet.getLastRowNum(); rowNum++){
                        Row sheetRow = sheet.getRow(rowNum);
                        if(sheetRow != null){
                            HzImportEbomRecord record = excelObjectToRecord(sheet,rowNum);
                            String level = ExcelUtil.getCell(sheet.getRow(rowNum),3).getStringCellValue();
                            record.setLevel(level);
                            record.setHigh(high);
                            record.setBomDigifaxId(hzBomMainRecord.getPuid());
                            if("2Y".equals(level)){
                                record.setIs2Y(1);
                            }else {
                                record.setIs2Y(0);
                            }
                            if(level.endsWith("Y")){
                                record.setIsHas(1);
                                record.setIsPart(0);
                            }else {
                                record.setIsHas(0);
                                record.setIsPart(1);
                            }
                            if(level.equals("2") || level.equals("2Y")){
                                if(add2Y){
                                    list.add(record);
                                    high++;
                                    i++;
                                    add2Y =false;
                                }else {
                                    break;
                                }
                            }  else {
                                list.add(record);
                                high++;
                                i++;
                            }
                        }
                    }
                    list1.add(list);
                }

            }

            List<HzImportEbomRecord> records = analysisFinalExcelContent(list1,projectId);
            int j = 0;
            String s ="";

            for(int i=0;i<records.size();i++){//设置子层的父id为当前的id
                j = i;
                if(records.get(i).getLevel().endsWith("Y")){
                    int level = Integer.valueOf(records.get(i).getLevel().replace("Y",""))+1;
                    s = String.valueOf(level);
                    for(int k =j;k<records.size();k++){
                        if(s.equals(records.get(k).getLevel()) || (s+"Y").equals(records.get(k).getLevel())){
                            records.get(i).setPuid(records.get(k).getParentId());
                            break;
                        }
                    }
                }
            }


            if(ListUtil.isNotEmpty(records)){
                int size = records.size();
                //分批插入数据 一次1000条
                int i =0;
                int cou = 0;
                if(size>1000){
                    for(i =0;i<size/1000;i++){
                        List<HzImportEbomRecord> list = new ArrayList<>();
                        for(int k = 0;k<1000;k++){
                            HzImportEbomRecord hzImportEbomRecord =records.get(cou);
                            list.add(hzImportEbomRecord);
                            cou++;
                        }
                        hzEbomRecordDAO.importList(list);
                    }
                }
                if(i*1000<size){
                    List<HzImportEbomRecord> list = new ArrayList<>();
                    for(int k = 0;k<size-i*1000;k++){
                        HzImportEbomRecord hzImportEbomRecord =records.get(cou);
                        list.add(hzImportEbomRecord);
                        cou++;
                    }
                    hzEbomRecordDAO.importList(list);
                }
            }

        }catch (Exception e){
            return OperateResultMessageRespDTO.getFailResult();

        }
        return OperateResultMessageRespDTO.getSuccessResult();
    }


    /**
     * 将excel表格列转化为EBOM数据
     * @param sheet
     * @param rowNum
     * @return
     */
    private HzImportEbomRecord excelObjectToRecord(Sheet sheet,int rowNum){
        Row row = sheet.getRow(rowNum);
        String no = ExcelUtil.getCell(row,0).getStringCellValue();
        String lineId=ExcelUtil.getCell(row,1).getStringCellValue();
        String pBomLinePartName=ExcelUtil.getCell(row,2).getStringCellValue();
        String pBomOfWhichDept=ExcelUtil.getCell(row,4).getStringCellValue();


        String pBomLinePartEnName=ExcelUtil.getCell(row,5).getStringCellValue();
        String pUnit=ExcelUtil.getCell(row,6).getStringCellValue();
        String pPictureNo=ExcelUtil.getCell(row,7).getStringCellValue();
        String pPictureSheet=ExcelUtil.getCell(row,8).getStringCellValue();
        String pMaterialHigh=ExcelUtil.getCell(row,9).getStringCellValue();

        String pMaterial1=ExcelUtil.getCell(row,10).getStringCellValue();
        String pMaterial2=ExcelUtil.getCell(row,11).getStringCellValue();
        String pMaterial3=ExcelUtil.getCell(row,12).getStringCellValue();
        String pDensity=ExcelUtil.getCell(row,13).getStringCellValue();
        String pMaterialStandard=ExcelUtil.getCell(row,14).getStringCellValue();

        String pSurfaceTreat=ExcelUtil.getCell(row,15).getStringCellValue();
        String pTextureColorNum=ExcelUtil.getCell(row,16).getStringCellValue();
        String pManuProcess=ExcelUtil.getCell(row,17).getStringCellValue();
        String pSymmetry=ExcelUtil.getCell(row,18).getStringCellValue();
        String pImportance=ExcelUtil.getCell(row,19).getStringCellValue();

        String pRegulationFlag=ExcelUtil.getCell(row,20).getStringCellValue();
        String p3cpartFlag=ExcelUtil.getCell(row,21).getStringCellValue();
        String pRegulationCode=ExcelUtil.getCell(row,22).getStringCellValue();
        String pBwgBoxPart=ExcelUtil.getCell(row,23).getStringCellValue();
        String pDevelopType=ExcelUtil.getCell(row,24).getStringCellValue();

        String pDataVersion=ExcelUtil.getCell(row,25).getStringCellValue();

        String pTargetWeight ="";
        String pFeatureWeight ="";
        String pActualWeight="";

        try {
            pTargetWeight = ExcelUtil.getCell(row,26).getStringCellValue();
            BigDecimal bigDecimal = new BigDecimal(pTargetWeight);
            pTargetWeight =String.valueOf( bigDecimal.setScale(3, BigDecimal.ROUND_HALF_UP));
        }catch (Exception e){
            try {
                BigDecimal dec = new BigDecimal(ExcelUtil.getCell(row,26).getNumericCellValue());
                pTargetWeight =String.valueOf(dec.setScale(3, BigDecimal.ROUND_HALF_UP).doubleValue());
            }catch (Exception e1){
                pTargetWeight ="";
            }

        }

        try {
            BigDecimal bigDecimal = new BigDecimal(ExcelUtil.getCell(row,27).getStringCellValue());
            pFeatureWeight =String.valueOf( bigDecimal.setScale(3, BigDecimal.ROUND_HALF_UP));
        }catch (Exception e){
            try {
                BigDecimal dec = new BigDecimal(ExcelUtil.getCell(row,27).getNumericCellValue());
                pFeatureWeight =String.valueOf(dec.setScale(3, BigDecimal.ROUND_HALF_UP).doubleValue());
            }catch (Exception e1){
                pFeatureWeight="";
            }

        }

        try {
            BigDecimal bigDecimal = new BigDecimal(ExcelUtil.getCell(row,28).getStringCellValue());
            pActualWeight =String.valueOf( bigDecimal.setScale(3, BigDecimal.ROUND_HALF_UP));
        }catch (Exception e){
            try {
                BigDecimal dec = new BigDecimal(ExcelUtil.getCell(row,28).getNumericCellValue());
                pActualWeight =String.valueOf(dec.setScale(3, BigDecimal.ROUND_HALF_UP).doubleValue());
            }catch (Exception e1){
                pActualWeight="";
            }
        }
        String pFastener=ExcelUtil.getCell(row,29).getStringCellValue();

        String pFastenerStandard=ExcelUtil.getCell(row,30).getStringCellValue();
        String pFastenerLevel=ExcelUtil.getCell(row,31).getStringCellValue();
        if(pFastenerLevel.contains(".") && pFastenerLevel.length()>4){
            pFastenerLevel = pFastenerLevel.substring(0,3);
        }
        String pTorque=ExcelUtil.getCell(row,32).getStringCellValue();
        String pDutyEngineer=ExcelUtil.getCell(row,33).getStringCellValue();
        String pSupply=ExcelUtil.getCell(row,34).getStringCellValue();

        String pSupplyCode=ExcelUtil.getCell(row,35).getStringCellValue();
        String pBuyEngineer=ExcelUtil.getCell(row,36).getStringCellValue();
        String pRemark=ExcelUtil.getCell(row,37).getStringCellValue();
        String pBomLinePartClass=ExcelUtil.getCell(row,38).getStringCellValue();
        String pBomLinePartResource=ExcelUtil.getCell(row,39).getStringCellValue();

        String pInOutSideFlag=ExcelUtil.getCell(row,40).getStringCellValue();
        String pUpc=ExcelUtil.getCell(row,41).getStringCellValue();
        String fna=ExcelUtil.getCell(row,42).getStringCellValue();
        String pFnaDesc=ExcelUtil.getCell(row,43).getStringCellValue();
        String number=ExcelUtil.getCell(row,44).getStringCellValue();


        HzImportEbomRecord record = new HzImportEbomRecord();
        record.setNo(Integer.valueOf(no));
        record.setLineId(lineId);
        if(number !=null && number !=""){
            record.setNumber(Integer.valueOf(number));
        }
        if("Y".endsWith(p3cpartFlag)){
            record.setP3cpartFlag(1);
        }else {
            record.setP3cpartFlag(0);
        }
        record.setpActualWeight(pActualWeight);
        record.setpBomLinePartClass(pBomLinePartClass);
        record.setpBomLinePartEnName(pBomLinePartEnName);
        record.setpBomLinePartName(pBomLinePartName);
        record.setpBomLinePartResource(pBomLinePartResource);
        record.setpBuyEngineer(pBuyEngineer);
        record.setpBomOfWhichDept(pBomOfWhichDept);
        record.setpBwgBoxPart(pBwgBoxPart);
        record.setpCreateName(UserInfo.getUser().getUserName());
        record.setpDataVersion(pDataVersion);
        record.setpDensity(pDensity);
        record.setpDevelopType(pDevelopType);
        record.setpDutyEngineer(pDutyEngineer);
        record.setpFastener(pFastener);
        record.setpFastenerLevel(pFastenerLevel);
        record.setpFastenerStandard(pFastenerStandard);
        record.setpFeatureWeight(pFeatureWeight);
        record.setpFnaDesc(pFnaDesc);
        record.setpFnaInfo(fna);
        record.setpImportance(pImportance);
        record.setpInOutSideFlag(pInOutSideFlag == "外饰件"?0:1);
        record.setpManuProcess(pManuProcess);
        record.setpMaterial1(pMaterial1);
        record.setpMaterial2(pMaterial2);
        record.setpMaterial3(pMaterial3);
        record.setpMaterialHigh(pMaterialHigh);
        record.setpMaterialStandard(pMaterialStandard);
        record.setpPictureNo(pPictureNo);
        record.setpPictureSheet(pPictureSheet);
        record.setpRegulationCode(pRegulationCode);
        record.setpRegulationFlag(pRegulationFlag == "Y"?1:0);
        record.setpRemark(pRemark);
        record.setpSupply(pSupply);
        record.setpSupplyCode(pSupplyCode);
        record.setpSurfaceTreat(pSurfaceTreat);
        record.setpSymmetry(pSymmetry);
        record.setpTargetWeight(pTargetWeight);
        record.setpTextureColorNum(pTextureColorNum);
        record.setpTorque(pTorque);
        record.setpUnit(pUnit);
        record.setpUpc(pUpc);
        record.setpUpdateName(UserInfo.getUser().getUserName());
        record.setLinePuid(UUID.randomUUID().toString());
        return record;
    }

    /**
     * 生成每条bom的父puid值
     * @param records
     * @return
     */
    private Map<String,String> generateParentPuid(List<HzImportEbomRecord> records) {
        Map<String,String> mapList = new LinkedHashMap<>();
        for(int i = 0;i<records.size();i++){//产生父键
            HzImportEbomRecord record = records.get(i);
            String level = record.getLevel();
            int high = record.getHigh();
            mapList.put(level+"-"+high,UUID.randomUUID().toString());
        }
        return mapList;
    }


    /**
     * 解析产生每条bom的lineIndex
     * @param records
     * @return
     */
    private Map<String,String> analysisLineIndex(List<HzImportEbomRecord> records,String projectId) {
        Map<String,String> lineIndexMap = new HashMap<>();
        for(int i = 1;i<records.size();i++){//产生lineIndex
            HzImportEbomRecord preDTO = records.get(i-1);
            HzImportEbomRecord currentDTO = records.get(i);
            if(preDTO.getHigh() == 0){
                Integer lineIndexFirstNum = hzBomLineRecordDao.getMaxLineIndexFirstNum(projectId);
                if(lineIndexFirstNum == null){
                    lineIndexMap.put(preDTO.getLevel()+"-"+0,lineIndexFirst+".10");
                    lineIndexFirst +=10;
                }else {
                    lineIndexFirstNum = lineIndexFirstNum+lineIndexFirst;
                    lineIndexMap.put(preDTO.getLevel()+"-"+0,lineIndexFirstNum+".10");
                    lineIndexFirst+=10;
                }
            }
            String level = currentDTO.getLevel();
            int high = currentDTO.getHigh();
            String currentKey = level+"-"+high;
            String preKey = preDTO.getLevel()+"-"+preDTO.getHigh();
            String currentIndex = "";
            int preLevelNum = Integer.valueOf(preDTO.getLevel().replace("Y",""));
            int currentLevelNum = Integer.valueOf(level.replace("Y",""));

            if(currentLevelNum>preLevelNum){
                for(String key:lineIndexMap.keySet()){
                    if(key.equals(preKey)){
                        StringBuffer s = new StringBuffer(lineIndexMap.get(preKey));
                        currentIndex = s.append(".10").toString();
                        lineIndexMap.put(currentKey,currentIndex);
                        break;
                    }
                }
            }else if(currentLevelNum == preLevelNum){
                for(String key:lineIndexMap.keySet()){
                    if(key.equals(preKey)){
                        String value = lineIndexMap.get(preKey);
                        int lastNum = Integer.valueOf(value.split("\\.")[value.split("\\.").length-1]);
                        int charAtNum = value.lastIndexOf(".");
                        String preIndexDoNotContainLastNum = value.substring(0,charAtNum);
                        lastNum+=10;
                        currentIndex = preIndexDoNotContainLastNum+"."+lastNum;
                        lineIndexMap.put(currentKey,currentIndex);
                        break;
                    }

                }
            }else {
                for(Map.Entry<String,String> entry:lineIndexMap.entrySet()){
                    String parentKey = currentLevelNum+"Y-";
                    if(entry.getKey().indexOf(parentKey)>-1){
                        String value = entry.getValue();
                        int lastNum = Integer.valueOf(value.split("\\.")[value.split("\\.").length-1]);
                        int charAtNum = value.lastIndexOf(".");
                        String preIndexDoNotContainLastNum = value.substring(0,charAtNum);
                        lastNum+=10;
                        currentIndex = preIndexDoNotContainLastNum+"."+lastNum;
                        lineIndexMap.put(currentKey,currentIndex);
                        break;
                    }
                }
            }

        }
        return lineIndexMap;

    }

    /**
     * 产生最终的bom解算结果
     * @param records
     * @return
     */
    private List<HzImportEbomRecord> analysisResultBom(List<HzImportEbomRecord> records,String projectId) {
        Map<String,String> mapList = generateParentPuid(records);
        Map<String,String> lineIndexMap = analysisLineIndex(records,projectId);
        List<HzImportEbomRecord> recordList = new ArrayList<>();
        for(int i = 0;i<records.size();i++){//找父 找lineIndex 最为关键
            HzImportEbomRecord record = records.get(i);
            String level = record.getLevel();
            int high = record.getHigh();
            String parentId = "";
            String currentKey = record.getLevel()+"-"+high;
            String currentLineIndex = "";
            //找父id
            if(high!=0){
                int m = Integer.valueOf(level.replace("Y",""))-1;
                String parentKey = m+"Y-";
                for(Map.Entry<String,String> entry:mapList.entrySet()){
                    if(entry.getKey().indexOf(parentKey)>-1){
                        parentId = entry.getValue();
                    }
                    if(entry.getKey().equals(currentKey)){
                        break;
                    }
                }
            }
            //找lineIndex
            for(String key :lineIndexMap.keySet()){
                if(key.equals(currentKey)){
                    currentLineIndex = lineIndexMap.get(currentKey);
                    break;
                }
            }
//            else {
//                Integer lineIndexFirstNum = hzBomLineRecordDao.getMaxLineIndexFirstNum(projectId);
//                if(lineIndexFirstNum == null){
//                    currentLineIndex = lineIndexFirst+".10";
//                }else {
//                    currentLineIndex =lineIndexFirstNum+".10";
//                }
//            }
            record.setLineIndex(currentLineIndex);
            record.setParentId(parentId);
            if(!level.endsWith("Y")){
                record.setPuid(UUID.randomUUID().toString());
            }
            record.setOrderNum(record.getNo()*100);
            recordList.add(record);
        }
        return recordList;
    }


    /**
     * 将 传入的excel文件解析为符合数据库存储的结构
     * @param lists
     * @param projectId
     * @return
     */
    List<HzImportEbomRecord> analysisFinalExcelContent(List<List<HzImportEbomRecord>> lists,String projectId){
        List<HzImportEbomRecord> list = new ArrayList<>();
        for(List<HzImportEbomRecord> records:lists){
            list.addAll(analysisResultBom(records,projectId));
        }
        return list;
    }


    private String errorLogInfo(Workbook workbook){
        StringBuffer stringBuffer = new StringBuffer("文件解析失败!</br>");
        for(int numSheet = 0; numSheet < workbook.getNumberOfSheets(); numSheet++ ){
            Sheet sheet = workbook.getSheetAt(numSheet);
            for(int rowNum = 1; rowNum <= sheet.getLastRowNum(); rowNum++){
                String level = ExcelUtil.getCell(sheet.getRow(rowNum),3).getStringCellValue();
                String number=ExcelUtil.getCell(sheet.getRow(rowNum),44).getStringCellValue();

                if(level.endsWith("Y") && rowNum !=sheet.getLastRowNum()){
                    int lev = Integer.valueOf(level.replace("Y",""))+1;
                    String nextLevel = ExcelUtil.getCell(sheet.getRow(rowNum+1),3).getStringCellValue();
                    if(!nextLevel.equals(lev+"Y") && !nextLevel.equals(String.valueOf(lev))){
                        stringBuffer.append("第"+(rowNum+1)+"行的层级填写不正确，" +
                                ""+level+"下的层级应该为:<strong>"+lev+"Y</strong>"+"或者<strong>"+lev+"</strong>" +
                                ";而实际为:"+nextLevel+"</br>");
                        this.errorCount++;
                        continue;
                    }
                }


                if(rowNum == sheet.getLastRowNum()){
                    if(level.endsWith("Y")){
                        stringBuffer.append("最后一行的层级不能带Y,因为找不到他的子层!");
                        this.errorCount++;
                        continue;
                    }
                }
                try {
                    if(number!=null && number!="")
                    Integer.valueOf(number);
                }catch (Exception e){
                    stringBuffer.append("第"+(rowNum+1)+"行的数量填写应该为整数，而实际为:"+number+"</br>");
                    this.errorCount++;
                    continue;
                }
            }

        }
        return stringBuffer.toString();
    }
}
