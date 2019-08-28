/*
 *
 *  * Copyright (c) 2019 Author: Fancyears Milos Malvis @1243093366@qq.com
 *  * ALL COPYRIGHT REVERSED.
 *
 */

package integration.service.integrate;

import cn.net.connor.hozon.dao.pojo.configuration.derivative.HzDerivativeMaterielBasic;
import integration.option.ActionFlagOption;
import net.sf.json.JSONObject;

import java.util.List;

/**
 * @Author: Fancyears Milos Malvis
 * @Date: Created in  2019/8/13 17:14
 * @Modified By:
 */
public interface SynConfigurableMaterialService {
    /**
     * 新增
     *
     * @param puid              表 Hz_Cfg0_Model_Record 的主键 PUID
     * @param cfg0MainPuids     表 HZ_CFG0_MAIN_RECORD   的主键 PUID
     * @param modeBasiceDetails 配置物料编码    表  Hz_Cfg0_Model_Record  的  OBJECT_NAME
     * @param projectPuid       项目ID         表   hz_cfg0_main_record 的 p_cfg0_of_which_project_puid
     * @return
     */
    JSONObject addConfigurableMaterial(String[] puid, String[] cfg0MainPuids, String[] modeBasiceDetails, String projectPuid);

    /**
     * 更新，一般不删除
     *
     * @param puid              表 Hz_Cfg0_Model_Record 的主键 PUID
     * @param cfg0MainPuids     表 HZ_CFG0_MAIN_RECORD   的主键 PUID
     * @param modeBasiceDetails 配置物料编码    表  Hz_Cfg0_Model_Record  的  OBJECT_NAME
     * @param projectPuid       项目ID         表   hz_cfg0_main_record 的 p_cfg0_of_which_project_puid
     * @return
     */
    JSONObject deleteConfigurableMaterial(String[] puid, String[] cfg0MainPuids, String[] modeBasiceDetails, String projectPuid);

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
    JSONObject execute(String[] puids, String[] cfg0MainPuids, String[] modeBasiceDetails, String projectPuid, ActionFlagOption option);

    JSONObject add(List<HzDerivativeMaterielBasic> hzDerivativeMaterielBasics);

    JSONObject delete(List<HzDerivativeMaterielBasic> hzDerivativeMaterielBasics);

    JSONObject execute2(List<HzDerivativeMaterielBasic> hzDerivativeMaterielBasics, ActionFlagOption option);
}
