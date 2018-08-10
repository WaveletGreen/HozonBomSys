package com.connor.hozon.bom.bomSystem.service.iservice.cfg.vwo;

import sql.pojo.cfg.vwo.HzVwoInfo;

/**
 * @Author: Fancyears·Maylos·Maywas
 * @Description:
 * @Date: Created in 2018/8/10 13:53
 * @Modified By:
 */
public interface IHzVwoInfoService {
    /**
     * 主键删除
     *
     * @param id
     * @return
     */
    boolean doDeleteByPrimaryKey(Long id);

    /**
     * 插入1条vwo
     *
     * @param record
     * @return
     */
    int doInsert(HzVwoInfo record);

    /**
     * 主键搜索
     *
     * @param id
     * @return
     */
    HzVwoInfo doSelectByPrimaryKey(Long id);

    /**
     * 更新vwo
     *
     * @param record
     * @return
     */
    boolean doUpdateByPrimaryKey(HzVwoInfo record);

    /**
     * 寻找当月最大的vwo
     *
     * @return
     */
    HzVwoInfo doFindMaxAreaVwoNum();
}
