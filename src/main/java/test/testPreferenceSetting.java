//package test;
//
//import com.connor.hozon.bom.bomSystem.dao.bom.HzBomDataDao;
//import com.connor.hozon.bom.bomSystem.dao.bom.HzBomMainRecordDao;
//import org.junit.jupiter.api.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringRunner;
//import share.bean.PreferenceSetting;
//import com.connor.hozon.bom.bomSystem.dao.bom.HzPreferenceSettingDao;
//import sql.pojo.HzPreferenceSetting;
//import sql.pojo.bom.HZBomMainRecord;
//import sql.redis.SerializeUtil;
//
//@SpringBootTest
//@RunWith(SpringRunner.class)
//public class testPreferenceSetting {
//    @Autowired
//    HzBomDataDao hzBomDataDao;
//    @Autowired
//    HzBomMainRecordDao hzBomMainRecordDao;
//    @Autowired
//
//    @Test
//    public void testPreferenceSetting() {
//        HzPreferenceSetting setting = new HzPreferenceSetting();
//        setting.setSettingName("Hz_ExportBomPreferenceRedis");
//        HZBomMainRecord main = hzBomMainRecordDao.selectByProjectPuid("c1a7c2ea-d687-4f01-b1d3-ba63fec57281");
//        setting.setBomMainRecordPuid(main.getPuid());
//        setting = hzBomDataDao.loadSetting(setting);
//        byte[] btOfSetting = setting.getPreferencesettingblock();
//        Object objOfSetting = SerializeUtil.unserialize(btOfSetting);
//        if (objOfSetting instanceof PreferenceSetting) {
//
//        }
//    }
//}
