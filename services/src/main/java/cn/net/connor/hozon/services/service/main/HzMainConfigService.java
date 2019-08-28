/*
 *
 *  * Copyright (c) 2019 Author: Fancyears Milos Malvis @1243093366@qq.com
 *  * ALL COPYRIGHT REVERSED.
 *
 */

package cn.net.connor.hozon.services.service.main;

import cn.net.connor.hozon.dao.pojo.main.HzMainConfig;

/**
 * @Author: Fancyears Milos Malvis
 * @Date: Created in  2019/8/1 11:21
 * @Modified By:
 */
public interface HzMainConfigService {
    /**
     * 根据项目ID查询主配置
     * @param projectId
     * @return
     */
    HzMainConfig selectByProjectId(String projectId);

    /**
     * 主键查询
     * @param id
     * @return
     */
    HzMainConfig doGetByPrimaryKey(String id);

    /**
     * 选择更新
     *
     * @param mainRecord
     * @return
     */
    boolean doUpdateByPrimaryKeySelective(HzMainConfig mainRecord) ;
}
