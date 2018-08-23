package com.connor.hozon.bom.resources.service.file.impl;

import com.connor.hozon.bom.bomSystem.dao.bom.HzBomMainRecordDao;
import com.connor.hozon.bom.bomSystem.dao.impl.bom.HzBomLineRecordDaoImpl;
import com.connor.hozon.bom.common.util.user.UserInfo;
import com.connor.hozon.bom.resources.dto.response.OperateResultMessageRespDTO;
import com.connor.hozon.bom.resources.service.file.FileUploadService;
import com.connor.hozon.bom.resources.util.ExcelUtil;
import com.connor.hozon.bom.resources.util.PrivilegeUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import sql.pojo.bom.HZBomMainRecord;
import sql.pojo.bom.HzImportEbomRecord;

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

    private static final  String projectId = "e5969e81-0339-4e3a-98a9-a918f64e4289";
    @Override
    public OperateResultMessageRespDTO UploadEbomToDB(MultipartFile file,String projectId) {
        projectId = "e5969e81-0339-4e3a-98a9-a918f64e4289";
        long t1 = System.currentTimeMillis();
        final StringBuffer stringBuffer = new StringBuffer();//错误信息记录
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
            String textUtl = "D:\\file\\upload\\..EP10分时租赁全配置BOM清单2018.01.25.xlsx";
            //读取excel文件
            Workbook workbook = ExcelUtil.getWorkbook(textUtl);

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

            List<HzImportEbomRecord> records = analysisFinalExcelContent(list1);
            int j = 0;
            String s ="";
            for(HzImportEbomRecord record:records){
                if(record.getLevel().endsWith("Y")){
                    int level = Integer.valueOf(record.getLevel().replace("Y",""))+1;
                    s = String.valueOf(level);
                    for(int i =j;i<records.size();i++){
                        if(records.get(i).getLevel().replace("Y","").equals(s)){
                            record.setPuid(records.get(i).getParentId());
                            j = i;
                            break;
                        }
                    }
                }
            }

            long t2 = System.currentTimeMillis();
            System.out.println("时间="+(t2-t1)+"ms");
            System.out.println("老资好几把累");
            System.out.println("时间="+(t2-t1)+"ms");

        }catch (Exception e){
            e.printStackTrace();

        }
        return null;
    }


    /**
     * 将excel表格列转化为EBOM数据
     * @param sheet
     * @param rowNum
     * @return
     */
    private HzImportEbomRecord excelObjectToRecord(Sheet sheet,int rowNum){
        String no = ExcelUtil.getCell(sheet.getRow(rowNum),0).getStringCellValue();
        String lineId=ExcelUtil.getCell(sheet.getRow(rowNum),1).getStringCellValue();
        String pBomLinePartName=ExcelUtil.getCell(sheet.getRow(rowNum),2).getStringCellValue();
        String pBomOfWhichDept=ExcelUtil.getCell(sheet.getRow(rowNum),4).getStringCellValue();


        String pBomLinePartEnName=ExcelUtil.getCell(sheet.getRow(rowNum),5).getStringCellValue();
        String pUnit=ExcelUtil.getCell(sheet.getRow(rowNum),6).getStringCellValue();
        String pPictureNo=ExcelUtil.getCell(sheet.getRow(rowNum),7).getStringCellValue();
        String pPictureSheet=ExcelUtil.getCell(sheet.getRow(rowNum),8).getStringCellValue();
        String pMaterialHigh=ExcelUtil.getCell(sheet.getRow(rowNum),9).getStringCellValue();

        String pMaterial1=ExcelUtil.getCell(sheet.getRow(rowNum),10).getStringCellValue();
        String pMaterial2=ExcelUtil.getCell(sheet.getRow(rowNum),11).getStringCellValue();
        String pMaterial3=ExcelUtil.getCell(sheet.getRow(rowNum),12).getStringCellValue();
        String pDensity=ExcelUtil.getCell(sheet.getRow(rowNum),13).getStringCellValue();
        String pMaterialStandard=ExcelUtil.getCell(sheet.getRow(rowNum),14).getStringCellValue();

        String pSurfaceTreat=ExcelUtil.getCell(sheet.getRow(rowNum),15).getStringCellValue();
        String pTextureColorNum=ExcelUtil.getCell(sheet.getRow(rowNum),16).getStringCellValue();
        String pManuProcess=ExcelUtil.getCell(sheet.getRow(rowNum),17).getStringCellValue();
        String pSymmetry=ExcelUtil.getCell(sheet.getRow(rowNum),18).getStringCellValue();
        String pImportance=ExcelUtil.getCell(sheet.getRow(rowNum),19).getStringCellValue();

        String pRegulationFlag=ExcelUtil.getCell(sheet.getRow(rowNum),20).getStringCellValue();
        String p3cpartFlag=ExcelUtil.getCell(sheet.getRow(rowNum),21).getStringCellValue();
        String pRegulationCode=ExcelUtil.getCell(sheet.getRow(rowNum),22).getStringCellValue();
        String pBwgBoxPart=ExcelUtil.getCell(sheet.getRow(rowNum),23).getStringCellValue();
        String pDevelopType=ExcelUtil.getCell(sheet.getRow(rowNum),24).getStringCellValue();

        String pDataVersion=ExcelUtil.getCell(sheet.getRow(rowNum),25).getStringCellValue();
        String pTargetWeight=ExcelUtil.getCell(sheet.getRow(rowNum),26).getStringCellValue();
        String pFeatureWeight=ExcelUtil.getCell(sheet.getRow(rowNum),27).getStringCellValue();
        String pActualWeight=ExcelUtil.getCell(sheet.getRow(rowNum),28).getStringCellValue();
        String pFastener=ExcelUtil.getCell(sheet.getRow(rowNum),29).getStringCellValue();

        String pFastenerStandard=ExcelUtil.getCell(sheet.getRow(rowNum),30).getStringCellValue();
        String pFastenerLevel=ExcelUtil.getCell(sheet.getRow(rowNum),31).getStringCellValue();
        String pTorque=ExcelUtil.getCell(sheet.getRow(rowNum),32).getStringCellValue();
        String pDutyEngineer=ExcelUtil.getCell(sheet.getRow(rowNum),33).getStringCellValue();
        String pSupply=ExcelUtil.getCell(sheet.getRow(rowNum),34).getStringCellValue();

        String pSupplyCode=ExcelUtil.getCell(sheet.getRow(rowNum),35).getStringCellValue();
        String pBuyEngineer=ExcelUtil.getCell(sheet.getRow(rowNum),36).getStringCellValue();
        String pRemark=ExcelUtil.getCell(sheet.getRow(rowNum),37).getStringCellValue();
        String pBomLinePartClass=ExcelUtil.getCell(sheet.getRow(rowNum),38).getStringCellValue();
        String pBomLinePartResource=ExcelUtil.getCell(sheet.getRow(rowNum),39).getStringCellValue();

        String pInOutSideFlag=ExcelUtil.getCell(sheet.getRow(rowNum),40).getStringCellValue();
        String pUpc=ExcelUtil.getCell(sheet.getRow(rowNum),41).getStringCellValue();
        String fna=ExcelUtil.getCell(sheet.getRow(rowNum),42).getStringCellValue();
        String pFnaDesc=ExcelUtil.getCell(sheet.getRow(rowNum),43).getStringCellValue();
        double number=ExcelUtil.getCell(sheet.getRow(rowNum),44).getNumericCellValue();


        HzImportEbomRecord record = new HzImportEbomRecord();
        record.setNo(Integer.valueOf(no));
        record.setLineId(lineId);
        record.setNumber((int)number);
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
        return record;
    }

    /**
     * 生成每条bom的父puid值
     * @param records
     * @return
     */
    private Map<String,String> generateParentPuid(List<HzImportEbomRecord> records) {
        Map<String,String> mapList = new HashMap<>();
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
    private Map<String,String> analysisLineIndex(List<HzImportEbomRecord> records) {
        Map<String,String> lineIndexMap = new HashMap<>();
        for(int i = 1;i<records.size();i++){//产生lineIndex
            HzImportEbomRecord preDTO = records.get(i-1);
            HzImportEbomRecord currentDTO = records.get(i);
            if(preDTO.getHigh() == 0){
                Integer lineIndexFirstNum = hzBomLineRecordDao.getMaxLineIndexFirstNum(projectId);
                if(lineIndexFirstNum == null){
                    lineIndexMap.put(preDTO.getLevel()+"-"+0,"10.10");
                }else {
                    lineIndexMap.put(preDTO.getLevel()+"-"+0,lineIndexFirstNum+".10");
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
    private List<HzImportEbomRecord> analysisResultBom(List<HzImportEbomRecord> records) {
        Map<String,String> mapList = generateParentPuid(records);
        Map<String,String> lineIndexMap = analysisLineIndex(records);
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
                        break;
                    }
                }
                //找lineIndex
                for(String key :lineIndexMap.keySet()){
                    if(key.equals(currentKey)){
                        currentLineIndex = lineIndexMap.get(currentKey);
                        break;
                    }
                }
            }else {
                Integer lineIndexFirstNum = hzBomLineRecordDao.getMaxLineIndexFirstNum(projectId);
                if(lineIndexFirstNum == null){
                    currentLineIndex = "10.10";
                }else {
                    currentLineIndex =lineIndexFirstNum+".10";
                }
            }
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


    List<HzImportEbomRecord> analysisFinalExcelContent(List<List<HzImportEbomRecord>> lists){
        List<HzImportEbomRecord> list = new ArrayList<>();
        for(List<HzImportEbomRecord> records:lists){
            list.addAll(analysisResultBom(records));
        }
        return list;
    }

}
