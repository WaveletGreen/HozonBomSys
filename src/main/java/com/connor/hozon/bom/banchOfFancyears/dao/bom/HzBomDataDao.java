package com.connor.hozon.bom.banchOfFancyears.dao.bom;

import org.springframework.context.annotation.Configuration;
import share.bean.PreferenceSetting;
import sql.pojo.HzPreferenceSetting;
import sql.pojo.bom.HzBomLineRecord;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Fancyears·Maylos·Mayways
 * Date: 2018/5/21
 * Time: 17:35
 */
@Configuration
public interface HzBomDataDao {
    List<HzBomLineRecord> selectByBomDigifaxId(HzBomLineRecord bomLineRecord);

    HzPreferenceSetting loadSetting(HzPreferenceSetting setting);
}
