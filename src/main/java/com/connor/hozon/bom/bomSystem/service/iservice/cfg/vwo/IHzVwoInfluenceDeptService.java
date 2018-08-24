package com.connor.hozon.bom.bomSystem.service.iservice.cfg.vwo;

import org.apache.ibatis.annotations.Param;
import org.springframework.context.annotation.Configuration;
import sql.pojo.cfg.vwo.HzVwoInfluenceDept;

/**
 * @Author: Fancyears·Maylos·Maywas
 * @Description:
 * @Date: Created in 2018/8/20 14:07
 * @Modified By:
 */
@Configuration
public interface IHzVwoInfluenceDeptService {
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
    int doInsert(HzVwoInfluenceDept record);

    /**
     * 非空插入
     *
     * @param record
     * @return
     */
    int doInsertSelective(HzVwoInfluenceDept record);

    /**
     * 主键查询
     *
     * @param id
     * @return
     */

    HzVwoInfluenceDept doSelectByPrimaryKey(Long id);
    /**
     * 根据VWO ID查找影响部门
     *
     * @param vwoId
     * @return
     */
    HzVwoInfluenceDept doSelectByVwoId(@Param("vwoId") Long vwoId);
    /**
     * 主键+非空更新
     *
     * @param record
     * @return
     */
    int doUpdateByPrimaryKeySelective(HzVwoInfluenceDept record);

    /**
     * 主键全更新
     *
     * @param record
     * @return
     */
    int doUpdateByPrimaryKey(HzVwoInfluenceDept record);
}
