/*
 *
 *  * Copyright (c) 2019 Author: Fancyears Milos Malvis @1243093366@qq.com
 *  * ALL COPYRIGHT REVERSED.
 *
 */

package cn.net.connor.hozon.dao.dao.configuration.fullConfigSheet;

import cn.net.connor.hozon.dao.MyBatisBaseDao;
import cn.net.connor.hozon.dao.pojo.configuration.fullConfigSheet.HzExFullCfgWithCfg;
import cn.net.connor.hozon.dao.pojo.configuration.fullConfigSheet.HzFullCfgWithCfg;
import cn.net.connor.hozon.dao.query.configuration.fullConfigSheet.HzFullCfgWithCfgQuery;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author: Fancyears·Maylos·Malvis
 * @Description: 全配置BOM一级清单2Y层对应的配置项
 * @Date: Created in 2018/8/27 21:17
 * @Modified By:
 */
@Repository
public interface HzFullCfgWithCfgDao extends MyBatisBaseDao<HzFullCfgWithCfg, Long, HzFullCfgWithCfgQuery> {

    int insert(List<HzFullCfgWithCfg> hzFullCfgWithCfgs);

    List<HzFullCfgWithCfg> selectByMainID(@Param("flCfgVersion") Long flCfgVersion);

    int insertBomLine(List<HzFullCfgWithCfg> hzFullCfgWithCfgs);

    int updateByBomLinePuid(HzFullCfgWithCfg hzFullCfgWithCfg);

    List<HzFullCfgWithCfg> query2YCfgByProjectId(@Param("projectId")String projectId);

    HzFullCfgWithCfg selectByBomLineUidWithVersion(HzFullCfgWithCfgQuery query);

    HzFullCfgWithCfg query2YCfgByBomLineId(@Param("bomLineId") String bomLineId);

    /**
     * 根据2Y和全配主键一起查询，连带出特性值对象
     *
     * @param query 查询实体对象
     * @return
     */
    HzExFullCfgWithCfg selectByBLOutWithCfg(HzFullCfgWithCfgQuery query);

    /**
     * 根据2Y和全配主键一起查询，连带出特性值对象和BOMLine对象
     *
     * @param query
     * @return
     */
    HzExFullCfgWithCfg selectByBLOutWithCfgAndBL(HzFullCfgWithCfgQuery query);

    int insertAll(List<HzFullCfgWithCfg> hzFullCfgWithCfgs);

    HzFullCfgWithCfg selectBy2Yid(HzFullCfgWithCfg hzFullCfgWithCfg);

    HzFullCfgWithCfg selectByFeatureId(@Param("featureId") String featureId);
}