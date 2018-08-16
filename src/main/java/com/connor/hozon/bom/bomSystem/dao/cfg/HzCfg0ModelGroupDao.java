package com.connor.hozon.bom.bomSystem.dao.cfg;

import sql.pojo.cfg.HzCfg0ModelGroup;

public interface HzCfg0ModelGroupDao {
    /**
     * 主键删除
     * @param id
     * @return
     */
    int deleteByPrimaryKey(String id);

    /**
     * 插入1条模型族数据
     * @param record
     * @return
     */
    int insert(HzCfg0ModelGroup record);

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
     * 主键更新
     * @param record
     * @return
     */
    int updateByPrimaryKey(HzCfg0ModelGroup record);

    /**
     * 根据主配置寻找族名
     * @param mainUid
     * @return
     */
    String selectGroupNameByMainUid(String mainUid);
}