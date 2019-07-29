package com.connor.hozon.bom.interaction.dao;

import cn.net.connor.hozon.dao.dao.configuration.BasicDao;
import sql.pojo.interaction.HzConfigBomColorBean;

import java.util.List;

/**
 * @Author: Fancyears·Maylos·Malvis
 * @Description:
 * @Date: Created in 2018/9/19 12:08
 * @Modified By:
 */
public interface HzConfigBomColorDao extends BasicDao<HzConfigBomColorBean> {
    /**
     * 根据某个2Y的主键和项目主键查找2Y的所有配色代码
     *
     * @param bomline    2Y主键
     * @param projectUid 项目主键
     * @return
     */
    List<HzConfigBomColorBean> selectBy2YUidWithProject(String bomline, String projectUid);

    /**
     * 查询油漆车身的颜色件信息
     * @param projectId
     * @return
     */
    List<HzConfigBomColorBean> selectPaintColorSet(String projectId);

    /**
     * 查询配置那边记录的油漆车身对应的BOM主键
     * @param projectId
     * @return
     */
    List<HzConfigBomColorBean> selectPaintBomLinePuidFormConfig(String projectId);
}
