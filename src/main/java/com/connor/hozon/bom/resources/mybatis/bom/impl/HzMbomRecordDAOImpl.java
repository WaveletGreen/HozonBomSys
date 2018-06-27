package com.connor.hozon.bom.resources.mybatis.bom.impl;

import com.connor.hozon.bom.resources.dto.request.FindForPageReqDTO;
import com.connor.hozon.bom.resources.mybatis.bom.HzMbomRecordDAO;
import com.connor.hozon.bom.resources.page.Page;
import com.connor.hozon.bom.resources.page.PageRequest;
import com.connor.hozon.bom.resources.query.HzMbomByPageQuery;
import org.springframework.stereotype.Service;
import sql.BaseSQLUtil;
import sql.pojo.bom.HzBomLineRecord;
import sql.pojo.bom.HzMbomLineRecord;
import sql.pojo.bom.HzMbomRecord;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by haozt on 2018/5/24
 */

@Service("HzMbomRecordDAO")
public class HzMbomRecordDAOImpl extends BaseSQLUtil implements HzMbomRecordDAO {

    @Override
    public int insertList(List<HzMbomRecord> list) {
        return super.insert("HzMbomRecordDAOImpl_insertList",list);
    }

    @Override
    public int insert(HzMbomRecord record) {
        return super.insert("HzMbomRecordDAOImpl_insert",record);
    }

    @Override
    public int update(HzMbomRecord record) {
        return super.update("HzMbomRecordDAOImpl_update",record);
    }
    @Override
    public int deleteByForeignId(String eBomPuid) {
        return super.update("HzMbomRecordDAOImpl_deleteByForeignId",eBomPuid);
    }
    @Override
    public List<HzMbomLineRecord> searchMbomMaintainDetail(HzBomLineRecord record) {
        return super.findForList("HzMbomRecordDAOImpl_searchMbomMaintainDetail",record);
    }

    @Override
    public Page<HzMbomLineRecord> findMbomForPage(HzMbomByPageQuery query) {
        PageRequest request = new PageRequest();
        Map map = new HashMap();
        map.put("projectId",query.getProjectId());
        map.put("isHas",query.getIsHas());
        map.put("pBomOfWhichDept",query.getpBomOfWhichDept());
        map.put("lineIndex",query.getLineIndex());
        map.put("lineId",query.getLineId());
        request.setPageNumber(query.getPage());
        request.setPageSize(query.getLimit());
        request.setFilters(map);
        return super.findForPage("HzMbomRecordDAOImpl_getMBomRecord","HzMbomRecordDAOImpl_getTotalCount",request);

    }

    @Override
    public HzMbomLineRecord findHzMbomByPuid(Map<String, Object> map) {
        return (HzMbomLineRecord) super.findForObject("HzMbomRecordDAOImpl_findHzMbomByPuid",map);
    }

    @Override
    public HzMbomRecord findHzMbomByeBomPuid(String eBomPuid) {

        return (HzMbomRecord)super.findForObject("HzMbomRecordDAOImpl_findHzMbomByeBomPuid",eBomPuid);
    }
}
