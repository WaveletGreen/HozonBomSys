/*
 *
 *  * Copyright (c) 2019 Author: Fancyears Milos Malvis @1243093366@qq.com
 *  * ALL COPYRIGHT REVERSED.
 *
 */

package integration.service.integrate.impl;

import cn.net.connor.hozon.dao.pojo.configuration.feature.HzFeatureValue;
import cn.net.connor.hozon.services.response.integration.IntegrateMsgResponseDTO;
import cn.net.connor.hozon.common.util.UUIDHelper;
import com.connor.hozon.bom.bomSystem.service.cfg.HzFeatureServiceImpl;
import integration.base.feature.ZPPTCO002;
import integration.logic.Features;
import integration.option.ActionFlagOption;
import integration.service.impl.cfg2.TransCfgService;
import integration.service.integrate.SynFeatureService;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
/**
 * @Author: Fancyears·Maylos·Malvis
 * @Description: fuck
 * @Date: Created in 2018/9/6 13:19
 * @Modified By:
 */
@Service
public class SynFeatureServiceImpl implements SynFeatureService {
    @Autowired
    TransCfgService transCfgService;
    @Autowired
    HzFeatureServiceImpl hzFeatureServiceImpl;
    /**
     * 日志记录
     */
    private final static Logger logger = LoggerFactory.getLogger(SynFeatureServiceImpl.class);

    /**
     * 一开始同步所有数据到ERP
     *
     * @param projectPuid
     * @return
     */
    @Override
    public JSONObject synAllByProjectPuid(String projectPuid) {
        return null;
    }

    /**
     * 添加特性值
     *
     * @param features
     * @return
     */
    @Override
    public JSONObject addFeature(List<HzFeatureValue> features) throws Exception {
        return execute(features, ActionFlagOption.ADD);
    }

    /**
     * 更新特性值
     *
     * @param features
     * @return
     */
    @Override
    public JSONObject updateFeature(List<HzFeatureValue> features) throws Exception {
        return execute(features, ActionFlagOption.UPDATE);
    }

    /**
     * 删除特性值
     *
     * @param features
     * @return
     */
    @Override
    public JSONObject deleteFeature(List<HzFeatureValue> features) throws Exception {
        List<HzFeatureValue> records;
        records = features.stream().filter(f -> f.getIsFeatureSent() == 1).collect(Collectors.toList());
        return execute(records, ActionFlagOption.DELETE);
    }

    /**
     * 先删除后添加ERP
     *
     * @param projectPuid
     * @return
     */
    @Override
    public JSONObject deleteThenAdd(String projectPuid) {
        return null;
    }


    private JSONObject execute(List<HzFeatureValue> records, ActionFlagOption option) throws Exception {
        transCfgService.setClearInputEachTime(true);
        List<HzFeatureValue> toSend = new ArrayList<>();
        //需要更新的数据，更新特性属性
        List<HzFeatureValue> needToUpdateStatus = new ArrayList<>();
        Map<String, List<HzFeatureValue>> _mapCoach = new HashMap<>();
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

        if (records == null || records.size() <= 0) {
            result.put("status", false);
            result.put("msg", "选择的列为空，请至少选择1列发送");
            return result;
        }

        Map<String, Map<String, HzFeatureValue>> coach = new HashMap<>();
        Map<String, String> packNumOfFeature = new HashMap<>();
        String packnum = UUIDHelper.generateUpperUid();
        records.forEach(_r -> {
            HzFeatureValue record;
            if ((record = hzFeatureServiceImpl.doSelectOneByPuid(_r.getPuid())) != null
//                    || (record = hzFeatureServiceImpl.doSelectOneAddedCfgByPuid(_r.getId())) != null
                    ) {
                toSend.add(record);
            }
        });

        int index;
        for (HzFeatureValue record : toSend) {
            String fpuid = record.getpCfg0FamilyPuid();
//            //收录特性集合
//            if (!_mapCoach.containsKey(fpuid)) {
//                List<HzFeatureValue> _list = new ArrayList<>();
//                _list.add(record);
//                _mapCoach.put(fpuid, _list);
//            } else {
//                _mapCoach.get(fpuid).add(record);
//            }
            //没有父层的puid
            if (!packNumOfFeature.containsKey(fpuid)) {
                //添加父层puid和包号的对应关系
                packNumOfFeature.put(fpuid, packnum);
                packnum = UUIDHelper.generateUpperUid();
            }
            //收录包号对应的特性
            index = 1;
            Features features = new Features();
            //有没有包号，没有则添加包号
            if (!coach.containsKey(packNumOfFeature.get(fpuid))) {
                Map<String, HzFeatureValue> _m = new HashMap<>();
                _m.put(String.valueOf(index), record);
                coach.put(packNumOfFeature.get(fpuid), _m);
            } else {
                index += coach.get(packNumOfFeature.get(fpuid)).size();
            }
            //数据包号
            features.setPackNo(packNumOfFeature.get(record.getpCfg0FamilyPuid()));
            //行号
            features.setLineNum(String.valueOf(index));

            //动作描述代码
            if (option == ActionFlagOption.ADD) {
                //没有发送过，添加发送
                if (null == record.getIsFeatureSent() || record.getIsFeatureSent() == 0) {
                    features.setActionFlag(option);
                }
                //有发送过，执行更新
                else {
                    features.setActionFlag(ActionFlagOption.UPDATE);
                }
            }
            //执行更新或删除
            else {
                features.setActionFlag(option);
            }
            //特性编码
            features.setFeaturesCode(record.getpCfg0FamilyName());
            //特性描述
            features.setFeaturesDescribe(record.getpCfg0FamilyDesc());
            //特性值编码
            features.setPropertyValuesCode(record.getFeatureValueCode());
            //特性值描述
            features.setPropertyValuesDescribe(record.getpCfg0Desc());
            //            featuresList.add(features);
            coach.get(packNumOfFeature.get(fpuid)).put(features.getLineNum(), record);
            transCfgService.getInput().getItem().add(features.getZpptci002());
        }

        if (!SynMaterielServiceImpl.debug) {
            transCfgService.execute();
        }

        List<ZPPTCO002> list = transCfgService.getOut().getItem();
        try {
            if (list != null && list.size() > 0) {
                for (ZPPTCO002 _l : list) {
                    total++;
                    if (_l == null) {
                        totalOfUnknown++;
                        continue;
                    }
                    IntegrateMsgResponseDTO dto = new IntegrateMsgResponseDTO();
                    HzFeatureValue record = coach.get(_l.getPPACKNO()).get(_l.getPZITEM());
                    dto.setItemId(record.getFeatureValueCode());
                    dto.setMsg(_l.getPMESSAGE());
                    dto.setPuid(record.getPuid());
                    if ("S".equalsIgnoreCase(_l.getPTYPE())) {
                        success.add(dto);
                        _forDelete.add(record.getpCfg0FamilyName() + "-" + record.getpCfg0FamilyDesc() + "-" + record.getFeatureValueCode() + "-" + record.getpCfg0FamilyDesc());
                        totalOfSuccess++;
                        //添加进传输成功list中进行批量更新
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
                //像这里就执行更新了，把某个字段更新,我记得字段都定义成了is_sent
                if (needToUpdateStatus.size() > 0) {
                    hzFeatureServiceImpl.doUpdateByBatch(_map);
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
