package com.connor.hozon.bom.resources.service.file.impl;

import com.connor.hozon.bom.resources.domain.dto.response.WriteResultRespDTO;
import com.connor.hozon.bom.resources.mybatis.accessories.HzAccessoriesLibsDAO;
import com.connor.hozon.bom.resources.service.file.FileUploadAccessoriesService;
import com.connor.hozon.bom.resources.util.ExcelUtil;
import com.connor.hozon.bom.resources.util.ListUtil;
import com.connor.hozon.bom.resources.util.StringUtil;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.multipart.MultipartFile;
import sql.pojo.accessories.HzAccessoriesLibs;
import sql.redis.HzDBException;

import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: xlf
 * Date: 2018/12/12
 * Time: 13:51
 */
@Service("fileUploadAccessoriesService")
public class FileUploadAccessoriesServiceImpl implements FileUploadAccessoriesService {
    @Autowired
    private HzAccessoriesLibsDAO hzAccessoriesLibsDAO;

    private int errorCount = 0;

    /**
     * 工艺辅料导入Excel
     * @param file
     * @return
     */
    @Override
    @Transactional(rollbackFor = {RuntimeException.class,HzDBException.class})
    public WriteResultRespDTO uploadAccessoriesToDB(MultipartFile file) {
        //判断文件格式(以.XLS或.XLSX结尾)
        boolean preCheck = ExcelUtil.preReadCheck(file.getOriginalFilename());
        if (!preCheck) {
            return WriteResultRespDTO.fileIsNotExcel();
        }
        //判断文件大小(0-10M)
        boolean suitSize = ExcelUtil.checkFileSize(file.getSize());
        if (!suitSize) {
            return WriteResultRespDTO.fileSizeOverFlow();
        }
        ExcelUtil.preReadCheck(file.getOriginalFilename());
        //上传文件到服务器
        String fileUrl = null;
        try {
            fileUrl = ExcelUtil.uploadFileToLocation(file.getBytes(), file.getOriginalFilename());
            Workbook workbook = ExcelUtil.getWorkbook(fileUrl);
            this.errorCount = 0;
            String errorMsg = errorLogInfo(workbook);
            if (this.errorCount != 0) {
                return WriteResultRespDTO.failResultRespDTO(errorMsg);
            }
            List<HzAccessoriesLibs> insertAccessoriesLibs = new ArrayList<>();
            List<HzAccessoriesLibs> updateAccessoriesLibs = new ArrayList<>();
//            for (int numSheet = 0; numSheet < workbook.getNumberOfSheets(); numSheet++) {
                Sheet sheet = workbook.getSheetAt(0);
                for (int rowNum = 1; rowNum <= sheet.getLastRowNum(); rowNum++) {
                    HzAccessoriesLibs libs = new HzAccessoriesLibs();
                    String materielId = ExcelUtil.getCell(sheet.getRow(rowNum), 1).getStringCellValue();
                    libs.setPuid(UUID.randomUUID().toString());
                    libs.setpMaterielCode(ExcelUtil.getCell(sheet.getRow(rowNum), 1).getStringCellValue());
                    libs.setpMaterielName(ExcelUtil.getCell(sheet.getRow(rowNum), 2).getStringCellValue());
                    libs.setpTechnicalConditions(ExcelUtil.getCell(sheet.getRow(rowNum), 3).getStringCellValue());
                    libs.setpMeasuringUnit(ExcelUtil.getCell(sheet.getRow(rowNum), 4).getStringCellValue());
                    libs.setpMaterielPurpose(ExcelUtil.getCell(sheet.getRow(rowNum), 5).getStringCellValue());
                    libs.setpDosageBicycle(ExcelUtil.getCell(sheet.getRow(rowNum), 6).getStringCellValue());
                    libs.setpNote(ExcelUtil.getCell(sheet.getRow(rowNum), 7).getStringCellValue());
                    HzAccessoriesLibs hzAccessoriesLibs = hzAccessoriesLibsDAO.queryAccessoriesByMaterielCode(materielId);
                    if (hzAccessoriesLibs == null){
                        insertAccessoriesLibs.add(libs);
                    }else {
                        updateAccessoriesLibs.add(libs);
                    }
                }
//            }

            if(ListUtil.isNotEmpty(insertAccessoriesLibs)){
                hzAccessoriesLibsDAO.importList(insertAccessoriesLibs);
            }
            if(ListUtil.isNotEmpty(updateAccessoriesLibs)){
                hzAccessoriesLibsDAO.updateList(updateAccessoriesLibs);
            }
            //把上传到服务器的文件删除
            ExcelUtil.deleteFile();
        } catch (Exception e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return WriteResultRespDTO.getFailResult();
        }
        return WriteResultRespDTO.getSuccessResult();
    }


    public String errorLogInfo(Workbook workbook) {
        StringBuffer stringBuffer = new StringBuffer("文件解析失败!</br>");
        for (int numSheet = 0; numSheet < workbook.getNumberOfSheets(); numSheet++) {
            Sheet sheet = workbook.getSheetAt(numSheet);
            for (int rowNum = 1; rowNum <= sheet.getLastRowNum(); rowNum++) {
                String pMaterielCode = ExcelUtil.getCell(sheet.getRow(rowNum), 1).getStringCellValue();
                String pMaterielName = ExcelUtil.getCell(sheet.getRow(rowNum), 2).getStringCellValue();
                if (StringUtil.isEmpty(pMaterielCode)) {
                    stringBuffer.append("第" + (rowNum) + "行的物料号不能为<strong>空</strong></br>");
                    this.errorCount++;
                }
                if (StringUtil.isEmpty(pMaterielName)) {
                    stringBuffer.append("第" + (rowNum) + "行的材料名称不能为<strong>空</strong></br>");
                    this.errorCount++;
                }
                continue;
            }
            int cellNum = sheet.getRow(0).getLastCellNum();
            if (cellNum > 8) {
                stringBuffer.append("Excel导入的列信息不正确</br>");
                this.errorCount++;
            }
            String no = ExcelUtil.getCell(sheet.getRow(0), 0).getStringCellValue();
            String pMaterielCode = ExcelUtil.getCell(sheet.getRow(0), 1).getStringCellValue();
            String pMaterielName = ExcelUtil.getCell(sheet.getRow(0), 2).getStringCellValue();
            String pTechnicalConditions = ExcelUtil.getCell(sheet.getRow(0), 3).getStringCellValue();
            String pMeasuringUnit = ExcelUtil.getCell(sheet.getRow(0), 4).getStringCellValue();
            String pMaterielPurpose = ExcelUtil.getCell(sheet.getRow(0), 5).getStringCellValue();
            String pDosageBicycle = ExcelUtil.getCell(sheet.getRow(0), 6).getStringCellValue();
            String pNote = ExcelUtil.getCell(sheet.getRow(0), 7).getStringCellValue();
            if (!no.equals("序号")) {
                stringBuffer.append("第一列标题不正确,应该为序号</br>");
                this.errorCount++;
            } else if (!pMaterielCode.equals("物料号")) {
                stringBuffer.append("第二列标题不正确,应该为物料号</br>");
                this.errorCount++;
            } else if (!pMaterielName.equals("材料名称")) {
                stringBuffer.append("第三列标题不正确,应该为材料名称</br>");
                this.errorCount++;
            } else if (!pTechnicalConditions.equals("技术条件/牌号规格")) {
                stringBuffer.append("第四列标题不正确,应该为技术条件/牌号规格</br>");
                this.errorCount++;
            } else if (!pMeasuringUnit.equals("计量单位")) {
                stringBuffer.append("第五列标题不正确,应该为计量单位</br>");
                this.errorCount++;
            } else if (!pMaterielPurpose.equals("材料用途")) {
                stringBuffer.append("第六列标题不正确,应该为材料用途</br>");
                this.errorCount++;
            } else if (!pDosageBicycle.equals("单车用量")) {
                stringBuffer.append("第七列标题不正确,应该为单车用量</br>");
                this.errorCount++;
            } else if (!pNote.equals("备注")) {
                stringBuffer.append("第八列标题不正确,应该为备注</br>");
                this.errorCount++;
            }
        }
        return stringBuffer.toString();
    }
}