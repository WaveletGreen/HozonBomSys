package com.connor.hozon.bom.common.config.init;


import com.connor.hozon.bom.bomSystem.dao.bom.HzBomDataDao;
import com.connor.hozon.bom.bomSystem.dao.bom.HzBomMainRecordDao;
import com.connor.hozon.bom.bomSystem.dao.bom.HzPreferenceSettingDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import share.bean.PreferenceSetting;
import sql.pojo.HzPreferenceSetting;
import sql.pojo.bom.HZBomMainRecord;
import sql.redis.SerializeUtil;

/*
 *
 * @auther linzf
 * @create 2017/8/24 0024
 */
@Component
@Order(value = 1)
public class InitDictCache implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(InitDictCache.class);

    //    @Inject
//    @SuppressWarnings("SpringJavaAutowiringInspection")
//    private DictDao dictDao;
    @Autowired
    HzBomDataDao hzBomDataDao;
    @Autowired
    HzBomMainRecordDao hzBomMainRecordDao;

    @Autowired
    HzPreferenceSettingDao hzPreferenceSettingMapper;

    @Override
    public void run(String... strings) throws Exception {
        //List<Dict> dictList = dictDao.loadAll();
        //DictCache.load(dictList);
        logger.warn("------初始化成功------");
//        HzPreferenceSetting setting = new HzPreferenceSetting();
//        setting.setSettingName("Hz_ExportBomPreferenceRedis");
//        HZBomMainRecord main = hzBomMainRecordDao.selectByProjectPuid("c1a7c2ea-d687-4f01-b1d3-ba63fec57281");
//        setting.setBomMainRecordPuid(main.getPuid());
//        setting = hzBomDataDao.loadSetting(setting);
//        byte[] btOfSetting = setting.getPreferencesettingblock();
//        Object objOfSetting = SerializeUtil.unserialize(btOfSetting);
//        boolean yes = false;
//        if (objOfSetting instanceof PreferenceSetting) {
//            for (String str : ((PreferenceSetting) objOfSetting).getPreferences()) {
//                if (str.equals("number")) {
//                    yes = true;
//                    break;
//                } else {
//                    yes = false;
//                }
//            }
//            if (!yes) {
//                String[] preference = new String[((PreferenceSetting) objOfSetting).getPreferences().length + 1];
//                String[] preferenceLocal = new String[((PreferenceSetting) objOfSetting).getPreferenceLocal().length + 1];
////                for (int i = 0; i < preference.length; i++) {
////                    preference[i] = new String("");
////                    preferenceLocal[i] = new String("");
////                }
//                System.arraycopy(((PreferenceSetting) objOfSetting).getPreferences(), 0, preference, 0, ((PreferenceSetting) objOfSetting).getPreferences().length);
//                System.arraycopy(((PreferenceSetting) objOfSetting).getPreferenceLocal(), 0, preferenceLocal, 0, ((PreferenceSetting) objOfSetting).getPreferenceLocal().length);
//                preference[preference.length - 1] = "number";
//                preferenceLocal[preferenceLocal.length - 1] = "数量";
//                ((PreferenceSetting) objOfSetting).setPreferences(preference);
//                ((PreferenceSetting) objOfSetting).setPreferenceLocal(preferenceLocal);
//                setting.setPreferencesettingblock(SerializeUtil.serialize(objOfSetting));
//                hzPreferenceSettingMapper.updateByPrimaryKey(setting);
//                System.out.println();
//            }
//        }

    }
}
