/*
 *
 *  * Copyright (c) 2019 Author: Fancyears Milos Malvis @1243093366@qq.com
 *  * ALL COPYRIGHT REVERSED.
 *
 */

package integration.service.integrate.impl;

import cn.net.connor.hozon.dao.dao.configuration.derivative.HzCfg0ModelFeatureDao;
import cn.net.connor.hozon.dao.dao.configuration.derivative.HzCfg0ModelGroupDao;
import cn.net.connor.hozon.dao.dao.configuration.derivative.HzCfg0ToModelRecordDao;
import cn.net.connor.hozon.dao.dao.configuration.derivative.HzDerivativeMaterielDetailDao;
import cn.net.connor.hozon.dao.pojo.bom.materiel.HzMaterielRecord;
import cn.net.connor.hozon.dao.pojo.configuration.derivative.HzCfg0ModelFeature;
import cn.net.connor.hozon.dao.pojo.configuration.derivative.HzCfg0ToModelRecord;
import cn.net.connor.hozon.dao.pojo.configuration.derivative.HzDerivativeMaterielBasic;
import cn.net.connor.hozon.dao.pojo.configuration.derivative.HzDerivativeMaterielDetail;
import cn.net.connor.hozon.dao.pojo.configuration.feature.HzMaterielFeatureBean;
import cn.net.connor.hozon.dao.pojo.main.HzMainConfig;
import cn.net.connor.hozon.services.service.depository.project.impl.HzSuperMaterielServiceImpl;
import cn.net.connor.hozon.services.service.main.HzMainConfigService;
import cn.net.connor.hozon.services.response.integration.IntegrateMsgResponseDTO;
import cn.net.connor.hozon.common.util.UUIDHelper;
import com.connor.hozon.bom.bomSystem.service.cfg.HzCfg0OptionFamilyService;
import com.connor.hozon.bom.bomSystem.service.cfg.HzFeatureServiceImpl;
import integration.base.classify.ZPPTCO003;
import integration.logic.ConfigurableMaterialAllocation;
import integration.option.ActionFlagOption;
import integration.service.impl.classify3.TransClassifyService;
import integration.service.integrate.SynConfigurableMaterialService;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
/**
 * @Author: Fancyears·Maylos·Malvis
 * @Description: fuck
 * @Date: Created in 2018/9/6 13:19
 * @Modified By:
 */
@Service
@Slf4j
public class SynConfigurableMaterialServiceImpl implements SynConfigurableMaterialService {

    @Autowired
    private HzMainConfigService hzMainConfigService;
    @Autowired
    private HzSuperMaterielServiceImpl hzSuperMaterielServiceImpl;
    @Autowired
    private HzCfg0OptionFamilyService hzCfg0OptionFamilyService;
    @Autowired
    private HzFeatureServiceImpl hzFeatureServiceImpl;
    @Autowired
    TransClassifyService transClassifyService;
    @Autowired
    HzCfg0ToModelRecordDao hzCfg0ToModelRecordDao;

    @Autowired
    HzDerivativeMaterielDetailDao hzDerivativeMaterielDetailDao;
    @Autowired
    HzCfg0ModelFeatureDao hzCfg0ModelFeatureDao;
    /**
     * 模型族dao层
     */
    @Autowired
    HzCfg0ModelGroupDao hzCfg0ModelGroupDao;


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
    public JSONObject execute(String[] puids, String[] cfg0MainPuids, String[] modeBasiceDetails, String projectPuid, ActionFlagOption option) {
        transClassifyService.setClearInputEachTime(true);
        transClassifyService.getInput().getItem().clear();

        //需要更新的数据，更新特性属性
        List<HzCfg0ToModelRecord> needToUpdateStatus = new ArrayList<>();
        JSONObject result = new JSONObject();
        /**
         * 成功项
         */
        List<IntegrateMsgResponseDTO> success = new ArrayList<>();
        /**
         * 失败项
         */
        List<IntegrateMsgResponseDTO> fail = new ArrayList<>();

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
            List<HzMaterielFeatureBean> hzMaterielFeatureBeans = hzFeatureServiceImpl.doSelectMaterielFeatureByProjectPuid(projectPuid);


            Map<String, HzMaterielFeatureBean> sortedBean = new HashMap<>();

            hzMaterielFeatureBeans.stream().filter(_b -> _b.getpCfg0ModelRecord() != null).forEach(_b -> sortedBean.put(_b.getpCfg0ModelRecord() + "=" + _b.getpCfg0FamilyDesc() + "<br/>" + _b.getpCfg0FamilyName(), _b));

            if (hzMaterielFeatureBeans == null || column == null || column.size() == 0) {
                continue;
            } else {
                //获取族
                String groupName = hzCfg0ModelGroupDao.selectGroupNameByMainUid(cfg0MainPuids[i]);
                //超级物料编码
                HzMainConfig mainRecord = hzMainConfigService.doGetByPrimaryKey(cfg0MainPuids[i]);
                HzMaterielRecord superMateriel = null;
                if (mainRecord != null) {
                    superMateriel = hzSuperMaterielServiceImpl.doSelectByProjectPuid(mainRecord.getProjectId());
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

        if (!SynMaterielServiceImpl.debug) {
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
                    IntegrateMsgResponseDTO dto = new IntegrateMsgResponseDTO();
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
            log.error("发送特性到ERP失败", e);
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

    public JSONObject add(List<HzDerivativeMaterielBasic> hzDerivativeMaterielBasics) {
        return execute2(hzDerivativeMaterielBasics, ActionFlagOption.ADD);
    }

    public JSONObject delete(List<HzDerivativeMaterielBasic> hzDerivativeMaterielBasics) {
        return execute2(hzDerivativeMaterielBasics, ActionFlagOption.DELETE);
    }

    public JSONObject execute2(List<HzDerivativeMaterielBasic> hzDerivativeMaterielBasics, ActionFlagOption option) {
        transClassifyService.setClearInputEachTime(true);
        transClassifyService.getInput().getItem().clear();

        //需要更新的数据，更新特性属性
        List<HzCfg0ModelFeature> needToUpdateStatus = new ArrayList<>();
        JSONObject result = new JSONObject();
        /**
         * 成功项
         */
        List<IntegrateMsgResponseDTO> success = new ArrayList<>();
        /**
         * 失败项
         */
        List<IntegrateMsgResponseDTO> fail = new ArrayList<>();

        List<String> _forDelete = new ArrayList<>();
        /***
         * 计数
         */
        int total = 0;
        int totalOfSuccess = 0;
        int totalOfFail = 0;
        int totalOfOutOfParent = 0;
        int totalOfUnknown = 0;

        if (hzDerivativeMaterielBasics == null || hzDerivativeMaterielBasics.size() <= 0) {
            result.put("status", false);
            result.put("msg", "传输数据为空，请至少选择1行发送");
            return result;
        }
        Map<String, HzCfg0ModelFeature> coach = new HashMap<String, HzCfg0ModelFeature>();
        Map<String, String> packNumOfFeature = new HashMap<String, String>();

        String packnum;

        //获取项目主数据
        HzMainConfig hzMainConfig = hzMainConfigService.selectByProjectId(hzDerivativeMaterielBasics.get(0).getDmbProjectUid());
        //获取族
        String groupName = hzCfg0ModelGroupDao.selectGroupNameByMainUid(hzMainConfig.getId());
        //超级物料编码
        HzMainConfig mainRecord = hzMainConfigService.doGetByPrimaryKey(hzMainConfig.getId());
        HzMaterielRecord superMateriel = null;
        if (mainRecord != null) {
            superMateriel = hzSuperMaterielServiceImpl.doSelectByProjectPuid(mainRecord.getProjectId());
        }

        List<HzCfg0ModelFeature> hzCfg0ModelFeatures = new ArrayList<>();
        for (int i = 0; i < hzDerivativeMaterielBasics.size(); i++) {
            packnum = UUIDHelper.generateUpperUid();

            List<HzDerivativeMaterielBasic> hzDerivativeMaterielBasicsList = new ArrayList<>();
            hzDerivativeMaterielBasicsList.add(hzDerivativeMaterielBasics.get(i));
            List<HzDerivativeMaterielDetail> hzDerivativeMaterielDetails = hzDerivativeMaterielDetailDao.selectByBasics(hzDerivativeMaterielBasicsList);

            HzCfg0ModelFeature hzCfg0ModelFeature = hzCfg0ModelFeatureDao.selectByPrimaryKey(hzDerivativeMaterielBasics.get(i).getDmbModelFeatureUid());
            hzCfg0ModelFeatures.add(hzCfg0ModelFeature);
            coach.put(packnum,hzCfg0ModelFeature);

            for (HzDerivativeMaterielDetail hzDerivativeMaterielDetail : hzDerivativeMaterielDetails) {

                    ConfigurableMaterialAllocation configurableMaterialAllocation = new ConfigurableMaterialAllocation();
                    //特性编码
                    configurableMaterialAllocation.setPeculiarityEncoding(hzDerivativeMaterielDetail.getCfg0Record().getpCfg0FamilyName());
                    //特性值编码
                    configurableMaterialAllocation.setPeculiarityValueEncoding(hzDerivativeMaterielDetail.getDmdFeatureValue());
                    //超级物料编码
                    configurableMaterialAllocation.setPitem(superMateriel.getpMaterielCode());
                    //数据包号
                    configurableMaterialAllocation.setPackNo(packnum);
                    //配置物料编码
                    configurableMaterialAllocation.setConfigurableMaterialEncoding(hzCfg0ModelFeature.getpFeatureSingleVehicleCode());
                    //添加族
                    configurableMaterialAllocation.setModelClass(groupName);
                    //动作描述
                    if (option == ActionFlagOption.ADD) {
                        if (hzCfg0ModelFeature.getIsSent() == null || hzCfg0ModelFeature.getIsSent() == 0) {
                            configurableMaterialAllocation.setActionFlag(ActionFlagOption.ADD.GetDesc());
                        } else if (hzCfg0ModelFeature.getIsSent() == 1) {
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

        if (!SynMaterielServiceImpl.debug) {
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
                    IntegrateMsgResponseDTO dto = new IntegrateMsgResponseDTO();
                    HzCfg0ModelFeature record = coach.get(_l.getPPACKNO());
                    dto.setPuid(record.getPuid());
                    dto.setItemId(record.getpFeatureSingleVehicleCode());
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
                    List<HzCfg0ModelFeature> hzCfg0ModelFeatureList = new ArrayList<HzCfg0ModelFeature>();
                    Set<String> keySet = coach.keySet();
                    for (String key : keySet) {
                        coach.get(key).setIsSent(1);
                        hzCfg0ModelFeatureList.add(coach.get(key));
                    }
                    hzCfg0ModelFeatureDao.updateIsSent(hzCfg0ModelFeatureList);
                }
            }
        } catch (Exception e) {
            log.error("发送特性到ERP失败", e);
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
