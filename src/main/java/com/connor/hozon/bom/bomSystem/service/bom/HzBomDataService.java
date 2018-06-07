package com.connor.hozon.bom.bomSystem.service.bom;

import com.connor.hozon.bom.bomSystem.dao.bom.HzBomDataDao;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import share.bean.PreferenceSetting;
import share.bean.RedisBomBean;
import sql.pojo.HzPreferenceSetting;
import sql.pojo.bom.HzBomLineRecord;
import sql.redis.SerializeUtil;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Fancyears·Maylos·Mayways
 * Date: 2018/5/21
 * Time: 17:35
 */
@Service("hzBomDataService")
public class HzBomDataService {
    @Autowired
    HzBomDataDao hzBomDataDao;

    public JSONArray load(String bdf) {
        /*
         * d050cb5e-2f54-4782-92dc-6cf750e0b066
         */
        int appendCount = 5;
        List<HzBomLineRecord> result = null;
        HzBomLineRecord bomLineRecord = new HzBomLineRecord();
        HzPreferenceSetting setting = new HzPreferenceSetting();
        setting.setSettingName("Hz_ExportBomPreferenceRedis");
        bomLineRecord.setProjectPuid(bdf.trim());
        result = hzBomDataDao.selectByProjectPuid(bomLineRecord);

        JSONArray array = new JSONArray();
        if (result.size() > 0) {
            HzBomLineRecord hbrForSetting = result.get(0);
            setting.setBomMainRecordPuid(hbrForSetting.getBomDigifaxId());
            setting = hzBomDataDao.loadSetting(setting);
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
                if (obj instanceof LinkedHashMap) {
                    if (((LinkedHashMap) obj).size() > 0) {
                        ((LinkedHashMap) obj).forEach((key, value) -> {
                            object.put(key, value);
                        });
                    }
                } else if (obj instanceof RedisBomBean) {
                    List<String> pSets = ((RedisBomBean) obj).getpSets();
                    List<String> pValues = ((RedisBomBean) obj).getpValues();
                    if (null != pSets && pSets.size() > 0 && null != pValues && pValues.size() > 0)
                        for (int i = 0; i < pSets.size(); i++) {
                            object.put(pSets.get(i), pValues.get(i));
                        }
                }
                array.add(object);

            }
        }
        return array;
    }
}
