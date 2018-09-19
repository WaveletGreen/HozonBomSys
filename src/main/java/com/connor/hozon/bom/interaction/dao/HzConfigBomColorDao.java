package com.connor.hozon.bom.interaction.dao;

import com.connor.hozon.bom.bomSystem.dao.BasicDao;
import sql.pojo.interaction.HzConfigBomColorBean;

import java.util.List;

/**
 * @Author: Fancyears·Maylos·Maywas
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
}
