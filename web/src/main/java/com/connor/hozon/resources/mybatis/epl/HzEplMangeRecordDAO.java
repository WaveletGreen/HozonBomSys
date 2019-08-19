package com.connor.hozon.resources.mybatis.epl;

import com.connor.hozon.resources.domain.query.HzEPLByPageQuery;
import com.connor.hozon.resources.page.Page;
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
