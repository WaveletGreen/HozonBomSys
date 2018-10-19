package com.connor.hozon.bom.resources.service.file.impl;

import com.connor.hozon.bom.bomSystem.dao.bom.HzBomMainRecordDao;
import com.connor.hozon.bom.common.util.user.UserInfo;
import com.connor.hozon.bom.resources.domain.dto.response.OperateResultMessageRespDTO;
import com.connor.hozon.bom.resources.mybatis.bom.HzPbomRecordDAO;
import com.connor.hozon.bom.resources.service.file.FileUploadUpdataService;
import com.connor.hozon.bom.resources.util.ExcelUtil;
import com.connor.hozon.bom.resources.util.PrivilegeUtil;
import com.connor.hozon.bom.resources.util.StringUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import sql.pojo.bom.HZBomMainRecord;
import sql.pojo.bom.HzImportEbomRecord;
import sql.pojo.bom.HzPbomLineRecord;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service("FileUploadUpdataService")
public class FileUploadUpdataServiceImpl implements FileUploadUpdataService {

    @Autowired
    private HzPbomRecordDAO hzPbomRecordDAO;

    @Autowired
    private HzBomMainRecordDao hzBomMainRecordDao;

    private int errorCount = 0;

    //private int lineIndexFirst =10;

    //private Integer maxOrderNum = null;

    @Override
    public OperateResultMessageRespDTO UploadPbomToDB(MultipartFile file, String projectId) {
        //FileUploadServiceImpl fileUploadService = new FileUploadServiceImpl();
        //判断权限
        boolean b = PrivilegeUtil.writePrivilege();
        if(!b){
            return OperateResultMessageRespDTO.getFailPrivilege();
        }
        //判断文件格式(以.XLS或.XLSX结尾)
        boolean preCheck = ExcelUtil.preReadCheck(file.getOriginalFilename());
        if(!preCheck){
            return OperateResultMessageRespDTO.fileIsNotExcel();
        }
        //判断文件大小(0-10M)
        boolean suitSize = ExcelUtil.checkFileSize(file.getSize());
        if(!suitSize){
            return OperateResultMessageRespDTO.fileSizeOverFlow();
        }
        ExcelUtil.preReadCheck(file.getOriginalFilename());
        //上传文件到服务器
        String fileUrl = null;
        try {
            fileUrl = ExcelUtil.uploadFileToLocation(file.getBytes(),file.getOriginalFilename());
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
            List<HzPbomLineRecord> hzPbomLineRecords = new ArrayList<>();
            int a = workbook.getNumberOfSheets();
            for(int numSheet = 0; numSheet < workbook.getNumberOfSheets(); numSheet++ ){
                Sheet sheet = workbook.getSheetAt(numSheet);
                String sheetName = workbook.getSheetName(numSheet);//sheet 表名 就一个sheet的话 没什么卵用
                int i = 1;
                int aa = sheet.getLastRowNum();
                while (i<=sheet.getLastRowNum()){
                    //List<HzImportEbomRecord> list = new ArrayList<>();
                    for(int rowNum = i; rowNum <= sheet.getLastRowNum(); rowNum++){
                        Row sheetRow = sheet.getRow(rowNum);
                        if(sheetRow != null){
                            //HzPbomLineRecord record = excelObjectToRecord(sheet,rowNum);
                            HzPbomLineRecord record = new HzPbomLineRecord();
                            //自制/采购、焊接/装配、采购单元、车间1、车间2、生产线、模具类别、外委件、工位
                            record.setResource(ExcelUtil.getCell(sheet.getRow(rowNum),12).getStringCellValue());
                            //0是,1不是,2不明确
                            String type = ExcelUtil.getCell(sheet.getRow(rowNum),13).getStringCellValue();
                            String buyUnit = ExcelUtil.getCell(sheet.getRow(rowNum),14).getStringCellValue();
                            if("Y".equals(type)){
                                record.setType(1);
                            }else if("N".equals(type)){
                                record.setType(0);
                            }else{
                                record.setType(2);
                            }
                            if("Y".equals(buyUnit)){
                                record.setBuyUnit(1);
                            }else if("N".equals(buyUnit)){
                                record.setBuyUnit(0);
                            }else{
                                record.setBuyUnit(2);
                            }

                            record.setWorkShop1(ExcelUtil.getCell(sheet.getRow(rowNum),15).getStringCellValue());
                            record.setWorkShop2(ExcelUtil.getCell(sheet.getRow(rowNum),16).getStringCellValue());
                            record.setProductLine(ExcelUtil.getCell(sheet.getRow(rowNum),17).getStringCellValue());
                            record.setMouldType(ExcelUtil.getCell(sheet.getRow(rowNum),18).getStringCellValue());
                            record.setOuterPart(ExcelUtil.getCell(sheet.getRow(rowNum),19).getStringCellValue());
                            record.setStation(ExcelUtil.getCell(sheet.getRow(rowNum),20).getStringCellValue());
                            record.setLineId(ExcelUtil.getCell(sheet.getRow(rowNum),1).getStringCellValue());//零件号
                            //获取项目ID
                            HZBomMainRecord hzBomMainRecord = hzBomMainRecordDao.selectByProjectPuid(projectId);
                            record.setBomDigifaxId(hzBomMainRecord.getPuid());

                            hzPbomLineRecords.add(record);
                            //修改之前保存原数据到before记录表(插入或更新)

                            //updateInput
                            hzPbomRecordDAO.updateInput(hzPbomLineRecords.get(i-1));
                            //修改后数据保存到after记录表(插入或更新)

                            i++;
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return OperateResultMessageRespDTO.getFailResult();
        }

        return OperateResultMessageRespDTO.getSuccessResult();
    }

    public String errorLogInfo(Workbook workbook){
        StringBuffer stringBuffer = new StringBuffer("文件解析失败!</br>");
        for(int numSheet = 0; numSheet < workbook.getNumberOfSheets(); numSheet++ ){
            Sheet sheet = workbook.getSheetAt(numSheet);
            for(int rowNum = 1; rowNum <= sheet.getLastRowNum(); rowNum++){
                String lineId = ExcelUtil.getCell(sheet.getRow(rowNum),1).getStringCellValue();
                String level = ExcelUtil.getCell(sheet.getRow(rowNum),3).getStringCellValue();
                String pBomLinePartResource = ExcelUtil.getCell(sheet.getRow(rowNum),11).getStringCellValue();
                if(StringUtil.isEmpty(lineId)){
                    stringBuffer.append("第"+(rowNum)+"行的‘零件号’不能为<strong>空</strong></br>") ;
                    this.errorCount++;
                }
                if(StringUtil.isEmpty(level)){
                    stringBuffer.append("第"+(rowNum)+"行‘层级’不能为<strong>空</strong></br>") ;
                    this.errorCount++;
                }else if(level.endsWith("y")){
                    stringBuffer.append("第"+(rowNum)+"行‘层级’填写不正确，层级尾缀应该填写为:<strong>Y</strong></br>") ;
                    this.errorCount++;
                }
                if(StringUtil.isEmpty(pBomLinePartResource)){
                    this.errorCount++;
                    stringBuffer.append("第"+(rowNum)+"行‘零部件来源’不能为<strong>空</strong></br>") ;
                }
                continue;
            }
        }
        return stringBuffer.toString();
    }

}
