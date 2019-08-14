/*
 *
 *  * Copyright (c) 2019 Author: Fancyears Milos Malvis @1243093366@qq.com
 *  * ALL COPYRIGHT REVERSED.
 *
 */

package cn.net.connor.hozon.dao.dao.configuration.fullConfigSheet;

import cn.net.connor.hozon.dao.MyBatisBaseDao;
import cn.net.connor.hozon.dao.pojo.configuration.fullConfigSheet.HzFullCfgMain;
import cn.net.connor.hozon.dao.query.configuration.fullConfigSheet.HzFullCfgMainQuery;
import org.springframework.stereotype.Repository;

/**
 * @Author: Fancyears·Maylos·Malvis
 * @Description: 全配置BOM一级清单主数据dao层
 * @Date: Created in 2018/8/27 21:17
 * @Modified By:
 */
@Repository
public interface HzFullCfgMainDao extends MyBatisBaseDao<HzFullCfgMain,Long,HzFullCfgMain> {

    Long insertBackId(HzFullCfgMain record);

    HzFullCfgMain selectByProjectId(String id);

    int deleteByProjectUid(String projectUid);

    Long insertSeqAll(HzFullCfgMain hzFullCfgMain);

    int updateStatusByOrderId(HzFullCfgMainQuery query);

    int updateStatusById(HzFullCfgMain hzFullCfgMain);

    int updateChangeByOrderId(Long orderId);
}