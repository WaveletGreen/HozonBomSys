/*
 *
 *  * Copyright (c) 2019 Author: Fancyears Milos Malvis @1243093366@qq.com
 *  * ALL COPYRIGHT REVERSED.
 *
 */

package com.connor.hozon.bom.bomSystem.dao.bom;

import sql.pojo.bom.HzBomLineRecord;

import java.util.List;
import java.util.Map;
/**
 * @Author: Fancyears·Maylos·Malvis
 * @Description: 0.0.2版BOM dao层
 * @Date: Created in 2018/8/27 21:17
 * @Modified By:
 */
public interface HzBomLineRecordDao {

    /**
     * 更新颜色件信息  1是颜色件 0不是
     * @param puid
     * @param isColorPart
     * @return
     */
    int updateColorPart(String puid, Integer isColorPart);

    /**
     * 找出一条BOMLine 对象 走变更查询单一条记录
     *
     * @param map 过滤器
     * @return
     * @author haozt
     */
    HzBomLineRecord findBomLineByPuid(Map map);

    /**
     * 找出一条BOMLine 对象 与变更无关
     *
     * @param map 需要传入的参数是<strong>projectId----项目ID</strong> <span style='color:red;'>puid---BOMLine主键</span>
     * @return
     * @author haozt
     */
    HzBomLineRecord findBomLine(Map map) ;


    /**
     * EBOM 批量更新 根据零件号来更新
     * @param records
     * @return
     */
    int updateList(List<HzBomLineRecord> records);

    /**
     * 批量更新
     * 走变更 根据puid更新
     * @param records
     * @return
     */
    @Deprecated
    int updateBatch(List<HzBomLineRecord> records) ;

    List<HzBomLineRecord> selectByPuids(List<String> withCfgPuids);
}