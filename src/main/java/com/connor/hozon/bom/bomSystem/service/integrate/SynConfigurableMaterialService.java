/*
 * Copyright (c) 2018.
 * This file was written by fancyears·milos·malvis @connor. Any question/bug you can post to 1243093366@qq.com.
 * ALL RIGHTS RESERVED.
 */

package com.connor.hozon.bom.bomSystem.service.integrate;

import com.connor.hozon.bom.bomSystem.dao.derivative.HzCfg0ModelGroupDao;
import com.connor.hozon.bom.bomSystem.dao.derivative.HzCfg0ToModelRecordDao;
import com.connor.hozon.bom.bomSystem.dto.HzMaterielFeatureBean;
import com.connor.hozon.bom.bomSystem.helper.IntegrateMsgDTO;
import com.connor.hozon.bom.bomSystem.helper.UUIDHelper;
import com.connor.hozon.bom.bomSystem.service.main.HzCfg0MainService;
import com.connor.hozon.bom.bomSystem.service.cfg.HzCfg0OptionFamilyService;
import com.connor.hozon.bom.bomSystem.service.cfg.HzCfg0Service;
import com.connor.hozon.bom.bomSystem.service.project.HzSuperMaterielService;
import integration.base.classify.ZPPTCO003;
import integration.logic.ConfigurableMaterialAllocation;
import integration.option.ActionFlagOption;
import integration.service.impl.classify3.TransClassifyService;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sql.pojo.cfg.derivative.HzCfg0ToModelRecord;
import sql.pojo.cfg.main.HzCfg0MainRecord;
import sql.pojo.project.HzMaterielRecord;

import java.util.*;
/**
 * @Author: Fancyears·Maylos·Malvis
 * @Description: fuck
 * @Date: Created in 2018/9/6 13:19
 * @Modified By:
 */
@Service("synConfigurableMaterialService")
public class SynConfigurableMaterialService {

    @Autowired
    private HzCfg0MainService hzCfg0MainService;
    @Autowired
    private HzSuperMaterielService hzSuperMaterielService;
    @Autowired
    private HzCfg0OptionFamilyService hzCfg0OptionFamilyService;
    @Autowired
    private HzCfg0Service hzCfg0Service;
    @Autowired
    TransClassifyService transClassifyService;
    @Autowired
    HzCfg0ToModelRecordDao hzCfg0ToModelRecordDao;
    /**
     * 模型族dao层
     */
    @Autowired
    HzCfg0ModelGroupDao hzCfg0ModelGroupDao;
    /**
     * 日志记录
     */
    private final static Logger logger = LoggerFactory.getLogger(SynConfigurableMaterialService.class);

    /**
     * 新增
     *
     * @param puid              表 Hz_Cfg0_Model_Record 的主键 PUID
     * @param cfg0MainPuids     表 HZ_CFG0_MAIN_RECORD   的主键 PUID
     * @param modeBasiceDetails 配置物料编码    表  Hz_Cfg0_Model_Record  的  OBJECT_NAME
     * @param projectPuid       项目ID         表   hz_cfg0_main_record 的 p_cfg0_of_which_project_puid
     * @return
     */
    public JSONObject addConfigurableMaterial(String[] puid, String[] cfg0MainPuids, String[] modeBasiceDetails, String projectPuid) {
        return execute(puid, cfg0MainPuids, modeBasiceDetails, projectPuid, ActionFlagOption.ADD);
    }

    /**
     * 更新，一般不删除
     *
     * @param puid              表 Hz_Cfg0_Model_Record 的主键 PUID
     * @param cfg0MainPuids     表 HZ_CFG0_MAIN_RECORD   的主键 PUID
     * @param modeBasiceDetails 配置物料编码    表  Hz_Cfg0_Model_Record  的  OBJECT_NAME
     * @param projectPuid       项目ID         表   hz_cfg0_main_record 的 p_cfg0_of_which_project_puid
     * @return
     */
    public JSONObject deleteConfigurableMaterial(String[] puid, String[] cfg0MainPuids, String[] modeBasiceDetails, String projectPuid) {
        return execute(puid, cfg0MainPuids, modeBasiceDetails, projectPuid, ActionFlagOption.DELETE);
    }

    /**
     * 核心方法
     *
     * @param puids             表 Hz_Cfg0_Model_Record 的主键 PUID
     * @param cfg0MainPuids     表 HZ_CFG0_MAIN_RECORD   的主键 PUID
     * @param modeBasiceDetails 配置物料编码    表  Hz_Cfg0_Model_Record  的  OBJECT_NAME
     * @param projectPuid       项目ID         表   hz_cfg0_main_record 的 p_cfg0_of_which_project_puid
     * @param option            动作标志
     * @return
     */
    private JSONObject execute(String[] puids, String[] cfg0MainPuids, String[] modeBasiceDetails, String projectPuid, ActionFlagOption option) {
        transClassifyService.setClearInputEachTime(true);
        transClassifyService.getInput().getItem().clear();

        //需要更新的数据，更新特性属性
        List<HzCfg0ToModelRecord> needToUpdateStatus = new ArrayList<>();
        JSONObject result = new JSONObject();
        /**
         * 成功项
         */
        List<IntegrateMsgDTO> success = new ArrayList<>();
        /**
         * 失败项
         */
        List<IntegrateMsgDTO> fail = new ArrayList<>();

        List<String> _forDelete = new ArrayList<>();
        /***
         * 计数
         */
        int total = 0;
        int totalOfSuccess = 0;
        int totalOfFail = 0;
        int totalOfOutOfParent = 0;
        int totalOfUnknown = 0;

        if (puids == null || puids.length <= 0 || cfg0MainPuids == null || cfg0MainPuids.length <= 0) {
            result.put("status", false);
            result.put("msg", "选择的列为空，请至少选择1列发送");
            return result;
        }
        if (puids.length != cfg0MainPuids.length) {
            result.put("status", false);
            result.put("msg", "前端数据传输错误");
            return result;
        }
        Map<String, HzCfg0ToModelRecord> coach = new HashMap<String, HzCfg0ToModelRecord>();
        Map<String, String> packNumOfFeature = new HashMap<String, String>();

        String packnum = UUIDHelper.generateUpperUid();

        for (int i = 0; i < puids.length; i++) {
            //没有父层的puid
            if (!packNumOfFeature.containsKey(puids[i])) {
                //添加父层puid和包号的对应关系
                packnum = UUIDHelper.generateUpperUid();
                packNumOfFeature.put(puids[i], packnum);
            }
            if (!coach.containsKey(packNumOfFeature.get(puids[i]))) {
                HzCfg0ToModelRecord hzCfg0ToModelRecord = new HzCfg0ToModelRecord();
                hzCfg0ToModelRecord.setPuid(puids[i]);
                hzCfg0ToModelRecord.setpOfCfg0MainRecord(modeBasiceDetails[i]);
                coach.put(packNumOfFeature.get(puids[i]), hzCfg0ToModelRecord);
            }
            List<String> column = hzCfg0OptionFamilyService.doGetColumnDef(projectPuid, "<br/>");
            List<HzMaterielFeatureBean> hzMaterielFeatureBeans = hzCfg0Service.doSelectMaterielFeatureByProjectPuid(projectPuid);


            Map<String, HzMaterielFeatureBean> sortedBean = new HashMap<>();

            hzMaterielFeatureBeans.stream().filter(_b -> _b.getpCfg0ModelRecord() != null).forEach(_b -> sortedBean.put(_b.getpCfg0ModelRecord() + "=" + _b.getpCfg0FamilyDesc() + "<br/>" + _b.getpCfg0FamilyName(), _b));

            if (hzMaterielFeatureBeans == null || column == null || column.size() == 0) {
                continue;
            } else {
                //获取族
                String groupName = hzCfg0ModelGroupDao.selectGroupNameByMainUid(cfg0MainPuids[i]);
                //超级物料编码
                HzCfg0MainRecord mainRecord = hzCfg0MainService.doGetByPrimaryKey(cfg0MainPuids[i]);
                HzMaterielRecord superMateriel = null;
                if (mainRecord != null) {
                    superMateriel = hzSuperMaterielService.doSelectByProjectPuid(mainRecord.getpCfg0OfWhichProjectPuid());
                }
                for (HzMaterielFeatureBean hzMaterielFeatureBean : hzMaterielFeatureBeans) {
                    if (hzMaterielFeatureBean.getpCfg0ModelRecord() != null && hzMaterielFeatureBean.getpCfg0ModelRecord().equals(puids[i])) {

                        ConfigurableMaterialAllocation configurableMaterialAllocation = new ConfigurableMaterialAllocation();
                        //特性编码
                        configurableMaterialAllocation.setPeculiarityEncoding(hzMaterielFeatureBean.getpCfg0FamilyName());
                        //特性编码值
                        configurableMaterialAllocation.setPeculiarityValueEncoding(hzMaterielFeatureBean.getpCfg0ObjectId());
                        //超级物料编码
                        configurableMaterialAllocation.setPitem(superMateriel.getpMaterielCode());
                        //数据包号
                        configurableMaterialAllocation.setPackNo(packnum);
                        //配置物料编码
                        configurableMaterialAllocation.setConfigurableMaterialEncoding(modeBasiceDetails[i]);
                        //添加族
                        configurableMaterialAllocation.setModelClass(groupName);
                        //动作描述
                        if (option == ActionFlagOption.ADD) {
                            if (hzMaterielFeatureBean.getIsSent() == null || hzMaterielFeatureBean.getIsSent() == 0) {
                                configurableMaterialAllocation.setActionFlag(ActionFlagOption.ADD.GetDesc());
                            } else if (hzMaterielFeatureBean.getIsSent() == 1) {
                                configurableMaterialAllocation.setActionFlag(ActionFlagOption.UPDATE.GetDesc());
                            }
                        }
                        //执行删除
                        else {
                            configurableMaterialAllocation.setActionFlag(option.GetDesc());
                        }
                        transClassifyService.getInput().getItem().add(configurableMaterialAllocation.getZpptci003());
                    }
                }
            }
        }

        if (!SynMaterielService.debug) {
            transClassifyService.execute();
        }

        List<ZPPTCO003> list = transClassifyService.getOut().getItem();

        try {
            if (list != null && list.size() > 0) {
                for (ZPPTCO003 _l : list) {
                    total++;
                    if (_l == null) {
                        totalOfUnknown++;
                        continue;
                    }
                    IntegrateMsgDTO dto = new IntegrateMsgDTO();
                    HzCfg0ToModelRecord record = coach.get(_l.getPPACKNO());
                    dto.setPuid(record.getPuid());
                    dto.setItemId(record.getpOfCfg0MainRecord());
                    dto.setMsg(_l.getPMESSAGE());
                    if ("S".equalsIgnoreCase(_l.getPTYPE())) {
                        success.add(dto);
                        totalOfSuccess++;
                        needToUpdateStatus.add(record);
                    } else {
                        fail.add(dto);
                        totalOfFail++;
                    }
                }
                Map<String, Object> _map = new HashMap<>();
                //设定需要更新特性值已发送,不用设定相关性值已发送
                _map.put("isFeatureSent", 1);
                _map.put("list", needToUpdateStatus);
                if (needToUpdateStatus.size() > 0) {
                    List<HzCfg0ToModelRecord> hzCfg0ToModelRecordList = new ArrayList<HzCfg0ToModelRecord>();
                    Set<String> keySet = coach.keySet();
                    for (String key : keySet) {
                        hzCfg0ToModelRecordList.add(coach.get(key));
                    }
                    hzCfg0ToModelRecordDao.setIsSent(hzCfg0ToModelRecordList);
                }
            }
        } catch (Exception e) {
            logger.error("发送特性到ERP失败", e);
        }


        result.put("success", success);
        result.put("fail", fail);
        result.put("total", total);
        result.put("totalOfSuccess", totalOfSuccess);
        result.put("totalOfFail", totalOfFail);
        result.put("totalOfOutOfParent", totalOfOutOfParent);
        result.put("totalOfUnknown", totalOfUnknown);
        result.put("_forDelete", _forDelete);
        return result;
    }
}
