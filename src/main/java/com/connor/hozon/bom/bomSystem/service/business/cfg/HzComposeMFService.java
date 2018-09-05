package com.connor.hozon.bom.bomSystem.service.business.cfg;

import com.alibaba.fastjson.JSONObject;
import com.connor.hozon.bom.bomSystem.dao.cfg.HzCfg0ModelDetailDao;
import com.connor.hozon.bom.bomSystem.dao.cfg.HzDerivativeMaterielBasicDao;
import com.connor.hozon.bom.bomSystem.dao.cfg.HzDerivativeMaterielDetailDao;
import com.connor.hozon.bom.bomSystem.dto.HzMaterielFeatureBean;
import com.connor.hozon.bom.bomSystem.dto.cfg.compose.HzComposeDelDto;
import com.connor.hozon.bom.bomSystem.dto.cfg.compose.HzComposeMFDTO;
import com.connor.hozon.bom.bomSystem.helper.UUIDHelper;
import com.connor.hozon.bom.bomSystem.service.cfg.*;
import com.connor.hozon.bom.bomSystem.service.project.HzSuperMaterielService;
import com.connor.hozon.bom.common.base.entity.QueryBase;
import com.connor.hozon.bom.common.util.user.UserInfo;
import com.connor.hozon.bom.resources.mybatis.factory.HzFactoryDAO;
import com.connor.hozon.bom.sys.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sql.pojo.cfg.*;
import sql.pojo.factory.HzFactory;
import sql.pojo.project.HzMaterielRecord;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.connor.hozon.bom.bomSystem.helper.StringHelper.checkString;

/**
 * @Author: Fancyears·Maylos·Maywas
 * @Description:
 * @Date: Created in 2018/8/30 14:32
 * @Modified By:
 */
@Service("hzComposeMFService")
public class HzComposeMFService {
    /**
     * 工厂
     */
    @Autowired
    HzFactoryDAO hzFactoryDAO;
    /**
     * 超级物料服务层
     */
    @Autowired
    HzSuperMaterielService hzSuperMaterielService;
    /**
     * 衍生物料基本信息
     */
    @Autowired
    HzCfg0ModelFeatureService hzCfg0ModelFeatureService;
    /**
     * 特性
     */
    @Autowired
    HzCfg0OptionFamilyService hzCfg0OptionFamilyService;
    /**
     * 特性值
     */
    @Autowired
    HzCfg0Service hzCfg0Service;
    /**
     * 车型模型
     */
    @Autowired
    HzCfg0ModelService hzCfg0ModelService;
    /**
     * 配色模型
     */
    @Autowired
    HzCfg0ModelColorService hzCfg0ModelColorService;
    /**
     * 衍生物料基础数据
     */
    @Autowired
    HzDerivativeMaterielBasicDao hzDerivativeMaterielBasicDao;
    /**
     * 衍生物料配置详情
     */
    @Autowired
    HzDerivativeMaterielDetailDao hzDerivativeMaterielDetailDao;
    /**
     * 车型模型详情
     */
    @Autowired
    HzCfg0ModelDetailDao hzCfg0ModelDetailDao;
    /**
     * 颜色集
     */
    @Autowired
    HzCfg0ColorSetService hzCfg0ColorSetService;
    /**
     * 日志
     */
    private static Logger logger = LoggerFactory.getLogger(HzComposeMFService.class);

    public void saveCompose(HzComposeMFDTO hzComposeMFDTO, JSONObject results) {
//        Map<String, Object> result = new HashMap<>();
        HzDerivativeMaterielBasic basic1 = hzDerivativeMaterielBasicDao.selectByModelAndColorUid(hzComposeMFDTO.getModelUid(), hzComposeMFDTO.getColorModel());
        if (basic1 != null) {
            results.put("msg", "已存在相同配置的衍生物料，请进行修改操作或先删除衍生物料");
            results.put("status", false);
            return;
        }
        HzCfg0ModelDetail modelDetail = new HzCfg0ModelDetail();
        modelDetail.setpModelPuid(hzComposeMFDTO.getModelUid());
        modelDetail = hzCfg0ModelDetailDao.selectByModelId(modelDetail);
        if (modelDetail == null) {
            results.put("status", false);
            results.put("msg", "选择的基本模型没有详细信息，请修改详细信息");
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
        HzCfg0ModelFeature feature = new HzCfg0ModelFeature();
        User user = UserInfo.getUser();
        HzFactory factory = hzFactoryDAO.findFactory(null, hzComposeMFDTO.getFactoryCode());
        if (factory == null) {
            factory = new HzFactory();
            factory.setpFactoryCode(hzComposeMFDTO.getFactoryCode());
            factory.setPuid(UUIDHelper.generateUpperUid());
            factory.setpCreateName(user.getUsername());
            factory.setpUpdateName(user.getUsername());
            factory.setpFactoryDesc(factory.getpFactoryCode());
            if (hzFactoryDAO.insert(factory) <= 0) {
                logger.error("新增工厂失败");
                factory.setPuid(null);
            }
        }

        HzCfg0ModelColor modelColor = new HzCfg0ModelColor();
        modelColor.setPuid(hzComposeMFDTO.getColorModel());
        HzCfg0ModelRecord cmodel = hzCfg0ModelService.getModelByPuid(hzComposeMFDTO.getModelUid());
        modelColor = hzCfg0ModelColorService.doGetById(modelColor);

        feature.setpFeatureSingleVehicleCode(modelDetail.getpModelCfgMng().replace("**", modelColor.getpModelShellOfColorfulModel()));
        feature.setpFeatureCnDesc(hzComposeMFDTO.getpCfg0ModelBasicDetail());
        feature.setFactoryCode(factory.getPuid());
        ModelFeatureDtoToPojo(hzComposeMFDTO, feature);
        if (true) {
            if (!hzCfg0ModelFeatureService.doInsert(feature)) {
                logger.error("新增衍生物料失败");
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
        basic.setDmbProjectUid(hzComposeMFDTO.getProjectUid());
        if (hzDerivativeMaterielBasicDao.insert(basic) <= 0) {
            logger.error("存储配置物料主数据失败");
            results.put("status", false);
            results.put("msg", "存储配置物料主数据失败");
            return;
        }

        Map<String, HzMaterielFeatureBean> model = new HashMap();
        List<HzDerivativeMaterielDetail> details = new ArrayList<>();

        List<Map<String, Object>> data = new ArrayList<>();
        List<String> column = hzCfg0OptionFamilyService.doGetColumnDef(hzComposeMFDTO.getProjectUid(), "<br/>");
        List<HzMaterielFeatureBean> hzMaterielFeatureBeans = hzCfg0Service.doSelectMaterielFeatureByProjectPuid(hzComposeMFDTO.getProjectUid());
        Map<String, HzMaterielFeatureBean> sortedBean = new HashMap<>();
        hzMaterielFeatureBeans.stream().
                filter(_b -> _b.getpCfg0ModelRecord() != null).
                forEach(_b -> sortedBean.put(_b.getpCfg0ModelRecord() + "=" + _b.getpCfg0FamilyDesc() + "<br/>" + _b.getpCfg0FamilyName(), _b));

        if (hzMaterielFeatureBeans == null || column == null || column.size() == 0) {
            results.put("status", false);
            results.put("msg", "在车型模型上没有找到足够的特性配置");
            return;
        } else {
            //在此修改各个模型对应的颜色或者模型数量=模型X颜色等
            hzMaterielFeatureBeans.stream()
                    .filter(bean -> bean.getpCfg0ModelRecord() != null && bean.getpCfg0ModelRecord().equals(hzComposeMFDTO.getModelUid()))
                    .forEach(bean -> model.put(bean.getpCfg0ObjectId(), bean));

            model.forEach((key, value) -> {
                HzDerivativeMaterielDetail detail = new HzDerivativeMaterielDetail();
                detail.setDmdCreator(user.getUsername());
                detail.setDmdDmbId(basic.getId());
                detail.setDmdUpdater(user.getUsername());
                detail.setDmdCfg0Uid(value.getPuid());
                detail.setDmdFeatureValue(value.getpCfg0ObjectId());
                detail.setDmdCfg0FamilyUid(value.getpCfg0FamilyPuid());
                details.add(detail);
            });
        }
        if (true) {
            boolean hasFail = false;
            for (int i = 0; i < details.size(); i++) {
                if (hzDerivativeMaterielDetailDao.insert(details.get(i)) <= 0) {
                    hasFail = true;
                    logger.error("存储配置物料详情数据" + details.get(i).getDmdFeatureValue() + "失败");
                }
            }
            if (hasFail) {
                results.put("status", false);
                results.put("msg", "存储配置物料详情数据失败，请联系管理员查看日志");
            }
        }
        System.out.println();
    }

    /**
     * DTO转成Pojo，pojo自动生成PUID
     *
     * @param hzComposeMFDTO dto
     * @param feature        pojo
     * @return pojo
     */
    public static HzCfg0ModelFeature ModelFeatureDtoToPojo(HzComposeMFDTO hzComposeMFDTO, HzCfg0ModelFeature feature) {
        feature.setMaterielType(hzComposeMFDTO.getMaterielType());
        feature.setPuid(UUIDHelper.generateUpperUid());
        feature.setpPertainToModel(hzComposeMFDTO.getModelUid());
        feature.setBasicCountUnit(hzComposeMFDTO.getBasicCountUnit());
        feature.setBulletinNum(hzComposeMFDTO.getBulletinNum());
        feature.setColor(hzComposeMFDTO.getColor());
        feature.setDeleteFlag(hzComposeMFDTO.getDeleteFlag());
        feature.setGrossWeight(hzComposeMFDTO.getGrossWeight());
        feature.setImportance(hzComposeMFDTO.getImportance());
        feature.setLabelFlag(hzComposeMFDTO.getLabelFlag());
        feature.setMaterialCode(hzComposeMFDTO.getMaterialCode());
        feature.setOldMaterielCode(hzComposeMFDTO.getOldMaterielCode());
        feature.setPurchaseType(hzComposeMFDTO.getPurchaseType());
        feature.setRulesFlag(hzComposeMFDTO.getRulesFlag());
        feature.setVinCode(hzComposeMFDTO.getVinCode());
        feature.setVirtualFlag(hzComposeMFDTO.getVirtualFlag());
        feature.setpPertainToColorModel(hzComposeMFDTO.getColorModel());
        feature.setMaterielDesc(hzComposeMFDTO.getMaterielDesc());
        feature.setMaterielEnDesc(hzComposeMFDTO.getMaterielEnDesc());
        feature.setOldMaterielCode(hzComposeMFDTO.getOldMaterielCode());
        feature.setMrpController(hzComposeMFDTO.getMrpController());
        return feature;
    }

    /**
     * 查询所有选配的
     *
     * @param projectUid
     * @param queryBase
     * @return
     */
    public Map<String, Object> loadComposes(String projectUid, QueryBase queryBase) {
        Map<String, Object> result = new HashMap<>();

        HzDerivativeMaterielBasic basic = new HzDerivativeMaterielBasic();
        basic.setDmbProjectUid(projectUid);
        List<HzCfg0OptionFamily> columns = hzCfg0OptionFamilyService.doGetCfg0OptionFamilyListByProjectPuid(projectUid);
        Map<String, Object> params = new HashMap<>();
        params.put("basic", basic);
        List<HzDerivativeMaterielBasic> basics = hzDerivativeMaterielBasicDao.selectByProjectUid(params);
        HzDerivativeMaterielDetail detail = new HzDerivativeMaterielDetail();
        List<Map<String, Object>> list = new ArrayList<>();
        HzMaterielRecord superMateriel = hzSuperMaterielService.doSelectByProjectPuid(projectUid);

        for (int i = 0; i < basics.size(); i++) {
            detail.setDmdDmbId(basics.get(i).getId());
            //详情
            List<HzDerivativeMaterielDetail> details = hzDerivativeMaterielDetailDao.selectByBasicWithCfg(detail);
            Map<String, HzDerivativeMaterielDetail> mapOfDetails = new HashMap<>();
            details.forEach(d -> mapOfDetails.put(d.getDmdCfg0FamilyUid(), d));
            HzCfg0ModelFeature feature = hzCfg0ModelFeatureService.doSelectByModelAndColorPuids(basics.get(i).getDmbModelUid(), basics.get(i).getDmbColorModelUid());
            HzCfg0ModelRecord modelRecord = hzCfg0ModelService.getModelByPuid(feature.getpPertainToModel());
            Map<String, Object> _result = new HashMap<>();
            for (int i1 = 0; i1 < columns.size(); i1++) {
                if (mapOfDetails.get(columns.get(i1).getPuid()) != null) {
                    _result.put("s" + i1, mapOfDetails.get(columns.get(i1).getPuid()).getDmdFeatureValue());
                } else {
                    _result.put("s" + i1, "-");
                }
            }
            if (superMateriel != null) {
                _result.put("superMateriel", superMateriel.getpMaterielCode());
            } else {
                _result.put("superMateriel", "");
            }
            _result.put("puid", feature.getpPertainToModel());
            _result.put("basicId", basics.get(i).getId());
            _result.put("puidOfModelFeature", feature.getPuid());
            _result.put("factory", feature.getFactoryCode());
            _result.put("modeBasicDetail", feature.getpFeatureSingleVehicleCode());
            _result.put("modeBasicDetailDesc", feature.getpFeatureCnDesc());
            _result.put("cfg0MainPuid", modelRecord.getpCfg0ModelOfMainRecord());
            list.add(_result);
        }
        result.put("result", list);
        result.put("totalCount", list.size());
        return result;
    }

    /**
     * 删除衍生物料
     *
     * @param delDtos
     * @param result
     */
    public void deleteCompose(List<HzComposeDelDto> delDtos, JSONObject result) {
        for (int i = 0; i < delDtos.size(); i++) {
            if (!hzCfg0ModelFeatureService.doDeleteByPrimaryKey(delDtos.get(i).getPuidOfModelFeature())) {
                logger.error("删除衍生物料失败" + delDtos.get(i).getModeBasicDetailDesc());
                result.put("status", false);
                result.put("msg", "删除衍生物料" + delDtos.get(i).getModeBasicDetailDesc() + "失败");
                return;
            }
//            HzDerivativeMaterielBasic basic = new HzDerivativeMaterielBasic();
//            basic.setId(delDtos.get(i).getBasicId());
//            if (hzDerivativeMaterielBasicDao.deleteByPrimaryKey(basic) <= 0) {
//                logger.error("删除衍生物料" + delDtos.get(i).getModeBasicDetailDesc() + "基本映射数据失败");
//                result.put("status", false);
//                result.put("msg", "删除衍生物料" + delDtos.get(i).getModeBasicDetailDesc() + "基本映射数据失败");
//                return;
//            }
            result.put("status", true);
            result.put("msg", "删除衍生物料" + delDtos.get(i).getModeBasicDetailDesc() + "及其基本映射数据成功");
        }
    }
}
