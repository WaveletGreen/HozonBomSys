package com.connor.hozon.bom.bomSystem.service.bom;

import com.connor.hozon.bom.bomSystem.dao.bom.HzBomDataDao;
import com.connor.hozon.bom.bomSystem.dao.bom.HzBomMainRecordDao;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import share.bean.PreferenceSetting;
import share.bean.RedisBomBean;
import sql.pojo.HzPreferenceSetting;
import sql.pojo.bom.HZBomMainRecord;
import sql.pojo.bom.HzBomLineRecord;
import sql.redis.SerializeUtil;

import java.util.HashMap;
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
    @Autowired
    HzBomMainRecordDao hzBomMainRecordDao;

    /**
     * 根据项目puid获取到bom列的设置
     *
     * @param bdf 项目puid
     * @return
     */
    public JSONArray doLoadColumns(String bdf) {
        int appendCount = 5;
        JSONArray array = new JSONArray();
        HzPreferenceSetting setting = new HzPreferenceSetting();
        setting.setSettingName("Hz_ExportBomPreferenceRedis");
        HZBomMainRecord main = hzBomMainRecordDao.selectByProjectPuid(bdf);
        setting.setBomMainRecordPuid(main.getPuid());
        setting = hzBomDataDao.loadSetting(setting);
        byte[] btOfSetting = setting.getPreferencesettingblock();
        Object objOfSetting = SerializeUtil.unserialize(btOfSetting);
        if (objOfSetting instanceof PreferenceSetting) {
            String[] localName = ((PreferenceSetting) objOfSetting).getPreferenceLocal();
            String[] trueName = ((PreferenceSetting) objOfSetting).getPreferences();
            String[] appendLocalName = new String[localName.length + appendCount];
            String[] appendTrueName = new String[trueName.length + appendCount];

            appendLocalName[0] = "序号";
            appendLocalName[1] = "顺序号";
            appendLocalName[2] = "ID";
            appendLocalName[3] = "父层ID";
            appendLocalName[4] = "是否是2Y层";

            appendTrueName[0] = "index";
            appendTrueName[1] = "on";
            appendTrueName[2] = "id";
            appendTrueName[3] = "pid";
            appendTrueName[4] = "is2Y";

            System.arraycopy(localName, 0, appendLocalName, appendCount, localName.length);
            System.arraycopy(trueName, 0, appendTrueName, appendCount, trueName.length);

            array.add(0, appendTrueName);
            array.add(1, appendLocalName);
//                array.add(0, trueName);
//                array.add(1, localName);
        }
        return array;
    }

    public JSONArray load(String bdf) {
        List<HzBomLineRecord> result = null;
        HzBomLineRecord bomLineRecord = new HzBomLineRecord();
        bomLineRecord.setProjectPuid(bdf.trim());
        result = hzBomDataDao.selectByProjectPuid(bomLineRecord);
        JSONArray array = new JSONArray();
        if (result.size() > 0) {
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

    /**
     * 获取项目下的所有2Y层
     *
     * @param projectPuid
     * @return
     */
    public List<HzBomLineRecord> doSelect2YByProjectPuid(String projectPuid) {
        return hzBomDataDao.select2YByProjectPuid(projectPuid);
    }

    /**
     * 传入部门和项目UID，获取部门下2Y层的子总成
     *
     * @param dept        部门名
     * @param projectUid 项目UID
     * @return 一组总成，需要进行查重操作
     */
    public List<HzBomLineRecord> doSelectVehicleAssembly(String dept, String projectUid) {
        HashMap params = new HashMap();
        params.put("dept", dept);
        params.put("projectUid", projectUid);
        return hzBomDataDao.selectVehicleAssembly(params);
    }
}
