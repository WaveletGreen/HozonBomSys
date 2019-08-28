/*
 *
 *  * Copyright (c) 2019 Author: Fancyears Milos Malvis @1243093366@qq.com
 *  * ALL COPYRIGHT REVERSED.
 *
 */

package cn.net.connor.hozon.dao.dao.configuration.fullConfigSheet;


import cn.net.connor.hozon.dao.dao.configuration.BasicDao;
import cn.net.connor.hozon.dao.pojo.configuration.fullConfigSheet.HzFullCfgMainChange;
import cn.net.connor.hozon.dao.query.configuration.fullConfigSheet.HzFullCfgMainChangeQuery;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 全配置BOM一级清单主配置变更dao
 */
@Repository
public interface HzFullCfgMainChangeDao extends BasicDao<HzFullCfgMainChange> {

    /**
     * 根据便更好查询
     *
     * @param changeOrderId
     * @return
     */
    HzFullCfgMainChange selectByChangeId(@Param("changeOrderId") Integer changeOrderId);

    /**
     * //TODO 这段查询有问题
     * 根据最新的变更号查询最新的主配置变更数据
     *
     * @param srcMainId
     * @return
     */
    HzFullCfgMainChange selectLastFullCfg(Long srcMainId);

    /**
     * 根据项目ID查询最新的全配置BOM一级清单主配置
     *
     * @param projectId
     * @return
     */
    HzFullCfgMainChange selectLastByProjectUid(@Param("projectId") String projectId);

    int updateStatusByOrderId(HzFullCfgMainChangeQuery query);

    int deleteById(@Param("mainId") Long mainId);

    List<HzFullCfgMainChange> selectNotEffectByProjectUid(@Param("projectId") String projectId);
}
