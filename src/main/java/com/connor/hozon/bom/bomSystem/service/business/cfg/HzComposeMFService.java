package com.connor.hozon.bom.bomSystem.service.business.cfg;

import com.alibaba.fastjson.JSONObject;
import com.connor.hozon.bom.bomSystem.dto.cfg.HzComposeMFDTO;
import com.connor.hozon.bom.bomSystem.helper.UUIDHelper;
import com.connor.hozon.bom.bomSystem.service.cfg.*;
import com.connor.hozon.bom.common.util.user.UserInfo;
import com.connor.hozon.bom.resources.mybatis.factory.HzFactoryDAO;
import com.connor.hozon.bom.sys.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sql.pojo.cfg.HzCfg0ModelColor;
import sql.pojo.cfg.HzCfg0ModelFeature;
import sql.pojo.cfg.HzCfg0ModelRecord;
import sql.pojo.factory.HzFactory;

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
    private static Logger logger = LoggerFactory.getLogger(HzComposeMFService.class);

    public void saveCompose(HzComposeMFDTO hzComposeMFDTO, JSONObject result) {
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
        HzCfg0ModelRecord model = hzCfg0ModelService.getModelByPuid(hzComposeMFDTO.getModelUid());
        modelColor = hzCfg0ModelColorService.doGetById(modelColor);

        if (false) {
            if (!hzCfg0ModelFeatureService.doInsert(feature)) {
                logger.error("新增衍生物料失败");
                result.put("status", false);
                result.put("msg", "新增衍生物料失败");
            }
        }

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
}
