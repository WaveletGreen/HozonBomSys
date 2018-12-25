package com.connor.hozon.bom.resources.service.bom.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.connor.hozon.bom.bomSystem.bean.HzExFullCfgWithCfg;
import com.connor.hozon.bom.bomSystem.dao.bom.HzBomMainRecordDao;
import com.connor.hozon.bom.bomSystem.dao.fullCfg.HzFullCfgMainDao;
import com.connor.hozon.bom.bomSystem.dao.fullCfg.HzFullCfgWithCfgDao;
import com.connor.hozon.bom.bomSystem.impl.bom.HzBomLineRecordDaoImpl;
import com.connor.hozon.bom.bomSystem.service.fullCfg.HzCfg0ModelService;
import com.connor.hozon.bom.bomSystem.service.fullCfg.HzCfg0OfBomLineService;
import com.connor.hozon.bom.resources.domain.dto.request.*;
import com.connor.hozon.bom.resources.domain.dto.response.HzEbomLevelRespDTO;
import com.connor.hozon.bom.resources.domain.dto.response.HzEbomRespDTO;
import com.connor.hozon.bom.resources.domain.dto.response.HzLouRespDTO;
import com.connor.hozon.bom.resources.domain.dto.response.WriteResultRespDTO;
import com.connor.hozon.bom.resources.domain.model.*;
import com.connor.hozon.bom.resources.domain.query.*;
import com.connor.hozon.bom.resources.enumtype.ChangeTableNameEnum;
import com.connor.hozon.bom.resources.executors.ExecutorServices;
import com.connor.hozon.bom.resources.mybatis.bom.HzEbomRecordDAO;
import com.connor.hozon.bom.resources.mybatis.bom.HzMbomRecordDAO;
import com.connor.hozon.bom.resources.mybatis.bom.HzPbomRecordDAO;
import com.connor.hozon.bom.resources.mybatis.change.HzChangeDataRecordDAO;
import com.connor.hozon.bom.resources.mybatis.epl.HzEPLDAO;
import com.connor.hozon.bom.resources.page.Page;
import com.connor.hozon.bom.resources.service.bom.HzEBOMReadService;
import com.connor.hozon.bom.resources.service.bom.HzMbomService;
import com.connor.hozon.bom.resources.service.bom.HzSingleVehiclesServices;
import com.connor.hozon.bom.resources.service.epl.HzEPLManageRecordService;
import com.connor.hozon.bom.resources.util.ListUtil;
import com.connor.hozon.bom.resources.util.Result;
import com.connor.hozon.bom.resources.util.StringUtil;
import com.google.common.collect.Lists;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sql.pojo.bom.*;
import sql.pojo.cfg.fullCfg.HzCfg0OfBomLineRecord;
import sql.pojo.cfg.fullCfg.HzFullCfgMain;
import sql.pojo.cfg.model.HzCfg0ModelRecord;
import sql.pojo.change.HzChangeDataRecord;
import sql.pojo.epl.HzEPLManageRecord;
import sql.redis.SerializeUtil;

import java.util.*;

/**
 * Created by haozt on 2018/06/06
 */
@Service("hzEBOMReadService")
@Transactional(rollbackFor={IllegalArgumentException.class})
public class HzEBOMReadServiceImpl implements HzEBOMReadService {

    @Autowired
    private HzEbomRecordDAO hzEbomRecordDAO;

    @Autowired
    private HzBomLineRecordDaoImpl hzBomLineRecordDao;

    @Autowired
    private HzCfg0OfBomLineService hzCfg0OfBomLineService;

    @Autowired
    private HzEPLManageRecordService hzEPLManageRecordService;

    /**
     * 特性关联2Y
     */
    @Autowired
    HzFullCfgWithCfgDao hzFullCfgWithCfgDao;
    /**
     * 全配置主配置
     */
    @Autowired
    HzFullCfgMainDao hzFullCfgMainDao;

    @Autowired
    private HzSingleVehiclesServices hzSingleVehiclesServices;

    @Autowired
    private HzCfg0ModelService hzCfg0ModelService;


    @Override
    public Page<HzEbomRespDTO> getHzEbomPage(HzEbomByPageQuery query) {
        try {
            int num = (query.getPage() - 1) * query.getPageSize();
            HzEbomRespDTO recordRespDTO = new HzEbomRespDTO();
            JSONArray array = new JSONArray();
            List<HzEbomRespDTO> recordRespDTOList = new ArrayList<>();
            String level = query.getLevel().trim();
            if (StringUtils.isNotBlank(level)) {
                if (level.toUpperCase().endsWith("Y")) {
                    int length = Integer.valueOf(level.replace("Y", ""));
                    query.setIsHas(1);
                    query.setLineIndex(String.valueOf(length - 1));
                } else {
                    query.setIsHas(0);
                    int length = Integer.valueOf(level);
                    query.setLineIndex(String.valueOf(length));
                }
            }
            Page<HzEPLManageRecord> recordPage;
            if(Integer.valueOf(1).equals(query.getShowBomStructure())){
                //展示BOM结构树 当前查询树结构平铺
                recordPage = hzEbomRecordDAO.getHzEbomTreeByPage(query);
            }else {
                //展示全部平铺结构
                recordPage = hzEbomRecordDAO.getHzEbomPage(query);
            }
            if (recordPage == null || recordPage.getResult() == null || recordPage.getResult().size() == 0) {
                return new Page<>(recordPage.getPageNumber(), recordPage.getPageSize(), 0);
            }

            List<HzEPLManageRecord> records = recordPage.getResult();
//            List<HzCfg0ModelRecord> hzCfg0ModelRecords = hzCfg0ModelService.doSelectByProjectPuid(query.getProjectId());
            for (HzEPLManageRecord record : records) {
                JSONObject jsonObject = HzEbomRecordFactory.bomLineRecordTORespDTO(record);
//                //获取分组号
//                String fastener = record.getpFastener();
//                String groupNum = record.getLineID();
//                try {
//                    if (StringUtil.isEmpty(fastener)||fastener.equals("/")) {
//                        if (groupNum.contains("-")) {
//                            groupNum = groupNum.split("-")[1].substring(0, 4);
//                        } else {
//                            groupNum = "";
//                        }
//
//                    } else {
//                        String parentId = record.getParentUid();
//                        groupNum = hzEPLManageRecordService.getGroupNum(query.getProjectId(), parentId);
//                    }
//                } catch (Exception e) {
//                    groupNum = "";
//                }
                jsonObject.put("No", ++num);
//                jsonObject.put("groupNum",groupNum);
                //单车用量 mmp 怎么设计感觉都不合理
//                List<HzSingleVehicleDosage> singleVehicleDosages = hzSingleVehicleDosageDAO.findSingleVehicleByBomPuid(record.getPuid(),query.getProjectId());
//                if(ListUtil.isNotEmpty(singleVehicleDosages) && ListUtil.isNotEmpty(hzCfg0ModelRecords)){
//                    for(int i = 0;i<hzCfg0ModelRecords.size();i++){
//                       for(HzSingleVehicleDosage dosage :singleVehicleDosages){
//                           if(hzCfg0ModelRecords.get(i).getPuid().equals(dosage.getCfg0ModelPuid())){
//                               jsonObject.put("title"+i,dosage.getDosage());
//                               break;
//                           }
//                       }
//                    }
//                }

//                if(null != record.getSingleVehDosage() && ListUtil.isNotEmpty(hzCfg0ModelRecords)){
//                    jsonObject = hzSingleVehiclesServices.singleVehDosage(record.getSingleVehDosage(),hzCfg0ModelRecords,jsonObject);
//                }
                array.add(jsonObject);
            }
            recordRespDTO.setJsonArray(array);
            recordRespDTOList.add(recordRespDTO);
            return new Page<>(recordPage.getPageNumber(), recordPage.getPageSize(), recordPage.getTotalCount(), recordRespDTOList);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public HzEbomRespDTO fingEbomById(String puid, String projectId) {
        try {
            HzEPLManageRecord record = hzEbomRecordDAO.findEbomById(puid, projectId);
            HzEbomRespDTO respDTO = new HzEbomRespDTO();
            List<HzCfg0ModelRecord> hzCfg0ModelRecords = hzCfg0ModelService.doSelectByProjectPuid(projectId);

            if (record != null) {
                respDTO = HzEbomRecordFactory.eplRecordToEbomRespDTO(record);
                Map<String,Object> object = new HashMap<>();
                if(ListUtil.isNotEmpty(hzCfg0ModelRecords)){
                    if(null != record.getSingleVehDosage()){
                        byte[] singleVehDosage = record.getSingleVehDosage();
                        Object obj = SerializeUtil.unserialize(singleVehDosage);
                        if(obj instanceof Map){
                            Map<String,Object> map = (Map)obj;
                            if(map.size()>0){
                                for(int i = 0;i<hzCfg0ModelRecords.size();i++){
                                    boolean find = false;
                                    for(Map.Entry<String,Object> entry:map.entrySet()){
                                        if(hzCfg0ModelRecords.get(i).getPuid().equals(entry.getKey())){
                                            find = true;
                                            if(null !=entry.getValue() && ""!=entry.getValue()){
                                                object.put(hzCfg0ModelRecords.get(i).getObjectName(),entry.getValue());
                                                break;
                                            }else {
                                                object.put(hzCfg0ModelRecords.get(i).getObjectName(),"");
                                                break;
                                            }

//                                        jsonArray.add(new JSONObject().put("title"+i,entry.getValue()));
                                        }
                                    }
                                    if(!find){
                                        object.put(hzCfg0ModelRecords.get(i).getObjectName(),"");
                                    }
                                }
                            }
                        }
                    }else {
                        for(HzCfg0ModelRecord cfg0ModelRecord :hzCfg0ModelRecords){
                            object.put(cfg0ModelRecord.getObjectName(),"");
                        }
                    }
                }
                respDTO.setMap(object);
            }
            return respDTO;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public HzEbomLevelRespDTO fingEbomLevelById(String puid, String projectId) {
        try {
            HzEPLManageRecord record = hzEbomRecordDAO.findEbomById(puid, projectId);
            HzEbomLevelRespDTO respDTO = new HzEbomLevelRespDTO();
            if (record != null) {
                JSONArray jsonArray = new JSONArray();
                JSONObject jsonObject = HzEbomRecordFactory.bomLineRecordTORespDTO(record);
                jsonArray.add(jsonObject);
                respDTO.setJsonArray(jsonArray);
            }
            return respDTO;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public int findIsHasByPuid(String puid, String projectId) {
        int list = hzEbomRecordDAO.findIsHasByPuid(puid, projectId);
        if (list == 0)
            return 0;
        else
            return 1;
    }

    @Override
    public List<HzEPLManageRecord> findCurrentBomChildren(HzEbomTreeQuery query) {
        List<HzEPLManageRecord> records = hzEbomRecordDAO.getHzBomLineChildren(query);
        return records;
    }

    @Override
    public HzBomLineRecord findParentFor2Y(String projectId, String puid) {
        Map<String, Object> map = new HashMap<>();
        map.put("projectId", projectId);
        map.put("puid", puid);
        List<HzBomLineRecord> records = hzBomLineRecordDao.findBomListForChange(map);
        if (ListUtil.isNotEmpty(records)) {
            if (records.get(0).getIs2Y().equals(1)) {
                return records.get(0);
            } else if (records.get(0).getParentUid() == null) {
                return records.get(0);
            } else {
                return findParentFor2Y(projectId, records.get(0).getParentUid());
            }
        } else {
            return null;
        }

    }


    @Override
    public HzLouRespDTO getHzLouInfoById(HzLouaQuery query) {
        try {
            HzLouRespDTO respDTO = new HzLouRespDTO();
            HzBomLineRecord hzBomLineRecord = findParentFor2Y(query.getProjectId(), query.getPuid());
            if (hzBomLineRecord != null) {
                HzCfg0OfBomLineRecord record = hzCfg0OfBomLineService.doSelectByBLUidAndPrjUid(query.getProjectId(), hzBomLineRecord.getPuid());
                if (record != null) {
                    respDTO.setCfg0Desc(record.getCfg0Desc());
                    respDTO.setCfg0FamilyDesc(record.getCfg0FamilyDesc());
                    respDTO.setpCfg0name(record.getpCfg0name());
                    respDTO.setpCfg0familyname(record.getpCfg0familyname());
                    return respDTO;
                }
            }
            return respDTO;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 检查是否关联特性
     * @param puids BOM 端数据
     * @param projectUid 项目id
     * @return
     */
    @Override
    public Result checkConnectWithFeature(List<String> puids, String projectUid) {
        HzFullCfgMain hzFullCfgMain = hzFullCfgMainDao.selectByProjectId(projectUid);
        StringBuffer sb = new StringBuffer();
        if(hzFullCfgMain == null){
            return Result.build(true,null) ;
        }
        HzExFullCfgWithCfg cfg = null;
        for (int i = 0; i < puids.size(); i++) {
            if (null != (cfg = hzFullCfgWithCfgDao.selectByBLOutWithCfgAndBL(hzFullCfgMain.getId(), puids.get(i)))) {
                if (cfg.getCfg() == null) {
                    continue;
                }
                sb.append("2Y层:" + cfg.getBomLine().getLineID() + " 已关联特性值" + cfg.getCfg().getpCfg0ObjectId() + "，请在全配置BOM一级清单中将" + cfg.getBomLine().getLineID() + "去除绑定特性值<br>");
            }
        }
        String errMsg = sb.toString();
        if(StringUtils.isBlank(errMsg)){
            return  Result.build(true,null);
        }
        return Result.build(false,sb.toString());
    }

}
