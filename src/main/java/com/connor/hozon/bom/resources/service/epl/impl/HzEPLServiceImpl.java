package com.connor.hozon.bom.resources.service.epl.impl;

import com.alibaba.fastjson.JSONObject;
import com.connor.hozon.bom.resources.domain.dto.request.EditHzEPLReqDTO;
import com.connor.hozon.bom.resources.domain.dto.response.HzEplRespDTO;
import com.connor.hozon.bom.resources.domain.dto.response.WriteResultRespDTO;
import com.connor.hozon.bom.resources.domain.model.HzBomSysFactory;
import com.connor.hozon.bom.resources.domain.model.HzEPLFactory;
import com.connor.hozon.bom.resources.domain.query.HzBOMQuery;
import com.connor.hozon.bom.resources.domain.query.HzEPLByPageQuery;
import com.connor.hozon.bom.resources.domain.query.HzEPLQuery;
import com.connor.hozon.bom.resources.enumtype.ChangeTableNameEnum;
import com.connor.hozon.bom.resources.mybatis.bom.HzEbomRecordDAO;
import com.connor.hozon.bom.resources.mybatis.bom.HzPbomRecordDAO;
import com.connor.hozon.bom.resources.mybatis.epl.HzEPLDAO;
import com.connor.hozon.bom.resources.page.Page;
import com.connor.hozon.bom.resources.service.bom.HzMbomService;
import com.connor.hozon.bom.resources.service.epl.HzEPLService;
import com.connor.hozon.bom.resources.util.ExcelUtil;
import com.connor.hozon.bom.resources.util.ListUtil;
import com.connor.hozon.bom.resources.util.Result;
import com.google.common.collect.Lists;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.web.multipart.MultipartFile;
import sql.pojo.bom.HzPbomLineRecord;
import sql.pojo.epl.HzEPLManageRecord;
import sql.pojo.epl.HzEPLRecord;

import java.io.InputStream;
import java.util.*;

/**
 * @Author: haozt
 * @Date: 2018/12/18
 * @Description:
 */
@Service("hzEPLService")
public class HzEPLServiceImpl implements HzEPLService {

    private HzEPLDAO hzEPLDAO;

    private HzEbomRecordDAO hzEbomRecordDAO;

    private HzPbomRecordDAO hzPbomRecordDAO;

    private HzMbomService hzMbomService;

    private TransactionTemplate configTransactionTemplate;

    @Autowired
    public void setHzEPLDAO(HzEPLDAO hzEPLDAO) {
        this.hzEPLDAO = hzEPLDAO;
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
    public void setConfigTransactionTemplate(TransactionTemplate configTransactionTemplate) {
        this.configTransactionTemplate = configTransactionTemplate;
    }
    @Override
    public WriteResultRespDTO addPartToEPL(EditHzEPLReqDTO reqDTO) {
        try {
            HzEPLQuery query = new HzEPLQuery();
            query.setPartId(reqDTO.getPartId());
            query.setProjectId(reqDTO.getProjectId());
            Result repeat = hzEPLDAO.partIdRepeat(query);
            if(repeat.isSuccess()){
                return WriteResultRespDTO.failResultRespDTO("当前添加的零件号已存在！");
            }
            int i = hzEPLDAO.insert(HzEPLFactory.eplReqDTOToRecord(reqDTO));
            if(i<=0){
                return WriteResultRespDTO.getFailResult();
            }
            return WriteResultRespDTO.getSuccessResult();
        }catch (Exception e){
            e.printStackTrace();
            return WriteResultRespDTO.getFailResult();
        }
    }

    @Override
    public WriteResultRespDTO updatePartFromEPLRecord(EditHzEPLReqDTO reqDTO) {
        try {
            //判断一下重复 前端也会判断
            HzEPLQuery query = new HzEPLQuery();
            query.setId(reqDTO.getId());
            query.setProjectId(reqDTO.getProjectId());
            Result repeat = hzEPLDAO.partIdRepeat(query);
            if(repeat.isSuccess()){
                return WriteResultRespDTO.failResultRespDTO("零件号已存在！");
            }
            //需同步修改数据到PBOM
            HzEPLRecord record = (HzEPLRecord) repeat.getData();
            Map<String,Object> map = new HashMap<>();
            map.put("lineId",record.getPartId());
            map.put("projectId",record.getProjectId());
            List<HzPbomLineRecord> pbomLineRecords = hzPbomRecordDAO.getPbomById(map);
            configTransactionTemplate.execute(new TransactionCallback<Void>() {
                @Override
                public Void doInTransaction(TransactionStatus status) {
                    if(ListUtil.isNotEmpty(pbomLineRecords)){
                        String partResource = record.getPartResource();
                        List<String> carParts = hzMbomService.loadingCarPartType();
                        if(carParts.contains(partResource)){
                            List<HzPbomLineRecord> recordList = new ArrayList<>();
                            pbomLineRecords.forEach(record -> {
                                HzPbomLineRecord pbomLineRecord = new HzPbomLineRecord();
                                pbomLineRecord.setLineId(reqDTO.getPartId());
                                pbomLineRecord.setpBomLinePartName(reqDTO.getPartName());
                                pbomLineRecord.setpBomOfWhichDept(reqDTO.getPartOfWhichDept());
                                pbomLineRecord.setpBomLinePartResource(reqDTO.getPartResource());
                                pbomLineRecord.setpBomLinePartClass(reqDTO.getPartClass());
                                pbomLineRecord.setBomDigifaxId(pbomLineRecords.get(0).getBomDigifaxId());
                                pbomLineRecord.setPuid(record.getPuid());
                                recordList.add(pbomLineRecord);
                            });
                            hzPbomRecordDAO.updateListByPuids(recordList);
                        }else {//标记为删除状态,走流程后就直接删除
                            StringBuffer stringBuffer = new StringBuffer();
                            pbomLineRecords.forEach(pbomLineRecord -> {
                                stringBuffer.append(pbomLineRecord.getPuid()+",");
                            });
                            hzPbomRecordDAO.deleteByPuids(stringBuffer.toString());
                        }
                    }
                    hzEPLDAO.update(HzEPLFactory.eplReqDTOToRecord(reqDTO));
                    return null;
                }
            });
            return WriteResultRespDTO.getSuccessResult();
        }catch (Exception e){
            e.printStackTrace();
            return WriteResultRespDTO.getFailResult();
        }
    }

    @Override
    public WriteResultRespDTO deletePartFromEPLByIds(String ids) {
        try {
            //删除时需要进行判断 如果数据被引用 不允许删除
            //存在历史生效数据记录的 改为删除状态 否则直接删除

            //进行引用判断
            List<String> list = Lists.newArrayList(ids.split(","));
            StringBuffer stringBuffer = new StringBuffer();
            //EBOM 中检查
            for(String id : list){
                HzBOMQuery hzBOMQuery = new HzBOMQuery();
                hzBOMQuery.setEplId(Long.valueOf(id));
                List<HzEPLManageRecord> records = hzEbomRecordDAO.findEBOMRecordsByEPLId(hzBOMQuery);
                if(ListUtil.isNotEmpty(records)){
                    stringBuffer.append("零件号"+records.get(0).getLineID()+"已存在引用关系,不允许删除!");
                    return WriteResultRespDTO.failResultRespDTO(stringBuffer.toString());
                }
            }

            List<Long> updateList = new ArrayList<>();
            List<Long> deleteList = new ArrayList<>();
            //PBOM检查
            for(String id : list){
                HzEPLQuery query = new HzEPLQuery();
                Long eplId = Long.valueOf(id);
                query.setId(eplId);
                HzEPLRecord  record = hzEPLDAO.getEPLRecordById(query);
                if(record != null){
                    Map<String,Object> map = new HashMap<>();
                    map.put("lineId",record.getPartId());
                    map.put("projectId",record.getProjectId());
                    if(ListUtil.isNotEmpty(hzPbomRecordDAO.getPbomById(map))){
                        stringBuffer.append("零件号"+record.getPartId()+"已存在引用关系,不允许删除!");
                        return WriteResultRespDTO.failResultRespDTO(stringBuffer.toString());
                    }

                    if(StringUtils.isNotBlank(record.getRevision())){
                        updateList.add(eplId);
                    }else {
                        deleteList.add(eplId);
                    }
                }
            }


            configTransactionTemplate.execute(new TransactionCallback<Void>() {
                @Override
                public Void doInTransaction(TransactionStatus status) {
                    if(ListUtil.isNotEmpty(deleteList)){
                        hzEPLDAO.delete(null,deleteList);
                    }
                    if(ListUtil.isNotEmpty(updateList)){
                        hzEPLDAO.deleteByIds(updateList);
                    }
                    return null;
                }
            });
            return WriteResultRespDTO.getSuccessResult();
        }catch (Exception e){
            e.printStackTrace();
            return WriteResultRespDTO.getFailResult();
        }
    }

    @Override
    public Page<HzEplRespDTO> getEPLRecordByPage(HzEPLByPageQuery query) {
        try {
            Page<HzEPLRecord> page  = hzEPLDAO.getEplRecordByPage(query);
            if (page == null || ListUtil.isEmpty(page.getResult())) {
                return new Page<>(page.getPageNumber(), page.getPageSize(), 0);
            }
            int num = (query.getPage() - 1) * query.getPageSize()+1;
            List<HzEPLRecord> records = page.getResult();
            List<HzEplRespDTO>  list =  new ArrayList();
            for(HzEPLRecord record : records){
                HzEplRespDTO respDTO = HzEPLFactory.eplRecordToRespDTO(record);
                respDTO.setNo(num++);
                list.add(respDTO);
            }
            return new Page<>(page.getPageNumber(), page.getPageSize(), page.getTotalCount(), list);
        }catch (Exception e){
            e.printStackTrace();
            return new Page<>(query.getPage(), query.getPageSize(), 0);
        }
    }

    @Override
    public HzEplRespDTO getEplById(Long id) {
        HzEPLQuery query = new HzEPLQuery();
        query.setId(id);
        try {
            HzEPLRecord record = hzEPLDAO.getEPLRecordById(query);
            if(null != record){
                HzEplRespDTO respDTO  = HzEPLFactory.eplRecordToRespDTO(record);
                return respDTO;
            }
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
        return null;
    }

    @Override
    public boolean partIdIsRepeat(Long id,String partId,String projectId) {
        HzEPLQuery query = new HzEPLQuery();
        query.setPartId(partId);
        query.setProjectId(projectId);
        query.setId(id);
        return hzEPLDAO.partIdRepeat(query).isSuccess();
    }

    @Override
    public JSONObject excelImport(MultipartFile file, String projectPuid) {
        JSONObject result = new JSONObject();
        result.put("status",true);
        result.put("msg","导入成功");

        //判断文件大小(0-10M)
        boolean suitSize = ExcelUtil.checkFileSize(file.getSize());
        if(!suitSize){
            result.put("status",false);
            result.put("msg","上传文件过大");
            return result;
        }
        try {
            List<String[]> data = ExcelUtil.readExcel(file);
            List<HzEPLRecord> hzEPLRecords = new ArrayList<>();
            try {
                hzEPLRecords = HzEPLRecord.getListBeen(data, projectPuid);
            }catch (Exception e){
                e.printStackTrace();
                result.put("status",false);
                result.put("msg","导入Excel数据字段错误,请校验字段是否缺失");
                return result;
            }

            /********检查导入的excel中是否有重复的零件号***********/
            StringBuffer msg = new StringBuffer();
            List<String> repetition = new ArrayList<>();
            for(int i=0;i<hzEPLRecords.size();i++){
                HzEPLRecord hzEPLRecordCheck = hzEPLRecords.get(i);
                if(repetition.contains(hzEPLRecordCheck.getPartId())){
                    continue;
                }
                //将所有导入数据设置为草稿状态
                hzEPLRecordCheck.setStatus(2);
                for(int j=0;j<hzEPLRecords.size();j++){
                    HzEPLRecord hzEPLRecord = hzEPLRecords.get(j);
                    if(j==i){
                        continue;
                    }
                    if(hzEPLRecordCheck.getPartId().equals(hzEPLRecord.getPartId())){
                        repetition.add(hzEPLRecordCheck.getPartId());
                        msg.append("导入的Excel中第 "+(i+2)+"行和第 "+(j+2)+"存在重复零件号“"+hzEPLRecordCheck.getPartId()+"”<br/>");
                    }
                }
            }
            if(repetition.size()>0){
                msg.append("请修改Excel");
                result.put("status",false);
                result.put("msg",msg.toString());
                return result;
            }


            //对比excel和数据库中的数据，如存在则修改
            List<HzEPLRecord> hzEPLRecordsUpdate = new ArrayList<>();
            List<HzEPLRecord> hzEPLRecordList = hzEPLDAO.selectByprojectPuid(projectPuid);


            Iterator<HzEPLRecord> iterator = hzEPLRecords.iterator();
            while (iterator.hasNext()){
                HzEPLRecord hzEPLRecordExcel = iterator.next();
                for(HzEPLRecord  hzEPLRecord : hzEPLRecordList){
                    if(hzEPLRecordExcel.getPartId().equals(hzEPLRecord.getPartId())){
                        hzEPLRecordExcel.setId(hzEPLRecord.getId());
                        hzEPLRecordsUpdate.add(hzEPLRecordExcel);
                        iterator.remove();
                    }
                }
            }

            if(hzEPLRecordsUpdate!=null&&hzEPLRecordsUpdate.size()>0){
                try {
                    hzEPLDAO.updateList(hzEPLRecordsUpdate);
                }catch (Exception e){
                    result.put("status",false);
                    result.put("msg","修改Excle导入数据失败");
                    return result;
                }
            }

            if(hzEPLRecords!=null&&hzEPLRecords.size()>0){
                if(hzEPLRecords.size()>1000){
                    List<List<HzEPLRecord>> hzEPLRecordsInser = new ArrayList<>();
                    int insertIndex = 0;
                    List<HzEPLRecord> hzEPLRecords1 = new ArrayList<>();
                    for(int i=0;i<hzEPLRecords.size();i++){
                        if(insertIndex<1000){
                            hzEPLRecords1.add(hzEPLRecords.get(i));
                            insertIndex++;
                        }else {
                            insertIndex = 0;
                            hzEPLRecordsInser.add(hzEPLRecords1);
                            hzEPLRecords1 = new ArrayList<>();
                            hzEPLRecords1.add(hzEPLRecords.get(i));
                        }
                    }
                    if(hzEPLRecords1.size()>0){
                        hzEPLRecordsInser.add(hzEPLRecords1);
                    }

                    for(List<HzEPLRecord> hzEPLRecordList1 : hzEPLRecordsInser){
                        if (hzEPLDAO.insertList(hzEPLRecordList1) <= 0 ? true : false) {
                            result.put("status", false);
                            result.put("msg", "Excle导入数据失败");
                            return result;
                        }
                    }
                }else {
                    if (hzEPLDAO.insertList(hzEPLRecords) <= 0 ? true : false) {
                        result.put("status", false);
                        result.put("msg", "Excle导入数据失败");
                        return result;
                    }
                }
            }

        }catch (Exception e){
            e.printStackTrace();
            result.put("status",false);
            result.put("msg",e.getMessage());
            return result;
        }

        return result;
    }
}
