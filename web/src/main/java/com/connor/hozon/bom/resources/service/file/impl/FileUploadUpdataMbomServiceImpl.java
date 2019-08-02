package com.connor.hozon.bom.resources.service.file.impl;

import cn.net.connor.hozon.dao.pojo.main.HzMainBom;
import cn.net.connor.hozon.dao.dao.main.HzMainBomDao;
import com.connor.hozon.bom.common.util.user.UserInfo;
import com.connor.hozon.bom.resources.domain.dto.response.WriteResultRespDTO;
import com.connor.hozon.bom.resources.mybatis.bom.HzMbomRecordDAO;
import com.connor.hozon.bom.resources.mybatis.factory.HzFactoryDAO;
import com.connor.hozon.bom.resources.service.file.FileUploadUpdataMbomService;
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
import sql.pojo.bom.HzMbomLineRecord;
import sql.pojo.factory.HzFactory;

import java.util.*;

@Service("fileUploadUpdataMbomService")
public class FileUploadUpdataMbomServiceImpl implements FileUploadUpdataMbomService {
    @Autowired
    private HzMbomRecordDAO hzMbomRecordDAO;

    @Autowired
    private HzMainBomDao hzMainBomDao;

    @Autowired
    private HzFactoryDAO hzFactoryDAO;

    private int errorCount = 0;

    @Override
    public WriteResultRespDTO UploadMbomToDB(MultipartFile file, String projectId) {
        User user = UserInfo.getUser();
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
            List<HzMbomLineRecord> hzMbomLineRecords = new ArrayList<>();
            for(int numSheet = 0; numSheet < workbook.getNumberOfSheets(); numSheet++ ){
                Sheet sheet = workbook.getSheetAt(numSheet);
                //String sheetName = workbook.getSheetName(numSheet);//sheet 表名 就一个sheet的话 没什么卵用
                int i = 1;
                Map map = new HashMap();
                while (i<=sheet.getLastRowNum()){
                    //基准行
                    String item_id = ExcelUtil.getCell(sheet.getRow(i),1).getStringCellValue();//零件号
                    String bj = ExcelUtil.getCell(sheet.getRow(i),9).getStringCellValue();//零件号
                    String bjbh = ExcelUtil.getCell(sheet.getRow(i),10).getStringCellValue();
                    String gylx = ExcelUtil.getCell(sheet.getRow(i),11).getStringCellValue();
                    String rggs = ExcelUtil.getCell(sheet.getRow(i),12).getStringCellValue();
                    String jp = ExcelUtil.getCell(sheet.getRow(i),13).getStringCellValue();
                    String hd = ExcelUtil.getCell(sheet.getRow(i),14).getStringCellValue();
                    String jwl = ExcelUtil.getCell(sheet.getRow(i),15).getStringCellValue();
                    String jzj = ExcelUtil.getCell(sheet.getRow(i),16).getStringCellValue();
                    String gj = ExcelUtil.getCell(sheet.getRow(i),17).getStringCellValue();
                    String fp = ExcelUtil.getCell(sheet.getRow(i),18).getStringCellValue();
                    String bg = ExcelUtil.getCell(sheet.getRow(i),19).getStringCellValue();
                    String bgh = ExcelUtil.getCell(sheet.getRow(i),20).getStringCellValue();
                    String factory = ExcelUtil.getCell(sheet.getRow(i),21).getStringCellValue();
                    String ccdd = ExcelUtil.getCell(sheet.getRow(i),22).getStringCellValue();
                    String bomType = ExcelUtil.getCell(sheet.getRow(i),23).getStringCellValue();

                    for(int rowNum = i+1; rowNum <= sheet.getLastRowNum(); rowNum++){
                        String item_ids = ExcelUtil.getCell(sheet.getRow(rowNum),1).getStringCellValue();
                        if(item_ids.equals(item_id)){
                            //备件、备件编号、工艺路线、人工工时、节拍、焊点、机物料、标准件、工具、废品
                            // 变更、变更号、工厂代码、发货料库存地点、BOM类型
                            String bjs = ExcelUtil.getCell(sheet.getRow(rowNum),9).getStringCellValue();//零件号
                            String bjbhs = ExcelUtil.getCell(sheet.getRow(rowNum),10).getStringCellValue();
                            String gylxs = ExcelUtil.getCell(sheet.getRow(rowNum),11).getStringCellValue();
                            String rggss = ExcelUtil.getCell(sheet.getRow(rowNum),12).getStringCellValue();
                            String jps = ExcelUtil.getCell(sheet.getRow(rowNum),13).getStringCellValue();
                            String hds = ExcelUtil.getCell(sheet.getRow(rowNum),14).getStringCellValue();
                            String jwls = ExcelUtil.getCell(sheet.getRow(rowNum),15).getStringCellValue();
                            String jzjs = ExcelUtil.getCell(sheet.getRow(rowNum),16).getStringCellValue();
                            String gjs = ExcelUtil.getCell(sheet.getRow(rowNum),17).getStringCellValue();
                            String fps = ExcelUtil.getCell(sheet.getRow(rowNum),18).getStringCellValue();
                            String bgs = ExcelUtil.getCell(sheet.getRow(rowNum),19).getStringCellValue();
                            String bghs = ExcelUtil.getCell(sheet.getRow(rowNum),20).getStringCellValue();
                            String factorys = ExcelUtil.getCell(sheet.getRow(rowNum),21).getStringCellValue();
                            String ccdds = ExcelUtil.getCell(sheet.getRow(rowNum),22).getStringCellValue();
                            String bomTypes = ExcelUtil.getCell(sheet.getRow(rowNum),23).getStringCellValue();

                            if(!bjs.equals(bj)||!bjbhs.equals(bjbh)||!gylxs.equals(gylx)||!rggss.equals(rggs)||
                                    !jps.equals(jp)||!hds.equals(hd)||!jwls.equals(jwl)||!jzjs.equals(jzj)||
                                    !gjs.equals(gj)||!fps.equals(fp)||!bgs.equals(bg)||!bghs.equals(bgh)||
                                    !factorys.equals(factory)||!ccdds.equals(ccdd)||!bomTypes.equals(bomType)){
                                map.put(item_ids,"");
                            }
                        }
                    }
                    i++;
                }
                if(map.size()>0){
                    //返回零件号
                    StringBuffer stringBuffer = new StringBuffer("超级MBOM导入文件解析失败!</br>");
                    Iterator iter = map.entrySet().iterator();
                    while (iter.hasNext()) {
                        Map.Entry entry = (Map.Entry) iter.next();
                        stringBuffer.append("零件号：<strong>"+entry.getKey().toString()+"</strong>修改不同步！！！</br>");
                    }

                    WriteResultRespDTO respDTO = new WriteResultRespDTO();
                    respDTO.setErrMsg(stringBuffer.toString());
                    respDTO.setErrCode(WriteResultRespDTO.FAILED_CODE);
                    return  respDTO;
                }else{
                    //while (i<=sheet.getLastRowNum()){
                    Map<String, Object> beforeMap = new HashMap<>();//before记录表
                    Map<String, Object> afterMap = new HashMap<>();//after记录表
                        for(int rowNum = 1; rowNum <= sheet.getLastRowNum(); rowNum++){
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
                                            return WriteResultRespDTO.getFailResult();
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
                                HzMainBom hzMainBom = hzMainBomDao.selectByProjectId(projectId);
                                record.setBomDigifaxId(hzMainBom.getPuid());

                                hzMbomLineRecords.add(record);
                                //修改之前保存原数据到before记录表(插入或更新)
                                Map<String, Object> m = new HashMap<>();
                                m.put("lineId", ExcelUtil.getCell(sheet.getRow(rowNum),1).getStringCellValue());
                                m.put("projectId", projectId);
                                List<HzMbomLineRecord> mbomRecord_old = hzMbomRecordDAO.findHzMbomByPuid(m);
                                List<HzMbomLineRecord> mbomRecord_before = hzMbomRecordDAO.findHzMbomByPuid_before(m);
                                List<HzMbomLineRecord> mbomRecord_after = hzMbomRecordDAO.findHzMbomByPuid_after(m);
                                //2018.11.25注释掉，只修改原数据不做保存到before、after表，走变更流程时才记录到before、after表
                                /*if(!beforeMap.containsKey(ExcelUtil.getCell(sheet.getRow(rowNum),1).getStringCellValue())){
                                    beforeMap.put(ExcelUtil.getCell(sheet.getRow(rowNum),1).getStringCellValue(),"");
                                    //找before记录表是否有记录
                                    if(mbomRecord_before.size()==0){
                                        //插入before记录表
                                        hzMbomRecordDAO.insert_before(mbomRecord_old.get(0));
                                    }else {
                                        for(int j=0;j<mbomRecord_before.size();j++){
                                            //更新before记录表
                                            if(StringUtil.isEmpty(mbomRecord_before.get(j).getMwoNo())){
                                                hzMbomRecordDAO.update_before(mbomRecord_old.get(0));
                                                break;
                                            }else {
                                                if(j==mbomRecord_before.size()-1){
                                                    //只有走过流程的记录，则也执行插入before记录表操作
                                                    hzMbomRecordDAO.insert_before(mbomRecord_old.get(0));
                                                }
                                            }
                                        }
                                    }
                                }*/

                                //导入更新操作--updateInput
                                hzMbomRecordDAO.updateInput(hzMbomLineRecords.get(rowNum-1));

                                //2018.11.25注释掉，只修改原数据不做保存到before、after表，走变更流程时才记录到before、after表
                                /*if(!afterMap.containsKey(ExcelUtil.getCell(sheet.getRow(rowNum),1).getStringCellValue())){
                                    afterMap.put(ExcelUtil.getCell(sheet.getRow(rowNum),1).getStringCellValue(),"");
                                    mbomRecord_old = hzMbomRecordDAO.findHzMbomByPuid(m);
                                    //修改后数据保存到after记录表(插入或更新)
                                    if(mbomRecord_after.size()==0){
                                        //插入after记录表
                                        hzMbomRecordDAO.insert_after(mbomRecord_old.get(0));
                                    }else {
                                        for(int j=0;j<mbomRecord_after.size();j++){
                                            //更新after记录表
                                            if(StringUtil.isEmpty(mbomRecord_after.get(j).getMwoNo())){
                                                hzMbomRecordDAO.update_after(mbomRecord_old.get(0));
                                                break;
                                            }else {
                                                if(j==mbomRecord_after.size()-1){
                                                    //只有走过流程的记录，则也执行插入after记录表操作
                                                    hzMbomRecordDAO.insert_after(mbomRecord_old.get(0));
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
                String pBomLinePartResource = ExcelUtil.getCell(sheet.getRow(rowNum),8).getStringCellValue();
                if(StringUtil.isEmpty(lineId)){
                    stringBuffer.append("第"+(rowNum)+"行的‘零件号’不能为<strong>空</strong></br>") ;
                    this.errorCount++;
                }
                if(StringUtil.isEmpty(level)){
                    stringBuffer.append("第"+(rowNum)+"行的‘层级’不能为<strong>空</strong></br>") ;
                    this.errorCount++;
                }else if(level.endsWith("y")){
                    stringBuffer.append("第"+(rowNum)+"行的‘层级’填写不正确，层级尾缀应该填写为:<strong>Y</strong></br>") ;
                    this.errorCount++;
                }
                //2018.11.24注释：MBOM中油漆物料可能没有零部件来源
//                if(StringUtil.isEmpty(pBomLinePartResource)){
//                    this.errorCount++;
//                    stringBuffer.append("第"+(rowNum)+"行‘零部件来源’不能为<strong>空</strong></br>") ;
//                }
                continue;
            }
        }
        return stringBuffer.toString();
    }

}
