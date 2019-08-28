/*
 *
 *  * Copyright (c) 2019 Author: Fancyears Milos Malvis @1243093366@qq.com
 *  * ALL COPYRIGHT REVERSED.
 *
 */

package cn.net.connor.hozon.dao.dao.interaction;

import cn.net.connor.hozon.dao.dao.configuration.BasicDao;
import cn.net.connor.hozon.dao.pojo.interaction.HzConfigBomColorBean;
import cn.net.connor.hozon.dao.pojo.interaction.HzConfigBomLineBean;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author: Fancyears·Maylos·Malvis
 * @Description:
 * @Date: Created in 2018/9/19 12:08
 * @Modified By:
 */
@Repository
public interface HzConfigBomColorDao extends BasicDao<HzConfigBomColorBean> {
    /**
     * 根据某个2Y的主键和项目主键查找2Y的所有配色代码
     *
     * @param bomLineUid    2Y主键
     * @param projectUid 项目主键
     * @return
     */
    List<HzConfigBomColorBean> selectBy2YUidWithProject(@Param("bomLineUid") String bomLineUid, @Param("projectUid")String projectUid);

    /**
     * 查询油漆车身的颜色件信息
     *
     * @param projectId
     * @return
     */
    List<HzConfigBomColorBean> selectPaintColorSet(String projectId);

    /**
     * 查询配置那边记录的油漆车身对应的BOM主键
     *
     * @param projectId 项目id
     * @return
     */
    List<HzConfigBomColorBean> selectPaintBomLinePuidFormConfig(String projectId);

    /**
     * 查询当前项目下所有的bomline对应的特性值记录
     *
     * @param projectId 项目id
     * @return
     */
    List<HzConfigBomLineBean> selectAllConfigToBomline(String projectId);
}
