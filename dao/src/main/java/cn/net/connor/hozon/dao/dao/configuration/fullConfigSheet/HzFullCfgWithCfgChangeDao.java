/*
 *
 *  * Copyright (c) 2019 Author: Fancyears Milos Malvis @1243093366@qq.com
 *  * ALL COPYRIGHT REVERSED.
 *
 */

package cn.net.connor.hozon.dao.dao.configuration.fullConfigSheet;


import cn.net.connor.hozon.dao.basic.MyBatisBaseDao;
import cn.net.connor.hozon.dao.pojo.configuration.fullConfigSheet.HzFullCfgWithCfgChange;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HzFullCfgWithCfgChangeDao extends MyBatisBaseDao<HzFullCfgWithCfgChange,Long,HzFullCfgWithCfgChange> {

    int insertList(List<HzFullCfgWithCfgChange> hzFullCfgWithCfgChanges);

    List<HzFullCfgWithCfgChange> selectByMainId(@Param("flMainId") Integer flMainId);
}