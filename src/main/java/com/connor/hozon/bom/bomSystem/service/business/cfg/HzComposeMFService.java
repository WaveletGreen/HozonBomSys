package com.connor.hozon.bom.bomSystem.service.business.cfg;

import com.alibaba.fastjson.JSONObject;
import com.connor.hozon.bom.bomSystem.dao.cfg.HzDerivativeMaterielBasicDao;
import com.connor.hozon.bom.bomSystem.dao.cfg.HzDerivativeMaterielDetailDao;
import com.connor.hozon.bom.bomSystem.dto.HzMaterielFeatureBean;
import com.connor.hozon.bom.bomSystem.dto.cfg.HzComposeMFDTO;
import com.connor.hozon.bom.bomSystem.helper.UUIDHelper;
import com.connor.hozon.bom.bomSystem.service.cfg.*;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
     * 日志
     */
    private static Logger logger = LoggerFactory.getLogger(HzComposeMFService.class);

    public void saveCompose(HzComposeMFDTO hzComposeMFDTO, JSONObject results) {
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
        feature.setFactoryCode(factory.getPuid());
        ModelFeatureDtoToPojo(hzComposeMFDTO, feature);

        HzCfg0ModelColor modelColor = new HzCfg0ModelColor();
        modelColor.setPuid(hzComposeMFDTO.getColorModel());
        HzCfg0ModelRecord cmodel = hzCfg0ModelService.getModelByPuid(hzComposeMFDTO.getModelUid());
        modelColor = hzCfg0ModelColorService.doGetById(modelColor);

        HzDerivativeMaterielBasic basic = new HzDerivativeMaterielBasic();
        basic.setDmbColorModelUid(modelColor.getPuid());
        basic.setDmbModelUid(cmodel.getPuid());
        basic.setDmbCreator(user.getUsername());
        basic.setDmbUpdater(user.getUsername());

        if (hzDerivativeMaterielBasicDao.insert(basic) <= 0) {
            logger.error("存储配置物料主数据失败");
            results.put("status", false);
            results.put("msg", "存储配置物料主数据失败");
            return;
        }

        Map<String, Object> result = new HashMap<>();
        Map<String, HzMaterielFeatureBean> model = new HashMap();
        List<HzDerivativeMaterielDetail> details = new ArrayList<>();

        List<Map<String, Object>> data = new ArrayList<>();
        List<String> column = hzCfg0OptionFamilyService.doGetColumnDef(hzComposeMFDTO.getProjectUid(), "<br/>");
        List<HzMaterielFeatureBean> hzMaterielFeatureBeans = hzCfg0Service.doSelectMaterielFeatureByProjectPuid(hzComposeMFDTO.getProjectUid());


        Map<String, HzMaterielFeatureBean> sortedBean = new HashMap<>();

        hzMaterielFeatureBeans.stream().filter(_b -> _b.getpCfg0ModelRecord() != null).forEach(_b -> sortedBean.put(_b.getpCfg0ModelRecord() + "=" + _b.getpCfg0FamilyDesc() + "<br/>" + _b.getpCfg0FamilyName(), _b));

        if (hzMaterielFeatureBeans == null || column == null || column.size() == 0) {
            result.put("status", false);
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
            if (!hzCfg0ModelFeatureService.doInsert(feature)) {
                logger.error("新增衍生物料失败");
                result.put("status", false);
                result.put("msg", "新增衍生物料失败");
            }
            boolean hasFail = false;
            if (hzDerivativeMaterielDetailDao.insertByBatch(details) <= 0) {
                hasFail = true;
                logger.error("存储配置物料详情数据失败");
            }
//            for (int i = 0; i < details.size(); i++) {
//                if (hzDerivativeMaterielDetailDao.insert(details.get(i)) <= 0) {
//                    hasFail = true;
//                    logger.error("存储配置物料详情数据" + details.get(i).getDmdFeatureValue() + "失败");
//                }
//            }
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
        List<HzCfg0OptionFamily> columns = hzCfg0OptionFamilyService.doGetCfg0OptionFamilyListByProjectPuid(projectUid);
//        List<HzDerivativeMaterielBasic> basics = hzDerivativeMaterielBasicDao.selectByProjectUid(projectUid);
        return result;
    }
}
