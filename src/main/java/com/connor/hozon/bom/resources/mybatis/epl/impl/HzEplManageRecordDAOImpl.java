package com.connor.hozon.bom.resources.mybatis.epl.impl;

import com.connor.hozon.bom.resources.domain.query.HzEPLByPageQuery;
import com.connor.hozon.bom.resources.mybatis.epl.HzEplMangeRecordDAO;
import com.connor.hozon.bom.resources.page.Page;
import com.connor.hozon.bom.resources.page.PageRequestParam;
import org.springframework.stereotype.Service;
import sql.BaseSQLUtil;
import sql.pojo.epl.HzEPLManageRecord;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by haozt on 2018/06/05
 */
@Service("hzEplMangeRecordDAO")
public class HzEplManageRecordDAOImpl extends BaseSQLUtil implements HzEplMangeRecordDAO {
//    @Override
//    public List<HzEPLManageRecord> getHzEplManageRecord() {
//        return super.findForList("HzEplManageRecordDAOImpl_getHzEplManageRecord",null);
//    }
//
//    public Page<HzEPLManageRecord> getEPLListForPage(HzEPLByPageQuery query){
//        PageRequestParam request = new PageRequestParam();
//        Map map = new HashMap();
//        map.put("projectId",query.getProjectId());
//        map.put("isHas",query.getIsHas());
//        map.put("pBomOfWhichDept",query.getpBomOfWhichDept());
//        map.put("lineIndex",query.getLineIndex());
//        map.put("lineId",query.getLineId());
//        map.put("pFastener",query.getpFastener());
//        request.setPageNumber(query.getPage());
//        request.setPageSize(query.getPageSize());
//        request.setFilters(map);
//        return super.findForPage("HzEplManageRecordDAOImpl_getHzEplManageRecord","HzEplManageRecordDAOImpl_findTotalCount",request);
//
//    }

    @Override
    public Page<HzEPLManageRecord> getEPLListForPage2(HzEPLByPageQuery query) {
        PageRequestParam request = new PageRequestParam();
        Map map = new HashMap();
        map.put("projectId",query.getProjectId());
        map.put("isHas",query.getIsHas());
        map.put("pBomOfWhichDept",query.getpBomOfWhichDept().trim());
        map.put("lineIndex",query.getLineIndex());
        map.put("lineId",query.getLineId().trim());
        map.put("pFastener",query.getpFastener());
        request.setPageNumber(query.getPage());
        request.setPageSize(query.getPageSize());
        request.setFilters(map);
        return super.findPage("HzEplManageRecordDAOImpl_getHzEplManageRecord2","HzEplManageRecordDAOImpl_findTotalCount3",request);
    }
}
