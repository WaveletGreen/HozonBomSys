package com.connor.hozon.bom.resources.mybatis.epl.impl;

import com.alibaba.fastjson.JSON;
import com.connor.hozon.bom.resources.dto.request.FindHzEPLRecordReqDTO;
import com.connor.hozon.bom.resources.mybatis.epl.HzEplMangeRecordDAO;
import com.connor.hozon.bom.resources.page.Page;
import com.connor.hozon.bom.resources.page.PageRequest;
import org.springframework.stereotype.Service;
import sql.BaseSQLUtil;
import sql.pojo.epl.HzEPLManageRecord;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by haozt on 2018/06/05
 */
@Service("HzEplMangeRecordDAO")
public class HzEplManageRecordDAOImpl extends BaseSQLUtil implements HzEplMangeRecordDAO {
    @Override
    public List<HzEPLManageRecord> getHzEplManageRecord() {
        return super.findForList("HzEplManageRecordDAOImpl_getHzEplManageRecord",null);
    }

    public Page<HzEPLManageRecord> getEPLListForPage(FindHzEPLRecordReqDTO recordReqDTO){
        PageRequest request = new PageRequest();
        Map map = new HashMap();
//        map.put("projectId",recordReqDTO.getProjectId());
        request.setPageNumber(recordReqDTO.getPage());
        request.setPageSize(recordReqDTO.getLimit());
        request.setFilters(map);
        return super.findForPage("HzEplManageRecordDAOImpl_getHzEplManageRecord","HzEplManageRecordDAOImpl_findTotalCount",request);

    }
}
