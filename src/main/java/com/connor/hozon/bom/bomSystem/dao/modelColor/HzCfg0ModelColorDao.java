/*
 * Copyright (c) 2018.
 * This file was wrote by fancyears·milos·malvis @connor. Any question/bug you can post to 1243093366@qq.com.
 * ALL RIGHTS RESERVED.
 */

package com.connor.hozon.bom.bomSystem.dao.modelColor;

import com.connor.hozon.bom.bomSystem.dao.BasicDao;
import org.apache.ibatis.annotations.Param;
import org.springframework.context.annotation.Configuration;
import sql.pojo.cfg.modelColor.HzCfg0ModelColor;

import java.util.List;

/**
 * @Author: Fancyears·Maylos·Maywas
 * @Description: 0.0.1版本TC同步数据用dao
 * @Date: Created in 2018/8/27 21:17
 * @Modified By:
 */
@Configuration
public interface HzCfg0ModelColorDao extends BasicDao<HzCfg0ModelColor> {
    /**
     * Author: Fancyears·Maylos·Mayways
     * Description: 根据配置器的puid，获取到所有的颜色车型的信息，包括其中的系统配置信息，map中的信息是经过排序后的
     * Date: 2018/5/23 9:52
     *
     * @param color
     * @return
     */
    List<HzCfg0ModelColor> selectByMainId(HzCfg0ModelColor color);

    /**
     * Author: Fancyears·Maylos·Mayways
     * Description: 获取到所有的颜色车型
     * Date: 2018/5/23 9:54
     *
     * @return
     */
    List<HzCfg0ModelColor> selectAll(@Param("projectPuid") String projectPuid);

    /**
     * 更新旧数据，将旧数据的大对象设置为null
     *
     * @param color 旧数据
     * @return
     */
    int updateOldData(HzCfg0ModelColor color);

    /**
     * Author: Fancyears·Maylos·Mayways
     * Description: 根据主键批量删除颜色车型
     * Date: 2018/5/23 9:55
     *
     * @param colors
     * @return
     */
    int deleteByBatch(List<HzCfg0ModelColor> colors);

    /**
     * 根据puid集合批量查询颜色车型
     * @param colors
     * @return
     */
    List<HzCfg0ModelColor> selectByPuids(List<HzCfg0ModelColor> colors);

    /**
     * 批量修改颜色车型
     * @param hzCfg0ModelColors
     * @return
     */
    int updateListData(List<HzCfg0ModelColor> hzCfg0ModelColors);

    int updateByVwoId(HzCfg0ModelColor hzCfg0ModelColor);

    int updateStatus(List<HzCfg0ModelColor> hzCfg0ModelColorsUpdate);

    int updateListAll(List<HzCfg0ModelColor> hzCfg0ModelColorsUpdate);

    int updateStatusByOrderId(Long orderId, int status);
}
