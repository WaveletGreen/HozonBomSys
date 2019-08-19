/*
 *
 *  * Copyright (c) 2019 Author: Fancyears Milos Malvis @1243093366@qq.com
 *  * ALL COPYRIGHT REVERSED.
 *
 */

package com.connor.hozon.bom.resources.mybatis.epl;

import com.connor.hozon.bom.resources.domain.query.HzEPLByPageQuery;
import com.connor.hozon.bom.resources.page.Page;
import cn.net.connor.hozon.dao.pojo.bom.epl.HzEPLManageRecord;

/**
 * Created by haozt on 2018/06/05
 */
public interface HzEplMangeRecordDAO {
//    List<HzEPLManageRecord> getHzEplManageRecord();
//
//    Page<HzEPLManageRecord> getEPLListForPage(HzEPLByPageQuery query);

    Page<HzEPLManageRecord> getEPLListForPage2(HzEPLByPageQuery query);
}
