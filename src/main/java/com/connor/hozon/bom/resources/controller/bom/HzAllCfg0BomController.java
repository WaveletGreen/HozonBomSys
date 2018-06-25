package com.connor.hozon.bom.resources.controller.bom;

import com.connor.hozon.bom.bomSystem.dao.bom.HzBomDataDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import share.bean.PreferenceSetting;
import sql.pojo.HzPreferenceSetting;
import sql.redis.SerializeUtil;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * \* User: xulf
 * \* Date: 2018/6/1
 * \* Time: 16:45
 * \
 */
@Controller
@RequestMapping("/allCfg0Bom")
public class HzAllCfg0BomController {
    @Autowired
    HzBomDataDao hzBomDataDao;

    @RequestMapping(value = "update/list/maintain", method = RequestMethod.GET)
    public String updatePbomLineMaintainRecordList(Model model) {
        LinkedHashMap<String, String> map = new LinkedHashMap<>();
        map.putAll(getEbomTitle());
        map.putAll(getPbomTitle());
        map.putAll(getMbomTitle());
        model.addAttribute("entity", map);
        return "pbom/mbomMaintenance/updatePbomMaintenance";
    }

    public Map<String, String> getEbomTitle() {
        LinkedHashMap<String, String> map = new LinkedHashMap<>();
        HzPreferenceSetting setting = new HzPreferenceSetting();
        setting.setBomMainRecordPuid("guatQrI446t5TA");
        setting = hzBomDataDao.loadSetting(setting);
        byte[] btOfSetting = setting.getPreferencesettingblock();
        Object objOfSetting = SerializeUtil.unserialize(btOfSetting);
        if (objOfSetting instanceof PreferenceSetting) {
            String[] localName = ((PreferenceSetting) objOfSetting).getPreferenceLocal();
            String[] trueName = ((PreferenceSetting) objOfSetting).getPreferences();
            for (int i = 0; i < localName.length; i++) {
                map.put(trueName[i], localName[i]);
            }
        }
        return map;
    }

    public Map<String, String> getPbomTitle() {
        LinkedHashMap<String, String> map = new LinkedHashMap<>();
        map.put("BJ", "备件");
        map.put("BJBH", "备件编号");
        map.put("GYLX", "工艺路线");
        map.put("GRGS", "人工工时");
        map.put("", "节拍");
        map.put("", "焊点\n");
        map.put("", "机物料\n");
        map.put("", "标准件\n");
        map.put("", "工具\n");
        map.put("", "废品\n");
        return map;
    }

    public Map<String, String> getMbomTitle() {
        LinkedHashMap<String, String> map = new LinkedHashMap<>();
        map.put("BJ", "备件");
        map.put("BJBH", "备件编号");
        map.put("GYLX", "工艺路线");
        map.put("GRGS", "人工工时");
        map.put("", "节拍");
        map.put("", "焊点\n");
        map.put("", "机物料\n");
        map.put("", "标准件\n");
        map.put("", "工具\n");
        map.put("", "废品\n");
        return map;
    }
}
