/*
 *
 *  * Copyright (c) 2019 Author: Fancyears Milos Malvis @1243093366@qq.com
 *  * ALL COPYRIGHT REVERSED.
 *
 */

package com.connor.hozon.service.configuration.derivative.impl;

import cn.net.connor.hozon.common.entity.QueryBase;
import cn.net.connor.hozon.common.util.UUIDHelper;
import cn.net.connor.hozon.dao.dao.configuration.derivative.HzDerivativeMaterielBasicDao;
import cn.net.connor.hozon.dao.dao.configuration.derivative.HzDerivativeMaterielDetailDao;
import cn.net.connor.hozon.dao.dao.configuration.fullConfigSheet.HzFullCfgModelDao;
import cn.net.connor.hozon.dao.dao.configuration.model.HzCfg0ModelDetailDao;
import cn.net.connor.hozon.dao.pojo.bom.epl.HzEPLManageRecord;
import cn.net.connor.hozon.dao.pojo.bom.materiel.HzMaterielRecord;
import cn.net.connor.hozon.dao.pojo.configuration.derivative.HzCfg0ModelFeature;
import cn.net.connor.hozon.dao.pojo.configuration.derivative.HzComposeDelDto;
import cn.net.connor.hozon.dao.pojo.configuration.derivative.HzDerivativeMaterielBasic;
import cn.net.connor.hozon.dao.pojo.configuration.derivative.HzDerivativeMaterielDetail;
import cn.net.connor.hozon.dao.pojo.configuration.feature.HzFeature;
import cn.net.connor.hozon.dao.pojo.configuration.feature.HzFeatureValue;
import cn.net.connor.hozon.dao.pojo.configuration.fullConfigSheet.HzFullCfgModel;
import cn.net.connor.hozon.dao.pojo.configuration.model.HzCfg0ModelDetail;
import cn.net.connor.hozon.dao.pojo.configuration.model.HzCfg0ModelRecord;
import cn.net.connor.hozon.dao.pojo.configuration.modelColor.HzCfg0ModelColor;
import cn.net.connor.hozon.dao.pojo.main.HzFactory;
import cn.net.connor.hozon.dao.pojo.main.HzMainConfig;
import cn.net.connor.hozon.dao.pojo.sys.User;
import cn.net.connor.hozon.services.request.configuration.derivative.HzDerivativeRequestDTO;
import cn.net.connor.hozon.services.service.configuration.fullConfigSheet.HzCfg0ModelService;
import cn.net.connor.hozon.services.service.depository.project.HzSuperMaterielService;
import cn.net.connor.hozon.services.service.main.HzMainConfigService;
import cn.net.connor.hozon.services.service.sys.UserInfo;
import com.connor.hozon.dao.configuration.modelColor.HzCfg0ModelColorDao;
import cn.net.connor.hozon.services.common.option.SpecialFeatureOptions;
import com.connor.hozon.service.configuration.derivative.HzConfigModelFeatureService;
import com.connor.hozon.service.configuration.derivative.HzDerivativeService;
import com.connor.hozon.service.configuration.feature.FeatureValueService;
import com.connor.hozon.service.configuration.feature.impl.FeatureServiceImpl;
import com.connor.hozon.service.configuration.model.impl.HzCfg0ModelRecordServiceImpl;
import com.connor.hozon.service.configuration.modelColor.HzCfg0ModelColorService;
import com.connor.hozon.resources.mybatis.bom.HzEbomRecordDAO;
import com.connor.hozon.resources.mybatis.factory.HzFactoryDAO;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static cn.net.connor.hozon.common.util.StringUtils.checkString;

/**
 * @Author: Fancyears·Maylos·Malvis
 * @Description: fuck
 * @Date: Created in 2018/9/6 13:19
 * @Modified By:
 */
@Service
@Slf4j
public class HzDerivativeServiceImpl implements HzDerivativeService {
    /**
     * 工厂
     */
    @Autowired
    private HzFactoryDAO hzFactoryDAO;
    /**
     * 超级物料服务层
     */
    @Autowired
    private HzSuperMaterielService hzSuperMaterielService;
    /**
     * 衍生物料基本信息
     */
    @Autowired
    private HzConfigModelFeatureService cfg0ModelFeatureService;
    /**
     * 特性
     */
    @Autowired
    private FeatureServiceImpl hzCfg0OptionFamilyService;
    /**
     * 特性值
     */
    @Autowired
    private FeatureValueService featureValueService;
    /**
     * 车型模型
     */
    @Autowired
    private HzCfg0ModelService hzCfg0ModelService;
    /**
     * 配色模型
     */
    @Autowired
    private HzCfg0ModelColorService hzCfg0ModelColorService;
    /**
     * 衍生物料基础数据
     */
    @Autowired
    private HzDerivativeMaterielBasicDao hzDerivativeMaterielBasicDao;
    /**
     * 衍生物料配置详情
     */
    @Autowired
    private HzDerivativeMaterielDetailDao hzDerivativeMaterielDetailDao;
    /**
     * 车型模型详情
     */
    @Autowired
    private HzCfg0ModelDetailDao hzCfg0ModelDetailDao;
    /**
     * 全配置BOM一级清单的车型模型
     */
    @Autowired
    private HzFullCfgModelDao fullCfgModelDao;
    /**
     * EBOM dao层
     */
    @Autowired
    private HzEbomRecordDAO hzEbomRecordDAO;
    /**
     * 主配置
     */
    @Autowired
    private HzMainConfigService hzMainConfigService;

//    @Autowired
//    private HzSingleVehiclesDao hzSingleVehiclesDao;
//    @Autowired
//    private HzSingleVehicleBomLineDao hzSingleVehicleBomLineDao;

    @Autowired
    private HzCfg0ModelColorDao hzCfg0ModelColorDao;
    @Autowired
    private HzCfg0ModelRecordServiceImpl hzCfg0ModelRecordService;
    /**
     * 最后添加车身颜色配置
     */
    private static final boolean finalAddCSYS = true;


    public void saveDerivative(HzDerivativeRequestDTO hzDerivativeRequestDTO, JSONObject results) {
        HzDerivativeMaterielBasic basic1 = hzDerivativeMaterielBasicDao.selectByModelAndColorUid(hzDerivativeRequestDTO.getModelUid(), hzDerivativeRequestDTO.getColorModel());
        if (basic1 != null) {
            results.put("msg", "已存在相同配置的衍生物料，请进行修改操作或先删除衍生物料");
            results.put("status", false);
            return;
        }

        HzCfg0ModelColor modelColor = new HzCfg0ModelColor();
        modelColor.setPuid(hzDerivativeRequestDTO.getColorModel());
        HzCfg0ModelRecord cmodel = hzCfg0ModelService.getModelByPuid(hzDerivativeRequestDTO.getModelUid());
        modelColor = hzCfg0ModelColorService.doGetById(modelColor);

        HzMainConfig mainRecord = hzMainConfigService.selectByProjectId(hzDerivativeRequestDTO.getProjectUid());
        HzFeatureValue local = new HzFeatureValue();
        local.setFeatureValueCode(modelColor.getpModelShellOfColorfulModel());
        /**强制设置为HZCSYS*/
        local.setpCfg0FamilyName(SpecialFeatureOptions.CSCODE.getDesc());
        local.setpCfg0MainItemPuid(mainRecord.getId());
        HzFeatureValue shell = featureValueService.selectByCodeAndDescWithMainItem(local);

        if (shell == null) {
            /**强制设置为中文描述“车身颜色”*/
            local.setpCfg0FamilyDesc(SpecialFeatureOptions.CSNAME.getDesc());
            if ((shell = featureValueService.selectByCodeAndCnDescWithMainItem(local)) == null) {
                results.put("msg", "没有找到任何一个特性为'HZCSYS'、特性值为'" + modelColor.getpModelShellOfColorfulModel() + "'的特性值，请尝试添加特性值");
                results.put("status", false);
                return;
            }
        }

        HzFullCfgModel cfgModel = new HzFullCfgModel();
        cfgModel.setModModelUid(hzDerivativeRequestDTO.getModelUid());
        cfgModel.setModPointType((short) 1);
        List<HzFullCfgModel> modelCfgs = fullCfgModelDao.selectByModelUidWithMarks(cfgModel);

        if (modelCfgs.size() > 0) {
            results.put("msg", "选择的模型包含选配关系，不予以生成配置物料特性数据");
            results.put("status", false);
            return;
        }
        cfgModel.setModPointType((short) 2);
        modelCfgs = fullCfgModelDao.selectByModelUidWithMarks(cfgModel);


        HzCfg0ModelDetail modelDetail = new HzCfg0ModelDetail();
        modelDetail.setpModelPuid(hzDerivativeRequestDTO.getModelUid());
        modelDetail = hzCfg0ModelDetailDao.selectByModelId(modelDetail);
        if (modelDetail == null) {
            results.put("status", false);
            results.put("msg", "选择的基本模型没有详细信息，请修改详细信息");
            return;
        }
        if (!checkString(modelDetail.getpModelCfgMng())) {
            results.put("status", false);
            results.put("msg", "选择的基本模型没有配置管理描述，无法自动生成衍生物料基本信息代码，请修改详细信息");
            return;
        }
        if (!modelDetail.getpModelCfgMng().contains("**")) {
            results.put("status", false);
            results.put("msg", "选择的基本模型配置管理描述不正确。必须包含**，请修改详细信息");
            return;
        }
        List<String> column = hzCfg0OptionFamilyService.getColumnDef(hzDerivativeRequestDTO.getProjectUid(), "<br/>");
        if (modelCfgs == null || column == null || column.size() == 0) {
            results.put("status", false);
            results.put("msg", "在车型模型上没有找到足够的特性配置");
            return;
        }
        StringBuilder sb = new StringBuilder();
        boolean isContinue = true;
        for (HzFullCfgModel modelCfg : modelCfgs) {
            if (!checkString(modelCfg.getModCfg0Uid())) {
                HzEPLManageRecord hzEPLManageRecord = hzEbomRecordDAO.findEbomById(modelCfg.getFlModelBomlineUid(), hzDerivativeRequestDTO.getProjectUid());
                if (hzEPLManageRecord == null) {
                    continue;
                }
                isContinue = false;
                sb.append("在车型模型" + modelDetail.getpModelVersion() + "存在标配打点图，但是忘记为2Y:" + hzEPLManageRecord.getLineID() + "选择特性值，请添加特性值<br>");
            }
        }
        if (!isContinue) {
            results.put("status", isContinue);
            results.put("msg", sb.toString());
            return;
        }
        HzCfg0ModelFeature feature = new HzCfg0ModelFeature();
        User user = UserInfo.getUser();
        /**
         * 检索工厂
         */
        HzFactory factory = null;
        if (checkString(hzDerivativeRequestDTO.getFactoryCode())) {
            factory = hzFactoryDAO.findFactory(null, hzDerivativeRequestDTO.getFactoryCode());
        } else {
            //默认为1001
            factory = hzFactoryDAO.findFactory(null, "1001");
        }
        if (factory == null) {
            factory = new HzFactory();
            factory.setpFactoryCode(hzDerivativeRequestDTO.getFactoryCode());
            factory.setPuid(UUIDHelper.generateUpperUid());
            factory.setpCreateName(user.getUsername());
            factory.setpUpdateName(user.getUsername());
            factory.setpFactoryDesc(factory.getpFactoryCode());
            if (hzFactoryDAO.insert(factory) <= 0) {
                log.error("新增工厂失败");
                factory.setPuid(null);
            }
        }
        /**
         * 如果超级物料为空，则存储超级物料
         */
        HzMaterielRecord sm = hzSuperMaterielService.doSelectByProjectPuid(hzDerivativeRequestDTO.getProjectUid());
        if (null == sm) {
            if (checkString(hzDerivativeRequestDTO.getSuperMateriel())) {
                sm = new HzMaterielRecord();
                sm.setpMaterielCode(hzDerivativeRequestDTO.getSuperMateriel());
                sm.setPuid(UUIDHelper.generateUpperUid());
                sm.setpFactoryPuid(factory.getPuid());
                sm.setpPertainToProjectPuid(hzDerivativeRequestDTO.getProjectUid());
                sm.setpMaterielDataType(11);
                if (!hzSuperMaterielService.doInsertOne(sm)) {
                    log.error("存储超级物料失败");
                }
            }
        }

        feature.setpFeatureSingleVehicleCode(modelDetail.getpModelCfgMng().replace("**", modelColor.getpModelShellOfColorfulModel()));
        feature.setpFeatureCnDesc(hzDerivativeRequestDTO.getpCfg0ModelBasicDetail());
        feature.setFactoryCode(factory.getPuid());
        ModelFeatureDtoToPojo(hzDerivativeRequestDTO, feature);
        if (true) {
            if (!cfg0ModelFeatureService.doInsert(feature)) {
                log.error("新增衍生物料失败");
                results.put("status", false);
                results.put("msg", "新增衍生物料失败");
                return;
            }
        }


        HzDerivativeMaterielBasic basic = new HzDerivativeMaterielBasic();
        basic.setDmbModelFeatureUid(feature.getPuid());
        basic.setDmbColorModelUid(modelColor.getPuid());
        basic.setDmbModelUid(cmodel.getPuid());
        basic.setDmbCreator(user.getUsername());
        basic.setDmbUpdater(user.getUsername());
        basic.setDmbProjectUid(hzDerivativeRequestDTO.getProjectUid());
        basic.setDmbSpecialFeatureUid(shell.getPuid());
        if (hzDerivativeMaterielBasicDao.insert(basic) <= 0) {
            log.error("存储配置物料主数据失败");
            results.put("status", false);
            results.put("msg", "存储配置物料主数据失败");
            return;
        }

        List<HzDerivativeMaterielDetail> details = new ArrayList<>();

        List<Map<String, Object>> data = new ArrayList<>();
        HzFeatureValue record = null;
        Map<String, List<HzFeatureValue>> mapOfTemp = new HashMap<>();

        for (HzFullCfgModel modelCfg : modelCfgs) {
            if ((record = featureValueService.selectOneByPuid(modelCfg.getModCfg0Uid())) != null) {
                /**进行特性的查重，不应该有2个以上的相同特性*/
                if (mapOfTemp.containsKey(record.getpCfg0FamilyPuid())) {
                    log.error("存在2个及以上特性值拥有相同的特性");
                    mapOfTemp.get(record.getpCfg0FamilyPuid()).add(record);
                } else {
                    List<HzFeatureValue> list = new ArrayList<>();
                    list.add(record);
                    mapOfTemp.put(record.getpCfg0FamilyPuid(), list);
                }
                HzDerivativeMaterielDetail detail = new HzDerivativeMaterielDetail();
                detail.setDmdCreator(user.getUsername());
                detail.setDmdDmbId(basic.getId());
                detail.setDmdUpdater(user.getUsername());
                detail.setDmdCfg0Uid(record.getPuid());
                detail.setDmdFeatureValue(record.getFeatureValueCode());
                detail.setDmdCfg0FamilyUid(record.getpCfg0FamilyPuid());
                details.add(detail);
            } else {
                log.error("没有找到特性值数据");
            }
        }
        /**最后添加一个车身颜色*/
        if (finalAddCSYS) {
            HzDerivativeMaterielDetail detail = new HzDerivativeMaterielDetail();
            detail.setDmdCreator(user.getUsername());
            detail.setDmdDmbId(basic.getId());
            detail.setDmdUpdater(user.getUsername());
            detail.setDmdCfg0Uid(shell.getPuid());
            detail.setDmdFeatureValue(shell.getFeatureValueCode());
            detail.setDmdCfg0FamilyUid(shell.getpCfg0FamilyPuid());
            details.add(detail);
        }
        if (true) {
            boolean hasFail = false;
            for (int i = 0; i < details.size(); i++) {
                if (hzDerivativeMaterielDetailDao.insert(details.get(i)) <= 0) {
                    hasFail = true;
                    log.error("存储配置物料详情数据" + details.get(i).getDmdFeatureValue() + "失败");
                }
            }
            if (hasFail) {
                results.put("status", false);
                results.put("msg", "存储配置物料详情数据失败，请联系管理员查看日志");
            }
        }
        results.put("status", true);
        System.out.println();
    }

    /**
     * DTO转成Pojo，pojo自动生成PUID
     *
     * @param hzDerivativeRequestDTO ProcessRequestDTO
     * @param feature                pojo
     * @return pojo
     */
    private static HzCfg0ModelFeature ModelFeatureDtoToPojo(HzDerivativeRequestDTO hzDerivativeRequestDTO, HzCfg0ModelFeature feature) {
        feature.setMaterielType(hzDerivativeRequestDTO.getMaterielType());
        feature.setPuid(UUIDHelper.generateUpperUid());
        feature.setpPertainToModel(hzDerivativeRequestDTO.getModelUid());
        feature.setBasicCountUnit(hzDerivativeRequestDTO.getBasicCountUnit());
        feature.setBulletinNum(hzDerivativeRequestDTO.getBulletinNum());
        feature.setColor(hzDerivativeRequestDTO.getColor());
        feature.setDeleteFlag(hzDerivativeRequestDTO.getDeleteFlag());
        feature.setGrossWeight(hzDerivativeRequestDTO.getGrossWeight());
        feature.setImportance(hzDerivativeRequestDTO.getImportance());
        feature.setLabelFlag(hzDerivativeRequestDTO.getLabelFlag());
        feature.setMaterialCode(hzDerivativeRequestDTO.getMaterialCode());
        feature.setOldMaterielCode(hzDerivativeRequestDTO.getOldMaterielCode());
        feature.setPurchaseType(hzDerivativeRequestDTO.getPurchaseType());
        feature.setRulesFlag(hzDerivativeRequestDTO.getRulesFlag());
        feature.setVinCode(hzDerivativeRequestDTO.getVinCode());
        feature.setVirtualFlag(hzDerivativeRequestDTO.getVirtualFlag());
        feature.setpPertainToColorModel(hzDerivativeRequestDTO.getColorModel());
        feature.setMaterielDesc(hzDerivativeRequestDTO.getMaterielDesc());
        feature.setMaterielEnDesc(hzDerivativeRequestDTO.getMaterielEnDesc());
        feature.setOldMaterielCode(hzDerivativeRequestDTO.getOldMaterielCode());
        feature.setMrpController(hzDerivativeRequestDTO.getMrpController());
        return feature;
    }

    /**
     * 查询所有选配的
     *
     * @param projectUid
     * @param queryBase
     * @return
     */
    public Map<String, Object> loadProjectsDerivatives(String projectUid, QueryBase queryBase) {
        Map<String, Object> result = new HashMap<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//        Object o = cfg0ModelFeatureService.doSelectAllByProjectUid(projectUid);
        HzDerivativeMaterielBasic basic = new HzDerivativeMaterielBasic();
        basic.setDmbProjectUid(projectUid);
//        List<HzFeature> columns = hzCfg0OptionFamilyService.selectCfg0OptionFamilyListByProjectPuid(projectUid);
        List<HzFeature> columns = hzCfg0OptionFamilyService.getFamilies(projectUid, 0, 2);
        Map<String, Object> params = new HashMap<>();
        params.put("basic", basic);
        List<HzDerivativeMaterielBasic> basics = hzDerivativeMaterielBasicDao.selectByProjectUid(params);
        HzDerivativeMaterielDetail detail = new HzDerivativeMaterielDetail();
        List<Map<String, Object>> list = new ArrayList<>();
        HzMaterielRecord superMateriel = hzSuperMaterielService.doSelectByProjectPuid(projectUid);
        Map<String, HzFactory> mapOffactories = new HashMap<>();//
        HzFeatureValue param = new HzFeatureValue();
        param.setpCfg0FamilyDesc("内饰颜色");
        param.setpCfg0MainItemPuid(projectUid);
        List<HzFeatureValue> innerColor = featureValueService.selectByDescAndProjectId(param);
        HzFeatureValue inner = null;
        //就去除第一个作为所有的内饰颜色
        if (innerColor != null && !innerColor.isEmpty()) {
            inner = innerColor.get(0);
        }
        for (int i = 0; i < basics.size(); i++) {
            detail.setDmdDmbId(basics.get(i).getId());
            //详情
            List<HzDerivativeMaterielDetail> details = hzDerivativeMaterielDetailDao.selectByBasicWithCfg(detail);
            Map<String, HzDerivativeMaterielDetail> mapOfDetails = new HashMap<>();
            /**没有加排序，存在相同特性的特性值，最后的特性值将覆盖前面的，显示在前端就是最后一个，这样的话会造成解算出错*/
            details.forEach(d -> mapOfDetails.put(d.getDmdCfg0FamilyUid(), d));
            HzCfg0ModelFeature feature = cfg0ModelFeatureService.doSelectByModelAndColorPuids(basics.get(i).getDmbModelUid(), basics.get(i).getDmbColorModelUid());
            if (null == feature) {
                continue;
            }
            HzCfg0ModelRecord modelRecord = hzCfg0ModelService.getModelByPuid(feature.getpPertainToModel());
            Map<String, Object> _result = new HashMap<>();
//            /**
//             * 从配置中包括品牌平台车型项目等单车信息数据，包括了衍生物料和基本信息数据，不能当作返回前端的数据，因为前端需要从其他地方获取到内饰颜色等信息
//             */
//            List<HzSingleVehicles> vehicles = hzSingleVehiclesDao.selectOrgByProjectUid(projectUid);
//            /**
//             * 从表中查询单车清单数据，一开始都是0个，需要进行差异数据对比，对比的是数量,再返回前端
//             */
//            List<HzSingleVehicles> hzSingleVehicles = hzSingleVehiclesDao.selectByProjectUid(projectUid);
//            /**
//             * 单车主配置+项目查询1条单车数据，单车主配置是配置物料特性表中的1行数据的主键
//             */
//            HzSingleVehicles hzSingleVehicle = hzSingleVehiclesDao.selectByDmbIdWithProjectUid(basics.get(i).getId(), projectUid);
//            /**
//             * 单车主配置+项目查询单车的所有2Y
//             */
//            List<HzSingleVehicleBomLineBean> hzSingleVehicleBomLineBeans = hzSingleVehicleBomLineDao.selectByProjectUidWithSv(projectUid, basics.get(i).getId());


            for (int i1 = 0; i1 < columns.size(); i1++) {
                if (mapOfDetails.get(columns.get(i1).getId()) != null) {
                    _result.put("s" + i1, mapOfDetails.get(columns.get(i1).getId()).getDmdFeatureValue());
                } else {
                    _result.put("s" + i1, "-");
                }
            }
            if (superMateriel != null) {
                _result.put("superMateriel", superMateriel.getpMaterielCode());
            } else {
                _result.put("superMateriel", "");
            }
            if (!mapOffactories.containsKey(feature.getFactoryCode())) {
                HzFactory factory = hzFactoryDAO.findFactory(feature.getFactoryCode(), null);
                if (factory == null) {
                    factory = new HzFactory();
                    factory.setPuid(feature.getFactoryCode());
                    factory.setpFactoryCode("");
                }
                mapOffactories.put(factory.getPuid(), factory);
            }
            _result.put("factory", mapOffactories.get(feature.getFactoryCode()).getpFactoryCode());

            _result.put("puid", feature.getpPertainToModel());
            _result.put("basicId", basics.get(i).getId());
            _result.put("puidOfModelFeature", feature.getPuid());
//            _result.put("factory", feature.getFactoryCode());
            _result.put("modeBasicDetail", feature.getpFeatureSingleVehicleCode());
            //重新运算一遍基本信息
            remarkBaseInfo(feature, _result, modelRecord, inner);
            _result.put("cfg0MainPuid", modelRecord.getpCfg0ModelOfMainRecord());
//            if(basics.get(i).getDmbStatus()==null||basics.get(i).getDmbStatus()==0){
//                _result.put("status","草稿状态");
//            }else if(basics.get(i).getDmbStatus()==10){
//                _result.put("status","在流程表单中");
//            }else if(basics.get(i).getDmbStatus()==999){
//                _result.put("status","已发布");
//            }
            _result.put("status", basics.get(i).getDmbStatus());
            _result.put("changeOrderNo", basics.get(i).getChangeOrderNo());
            _result.put("effectedDate", basics.get(i).getEffectedDate() == null ? "-" : sdf.format(basics.get(i).getEffectedDate()));
            list.add(_result);
        }
        result.put("result", list);
        result.put("totalCount", list.size());
        return result;
    }

    @Transactional
    public void remarkBaseInfo(HzCfg0ModelFeature feature, Map<String, Object> _result, HzCfg0ModelRecord modelRecord, HzFeatureValue inner) {
        String descCn = feature.getpFeatureCnDesc();
        String 平台;
        String 车型年;
        String 版型;
        StringBuilder desc = null;
        if (StringUtils.isEmpty(descCn)) {
            desc = new StringBuilder();
            HzCfg0ModelDetail param = new HzCfg0ModelDetail();
            param.setpModelPuid(modelRecord.getPuid());
            param = hzCfg0ModelDetailDao.selectByModelId(param);

            if (StringUtils.isNotEmpty(param.getpModelAnnual())) {
                HzCfg0ModelDetail modelDetail = new HzCfg0ModelDetail();
                modelDetail.setpModelPuid(modelRecord.getPuid());
                //TODO 多次查询很消耗性能，可以考虑放到一个新的实体对象上进行查询
                modelDetail = hzCfg0ModelDetailDao.selectByModelId(modelDetail);
                HzCfg0ModelColor outerColor = hzCfg0ModelColorDao.selectByPrimaryKey(feature.getpPertainToColorModel());
                平台 = param.getpModelPlatform();
                车型年 = param.getpModelAnnual() == null ? "" : param.getpModelAnnual();
                版型 = param.getpModelVersion();
                desc.append(平台).append(" ").append(车型年).append(" ").append(版型).append(" ");
                //内饰颜色拼接进去
                if (inner != null) {
                    desc.append(inner.getpCfg0Desc()).append("/");
                }
                if (outerColor != null) {
                    desc.append(outerColor.getpDescOfColorfulModel()).append(" ");
                }
                desc.append(modelDetail.getpModelCfgDesc());
                System.out.println(desc);
                feature.setpFeatureCnDesc(desc.toString());
                cfg0ModelFeatureService.doUpdateByPrimaryKey(feature);
            } else {
                log.error("车型年数据不存在");
            }
        }

        _result.put("modeBasicDetailDesc", feature.getpFeatureCnDesc());
    }

    /**
     * 删除衍生物料
     *
     * @param delDtos
     * @param result
     */
    public void deleteDerivative(List<HzComposeDelDto> delDtos, JSONObject result) {
        for (int i = 0; i < delDtos.size(); i++) {
            if (!cfg0ModelFeatureService.doDeleteByPrimaryKey(delDtos.get(i).getPuidOfModelFeature())) {
                log.error("删除衍生物料失败" + delDtos.get(i).getModeBasicDetailDesc());
                result.put("status", false);
                result.put("msg", "删除衍生物料" + delDtos.get(i).getModeBasicDetailDesc() + "失败");
                return;
            }
            result.put("status", true);
            result.put("msg", "删除衍生物料" + delDtos.get(i).getModeBasicDetailDesc() + "及其基本映射数据成功");
        }
    }

    public JSONObject saveProjectDerivatives(String projectPuid) {
        JSONObject result = new JSONObject();
        //项目下的所有颜色车型
        List<HzCfg0ModelColor> colorModels = hzCfg0ModelColorDao.selectAll(projectPuid);
        //项目下的所有基本车型
        List<HzCfg0ModelRecord> models = hzCfg0ModelRecordService.selectByProjectUid(projectPuid);
        List<HzDerivativeRequestDTO> composes = new ArrayList<HzDerivativeRequestDTO>();

        //组合车型和颜色
        for (HzCfg0ModelColor colorModel : colorModels) {
            for (HzCfg0ModelRecord model : models) {
                HzDerivativeRequestDTO hzDerivativeRequestDTO = new HzDerivativeRequestDTO();
                hzDerivativeRequestDTO.setProjectUid(projectPuid);
                hzDerivativeRequestDTO.setModelUid(model.getPuid());
                hzDerivativeRequestDTO.setColorModel(colorModel.getPuid());
                saveDerivative(hzDerivativeRequestDTO, result);
                boolean status = (boolean) result.get("status");
                if (status == false) {
                    return result;
                }
            }
        }
        return result;
    }

    public int deleteVehicleFake(List<HzComposeDelDto> delDtos) {
        List<HzDerivativeMaterielBasic> hzDerivativeMaterielBasics = new ArrayList<>();
        for (HzComposeDelDto hzComposeDelDto : delDtos) {
            HzDerivativeMaterielBasic hzDerivativeMaterielBasic = new HzDerivativeMaterielBasic();
            hzDerivativeMaterielBasic.setId(hzComposeDelDto.getBasicId());
            hzDerivativeMaterielBasic.setDmbStatus(2);
            hzDerivativeMaterielBasics.add(hzDerivativeMaterielBasic);
        }
        return hzDerivativeMaterielBasicDao.updateStatus(hzDerivativeMaterielBasics);
    }
}
