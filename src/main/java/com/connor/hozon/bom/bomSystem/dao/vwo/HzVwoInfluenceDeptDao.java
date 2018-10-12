package com.connor.hozon.bom.bomSystem.dao.vwo;

import com.connor.hozon.bom.bomSystem.dao.BasicDao;
import org.apache.ibatis.annotations.Param;
import sql.pojo.cfg.vwo.HzVwoInfluenceDept;

public interface HzVwoInfluenceDeptDao  extends BasicDao<HzVwoInfluenceDept>{
    /**
     * 主键删除
     *
     * @param id
     * @return
     */
    int deleteByPrimaryKey(Long id);

    /**
     * 主键查询
     *
     * @param id
     * @return
     */

    HzVwoInfluenceDept selectByPrimaryKey(Long id);

    /**
     * 根据VWO ID查找影响部门
     *
     * @param vwoId
     * @return
     */
    HzVwoInfluenceDept selectByVwoId(@Param("vwoId") Long vwoId);

}