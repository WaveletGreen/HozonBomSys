package com.connor.hozon.bom.resources.mybatis.epl;

import com.connor.hozon.bom.resources.page.Page;
import com.connor.hozon.bom.resources.query.HzEPLByPageQuery;
import sql.pojo.epl.HzEPLManageRecord;

import java.util.List;

/**
 * Created by haozt on 2018/06/05
 */
public interface HzEplMangeRecordDAO {
    List<HzEPLManageRecord> getHzEplManageRecord();

    Page<HzEPLManageRecord> getEPLListForPage(HzEPLByPageQuery query);
}
