package com.connor.hozon.resources.service.file.impl;

import cn.net.connor.hozon.dao.pojo.main.HzMainBom;
import cn.net.connor.hozon.dao.dao.main.HzMainBomDao;
import cn.net.connor.hozon.services.service.configuration.fullConfigSheet.impl.HzCfg0ModelServiceImpl;
import cn.net.connor.hozon.common.util.ListUtils;
import cn.net.connor.hozon.services.service.sys.UserInfo;
import cn.net.connor.hozon.common.constant.BOMTransConstants;
import cn.net.connor.hozon.common.entity.WriteResultRespDTO;
import com.connor.hozon.resources.domain.model.HzEbomRecordFactory;
import com.connor.hozon.resources.domain.model.HzPbomRecordFactory;
import com.connor.hozon.resources.domain.query.HzEPLQuery;
import cn.net.connor.hozon.services.common.enumtype.ChangeTableNameEnum;
import com.connor.hozon.resources.mybatis.bom.HzEbomRecordDAO;
import com.connor.hozon.resources.mybatis.bom.HzPbomRecordDAO;
import com.connor.hozon.resources.mybatis.epl.HzEPLDAO;
import com.connor.hozon.resources.service.bom.HzMbomService;
import com.connor.hozon.resources.service.file.FileUploadService;
import com.connor.hozon.resources.util.ExcelUtil;
import com.connor.hozon.resources.util.StringUtil;
import cn.net.connor.hozon.common.exception.HzBomException;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.web.multipart.MultipartFile;
import cn.net.connor.hozon.dao.pojo.bom.bom.HzImportEbomRecord;
import cn.net.connor.hozon.dao.pojo.bom.bom.HzPbomLineRecord;
import cn.net.connor.hozon.dao.pojo.configuration.model.HzCfg0ModelRecord;
import cn.net.connor.hozon.dao.pojo.bom.epl.HzEPLManageRecord;
import cn.net.connor.hozon.dao.pojo.bom.epl.HzEPLRecord;

import java.math.BigDecimal;
import java.util.*;

/**
 * @Author: haozt
 * @Date: 2018/8/22
 * @Description: EBOM导入Excel使用
 * @modify 导入Excel时，对应的零部件数据来源为EPL
 *          这里只导入基本的BOM属性
 *          如果导入的BOM零件数据在EPL中未存在记录，则不允许导入操作
 */
@Service("fileUploadService")
public class FileUploadServiceImpl implements FileUploadService {

    private HzMainBomDao hzMainBomDao;

    private HzEbomRecordDAO hzEbomRecordDAO;

    private HzPbomRecordDAO hzPbomRecordDAO;

    private HzMbomService hzMbomService;

    private HzCfg0ModelServiceImpl hzCfg0ModelServiceImpl;

    private HzEPLDAO hzEPLDAO;

    private TransactionTemplate configTransactionTemplate;

    @Autowired
    public void setHzMainBomDao(HzMainBomDao hzMainBomDao) {
        this.hzMainBomDao = hzMainBomDao;
    }
    @Autowired
    public void setHzEbomRecordDAO(HzEbomRecordDAO hzEbomRecordDAO) {
        this.hzEbomRecordDAO = hzEbomRecordDAO;
    }
    @Autowired
    public void setHzPbomRecordDAO(HzPbomRecordDAO hzPbomRecordDAO) {
        this.hzPbomRecordDAO = hzPbomRecordDAO;
    }
    @Autowired
    public void setHzMbomService(HzMbomService hzMbomService) {
        this.hzMbomService = hzMbomService;
    }
    @Autowired
    public void setHzCfg0ModelServiceImpl(HzCfg0ModelServiceImpl hzCfg0ModelServiceImpl) {
        this.hzCfg0ModelServiceImpl = hzCfg0ModelServiceImpl;
    }
    @Autowired
    public void setHzEPLDAO(HzEPLDAO hzEPLDAO) {
        this.hzEPLDAO = hzEPLDAO;
    }

    @Autowired
    public void setConfigTransactionTemplate(TransactionTemplate configTransactionTemplate) {
        this.configTransactionTemplate = configTransactionTemplate;
    }


    private int errorCount = 0;

    private int lineIndexFirst =10;

    private int lineIndexLast = 10;

    private Integer maxOrderNum = null;
    @Override
    public WriteResultRespDTO uploadEbomToDB(MultipartFile file, String projectId) {
        try {
            //判断文件格式
            boolean preCheck = ExcelUtil.preReadCheck(file.getOriginalFilename());
            if(!preCheck){
                return WriteResultRespDTO.fileIsNotExcel();
            }
            //判断文件大小
            boolean suitSize = ExcelUtil.checkFileSize(file.getSize());
            if(!suitSize){
                return WriteResultRespDTO.fileSizeOverFlow();
            }
            //上传文件到服务器
            String fileUrl = ExcelUtil.uploadFileToLocation(file.getBytes(),file.getOriginalFilename());
            //String textUtl = "D:\\file\\upload\\测试测试.xlsx";
            //读取excel文件
            Workbook workbook = ExcelUtil.getWorkbook(fileUrl);
            //这里导入文件只有一个sheet
//            for(int numSheet = 0; numSheet < workbook.getNumberOfSheets(); numSheet++ ){
                Sheet sheet = workbook.getSheetAt(0);
                if(null == sheet){
                    return WriteResultRespDTO.fileError();
                }
                Row row = sheet.getRow(0);
                if(row.getLastCellNum()<52){
                    return WriteResultRespDTO.fileFormatError();
                }

            configTransactionTemplate.execute(new TransactionCallback<Void>() {
                @Override
                public Void doInTransaction(TransactionStatus status) {
                    WriteResultRespDTO respDTO = null;
                    if( "是否颜色件".equals(row.getCell(row.getLastCellNum()-1).getStringCellValue())
                            || "生效时间".equals(row.getCell(row.getLastCellNum()-1).getStringCellValue())
                            ){
                         respDTO = importEbomExcelContentToDB(projectId,sheet);
                    }else {
                         respDTO = importBomSingleVehDosageToDB(sheet,projectId);
                    }
                    if(WriteResultRespDTO.isSuccess(respDTO)){
                        return null;
                    }
                    throw new HzBomException(respDTO.getErrMsg());
                }
            });

//            }
            return WriteResultRespDTO.getSuccessResult();
        }catch (Exception e){
            e.printStackTrace();
            return WriteResultRespDTO.failResultRespDTO(e.getMessage());
        }
    }







    /** 下面的私有方法为:EBOM导入Excel文件（第一次导入 不包含单车用量的导入  导入单车用量时，必须先有对应的EBOM数据）**/

    /**
     * 将EBOM excel中的内容导入到数据库
     * @param projectId
     * @param sheet
     * @return
     */
    private WriteResultRespDTO importEbomExcelContentToDB(String projectId,Sheet sheet){
        try {

            this.errorCount = 0;
            String errorMsg = errorLogInfo(sheet,projectId);
            if(this.errorCount!=0){
                return  WriteResultRespDTO.failResultRespDTO(errorMsg);
            }

            //todo 导入EBOM数据前 需要先把EPL的导入工作做好 去重
            Set<HzEPLRecord> eplRecords = new HashSet<>();
            for(int rowNum = 1; rowNum <= sheet.getLastRowNum(); rowNum++){
                Row row = sheet.getRow(rowNum);
                String lineId=ExcelUtil.getCell(row,1).getStringCellValue();
                HzEPLQuery query = new HzEPLQuery();
                query.setPartId(lineId);
                query.setProjectId(projectId);
                HzEPLRecord eplRecord = hzEPLDAO.getEPLRecordById(query);
                if(eplRecord != null){
                    continue;
                }
                HzEPLRecord record = new HzEPLRecord();
                String pBomLinePartName=ExcelUtil.getCell(row,2).getStringCellValue();
                String pBomOfWhichDept=ExcelUtil.getCell(row,4).getStringCellValue();
                String pBomLinePartEnName=ExcelUtil.getCell(row,8).getStringCellValue();

                String pUnit=ExcelUtil.getCell(row,10).getStringCellValue();
                String pPictureNo=ExcelUtil.getCell(row,11).getStringCellValue();
                String pPictureSheet=ExcelUtil.getCell(row,12).getStringCellValue();
                String pMaterialHigh=ExcelUtil.getCell(row,13).getStringCellValue();

                String pMaterial1=ExcelUtil.getCell(row,14).getStringCellValue();
                String pMaterial2=ExcelUtil.getCell(row,15).getStringCellValue();
                String pMaterial3=ExcelUtil.getCell(row,16).getStringCellValue();
                String pDensity=ExcelUtil.getCell(row,17).getStringCellValue();
                String pMaterialStandard=ExcelUtil.getCell(row,18).getStringCellValue();

                String pSurfaceTreat=ExcelUtil.getCell(row,19).getStringCellValue();
                String pTextureColorNum=ExcelUtil.getCell(row,20).getStringCellValue();
                String pManuProcess=ExcelUtil.getCell(row,21).getStringCellValue();
                String pSymmetry=ExcelUtil.getCell(row,22).getStringCellValue();
                String pImportance=ExcelUtil.getCell(row,23).getStringCellValue();

                String pRegulationFlag=ExcelUtil.getCell(row,24).getStringCellValue();
                String p3cpartFlag=ExcelUtil.getCell(row,25).getStringCellValue();
                String pRegulationCode=ExcelUtil.getCell(row,26).getStringCellValue();
                String pBwgBoxPart=ExcelUtil.getCell(row,27).getStringCellValue();
                String pDevelopType=ExcelUtil.getCell(row,28).getStringCellValue();

                String pDataVersion=ExcelUtil.getCell(row,29).getStringCellValue();

                String pTargetWeight ="";
                String pFeatureWeight ="";
                String pActualWeight="";

                try {
                    pTargetWeight = ExcelUtil.getCell(row,30).getStringCellValue();
                    if(StringUtils.isBlank(pTargetWeight)){
                        pTargetWeight = "";
                    }else {
                        BigDecimal bigDecimal = new BigDecimal(pTargetWeight);
                        pTargetWeight =String.valueOf(bigDecimal.setScale(3, BigDecimal.ROUND_HALF_UP));
                    }
                }catch (Exception e){
                    try {
                        BigDecimal dec = new BigDecimal(ExcelUtil.getCell(row,30).getNumericCellValue());
                        pTargetWeight =String.valueOf(dec.setScale(3, BigDecimal.ROUND_HALF_UP).doubleValue());
                    }catch (Exception e1){
                        pTargetWeight ="";
                    }

                }

                try {
                    pFeatureWeight = ExcelUtil.getCell(row,31).getStringCellValue();
                    if(StringUtils.isBlank(pFeatureWeight)){
                        pFeatureWeight ="";
                    }else {
                        BigDecimal bigDecimal = new BigDecimal(pFeatureWeight);
                        pFeatureWeight =String.valueOf( bigDecimal.setScale(3, BigDecimal.ROUND_HALF_UP));
                    }
                }catch (Exception e){
                    try {
                        BigDecimal dec = new BigDecimal(ExcelUtil.getCell(row,31).getNumericCellValue());
                        pFeatureWeight =String.valueOf(dec.setScale(3, BigDecimal.ROUND_HALF_UP).doubleValue());
                    }catch (Exception e1){
                        pFeatureWeight="";
                    }

                }

                try {
                    pActualWeight = ExcelUtil.getCell(row,32).getStringCellValue();
                    if(StringUtils.isBlank(pActualWeight)){
                        pActualWeight = "";
                    }else {
                        BigDecimal bigDecimal = new BigDecimal(pActualWeight);
                        pActualWeight =String.valueOf( bigDecimal.setScale(3, BigDecimal.ROUND_HALF_UP));
                    }
                }catch (Exception e){
                    try {
                        BigDecimal dec = new BigDecimal(ExcelUtil.getCell(row,32).getNumericCellValue());
                        pActualWeight =String.valueOf(dec.setScale(3, BigDecimal.ROUND_HALF_UP).doubleValue());
                    }catch (Exception e1){
                        pActualWeight="";
                    }
                }
                String pFastener=ExcelUtil.getCell(row,33).getStringCellValue();

                String pFastenerStandard=ExcelUtil.getCell(row,34).getStringCellValue();
                String pFastenerLevel=ExcelUtil.getCell(row,35).getStringCellValue();
                if(pFastenerLevel.contains(".") && pFastenerLevel.length()>4){
                    pFastenerLevel = pFastenerLevel.substring(0,3);
                }
                String pTorque=ExcelUtil.getCell(row,36).getStringCellValue();
                String pDutyEngineer=ExcelUtil.getCell(row,37).getStringCellValue();
                String pSupply=ExcelUtil.getCell(row,38).getStringCellValue();

                String pSupplyCode=ExcelUtil.getCell(row,39).getStringCellValue();
                String pBuyEngineer=ExcelUtil.getCell(row,40).getStringCellValue();
                String pRemark=ExcelUtil.getCell(row,41).getStringCellValue();
                String pBomLinePartClass=ExcelUtil.getCell(row,42).getStringCellValue();

                String pBomLinePartResource= ExcelUtil.getCell(row,43).getStringCellValue();
                String pInOutSideFlag=ExcelUtil.getCell(row,44).getStringCellValue();


                record.setIs3cpart(BOMTransConstants.constantStringToInteger(p3cpartFlag));
                record.setActualWeight(pActualWeight);
                record.setPartClass(pBomLinePartClass);
                record.setPartEnName(pBomLinePartEnName);
                record.setPartName(pBomLinePartName);
                record.setPartResource(pBomLinePartResource);
                record.setBuyEngineer(pBuyEngineer);
                record.setPartOfWhichDept(pBomOfWhichDept);
                record.setBwgBoxPart(pBwgBoxPart);
                record.setCreateName(UserInfo.getUser().getUsername());
                record.setDataVersion(pDataVersion);
                record.setDensity(pDensity);
                record.setDevelopType(pDevelopType);
                record.setDutyEngineer(pDutyEngineer);
                record.setFastener(pFastener);
                record.setFastenerLevel(pFastenerLevel);
                record.setFastenerStandard(pFastenerStandard);
                record.setFeatureWeight(pFeatureWeight);

                record.setImportance(pImportance);
                record.setInOutSideFlag(BOMTransConstants.constantStringToInteger(pInOutSideFlag));

                record.setManuProcess(pManuProcess);
                record.setMaterial1(pMaterial1);
                record.setMaterial2(pMaterial2);
                record.setMaterial3(pMaterial3);
                record.setMaterialHigh(pMaterialHigh);
                record.setMaterialStandard(pMaterialStandard);
                record.setPictureNo(pPictureNo);
                record.setPictureSheet(pPictureSheet);
                record.setRegulationCode(pRegulationCode);
                record.setRegulationFlag(BOMTransConstants.constantStringToInteger(pRegulationFlag));

                record.setRemark(pRemark);
                record.setSupply(pSupply);
                record.setSupplyCode(pSupplyCode);
                record.setSurfaceTreat(pSurfaceTreat);
                record.setSymmetry(pSymmetry);
                record.setTargetWeight(pTargetWeight);
                record.setTextureColorNum(pTextureColorNum);
                record.setTorque(pTorque);
                record.setUnit(pUnit);
                record.setUpdateName(UserInfo.getUser().getUsername());
                record.setProjectId(projectId);
                record.setPartId(lineId);
                eplRecords.add(record);
            }
            if(ListUtils.isNotEmpty(eplRecords)){
                hzEPLDAO.insertList(new ArrayList<>(eplRecords));
            }


            this.lineIndexFirst = 10;
            this.lineIndexLast = 10;

            List<List<HzImportEbomRecord>> list1 = new ArrayList<>();

            HzMainBom hzMainBom = hzMainBomDao.selectByProjectId(projectId);
            Double maxSortNum =hzEbomRecordDAO.findMaxBomOrderNum(projectId);
            if(maxSortNum != null){
                maxOrderNum = maxSortNum.intValue();
            }
//            for(int numSheet = 0; numSheet < workbook.getNumberOfSheets(); numSheet++ ){
//                Sheet sheet = workbook.getSheetAt(numSheet);
//                String sheetName = workbook.getSheetName(numSheet);//sheet 表名 就一个sheet的话 没什么卵用
                int i = 1;
                while (i<=sheet.getLastRowNum()){
                    List<HzImportEbomRecord> list = new ArrayList<>();
                    int high = 0;
                    boolean add2Y = true;
                    for(int rowNum = i; rowNum <= sheet.getLastRowNum(); rowNum++){
                        Row sheetRow = sheet.getRow(rowNum);
                        if(sheetRow != null){
                            HzImportEbomRecord record = excelObjectToRecord(sheet,rowNum,projectId);
                            String level = ExcelUtil.getCell(sheet.getRow(rowNum),3).getStringCellValue();
                            record.setLevel(level);
                            record.setHigh(high);
                            record.setBomDigifaxId(hzMainBom.getPuid());
                            if("2Y".equals(level)){
                                record.setIs2Y(1);
                            }else {
                                record.setIs2Y(0);
                            }
                            if(level.endsWith("Y")){
                                record.setIsHas(1);
//                                record.setIsPart(0);
                            }else {
                                record.setIsHas(0);
//                                record.setIsPart(1);
                            }
                            if(level.equals("2Y")){
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

//            }

            List<HzImportEbomRecord> records = analysisFinalExcelContent(list1,projectId);
            int j = 0;
            String s ="";
            String ss = "";
            for(int m=0;m<records.size();m++){//设置子层的父id为当前的id
                j = m;
                if(records.get(m).getLevel().endsWith("Y")){//2Y - 2/3Y
                    int level = Integer.valueOf(records.get(m).getLevel().replace("Y",""));
                    int lev = level+1;
                    s = String.valueOf(level);
                    ss = String.valueOf(lev);
                    for(int k =j;k<records.size();k++){
                        if(s.equals(records.get(k).getLevel()) || (ss+"Y").equals(records.get(k).getLevel())){
                            records.get(m).setPuid(records.get(k).getParentUid());
                            break;
                        }
                    }
                }
            }

            List<HzPbomLineRecord> hzPbomLineRecords = new ArrayList<>();
            List<String> carParts = hzMbomService.loadingCarPartType();
            if(ListUtils.isNotEmpty(records)){
                records.forEach(record -> {
                    if(carParts.contains(record.getpBomLinePartResource())){
                        HzPbomLineRecord pbomLineRecord = HzPbomRecordFactory.ImportEbomRecordToPbomRecord(record);
                        if(Integer.valueOf(1).equals(pbomLineRecord.getIsHas())){
                            boolean findChildren = false;
                            for(HzImportEbomRecord r :records){
                                if(carParts.contains(r.getpBomLinePartResource())){
                                    if(pbomLineRecord.geteBomPuid().equals(r.getParentUid())){
                                        findChildren = true;
                                        break;
                                    }
                                }
                            }
                            if(!findChildren){
                                pbomLineRecord.setIsHas(0);
                            }
                        }
                        hzPbomLineRecords.add(pbomLineRecord);
                    }
                });


                configTransactionTemplate.execute(new TransactionCallback<Void>() {

                      @Override
                      public Void doInTransaction(TransactionStatus status) {
                          try {
                          hzEbomRecordDAO.importList(records);
                          if(ListUtils.isNotEmpty(hzPbomLineRecords)){
                              hzPbomRecordDAO.insertList(hzPbomLineRecords);
                          }
                          }catch (Exception e){
                              e.printStackTrace();
                              throw new HzBomException("BOM数据导入失败!",e);
                          }
                          return null;
                      }
                  });
            }

            ExcelUtil.deleteFile();
        }catch (Exception e){
            e.printStackTrace();
            return WriteResultRespDTO.getFailResult();
        }
        return WriteResultRespDTO.getSuccessResult();
    }




    /**
     * 将excel表格列转化为EBOM数据
     * @param sheet
     * @param rowNum
     * @return
     */
    private HzImportEbomRecord excelObjectToRecord(Sheet sheet,int rowNum,String projectId){
        Row row = sheet.getRow(rowNum);
        String no = ExcelUtil.getCell(row,0).getStringCellValue();
        String lineId=ExcelUtil.getCell(row,1).getStringCellValue();
        HzEPLQuery query = new HzEPLQuery();
        query.setPartId(lineId);
        query.setProjectId(projectId);
        HzEPLRecord eplRecord = hzEPLDAO.getEPLRecordById(query);
        if(eplRecord == null){
            throw new HzBomException("EPL中找不到对应数据！");
        }
        String pBomLinePartName=eplRecord.getPartName();
        String pBomOfWhichDept=eplRecord.getPartOfWhichDept();


        String pBomLinePartEnName=eplRecord.getPartEnName();
//        String pUnit=ExcelUtil.getCell(row,10).getStringCellValue();
//        String pPictureNo=ExcelUtil.getCell(row,11).getStringCellValue();
//        String pPictureSheet=ExcelUtil.getCell(row,12).getStringCellValue();
//        String pMaterialHigh=ExcelUtil.getCell(row,13).getStringCellValue();

//        String pMaterial1=ExcelUtil.getCell(row,14).getStringCellValue();
//        String pMaterial2=ExcelUtil.getCell(row,15).getStringCellValue();
//        String pMaterial3=ExcelUtil.getCell(row,16).getStringCellValue();
//        String pDensity=ExcelUtil.getCell(row,17).getStringCellValue();
//        String pMaterialStandard=ExcelUtil.getCell(row,18).getStringCellValue();

//        String pSurfaceTreat=ExcelUtil.getCell(row,19).getStringCellValue();
//        String pTextureColorNum=ExcelUtil.getCell(row,20).getStringCellValue();
//        String pManuProcess=ExcelUtil.getCell(row,21).getStringCellValue();
//        String pSymmetry=ExcelUtil.getCell(row,22).getStringCellValue();
//        String pImportance=ExcelUtil.getCell(row,23).getStringCellValue();

//        String pRegulationFlag=ExcelUtil.getCell(row,24).getStringCellValue();
//        String p3cpartFlag=ExcelUtil.getCell(row,25).getStringCellValue();
//        String pRegulationCode=ExcelUtil.getCell(row,26).getStringCellValue();
//        String pBwgBoxPart=ExcelUtil.getCell(row,27).getStringCellValue();
//        String pDevelopType=ExcelUtil.getCell(row,28).getStringCellValue();

//        String pDataVersion=ExcelUtil.getCell(row,29).getStringCellValue();
//
//        String pTargetWeight ="";
//        String pFeatureWeight ="";
//        String pActualWeight="";
//
//        try {
//            pTargetWeight = ExcelUtil.getCell(row,30).getStringCellValue();
//            if(pTargetWeight == null || pTargetWeight ==""){
//                pTargetWeight = "";
//            }else {
//                BigDecimal bigDecimal = new BigDecimal(pTargetWeight);
//                pTargetWeight =String.valueOf( bigDecimal.setScale(3, BigDecimal.ROUND_HALF_UP));
//            }
//        }catch (Exception e){
//            try {
//                BigDecimal dec = new BigDecimal(ExcelUtil.getCell(row,30).getNumericCellValue());
//                pTargetWeight =String.valueOf(dec.setScale(3, BigDecimal.ROUND_HALF_UP).doubleValue());
//            }catch (Exception e1){
//                pTargetWeight ="";
//            }
//
//        }
//
//        try {
//            pFeatureWeight = ExcelUtil.getCell(row,31).getStringCellValue();
//            if(pFeatureWeight == null || pFeatureWeight == ""){
//                pFeatureWeight ="";
//            }else {
//                BigDecimal bigDecimal = new BigDecimal(pFeatureWeight);
//                pFeatureWeight =String.valueOf( bigDecimal.setScale(3, BigDecimal.ROUND_HALF_UP));
//            }
//        }catch (Exception e){
//            try {
//                BigDecimal dec = new BigDecimal(ExcelUtil.getCell(row,31).getNumericCellValue());
//                pFeatureWeight =String.valueOf(dec.setScale(3, BigDecimal.ROUND_HALF_UP).doubleValue());
//            }catch (Exception e1){
//                pFeatureWeight="";
//            }
//
//        }
//
//        try {
//            pActualWeight = ExcelUtil.getCell(row,32).getStringCellValue();
//            if(pActualWeight == null || pActualWeight == ""){
//                pActualWeight = "";
//            }else {
//                BigDecimal bigDecimal = new BigDecimal(pActualWeight);
//                pActualWeight =String.valueOf( bigDecimal.setScale(3, BigDecimal.ROUND_HALF_UP));
//            }
//        }catch (Exception e){
//            try {
//                BigDecimal dec = new BigDecimal(ExcelUtil.getCell(row,32).getNumericCellValue());
//                pActualWeight =String.valueOf(dec.setScale(3, BigDecimal.ROUND_HALF_UP).doubleValue());
//            }catch (Exception e1){
//                pActualWeight="";
//            }
//        }
//        String pFastener=ExcelUtil.getCell(row,33).getStringCellValue();
//
//        String pFastenerStandard=ExcelUtil.getCell(row,34).getStringCellValue();
//        String pFastenerLevel=ExcelUtil.getCell(row,35).getStringCellValue();
//        if(pFastenerLevel.contains(".") && pFastenerLevel.length()>4){
//            pFastenerLevel = pFastenerLevel.substring(0,3);
//        }
//        String pTorque=ExcelUtil.getCell(row,36).getStringCellValue();
//        String pDutyEngineer=ExcelUtil.getCell(row,37).getStringCellValue();
//        String pSupply=ExcelUtil.getCell(row,38).getStringCellValue();
//
//        String pSupplyCode=ExcelUtil.getCell(row,39).getStringCellValue();
//        String pBuyEngineer=ExcelUtil.getCell(row,40).getStringCellValue();
//        String pRemark=ExcelUtil.getCell(row,41).getStringCellValue();
        String pBomLinePartClass=eplRecord.getPartClass();


        String pBomLinePartResource= eplRecord.getPartResource();
        Long eplId = eplRecord.getId();

//
//        String pInOutSideFlag=ExcelUtil.getCell(row,44).getStringCellValue();
        String pUpc=ExcelUtil.getCell(row,45).getStringCellValue();
        String fna=ExcelUtil.getCell(row,46).getStringCellValue();
        String pFnaDesc=ExcelUtil.getCell(row,47).getStringCellValue();

        String number="";

        try {
            number = ExcelUtil.getCell(row,48).getStringCellValue();;
            if(StringUtil.isEmpty(number)){
                number = "";
            }
        }catch (Exception e){
            try {
                BigDecimal dec = new BigDecimal(ExcelUtil.getCell(row,48).getNumericCellValue());
                number =String.valueOf(dec.setScale(3, BigDecimal.ROUND_HALF_UP).doubleValue());
            }catch (Exception e1){
                number="";
            }
        }


        String sparePart=ExcelUtil.getCell(row,49).getStringCellValue();
        String sparePartNum=ExcelUtil.getCell(row,50).getStringCellValue();
        String colorPart=ExcelUtil.getCell(row,51).getStringCellValue();



        HzImportEbomRecord record = new HzImportEbomRecord();
        record.setNo(Integer.valueOf(no));
        record.setLineId(lineId);
        if(StringUtils.isNotBlank(number)){
            record.setNumber(number);
        }
//        if("Y".equals(p3cpartFlag)){
//            record.setP3cpartFlag(1);
//        }else if("N".equals(p3cpartFlag)) {
//            record.setP3cpartFlag(0);
//        }else {
//            record.setP3cpartFlag(null);
//        }
//        record.setpActualWeight(pActualWeight);
        record.setpBomLinePartClass(pBomLinePartClass);
        record.setpBomLinePartEnName(pBomLinePartEnName);
        record.setpBomLinePartName(pBomLinePartName);
        record.setpBomLinePartResource(pBomLinePartResource);
//        record.setpBuyEngineer(pBuyEngineer);
        record.setpBomOfWhichDept(pBomOfWhichDept);
//        record.setpBwgBoxPart(pBwgBoxPart);
        record.setpCreateName(UserInfo.getUser().getUsername());
//        record.setpDataVersion(pDataVersion);
//        record.setpDensity(pDensity);
//        record.setpDevelopType(pDevelopType);
//        record.setpDutyEngineer(pDutyEngineer);
//        record.setpFastener(pFastener);
//        record.setpFastenerLevel(pFastenerLevel);
//        record.setpFastenerStandard(pFastenerStandard);
//        record.setpFeatureWeight(pFeatureWeight);
        record.setpFnaDesc(pFnaDesc);
        record.setpFnaInfo(fna);
//        record.setpImportance(pImportance);
//        if("内饰件".equals(pInOutSideFlag)){
//            record.setpInOutSideFlag(1);
//        }else if("外饰件".equals(pInOutSideFlag)){
//            record.setpInOutSideFlag(0);
//        }else {
//            record.setpInOutSideFlag(null);
//        }
//        record.setpManuProcess(pManuProcess);
//        record.setpMaterial1(pMaterial1);
//        record.setpMaterial2(pMaterial2);
//        record.setpMaterial3(pMaterial3);
//        record.setpMaterialHigh(pMaterialHigh);
//        record.setpMaterialStandard(pMaterialStandard);
//        record.setpPictureNo(pPictureNo);
//        record.setpPictureSheet(pPictureSheet);
//        record.setpRegulationCode(pRegulationCode);
//        if(pRegulationFlag!=null && pRegulationFlag!=""){
//            record.setpRegulationFlag(pRegulationFlag == "Y"?1:0);
//        }else {
//            record.setpRegulationFlag(null);
//        }
//        record.setpRemark(pRemark);
//        record.setpSupply(pSupply);
//        record.setpSupplyCode(pSupplyCode);
//        record.setpSurfaceTreat(pSurfaceTreat);
//        record.setpSymmetry(pSymmetry);
//        record.setpTargetWeight(pTargetWeight);
//        record.setpTextureColorNum(pTextureColorNum);
//        record.setpTorque(pTorque);
//        record.setpUnit(pUnit);
        record.setpUpc(pUpc);
        record.setpUpdateName(UserInfo.getUser().getUsername());
        record.setSparePart(sparePart);
        record.setSparePartNum(sparePartNum);
        record.setColorPart(BOMTransConstants.constantStringToInteger(colorPart));
        record.setEplId(eplId);
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
           //这里并没有产生多余的数据，层级不带Y也产生puid是因为下面解析要用到
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
//
//        int lineIndexFirst = 10;//默认2Y层lineIndex 第一位
//        int lineIndexLast = 10;//默认2Y层lineIndex 最后一位
        Map<String,String> lineIndexMap = new LinkedHashMap<>();
        for(int i = 1;i<records.size();i++){//产生lineIndex
            HzImportEbomRecord preDTO = records.get(i-1);
            HzImportEbomRecord currentDTO = records.get(i);
            if(preDTO.getHigh() == 0){ // 2Y
                HzEPLManageRecord record = hzEbomRecordDAO.getMaxLineIndexFirstNum(projectId);
                if(record == null){//之前未导入数据
                    lineIndexMap.put(preDTO.getLevel()+"-"+0,lineIndexFirst+"."+lineIndexLast);
                    lineIndexFirst +=10;
                    lineIndexLast +=10;
                }else {
                    int lineIndexFirstNum = Integer.valueOf(record.getLineIndex().split("\\.")[0]);
                    int lineIndexLastNum = Integer.valueOf(record.getLineIndex().split("\\.")[1]);
                    lineIndexFirstNum = lineIndexFirstNum+lineIndexFirst;
                    lineIndexLastNum = lineIndexLastNum + lineIndexLast;
                    lineIndexMap.put(preDTO.getLevel()+"-"+0,lineIndexFirstNum+"."+lineIndexLastNum);
                    lineIndexFirst+=10;
                    lineIndexLast +=10;
                }
            }
            String preLevel = preDTO.getLevel();
            String level = currentDTO.getLevel();
            int high = currentDTO.getHigh();
            String currentKey = level+"-"+high;
            String preKey = preDTO.getLevel()+"-"+preDTO.getHigh();
            String currentIndex = "";
            int preLevelNum = Integer.valueOf(preLevel.replace("Y",""));
            int currentLevelNum = Integer.valueOf(level.replace("Y",""));

            if(currentLevelNum >= preLevelNum){
                for(String key:lineIndexMap.keySet()){
                    if(key.equals(preKey)){
                        String value = lineIndexMap.get(preKey);
                        if(preLevel.endsWith("Y")){
                            StringBuffer s = new StringBuffer(lineIndexMap.get(preKey));
                            currentIndex = s.append(".10").toString();
                            lineIndexMap.put(currentKey,currentIndex);
                        }else if(preLevelNum == currentLevelNum && level.endsWith("Y")){
                            String[] values = value.split("\\.");
                            StringBuffer stringBuffer = new StringBuffer();
                            for(int j = 0;j<currentLevelNum;j++){
                                if(j!=currentLevelNum-1)
                                    stringBuffer.append(values[j]+".");
                                else
                                    stringBuffer.append(values[j]);
                            }
                            value = stringBuffer.toString();
                            int lastNum = Integer.valueOf(value.split("\\.")[value.split("\\.").length-1]);
                            int charAtNum = value.lastIndexOf(".");
                            String preIndexDoNotContainLastNum = value.substring(0,charAtNum);
                            lastNum+=10;
                            currentIndex = preIndexDoNotContainLastNum+"."+lastNum;
                            lineIndexMap.put(currentKey,currentIndex);
                        } else {
                            int lastNum = Integer.valueOf(value.split("\\.")[value.split("\\.").length-1]);
                            int charAtNum = value.lastIndexOf(".");
                            String preIndexDoNotContainLastNum = value.substring(0,charAtNum);
                            lastNum+=10;
                            currentIndex = preIndexDoNotContainLastNum+"."+lastNum;
                            lineIndexMap.put(currentKey,currentIndex);
                        }
                        break;
                    }
                }
            }
            else {
                String value = lineIndexMap.get(preKey);
                String[] values = value.split("\\.");
                int count = currentLevelNum;
                if(!level.endsWith("Y")){
                    count = currentLevelNum+1;
                }
                StringBuffer stringBuffer = new StringBuffer();
                for(int j = 0;j<count;j++){
                    if(j!=count-1)
                        stringBuffer.append(values[j]+".");
                    else
                        stringBuffer.append(values[j]);
                }

                value = stringBuffer.toString();
                int lastNum = Integer.valueOf(value.split("\\.")[value.split("\\.").length-1]);
                int charAtNum = value.lastIndexOf(".");
                String preIndexDoNotContainLastNum = value.substring(0,charAtNum);
                lastNum+=10;
                currentIndex = preIndexDoNotContainLastNum+"."+lastNum;
                lineIndexMap.put(currentKey,currentIndex);
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
            int currentLevelNum = Integer.valueOf(level.replace("Y",""));
            //找父id
            if(high!=0){
                if(level.endsWith("Y")){
                    currentLevelNum = currentLevelNum-1;
                }
                String parentKey = currentLevelNum+"Y-";
                for(Map.Entry<String,String> entry:mapList.entrySet()){
                    if(entry.getKey().indexOf(parentKey)>-1){
                        parentId = entry.getValue();
                    }
                    if(entry.getKey().equals(currentKey)){
                        break;
                    }
                }
            }
            record.setLineIndex(lineIndexMap.get(currentKey));
            record.setParentUid(parentId);
            if(!level.endsWith("Y")){
                record.setPuid(UUID.randomUUID().toString());
            }
            double d = 0;
            if(maxOrderNum !=null){
                d = maxOrderNum+record.getNo()*100;
            }else {
                d = record.getNo()*100;
            }
            record.setSortNum(String.valueOf(d));
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


    /**
     * 解析文件内容 返回不符合规定的结果
     * @param sheet
     * @return
     */
    private String errorLogInfo(Sheet sheet,String projectId){
        StringBuffer stringBuffer = new StringBuffer("文件解析失败!</br>");
//        for(int numSheet = 0; numSheet < workbook.getNumberOfSheets(); numSheet++ ){
//            Sheet sheet = workbook.getSheetAt(numSheet);
            for(int rowNum = 1; rowNum <= sheet.getLastRowNum(); rowNum++){
                String lineId=ExcelUtil.getCell(sheet.getRow(rowNum),1).getStringCellValue();
                String level = ExcelUtil.getCell(sheet.getRow(rowNum),3).getStringCellValue();
                if(StringUtil.isEmpty(lineId)){
                    stringBuffer.append("第"+(rowNum)+"行的零件号:"+lineId+"不能为<strong>空</strong></br>") ;
                    this.errorCount++;
                }
//                HzEPLQuery query = new HzEPLQuery();
//                query.setProjectId(projectId);
//                query.setPartId(lineId);
//                HzEPLRecord record = hzEPLDAO.getEPLRecordById(query);
//                if(record == null || !Integer.valueOf(1).equals(record.getStatus())){
//                    stringBuffer.append("第"+(rowNum)+"行的零件号:"+"<strong style='color: red'>"+lineId+"</strong>在EPL中不存在<strong style='color: red'>已生效</strong>记录!</br>") ;
//                    this.errorCount++;
//                }
                if(StringUtil.isEmpty(level)){
                    stringBuffer.append("第"+(rowNum)+"行的层级不能为<strong>空</strong></br>") ;
                    this.errorCount++;
                }else if(level.endsWith("y")){
                    stringBuffer.append("第"+(rowNum)+"行的层级填写不正确，层级尾缀应该填写为:<strong>Y</strong></br>") ;
                    this.errorCount++;
                }
            }
//        }
        if(this.errorCount == 0){
//            for(int numSheet = 0; numSheet < workbook.getNumberOfSheets(); numSheet++ ){
//                Sheet sheet = workbook.getSheetAt(numSheet);
                for(int rowNum = 1; rowNum <= sheet.getLastRowNum(); rowNum++){
                    String level = ExcelUtil.getCell(sheet.getRow(rowNum),3).getStringCellValue();
                    int lev = Integer.valueOf(level.replace("Y",""))+1;
                    if(level.endsWith("Y") && (rowNum)!=sheet.getLastRowNum()){
                        String nextLevel = ExcelUtil.getCell(sheet.getRow(rowNum+1),3).getStringCellValue();
                        if(!nextLevel.equals(lev+"Y") && !nextLevel.equals(String.valueOf(lev-1))){
                            stringBuffer.append("第"+(rowNum+1)+"行的层级填写不正确，" +
                                    ""+level+"下的层级应该为:<strong>"+lev+"Y</strong>"+"或者<strong>"+(lev-1)+"</strong>" +
                                    ";而实际为:"+nextLevel+"</br>");
                            this.errorCount++;
                            continue;
                        }
                    }

                    for(int i = rowNum+1;i<sheet.getLastRowNum();i++){
                        String nextLevel = ExcelUtil.getCell(sheet.getRow(i),3).getStringCellValue();
                        String nextLev = level.replace("Y","");
                        int nextL = Integer.valueOf(nextLev);
                        if(nextLevel.endsWith("Y")){
                            break;
                        }else if(!nextLevel.equals(nextLev) && Integer.valueOf(nextLevel)>nextL){
                            String s = "第"+(i)+"行的层级填写不正确，" +
                                    "应该为:<strong>"+(lev)+"Y</strong>"+"或者<strong>"+(lev-1)+"</strong>" +
                                    ";而实际为:"+nextLevel+"</br>";
                            if(!stringBuffer.toString().contains(s)){
                                stringBuffer.append(s);
                            }
                            this.errorCount++;
                            break;
                        }
                    }

                    if(rowNum == sheet.getLastRowNum()){
                        if(level.endsWith("Y")){
                            stringBuffer.append("最后一行的层级不能带Y,因为找不到他的子层!</br>");
                            this.errorCount++;
                        }
                    }
                }

//            }
        }

        return stringBuffer.toString();
    }





    /**下面的私有方法为:导入单车用量数据**/


    /**
     * 导入单车用量时，检查文件内容
     * @param sheet
     * @return 不符合规定的解析结果
     */
    private String singleVehiclesDosageExcelErrorMsg(List<HzCfg0ModelRecord> hzCfg0ModelRecords,Sheet sheet,String projectId){
        StringBuffer stringBuffer = new StringBuffer("文件解析失败!</br>");
        Integer num = hzEbomRecordDAO.findEBomTotalCount(projectId);
        if(num == null || num<=0){
            stringBuffer.append("未找到相关BOM信息，导入单车用量信息前，应先存在相关BOM记录!</br>");
            this.errorCount++;
        }
        if(ListUtils.isEmpty(hzCfg0ModelRecords)){
            stringBuffer.append("未找到相关版型信息，导入单车用量信息前，应先在全配置表中添加当前项目的版型信息!</br>");
            this.errorCount++;
        }
        for(int rowNum = 1; rowNum <= sheet.getLastRowNum(); rowNum++){
            String lineId=ExcelUtil.getCell(sheet.getRow(rowNum),1).getStringCellValue();
            if(StringUtil.isEmpty(lineId)){
                stringBuffer.append("第"+(rowNum)+"行的零件名称不能为<strong>空</strong></br>") ;
                this.errorCount++;
            }
        }




        if(this.errorCount == 0){
            Row title = sheet.getRow(0);
            short lastCellNum = title.getLastCellNum();
            List<String> titleName = new ArrayList<>();
            if(ListUtils.isNotEmpty(hzCfg0ModelRecords)){
                if(lastCellNum > 52){
                    for(int i = 53;i<lastCellNum;i++){//第50列以后为要导入的版型信息
                        boolean find = false;
                        String s = ExcelUtil.getCell(title,i).getStringCellValue();
                        if(s.contains(BOMTransConstants.VEH_DOSAGE_CN)){
                            s=s.replace(BOMTransConstants.VEH_DOSAGE_CN,BOMTransConstants.EMPTY);
                        }
                        if(s.contains(BOMTransConstants.VEH_DOSAGE_EN)){
                            s=s.replace(BOMTransConstants.VEH_DOSAGE_EN,BOMTransConstants.EMPTY);
                        }
                        for(HzCfg0ModelRecord record :hzCfg0ModelRecords){
                            if(s.equals(record.getObjectName())){
                                find = true;
                                break;
                            }
                        }
                        if(!find){
                            stringBuffer.append("当前导入文件中的<strong>"+s+"</strong>版型不存在与当前项目中!</br>");
                            this.errorCount++;
                        }else {
                            titleName.add(s);
                        }

                    }
                }
                for(HzCfg0ModelRecord record :hzCfg0ModelRecords){
                    boolean find = false;
                    if(titleName.contains(record.getObjectName())){
                        find = true;
                    }
                    if(!find){
                        stringBuffer.append("当前项目中的<strong>"+record.getObjectName()+"</strong>版型信息未在导入文件中找到!</br>");
                        this.errorCount++;
                    }
                }

            }

            //导入数据信息符合要求，进行数据对比
//            if(sheet.getLastRowNum()!=list.size()){
//                stringBuffer.append("当前导入文件的BOM数据量与当前项目中的BOM数据量不匹配!+</br>") ;
//                this.errorCount++;
//                return stringBuffer.toString();
//            }

//            for(int i = 0;i<list.size();i++){
//                Row sheetRow = sheet.getRow(i+1);
//                if(sheetRow != null){
//                    String lineId = ExcelUtil.getCell(sheet.getRow(i+1),1).getStringCellValue();
//                    if(!list.get(i).getLineID().equals(lineId)){
//                        stringBuffer.append("第"+(i+1)+"行的零件号与当前项目的第"+(i+1)+"行的零件号不匹配!") ;
//                        this.errorCount++;
//                    }
//                }
//            }

        }
        return stringBuffer.toString();
    }


    /**
     * 导入单车用量
     * @param
     * @return
     */
    private WriteResultRespDTO importBomSingleVehDosageToDB(Sheet sheet,String projectId){
        try {
            HzMainBom hzMainBom = hzMainBomDao.selectByProjectId(projectId);
            this.errorCount = 0;
            WriteResultRespDTO respDTO = new WriteResultRespDTO();
//            List<HzBomLineRecord> bomLineRecords = hzBomLineRecordDao.getAllBomLineRecordByProjectId(projectId);
            List<HzCfg0ModelRecord> hzCfg0ModelRecords = hzCfg0ModelServiceImpl.doSelectByProjectPuid(projectId);
            Set<HzCfg0ModelRecord> set = new HashSet<>(hzCfg0ModelRecords);
            List<HzCfg0ModelRecord> records = new ArrayList<>(set);
            String errorMsg = singleVehiclesDosageExcelErrorMsg(records,sheet,projectId);
            if(this.errorCount!=0){
                return WriteResultRespDTO.failResultRespDTO(errorMsg);
            }
            //存表头信息 和对应的列数字
            Map<Integer,String> titleMap = new HashMap<>();
            Row title = sheet.getRow(0);
            for(int i = 50;i<title.getLastCellNum();i++){
                String s = title.getCell(i).getStringCellValue();
                if(s.contains(BOMTransConstants.VEH_DOSAGE_CN)){
                    s=s.replace(BOMTransConstants.VEH_DOSAGE_CN,BOMTransConstants.EMPTY);
                }
                if(s.contains(BOMTransConstants.VEH_DOSAGE_EN)){
                    s=s.replace(BOMTransConstants.VEH_DOSAGE_EN,BOMTransConstants.EMPTY);
                }
                titleMap.put(i,s);
            }

            List<HzEPLManageRecord> singleVehDosageRecords = new ArrayList<>();

            for(int i =1;i<=sheet.getLastRowNum();i++){
                Row sheetRow = sheet.getRow(i);//行
                if(sheetRow != null){
                    HzImportEbomRecord importEbomRecord = excelObjectToRecord(sheet,i,projectId);
                    HzEPLManageRecord record = HzEbomRecordFactory.importEbomRecordToBomLineRecord(importEbomRecord);
                    StringBuilder builder = new StringBuilder();
                    for(int j =50;j<title.getLastCellNum();j++){
                        String key = titleMap.get(j);
                        if(StringUtils.isEmpty(key)){
                            continue;
                        }
                        String vehDosage = ExcelUtil.getCell(sheetRow,j).getStringCellValue();
                        if(StringUtils.isNotEmpty(vehDosage)){
                            builder.append(key+"#"+vehDosage+",");
                        }else {
                            builder.append(key+"#-"+",");
                        }
                    }
                    record.setVehNum(builder.toString());
                    record.setBomDigifaxId(hzMainBom.getPuid());
                    record.setTableName(ChangeTableNameEnum.HZ_EBOM.getTableName());
                    singleVehDosageRecords.add(record);
                }
            }

            hzEbomRecordDAO.updateListByEplId(singleVehDosageRecords);
            ExcelUtil.deleteFile();
            return WriteResultRespDTO.getSuccessResult();
        }catch (Exception e){
            e.printStackTrace();
            return WriteResultRespDTO.getFailResult();
        }
    }

}
