/*
 *
 *  * Copyright (c) 2019 Author: Fancyears Milos Malvis @1243093366@qq.com
 *  * ALL COPYRIGHT REVERSED.
 *
 */

package cn.net.connor.hozon.dao.dao.configuration.fullConfigSheet;


import cn.net.connor.hozon.dao.dao.configuration.BasicDao;
import cn.net.connor.hozon.dao.pojo.configuration.fullConfigSheet.HzFullCfgModelChange;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HzFullCfgModelChangeDao extends BasicDao<HzFullCfgModelChange> {

    int insertList(List<HzFullCfgModelChange> hzFullCfgModelChanges);

    List<HzFullCfgModelChange> selectByMainId(Integer id);
}