package com.connor.hozon.bom.bomSystem.dao.fullCfg;

import sql.pojo.cfg.fullCfg.HzFcfgBomLvl1ListOperation;

@Deprecated
public interface HzFcfgBomLvl1ListOperationDao {
    /**
     * 根据主键删除，真的删除
     * @param puid 主键，来自bomline
     * @return
     */
    int deleteByPrimaryKey(String puid);
    /**
     * 根据主键删除，逻辑删除，这只改状态值status=0
     * @param record 记录，包含主键，来自bomline
     * @return
     */
    int logicDeleteByPrimaryKey(HzFcfgBomLvl1ListOperation record);

    /**
     * 插入1条操作信息，插入应该为新增，反之为更新
     * @param record
     * @return
     */
    int insert(HzFcfgBomLvl1ListOperation record);

    /**
     * 根据主键获取操作信息
     * @param puid 主键，来自bomline
     * @return
     */
    HzFcfgBomLvl1ListOperation selectByPrimaryKey(String puid);

    /**
     * 更新操作，应设置P_OPRATION_TYPE_NAME为更新，操作人和操作日期
     * @param record 1条操作信息
     * @return
     */
    int updateByPrimaryKey(HzFcfgBomLvl1ListOperation record);
}