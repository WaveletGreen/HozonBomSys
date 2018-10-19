package com.connor.hozon.bom.resources.service.file.impl;

import com.connor.hozon.bom.bomSystem.dao.bom.HzBomMainRecordDao;
import com.connor.hozon.bom.common.util.user.UserInfo;
import com.connor.hozon.bom.resources.domain.dto.response.OperateResultMessageRespDTO;
import com.connor.hozon.bom.resources.mybatis.bom.HzMbomRecordDAO;
import com.connor.hozon.bom.resources.mybatis.factory.HzFactoryDAO;
import com.connor.hozon.bom.resources.service.file.FileUploadUpdataMbom2Service;
import com.connor.hozon.bom.resources.util.ExcelUtil;
import com.connor.hozon.bom.resources.util.PrivilegeUtil;
import com.connor.hozon.bom.resources.util.StringUtil;
import com.connor.hozon.bom.sys.entity.User;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import sql.pojo.bom.HZBomMainRecord;
import sql.pojo.bom.HzMbomLineRecord;
import sql.pojo.factory.HzFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service("FileUploadUpdataMbom2Service")
public class FileUploadUpdataMbom2ServiceImpl implements FileUploadUpdataMbom2Service {
    @Autowired
    private HzMbomRecordDAO hzMbomRecordDAO;

    @Autowired
    private HzBomMainRecordDao hzBomMainRecordDao;

    @Autowired
    private HzFactoryDAO hzFactoryDAO;

    private int errorCount = 0;

    @Override
    public OperateResultMessageRespDTO UploadMbomToDB(MultipartFile file, String projectId) {
        User user = UserInfo.getUser();
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
            List<HzMbomLineRecord> hzMbomLineRecords = new ArrayList<>();
            for(int numSheet = 0; numSheet < workbook.getNumberOfSheets(); numSheet++ ){
                Sheet sheet = workbook.getSheetAt(numSheet);
                String sheetName = workbook.getSheetName(numSheet);//sheet 表名 就一个sheet的话 没什么卵用
                int i = 1;
                while (i<=sheet.getLastRowNum()){
                    for(int rowNum = i; rowNum <= sheet.getLastRowNum(); rowNum++){
                        Row sheetRow = sheet.getRow(rowNum);
                        if(sheetRow != null){
                            HzMbomLineRecord record = new HzMbomLineRecord();
                            //备件、备件编号、工艺路线、人工工时、节拍、焊点、机物料、标准件、工具、废品
                            // 变更、变更号、工厂代码、发货料库存地点、BOM类型
                            record.setSparePart(ExcelUtil.getCell(sheet.getRow(rowNum),9).getStringCellValue());
                            record.setSparePartNum(ExcelUtil.getCell(sheet.getRow(rowNum),10).getStringCellValue());
                            record.setProcessRoute(ExcelUtil.getCell(sheet.getRow(rowNum),11).getStringCellValue());
                            record.setLaborHour(ExcelUtil.getCell(sheet.getRow(rowNum),12).getStringCellValue());
                            record.setRhythm(ExcelUtil.getCell(sheet.getRow(rowNum),13).getStringCellValue());
                            record.setSolderJoint(ExcelUtil.getCell(sheet.getRow(rowNum),14).getStringCellValue());
                            record.setMachineMaterial(ExcelUtil.getCell(sheet.getRow(rowNum),15).getStringCellValue());
                            record.setStandardPart(ExcelUtil.getCell(sheet.getRow(rowNum),16).getStringCellValue());
                            record.setTools(ExcelUtil.getCell(sheet.getRow(rowNum),17).getStringCellValue());
                            record.setWasterProduct(ExcelUtil.getCell(sheet.getRow(rowNum),18).getStringCellValue());
                            record.setChange(ExcelUtil.getCell(sheet.getRow(rowNum),19).getStringCellValue());
                            record.setChangeNum(ExcelUtil.getCell(sheet.getRow(rowNum),20).getStringCellValue());
                            //工厂代码为新增时，要新增工厂到工厂记录表
                            String factory = ExcelUtil.getCell(sheet.getRow(rowNum),21).getStringCellValue();
                            if (!factory.equals("") && null != factory) {
                                HzFactory hzFactory = hzFactoryDAO.findFactory("", factory);
                                if (hzFactory == null) {
                                    String puid = UUID.randomUUID().toString();
                                    hzFactory = new HzFactory();
                                    hzFactory.setPuid(puid);
                                    hzFactory.setpFactoryCode(factory);
                                    hzFactory.setpUpdateName(user.getUserName());
                                    hzFactory.setpCreateName(user.getUserName());
                                    int j = hzFactoryDAO.insert(hzFactory);
                                    if (j < 0) {
                                        return OperateResultMessageRespDTO.getFailResult();
                                    }
                                    record.setpFactoryId(puid);
                                } else {
                                    record.setpFactoryId(hzFactory.getPuid());
                                }
                            }
                            record.setpStockLocation(ExcelUtil.getCell(sheet.getRow(rowNum),22).getStringCellValue());

                            //1生产，6财务
                            String bomType = ExcelUtil.getCell(sheet.getRow(rowNum),23).getStringCellValue();
                            if (bomType.equals("生产")) {
                                record.setpBomType(1);
                            } else if (bomType.equals("财务")) {
                                record.setpBomType(6);
                            } else {
                                record.setpBomType(0);
                            }

                            record.setLineId(ExcelUtil.getCell(sheet.getRow(rowNum),1).getStringCellValue());//零件号
                            //获取项目ID
                            HZBomMainRecord hzBomMainRecord = hzBomMainRecordDao.selectByProjectPuid(projectId);
                            record.setBomDigifaxId(hzBomMainRecord.getPuid());

                            hzMbomLineRecords.add(record);
                            //updateInput
                            hzMbomRecordDAO.updateInputProduct(hzMbomLineRecords.get(i-1));
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
                String pBomLinePartResource = ExcelUtil.getCell(sheet.getRow(rowNum),8).getStringCellValue();
                if(StringUtil.isEmpty(lineId)){
                    stringBuffer.append("第"+(rowNum)+"行的‘零件号’不能为<strong>空</strong></br>") ;
                    this.errorCount++;
                }
                if(StringUtil.isEmpty(level)){
                    stringBuffer.append("第"+(rowNum)+"行‘层级’不能为<strong>空</strong></br>") ;
                    this.errorCount++;
                }else if(level.endsWith("y")){
                    stringBuffer.append("第"+(rowNum)+"行的‘层级’填写不正确，层级尾缀应该填写为:<strong>Y</strong></br>") ;
                    this.errorCount++;
                }
                if(StringUtil.isEmpty(pBomLinePartResource)){
                    this.errorCount++;
                    stringBuffer.append("第"+(rowNum)+"行的‘零部件来源’不能为<strong>空</strong></br>") ;
                }
                continue;
            }
        }
        return stringBuffer.toString();
    }

}
