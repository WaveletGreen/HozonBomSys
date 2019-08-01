/*
 *
 *  * Copyright (c) 2019 Author: Fancyears Milos Malvis @1243093366@qq.com
 *  * ALL COPYRIGHT REVERSED.
 *
 */

package cn.net.connor.hozon.dao.dao.main;

import cn.net.connor.hozon.dao.dao.configuration.BasicDao;
import cn.net.connor.hozon.dao.pojo.main.HzMainConfig;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @Author: Fancyears·Maylos·Malvis
 * @Description: 主配置
 * @Date: Created in 2018/8/27 21:17
 * @Modified By:
 */
@Repository
public interface HzMainConfigDao extends BasicDao<HzMainConfig>{
    /**
     * 主键查询
     * @param id
     * @return
     */
    HzMainConfig selectByPrimaryKey(@Param("id") String id);

    /**
     * 根据项目主键查询
     * @param projectId
     * @return
     */
    HzMainConfig selectByProjectId(@Param("projectId") String projectId);

}