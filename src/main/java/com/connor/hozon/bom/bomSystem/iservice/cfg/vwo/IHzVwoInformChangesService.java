package com.connor.hozon.bom.bomSystem.iservice.cfg.vwo;

import org.apache.ibatis.annotations.Param;
import sql.pojo.cfg.vwo.HzVwoInformChanges;

import java.util.List;

/**
 * @Author: Fancyears·Maylos·Maywas
 * @Description: x
 * @Date: Created in 2018/8/15 16:36
 * @Modified By:
 */
public interface IHzVwoInformChangesService {
    /**
     * 主键删除
     *
     * @param id
     * @return
     */
    int doDeleteByPrimaryKey(@Param("id") Long id);

    /**
     * 插入1条数据
     *
     * @param record
     * @return
     */
    int doInsert(HzVwoInformChanges record);

    /**
     * 选择性插入
     *
     * @param record
     * @return
     */
    int doInsertSelective(HzVwoInformChanges record);

    /**
     * 主键查询
     *
     * @param id
     * @return
     */
    HzVwoInformChanges doSelectByPrimaryKey(@Param("id") Long id);

    /**
     * 根据VWO主键查询所有关联的人员
     *
     * @param vwoId
     * @return
     */
    List<HzVwoInformChanges> doSelectByVwoId(@Param("vwoId") Long vwoId);

    /**
     * 选择性更新
     *
     * @param record
     * @return
     */
    int doUpdateByPrimaryKeySelective(HzVwoInformChanges record);

    /**
     * 主键更新，无选择性更新
     *
     * @param record
     * @return
     */
    int doUpdateByPrimaryKey(HzVwoInformChanges record);

    /**
     * 获取VWO号下的总关联数
     *
     * @param vwoId
     * @return
     */
    Long tellMeHowManyOfIt(@Param("vwoId") Long vwoId);
}
