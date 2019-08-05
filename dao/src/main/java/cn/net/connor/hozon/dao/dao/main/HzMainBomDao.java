/*
 *
 *  * Copyright (c) 2019 Author: Fancyears Milos Malvis @1243093366@qq.com
 *  * ALL COPYRIGHT REVERSED.
 *
 */

package cn.net.connor.hozon.dao.dao.main;

import cn.net.connor.hozon.dao.dao.configuration.BasicDao;
import cn.net.connor.hozon.dao.pojo.main.HzMainBom;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @Author: Fancyears·Maylos·Malvis
 * @Description: BOM主配置dao层
 * @Date: Created in 2018/8/27 21:17
 * @Modified By:
 */
@Repository
public interface HzMainBomDao extends BasicDao<HzMainBom> {
    /**
     * 根据项目查找BOM主配置
     *
     * @param projectUid 项目UID
     * @return 返回当前项目下的主配置
     */
    HzMainBom selectByProjectId(@Param("换成projectId") String projectUid);
}