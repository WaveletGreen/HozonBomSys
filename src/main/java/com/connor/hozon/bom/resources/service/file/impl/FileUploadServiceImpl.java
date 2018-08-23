package com.connor.hozon.bom.resources.service.file.impl;

import com.connor.hozon.bom.resources.dto.response.OperateResultMessageRespDTO;
import com.connor.hozon.bom.resources.service.file.FileUploadService;
import com.connor.hozon.bom.resources.util.ExcelUtil;
import com.connor.hozon.bom.resources.util.PrivilegeUtil;
import com.connor.hozon.bom.resources.util.StringUtil;
import com.sun.prism.shader.Solid_TextureYV12_AlphaTest_Loader;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Author: haozt
 * @Date: 2018/8/22
 * @Description:
 */
@Service("FileUploadService")
public class FileUploadServiceImpl implements FileUploadService {
    @Override
    public OperateResultMessageRespDTO UploadEbomToDB(MultipartFile file) {
        int i = 800;
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
            for(int numSheet = 0; numSheet < workbook.getNumberOfSheets(); numSheet++ ){
                Sheet sheet = workbook.getSheetAt(numSheet);
                String sheetName = workbook.getSheetName(numSheet);//EBOM 表名 就一张表的话 没什么卵用

                for(int rowNum = 800; rowNum <= sheet.getLastRowNum(); rowNum++){
                    Row sheetRow = sheet.getRow(rowNum);
                    if(sheetRow != null){
                        String no = ExcelUtil.getCell(sheet.getRow(rowNum),0).getStringCellValue();
                        String lineId=ExcelUtil.getCell(sheet.getRow(rowNum),1).getStringCellValue();
                        String pBomLinePartName=ExcelUtil.getCell(sheet.getRow(rowNum),2).getStringCellValue();
                        String level = ExcelUtil.getCell(sheet.getRow(rowNum),3).getStringCellValue();
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
                        i++;


                    }
                }
            }
            long t2 = System.currentTimeMillis();
            System.out.println("老资好几把累");
            System.out.println("时间="+(t2-t1)+"ms");

        }catch (Exception e){
            System.out.println(i);
            e.printStackTrace();

        }
        return null;
    }
}
