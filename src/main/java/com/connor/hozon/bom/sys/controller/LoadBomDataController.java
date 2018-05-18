package com.connor.hozon.bom.sys.controller;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import sql.pojo.cfg.HzCfg0BomLineOfModel;
import share.bean.PreferenceSetting;
import share.bean.RedisBomBean;
import sql.BaseSQLUtil;
import sql.IBaseSQLUtil;
import sql.pojo.bom.HzBomLineRecord;
import sql.pojo.HzPreferenceSetting;
import sql.redis.SerializeUtil;

import javax.validation.constraints.NotNull;
import java.util.*;

@Controller
@RequestMapping("/loadBom")
public class LoadBomDataController {

    IBaseSQLUtil baseSQLUtil;

    @RequestMapping(value = "/loadCfg0BomLineOfModel", method = RequestMethod.GET)
    @ResponseBody
    public JSONObject loadCfg0BomLineOfModel(@RequestParam String bdf) {
        JSONObject respond = new JSONObject();
        JSONArray _data = new JSONArray();
        JSONArray _model = new JSONArray();
        IBaseSQLUtil baseSQLUtil = null;
        if (baseSQLUtil == null) {
            baseSQLUtil = new BaseSQLUtil();
        }
        List<HzCfg0BomLineOfModel> hzCfg0BomlineOfModels = baseSQLUtil.executeQueryByPass(new HzCfg0BomLineOfModel(), bdf, "sql.mapper.cfg.i.HzCfg0BomLineOfModelMapper.selectByModelMainId");
        if (hzCfg0BomlineOfModels == null || hzCfg0BomlineOfModels.size() <= 0)
            return null;
        Set<String> optionName = new HashSet<>();
        Map<String, HzCfg0BomLineOfModel> mapToModel = new HashMap<>();
        Map<String, HzCfg0BomLineOfModel> modelWithBomLineMap = new HashMap();
        Map<String, List<HzCfg0BomLineOfModel>> mapModelHasCfg0 = new HashMap<>();

        hzCfg0BomlineOfModels.forEach((model) -> {
            mapToModel.put(model.getObjectName(), model);
            optionName.add(model.getpCfg0OptionValue());
            if (model.getpBomLineId() != null) {
                modelWithBomLineMap.put(model.getpBomLineId(), model);
            }
        });
        //hzCfg0BomlineOfModels.stream().filter(model->model.getpBomLineId()!=null).forEach(model->modelWithBomLineMap.put(model.getpBomLineId(), model));
        //hzCfg0BomlineOfModels.stream().filter(model -> model.getpBomLineId() != null).map(model -> modelWithBomLineMap.put(model.getpBomLineId(), model));

        mapToModel.keySet().forEach((name) -> {
            List<HzCfg0BomLineOfModel> models = new ArrayList<>();
            hzCfg0BomlineOfModels.forEach((model) -> {
                if (model.getObjectName().equals(name)) {
                    models.add(model);
                }
            });
            mapModelHasCfg0.put(name, models);
        });
        modelWithBomLineMap.forEach((keyOfMap, withBomLine) -> {
            JSONObject data = new JSONObject();
            data.put(HzCfg0BomLineOfModel.selfDesc[0], withBomLine.getpBomOfWhichDept());
            data.put(HzCfg0BomLineOfModel.selfDesc[1], withBomLine.getpBomLineId());
            data.put(HzCfg0BomLineOfModel.selfDesc[2], withBomLine.getpBomLineName());
            data.put(HzCfg0BomLineOfModel.selfDesc[3], withBomLine.getpBomLineName());
            data.put(HzCfg0BomLineOfModel.selfDesc[4], withBomLine.getpCfg0Desc() != null ? withBomLine.getpCfg0Desc() : "");
            data.put(HzCfg0BomLineOfModel.selfDesc[5], withBomLine.getpCfg0ObjectId());
            data.put(HzCfg0BomLineOfModel.selfDesc[6], withBomLine.getpCfg0Desc() != null ? withBomLine.getpCfg0Desc() : "");
            mapModelHasCfg0.forEach((key, value) -> {
                for (HzCfg0BomLineOfModel model : value) {
                    if (keyOfMap.equals(model.getpBomLineId())) {
                        if (model.getpParseLogicValue() == 1) {
                            data.put(key, "●");
                        } else {
                            data.put(key, "○");
                        }
                        break;
                    } else {
                        data.put(key, "-");
                    }
                }
            });
            _data.add(data);
        });

        mapToModel.forEach((key, value) -> {
            JSONObject object = new JSONObject();
            object.put("key", key);
            object.put("hide", value.getModelPuid());
            _model.add(object);
        });
        respond.put("data", _data);
        respond.put("model", _model);
        System.out.println();
        return respond;
    }

    @RequestMapping(value = "/loadByID", method = RequestMethod.GET)
    @ResponseBody
    public JSONArray getLineRecords(@RequestParam String bdf) {
        /*
         * d050cb5e-2f54-4782-92dc-6cf750e0b066
         */
        int appendCount = 5;
        if (baseSQLUtil == null) {
            baseSQLUtil = new BaseSQLUtil();
        }
        List<HzBomLineRecord> result = null;
        HzBomLineRecord bomLineRecord = new HzBomLineRecord();
        HzPreferenceSetting setting = new HzPreferenceSetting();
        setting.setSettingName("Hz_ExportBomPreferenceRedis");
        bomLineRecord.setBomDigifaxId(bdf.trim());
        result = baseSQLUtil.executeQuery(bomLineRecord, "sql.mapper.bom.i.HzBomLineRecordMapper.selectByBomDigifaxId");
        JSONArray array = new JSONArray();
        if (result.size() > 0) {
            HzBomLineRecord hbrForSetting = result.get(0);
            setting.setBomMainRecordPuid(hbrForSetting.getBomDigifaxId());
            setting = baseSQLUtil.executeQueryById(setting,
                    "sql.mapper.HzPreferenceSettingMapper.selectSettingByNameWithMainRecord");
            byte[] btOfSetting = setting.getPreferencesettingblock();
            Object objOfSetting = SerializeUtil.unserialize(btOfSetting);
            if (objOfSetting instanceof PreferenceSetting) {
                String[] localName = ((PreferenceSetting) objOfSetting).getPreferenceLocal();
                String[] trueName = ((PreferenceSetting) objOfSetting).getPreferences();
                String[] appendLocalName = new String[localName.length + appendCount];
                String[] appendTrueName = new String[trueName.length + appendCount];

//                appendLocalName[0] = "序号";
//                appendLocalName[1] = "˳顺序号";
//                appendLocalName[2] = "ID";
//                appendLocalName[3] = "父层ID";
//                appendLocalName[4] = "是否是2Y层";
//
//                appendTrueName[0] = "index";
//                appendTrueName[1] = "on";
//                appendTrueName[2] = "id";
//                appendTrueName[3] = "pid";
//                appendTrueName[4] = "is2Y";
//
//                System.arraycopy(localName, 0, appendLocalName, appendCount, localName.length);
//                System.arraycopy(trueName, 0, appendTrueName, appendCount, trueName.length);
//
//                array.add(0, appendTrueName);
//                array.add(1, appendLocalName);
                array.add(0, trueName);
                array.add(1, localName);
            }
            for (HzBomLineRecord hbr : result) {
                JSONObject object = new JSONObject();
                object.put("index", hbr.getIndex());
                object.put("on", hbr.getOrderNum());
                object.put("id", hbr.getPuid());
                object.put("is2Y", hbr.getIs2Y());
                if (hbr.getParentUid() != null) {
                    object.put("pid", hbr.getParentUid());
                }
                if (hbr.getIs2Y() == 0) {
                    object.put("pid", hbr.getParentUid());
                }
                byte[] xx = hbr.getBomLineBlock();
                Object obj = SerializeUtil.unserialize(xx);
                if (obj instanceof RedisBomBean) {
                    List<String> pSets = ((RedisBomBean) obj).getpSets();
                    List<String> pValues = ((RedisBomBean) obj).getpValues();
                    if (pSets != null && pSets.size() > 0) {
                        for (int i = 0; i < pSets.size(); i++) {
                            object.put(pSets.get(i), pValues.get(i));
                        }
                    }
                    array.add(object);
                }
            }
        }
        return array;
    }


    public static void main(String[] args) {
//        IBaseSQLUtil baseSQLUtil = null;
//        if (baseSQLUtil == null) {
//            baseSQLUtil = new BaseSQLUtil();
//        }
//        List<HzCfg0BomlineOfModel> hzCfg0BomlineOfModels = baseSQLUtil.executeQueryByPass(new HzCfg0BomlineOfModel(), "d050cb5e-2f54-4782-92dc-6cf750e0b066", "sql.mapper.i.bom.HzCfg0BomlineOfModelMapper.selectByDigifaxId");
//        Set<String> modelName = new HashSet<>();
//        hzCfg0BomlineOfModels.forEach((model) -> {
//            modelName.add(model.getObjectName());
//        });
        LoadBomDataController controller = new LoadBomDataController();
//        test1(controller);
    }

    private static void test1(@NotNull LoadBomDataController controller) {
        controller.getLineRecords("d050cb5e-2f54-4782-92dc-6cf750e0b066");
        controller.loadCfg0BomLineOfModel("9deb81ea-9544-473b-aeb5-d7683c7753c3");
    }

}
