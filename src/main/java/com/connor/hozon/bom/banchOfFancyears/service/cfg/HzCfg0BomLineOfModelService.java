package com.connor.hozon.bom.banchOfFancyears.service.cfg;

import com.connor.hozon.bom.banchOfFancyears.dao.cfg.HzCfg0BomLineOfModelDao;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sql.pojo.cfg.HzCfg0BomLineOfModel;

import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: Fancyears·Maylos·Mayways
 * Date: 2018/5/21
 * Time: 18:14
 */
@Service("hzCfg0BomLineOfModelService")
public class HzCfg0BomLineOfModelService {
    @Autowired
    HzCfg0BomLineOfModelDao hzCfg0BomLineOfModelDao;

    /**
     * @param bdf 数模层ID
     * @return java.util.List<sql.pojo.cfg.HzCfg0BomLineOfModel>
     * Author: Fancyears·Maylos·Mayways
     * Description: 根据数模层的puid获取到下级的所有bom行的配置信息，包含了车型模型的信息在内
     * Date: 2018/5/21 18:28
     */
    public List<HzCfg0BomLineOfModel> doLoadByModelMainId(String bdf) {
        return hzCfg0BomLineOfModelDao.selectByModelMainId(bdf);
    }

    /**
     * @param bdf 数模层puid
     * @return net.sf.json.JSONObject
     * Author: Fancyears·Maylos·Mayways
     * Description: 根据数模层获取到bom的配置信息和车型模型信息
     * Date: 2018/5/21 18:27
     */
    public JSONObject parse(String bdf) {
        List<HzCfg0BomLineOfModel> hzCfg0BomlineOfModels = doLoadByModelMainId(bdf);
        if (hzCfg0BomlineOfModels == null || hzCfg0BomlineOfModels.size() <= 0)
            return null;
        JSONObject respond = new JSONObject();
        JSONArray _data = new JSONArray();
        JSONArray _model = new JSONArray();
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
        return respond;
    }

}
