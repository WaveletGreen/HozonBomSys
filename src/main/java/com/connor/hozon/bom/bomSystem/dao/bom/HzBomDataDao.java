package com.connor.hozon.bom.bomSystem.dao.bom;

import org.springframework.context.annotation.Configuration;
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
    /**
     * Author: Fancyears·Maylos·Mayways
     * Description: 根据数模层puid获取到所有的BomLine
     * Date: 2018/5/23 9:59
     *
     * @param bomLineRecord
     * @return
     */
    List<HzBomLineRecord> selectByProjectPuid(HzBomLineRecord bomLineRecord);

    /**
     * Author: Fancyears·Maylos·Mayways
     * Description: 根据数模层的puid，获取到数模层对应的bom设置的首选项信息，用于显示在前端
     * Date: 2018/5/23 10:00
     *
     * @param setting
     * @return
     */
    HzPreferenceSetting loadSetting(HzPreferenceSetting setting);
}
