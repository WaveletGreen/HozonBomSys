package com.connor.hozon.bom.bomSystem.iservice.cfg.vwo;

import sql.pojo.cfg.vwo.HzVwoInfluenceUser;

/**
 * @Author: Fancyears·Maylos·Maywas
 * @Description: x
 * @Date: Created in 2018/8/20 16:20
 * @Modified By:
 */
public interface IHzVwoInfluenceUserService {
    /**
     * 主键删除
     *
     * @param id
     * @return
     */
    int doDeleteByPrimaryKey(Long id);

    /**
     * 全插入
     *
     * @param record
     * @return
     */
    int doInsert(HzVwoInfluenceUser record);

    /**
     * 非空插入
     *
     * @param record
     * @return
     */
    int doInsertSelective(HzVwoInfluenceUser record);

    /**
     * 主键查询
     *
     * @param id
     * @return
     */

    HzVwoInfluenceUser doSelectByPrimaryKey(Long id);

    /**
     * 根据VWO主键进行查询
     *
     * @param vwoId
     * @return
     */
    HzVwoInfluenceUser doSelectByVwoId(Long vwoId);

    /**
     * 主键+非空更新
     *
     * @param record
     * @return
     */
    int doUpdateByPrimaryKeySelective(HzVwoInfluenceUser record);

    /**
     * 全更新
     *
     * @param record
     * @return
     */
    int doUpdateByPrimaryKey(HzVwoInfluenceUser record);
}
