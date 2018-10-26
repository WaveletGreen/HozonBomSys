/*
 * Copyright (c) 2018.
 * This file was wrote by fancyears·milos·maywas @connor. Any question/bug you can't post to 1243093366@qq.com.
 * ALL RIGHTS RESERVED.
 */

package com.connor.hozon.bom.bomSystem.dao.bom;

import com.connor.hozon.bom.bomSystem.dao.BasicDao;
import org.apache.ibatis.annotations.Param;
import org.springframework.context.annotation.Configuration;
import sql.pojo.HzPreferenceSetting;
import sql.pojo.bom.HzBomLineRecord;

import java.util.List;
import java.util.Map;

/**
 * @Author: Fancyears·Maylos·Maywas
 * @Description: 0.0.1版的BOM dao层
 * @Date: Created in 2018/8/27 21:17
 * @Modified By:
 */
@Configuration
public interface HzBomDataDao extends BasicDao<HzBomLineRecord>{
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

    /**
     * Author: Fancyears·Maylos·Mayways
     * Description: 根据数模层puid获取到所有的BomLine
     * Date: 2018/5/23 9:59
     *
     * @param projectPuid 项目puid
     * @return
     */
    List<HzBomLineRecord> select2YByProjectPuid(@Param("projectPuid") String projectPuid);

    /**
     * Author: Fancyears·Maylos·Mayways
     * Description: 根据部门和2Y层获取到2Y层的下级总成，不包含modelUid
     * Date: 2018/5/23 9:59
     *
     * @param params 包含部门名称和项目UID
     * @return
     */
    List<HzBomLineRecord> selectVehicleAssembly(Map<String, Object> params);

    /**
     * 查找已经存储好的2级配色方案
     *
     * @param params 包含modelUid
     * @return
     */
    List<HzBomLineRecord> selectVehicleAssembly2(Map<String, Object> params);


}
