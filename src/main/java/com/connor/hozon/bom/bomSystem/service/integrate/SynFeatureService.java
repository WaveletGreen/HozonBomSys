package com.connor.hozon.bom.bomSystem.service.integrate;

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
        List<Features> featuresList = new ArrayList<>();
        Map<String, HzCfg0Record> _mapCoach = new HashMap<>();
        JSONObject result = new JSONObject();
        StringBuilder sbs = new StringBuilder();
        sbs.append("发送成功:<br/>");
        StringBuilder sbf = new StringBuilder();
        sbf.append("发送失败:<br/>");

        boolean hasFail = false;

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

        int index = 0;
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
            transCfgService.getInput().getItem().add(features.getZpptci002());
            _mapCoach.put(packnum, record);
        }

        transCfgService.execute();
        List<ZPPTCO002> list = transCfgService.getOut().getItem();
        if (list != null && list.size() > 0) {
            result.put("status", true);
            for (ZPPTCO002 _l : list) {
                if ("S".equalsIgnoreCase(_l.getPTYPE())) {
                    sbs.append("&emsp;&emsp;&emsp;&emsp;" + _mapCoach.get(_l.getPPACKNO()).getpCfg0ObjectId() + "<br/>");
                } else {
                    sbf.append("&emsp;&emsp;&emsp;&emsp;" + _mapCoach.get(_l.getPPACKNO()).getpCfg0ObjectId() + "(" + _l.getPMESSAGE() + ")<br/>");
                    hasFail = true;
                }
            }
        }
        if (hasFail) {
            result.put("msg", sbs.append("<br/>" + sbf).toString());
        } else {
            result.put("msg", sbs.toString());
        }
        return result;
    }
}
