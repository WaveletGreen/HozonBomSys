package com.connor.hozon.bom.bomSystem.dao.vwo;

import com.connor.hozon.bom.bomSystem.dao.BasicDao;
import org.apache.ibatis.annotations.Param;
import sql.pojo.cfg.vwo.HzVwoInfluenceUser;
public interface HzVwoInfluenceUserDao  extends BasicDao<HzVwoInfluenceUser>{
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

    HzVwoInfluenceUser selectByPrimaryKey(@Param("id") Long id);

    /**
     * 根据VWO主键进行查询
     *
     * @param vwoId
     * @return
     */
    HzVwoInfluenceUser selectByVwoId(@Param("vwoId") Long vwoId);

}
