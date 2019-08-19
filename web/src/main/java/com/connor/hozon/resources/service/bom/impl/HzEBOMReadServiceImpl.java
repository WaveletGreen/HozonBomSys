package com.connor.hozon.resources.service.bom.impl;

import cn.net.connor.hozon.dao.dao.configuration.fullConfigSheet.HzFullCfgMainDao;
import cn.net.connor.hozon.dao.dao.configuration.fullConfigSheet.HzFullCfgWithCfgDao;
import cn.net.connor.hozon.dao.pojo.bom.epl.HzEPLManageRecord;
import cn.net.connor.hozon.dao.pojo.configuration.fullConfigSheet.HzConfigToBomLine;
import cn.net.connor.hozon.dao.pojo.configuration.fullConfigSheet.HzExFullCfgWithCfg;
import cn.net.connor.hozon.dao.pojo.configuration.fullConfigSheet.HzFullCfgMain;
import cn.net.connor.hozon.dao.pojo.configuration.model.HzCfg0ModelRecord;
import cn.net.connor.hozon.dao.query.configuration.fullConfigSheet.HzFullCfgWithCfgQuery;
import cn.net.connor.hozon.services.service.configuration.fullConfigSheet.impl.HzCfg0ModelServiceImpl;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.connor.hozon.dao.bom.bom.impl.HzBomLineRecordDaoImpl;
import com.connor.hozon.service.configuration.fullCfg.HzConfigToBomLineService;
import com.connor.hozon.resources.domain.dto.response.HzEbomLevelRespDTO;
import com.connor.hozon.resources.domain.dto.response.HzEbomRespDTO;
import com.connor.hozon.resources.domain.dto.response.HzLouRespDTO;
import com.connor.hozon.resources.domain.model.HzBomSysFactory;
import com.connor.hozon.resources.domain.model.HzEbomRecordFactory;
import com.connor.hozon.resources.domain.query.HzEbomByPageQuery;
import com.connor.hozon.resources.domain.query.HzEbomTreeQuery;
import com.connor.hozon.resources.domain.query.HzLouaQuery;
import com.connor.hozon.resources.mybatis.bom.HzEbomRecordDAO;
import com.connor.hozon.resources.page.Page;
import com.connor.hozon.resources.service.bom.HzEBOMReadService;
import com.connor.hozon.resources.service.bom.HzSingleVehiclesServices;
import com.connor.hozon.resources.service.epl.HzEPLManageRecordService;
import com.connor.hozon.resources.util.Result;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

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
    private HzConfigToBomLineService hzConfigToBomLineServiceImpl;

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
    private HzCfg0ModelServiceImpl hzCfg0ModelServiceImpl;


    @Override
    public Page<HzEbomRespDTO> getHzEbomPage(HzEbomByPageQuery query) {
        try {
            int num = (query.getPage() - 1) * query.getPageSize();
            HzEbomRespDTO recordRespDTO = new HzEbomRespDTO();
            JSONArray array = new JSONArray();
            List<HzEbomRespDTO> recordRespDTOList = new ArrayList<>();
            query = HzBomSysFactory.bomQueryLevelTrans(query);
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
            List<HzCfg0ModelRecord> hzCfg0ModelRecords = hzCfg0ModelServiceImpl.doSelectByProjectPuid(query.getProjectId());
            for (HzEPLManageRecord record : records) {
                JSONObject jsonObject = HzEbomRecordFactory.bomLineRecordTORespDTO(record);
//                //获取分组号
                String groupNum = record.getLineID();
                try {
                    if (groupNum.contains("-")) {
                        groupNum = groupNum.split("-")[1].substring(0, 4);
                    } else {
                        groupNum = "";
                    }
                } catch (Exception e) {
                    groupNum = "";
                }
                jsonObject.put("No", ++num);
                jsonObject.put("groupNum",groupNum);
                //单车用量
                jsonObject = hzSingleVehiclesServices.singleVehNum(record.getVehNum(),hzCfg0ModelRecords,jsonObject);
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
            List<HzCfg0ModelRecord> hzCfg0ModelRecords = hzCfg0ModelServiceImpl.doSelectByProjectPuid(projectId);
            if (record != null) {
                respDTO = HzEbomRecordFactory.eplRecordToEbomRespDTO(record);
                JSONObject object1 = hzSingleVehiclesServices.singleVehNum(record.getVehNum(),hzCfg0ModelRecords);
                respDTO.setMap(object1);
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
        return hzEbomRecordDAO.getHzBomLineChildren(query);
    }

    @Override
    public HzEPLManageRecord findParentFor2Y(String projectId, String puid) {
        HzEPLManageRecord record = hzEbomRecordDAO.findEbomById(puid,projectId);
        if (record != null) {
            if (Integer.valueOf(1).equals(record.getIs2Y())) {
                return record;
            } else if (StringUtils.isBlank(record.getParentUid())) {
                return record;
            } else {
                return findParentFor2Y(projectId, record.getParentUid());
            }
        } else {
            return null;
        }

    }


    @Override
    public HzLouRespDTO getHzLouInfoById(HzLouaQuery query) {
        try {
            HzLouRespDTO respDTO = new HzLouRespDTO();
            HzEPLManageRecord hzBomLineRecord = findParentFor2Y(query.getProjectId(), query.getPuid());
            if (hzBomLineRecord != null) {
                HzConfigToBomLine record = hzConfigToBomLineServiceImpl.selectByBLUidAndPrjUid(query.getProjectId(), hzBomLineRecord.getPuid());
                if (record != null) {
                    respDTO.setCfg0Desc(record.getFeatureValueDesc());
                    respDTO.setCfg0FamilyDesc(record.getFeatureDesc());
                    respDTO.setpCfg0name(record.getFeatureValueCode());
                    respDTO.setpCfg0familyname(record.getFeatureCode());
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
            if (null != (cfg = hzFullCfgWithCfgDao.selectByBLOutWithCfgAndBL(new HzFullCfgWithCfgQuery(hzFullCfgMain.getId(), puids.get(i))))) {
                if (cfg.getCfg() == null) {
                    continue;
                }
                sb.append("2Y层:" + cfg.getBomLine().getLineID() + " 已关联特性值" + cfg.getCfg().getFeatureValueCode() + "，请在全配置BOM一级清单中将" + cfg.getBomLine().getLineID() + "去除绑定特性值<br>");
            }
        }
        String errMsg = sb.toString();
        if(StringUtils.isBlank(errMsg)){
            return  Result.build(true,null);
        }
        return Result.build(false,sb.toString());
    }

}
