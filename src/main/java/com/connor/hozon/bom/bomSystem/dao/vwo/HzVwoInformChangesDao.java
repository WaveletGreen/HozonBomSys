package com.connor.hozon.bom.bomSystem.dao.vwo;

import com.connor.hozon.bom.bomSystem.dao.BasicDao;
import org.apache.ibatis.annotations.Param;
import sql.pojo.cfg.vwo.HzVwoInformChanges;

import java.util.List;

public interface HzVwoInformChangesDao extends BasicDao<HzVwoInformChanges> {
    /**
     * 主键删除
     *
     * @param id
     * @return
     */
    int deleteByPrimaryKey(@Param("id") Long id);

    /**
     * 主键查询
     *
     * @param id
     * @return
     */
    HzVwoInformChanges selectByPrimaryKey(@Param("id") Long id);

    /**
     * 根据VWO主键查询所有关联的人员
     *
     * @param vwoId
     * @return
     */
    List<HzVwoInformChanges> selectByVwoId(@Param("vwoId") Long vwoId);

    /**
     * 获取VWO号下的总关联数
     *
     * @param vwoId
     * @return
     */
    Long tellMeHowManyOfIt(@Param("vwoId") Long vwoId);
}