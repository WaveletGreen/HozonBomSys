package com.connor.hozon.bom.bomSystem.service.integrate;

import com.connor.hozon.bom.bomSystem.helper.IntegrateMsgDTO;
import com.connor.hozon.bom.bomSystem.helper.UUIDHelper;
import com.connor.hozon.bom.bomSystem.service.cfg.HzCfg0Service;
import com.connor.hozon.bom.bomSystem.service.iservice.integrate.ISynFeatureService;
import integration.base.feature.ZPPTCO002;
import integration.logic.Features;
import integration.option.ActionFlagOption;
import integration.service.impl.cfg2.TransCfgService;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sql.pojo.cfg.HzCfg0Record;

import java.util.*;

@Service("synFeatureService")
public class SynFeatureService implements ISynFeatureService {
    @Autowired
    TransCfgService transCfgService;
    @Autowired
    HzCfg0Service hzCfg0Service;

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
     * @param records
     * @return
     */
    @Override
    public JSONObject addFeature(List<HzCfg0Record> records) throws Exception {
        return execute(records, ActionFlagOption.ADD);
    }

    /**
     * 更新特性值
     *
     * @param projectPuid
     * @return
     */
    @Override
    public JSONObject updateFeature(String projectPuid) {
        return null;
    }

    /**
     * 删除特性值
     *
     * @param projectPuid
     * @return
     */
    @Override
    public JSONObject deleteFeature(String projectPuid) {
        return null;
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


    private JSONObject execute(List<HzCfg0Record> records, ActionFlagOption option) throws Exception {
        transCfgService.setClearInputEachTime(true);
        List<HzCfg0Record> toSend = new ArrayList<>();
        Map<String, HzCfg0Record> _mapCoach = new HashMap<>();
        JSONObject result = new JSONObject();
        /**
         * 成功项
         */
        List<IntegrateMsgDTO> success = new ArrayList<>();
        /**
         * 失败项
         */
        List<IntegrateMsgDTO> fail = new ArrayList<>();
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

        Map<String, Map<String, HzCfg0Record>> coach = new HashMap<>();

        Map<String, String> packNumOfFeature = new HashMap<>();
        String packnum = UUIDHelper.generateUpperUid();
        records.forEach(_r -> {
            HzCfg0Record record;
            if ((record = hzCfg0Service.doSelectOneByPuid(_r.getPuid())) != null
                    || (record = hzCfg0Service.doSelectOneAddedCfgByPuid(_r.getPuid())) != null) {

                toSend.add(record);
            }
        });

        int index;
        for (HzCfg0Record record : toSend) {
            index = 1;
            Features features = new Features();
            String fpuid = record.getpCfg0FamilyPuid();
            //没有父层的puid
            if (!packNumOfFeature.containsKey(fpuid)) {
                //添加父层puid和包号的对应关系
                packNumOfFeature.put(fpuid, packnum);
                packnum = UUIDHelper.generateUpperUid();
            }
            //有没有包号，没有则添加包号
            if (!coach.containsKey(packNumOfFeature.get(fpuid))) {
                Map<String, HzCfg0Record> _m = new HashMap<>();
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
            features.setActionFlag(option);
            //特性编码
            features.setFeaturesCode(record.getpCfg0FamilyName());
            //特性描述
            features.setFeaturesDescribe(record.getpCfg0FamilyDesc());
            //特性值编码
            features.setPropertyValuesCode(record.getpCfg0ObjectId());
            //特性值描述
            features.setPropertyValuesDescribe(record.getpCfg0Desc());
            //            featuresList.add(features);
            coach.get(packNumOfFeature.get(fpuid)).put(features.getLineNum(), record);
            transCfgService.getInput().getItem().add(features.getZpptci002());
        }

        transCfgService.execute();
        List<ZPPTCO002> list = transCfgService.getOut().getItem();
        if (list != null && list.size() > 0) {
            for (ZPPTCO002 _l : list) {
                total++;
                if (_l == null) {
                    totalOfUnknown++;
                    continue;
                }
                IntegrateMsgDTO dto = new IntegrateMsgDTO();
                HzCfg0Record record = coach.get(_l.getPPACKNO()).get(_l.getPZITEM());
                dto.setItemId(record.getpCfg0ObjectId());
                dto.setMsg(_l.getPMESSAGE());
                dto.setPuid(record.getPuid());
                if ("S".equalsIgnoreCase(_l.getPTYPE())) {
                    success.add(dto);
                    totalOfSuccess++;
                } else {
                    fail.add(dto);
                    totalOfFail++;
                }
            }
        }

        result.put("success", success);
        result.put("fail", fail);

        result.put("total", total);
        result.put("totalOfSuccess", totalOfSuccess);
        result.put("totalOfFail", totalOfFail);
        result.put("totalOfOutOfParent", totalOfOutOfParent);
        result.put("totalOfUnknown", totalOfUnknown);

        return result;
    }
}
