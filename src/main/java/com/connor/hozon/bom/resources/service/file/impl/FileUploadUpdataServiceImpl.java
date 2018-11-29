package com.connor.hozon.bom.resources.service.file.impl;

import com.connor.hozon.bom.bomSystem.dao.bom.HzBomMainRecordDao;
import com.connor.hozon.bom.common.util.user.UserInfo;
import com.connor.hozon.bom.resources.domain.dto.response.WriteResultRespDTO;
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
import java.util.*;

@Service("fileUploadUpdataService")
public class FileUploadUpdataServiceImpl implements FileUploadUpdataService {

    @Autowired
    private HzPbomRecordDAO hzPbomRecordDAO;

    @Autowired
    private HzBomMainRecordDao hzBomMainRecordDao;

    private int errorCount = 0;

    //private int lineIndexFirst =10;

    //private Integer maxOrderNum = null;

    @Override
    public WriteResultRespDTO UploadPbomToDB(MultipartFile file, String projectId) {
        //FileUploadServiceImpl fileUploadService = new FileUploadServiceImpl();
        //判断权限
        boolean b = PrivilegeUtil.writePrivilege();
        if(!b){
            return WriteResultRespDTO.getFailPrivilege();
        }
        //判断文件格式(以.XLS或.XLSX结尾)
        boolean preCheck = ExcelUtil.preReadCheck(file.getOriginalFilename());
        if(!preCheck){
            return WriteResultRespDTO.fileIsNotExcel();
        }
        //判断文件大小(0-10M)
        boolean suitSize = ExcelUtil.checkFileSize(file.getSize());
        if(!suitSize){
            return WriteResultRespDTO.fileSizeOverFlow();
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
                WriteResultRespDTO respDTO = new WriteResultRespDTO();
                respDTO.setErrMsg(errorMsg);
                respDTO.setErrCode(WriteResultRespDTO.FAILED_CODE);
                return  respDTO;
            }
            List<HzPbomLineRecord> hzPbomLineRecords = new ArrayList<>();
            for(int numSheet = 0; numSheet < workbook.getNumberOfSheets(); numSheet++ ){
                Sheet sheet = workbook.getSheetAt(numSheet);
                //String sheetName = workbook.getSheetName(numSheet);//sheet 表名 就一个sheet的话 没什么卵用
                int i = 1;
                Map map = new HashMap();
                while (i<=sheet.getLastRowNum()){
                    //基准行
                    String item_id = ExcelUtil.getCell(sheet.getRow(i),1).getStringCellValue();
                    String zzcg = ExcelUtil.getCell(sheet.getRow(i),12).getStringCellValue();
                    String type = ExcelUtil.getCell(sheet.getRow(i),13).getStringCellValue();
                    String buyUnit = ExcelUtil.getCell(sheet.getRow(i),14).getStringCellValue();
                    String cj1 = ExcelUtil.getCell(sheet.getRow(i),15).getStringCellValue();
                    String cj2 = ExcelUtil.getCell(sheet.getRow(i),16).getStringCellValue();
                    String scx = ExcelUtil.getCell(sheet.getRow(i),17).getStringCellValue();
                    String mjlx = ExcelUtil.getCell(sheet.getRow(i),18).getStringCellValue();
                    String wwj = ExcelUtil.getCell(sheet.getRow(i),19).getStringCellValue();
                    String gw = ExcelUtil.getCell(sheet.getRow(i),20).getStringCellValue();

                    for(int rowNum = i+1; rowNum <= sheet.getLastRowNum(); rowNum++){
                        String item_ids = ExcelUtil.getCell(sheet.getRow(rowNum),1).getStringCellValue();
                        if(item_ids.equals(item_id)){
                            //自制/采购、焊接/装配、采购单元、车间1、车间2、生产线、模具类别、外委件、工位
                            String zzcgs = ExcelUtil.getCell(sheet.getRow(rowNum),12).getStringCellValue();
                            String types = ExcelUtil.getCell(sheet.getRow(rowNum),13).getStringCellValue();
                            String buyUnits = ExcelUtil.getCell(sheet.getRow(rowNum),14).getStringCellValue();
                            String cj1s = ExcelUtil.getCell(sheet.getRow(rowNum),15).getStringCellValue();
                            String cj2s = ExcelUtil.getCell(sheet.getRow(rowNum),16).getStringCellValue();
                            String scxs = ExcelUtil.getCell(sheet.getRow(rowNum),17).getStringCellValue();
                            String mjlxs = ExcelUtil.getCell(sheet.getRow(rowNum),18).getStringCellValue();
                            String wwjs = ExcelUtil.getCell(sheet.getRow(rowNum),19).getStringCellValue();
                            String gws = ExcelUtil.getCell(sheet.getRow(rowNum),20).getStringCellValue();
                            if(!zzcgs.equals(zzcg)||!types.equals(type)||!buyUnits.equals(buyUnit)||!cj1s.equals(cj1)||
                                    !cj2s.equals(cj2)||!scxs.equals(scx)||!mjlxs.equals(mjlx)||!wwjs.equals(wwj)||
                                    !gws.equals(gw)){
                                map.put(item_ids,"");
                            }
                        }
                        //i++;
                    }
                    i++;
                }
                if(map.size()>0){
                    //返回零件号
                    //List<String> list = new ArrayList<>();
                    StringBuffer stringBuffer = new StringBuffer("PBOM导入文件解析失败!</br>");
                    Iterator iter = map.entrySet().iterator();
                    while (iter.hasNext()) {
                        Map.Entry entry = (Map.Entry) iter.next();
                        //list.add(entry.getKey().toString());
                        stringBuffer.append("零件号：<strong>"+entry.getKey().toString()+"</strong>修改不同步！！！</br>");
                    }

                    WriteResultRespDTO respDTO = new WriteResultRespDTO();
                    respDTO.setErrMsg(stringBuffer.toString());
                    respDTO.setErrCode(WriteResultRespDTO.FAILED_CODE);
                    return  respDTO;
                }else{
                    //while (i<=sheet.getLastRowNum()){
                    Map<String, Object> beforeMap = new HashMap<>();//before表记录冲突
                    Map<String, Object> afterMap = new HashMap<>();//after表记录冲突
                    for(int rowNum = 1; rowNum <= sheet.getLastRowNum(); rowNum++){
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
                            Map<String, Object> m = new HashMap<>();
                            m.put("lineId", ExcelUtil.getCell(sheet.getRow(rowNum),1).getStringCellValue());
                            m.put("projectId", projectId);
                            List<HzPbomLineRecord> pbomRecord_old = hzPbomRecordDAO.getPbomById(m);
                            List<HzPbomLineRecord> pbomRecord_before = hzPbomRecordDAO.getPbomById_before(m);
                            List<HzPbomLineRecord> pbomRecord_after = hzPbomRecordDAO.getPbomById_after(m);
                            //2018.11.25注释掉，只修改原数据不做保存到before、after表，走变更流程时才记录
                            /*if(!beforeMap.containsKey(ExcelUtil.getCell(sheet.getRow(rowNum),1).getStringCellValue())){
                                beforeMap.put(ExcelUtil.getCell(sheet.getRow(rowNum),1).getStringCellValue(),"");
                                //找before记录表是否有记录
                                if(pbomRecord_before.size()==0){
                                    //插入before记录表
                                    hzPbomRecordDAO.insert_before(pbomRecord_old.get(0));
                                }else {
                                    for(int j=0;j<pbomRecord_before.size();j++){
                                        //更新before记录表
                                        if(StringUtil.isEmpty(pbomRecord_before.get(j).getMwoNo())){
                                            hzPbomRecordDAO.update_before(pbomRecord_old.get(0));
                                            break;
                                        }else {
                                            if(j==pbomRecord_before.size()-1){
                                                //只有走过流程的记录，则也执行插入before记录表操作
                                                hzPbomRecordDAO.insert_before(pbomRecord_old.get(0));
                                            }
                                        }
                                    }
                                }
                            }*/

                            //更新操作--updateInput
                            hzPbomRecordDAO.updateInput(hzPbomLineRecords.get(rowNum-1));

                            //2018.11.25注释掉，只修改原数据不做保存到before、after表，走变更流程时才记录
                            /*if(!afterMap.containsKey(ExcelUtil.getCell(sheet.getRow(rowNum),1).getStringCellValue())){
                                afterMap.put(ExcelUtil.getCell(sheet.getRow(rowNum),1).getStringCellValue(),"");
                                pbomRecord_old = hzPbomRecordDAO.getPbomById(m);
                                //修改后数据保存到after记录表(插入或更新)
                                if(pbomRecord_after.size()==0){
                                    //插入after记录表
                                    hzPbomRecordDAO.insert_after(pbomRecord_old.get(0));
                                }else {
                                    for(int j=0;j<pbomRecord_after.size();j++){
                                        //更新after记录表
                                        if(StringUtil.isEmpty(pbomRecord_after.get(j).getMwoNo())){
                                            hzPbomRecordDAO.update_after(pbomRecord_old.get(0));
                                            break;
                                        }else {
                                            if(j==pbomRecord_after.size()-1){
                                                //只有走过流程的记录，则也执行插入after记录表操作
                                                hzPbomRecordDAO.insert_after(pbomRecord_old.get(0));
                                            }
                                        }
                                    }
                                }
                            }*/
                            //i++;
                        }
                    }
                    //}
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
            return WriteResultRespDTO.getFailResult();
        }

        return WriteResultRespDTO.getSuccessResult();
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
