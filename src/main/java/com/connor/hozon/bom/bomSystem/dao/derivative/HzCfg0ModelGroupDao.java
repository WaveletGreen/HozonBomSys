package com.connor.hozon.bom.bomSystem.dao.derivative;

import com.connor.hozon.bom.bomSystem.dao.BasicDao;
import sql.pojo.cfg.derivative.HzCfg0ModelGroup;

public interface HzCfg0ModelGroupDao extends BasicDao<HzCfg0ModelGroup> {
    /**
     * 主键删除
     * @param id
     * @return
     */
    int deleteByPrimaryKey(String id);


    /**
     * 主键筛选
     * @param id
     * @return
     */
    HzCfg0ModelGroup selectByPrimaryKey(String id);

    /**
     * 根据主配置寻找模型族
     * @param mainUid
     * @return
     */
    HzCfg0ModelGroup selectByMainUid(String mainUid);

    /**
     * 根据主配置寻找族名
     * @param mainUid
     * @return
     */
    String selectGroupNameByMainUid(String mainUid);
}