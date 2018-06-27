package com.connor.hozon.bom.resources.mybatis.bom.impl;

import com.connor.hozon.bom.resources.dto.request.FindForPageReqDTO;
import com.connor.hozon.bom.resources.mybatis.bom.HzPbomRecordDAO;
import com.connor.hozon.bom.resources.page.Page;
import com.connor.hozon.bom.resources.page.PageRequest;
import com.connor.hozon.bom.resources.query.HzPbomByPageQuery;
import org.springframework.stereotype.Service;
import sql.BaseSQLUtil;
import sql.pojo.bom.HzBomLineRecord;
import sql.pojo.bom.HzPbomLineRecord;
import sql.pojo.bom.HzPbomRecord;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by haozt on 2018/5/25
 */
@Service("HzPbomRecordDAO")
public class HzPbomRecordDAOImpl extends BaseSQLUtil implements HzPbomRecordDAO {

    @Override
    public HzPbomLineRecord getPbomById(Map<String, Object> map) {

        return (HzPbomLineRecord)super.findForObject("HzPbomRecordDAOImpl_getPbomById",map);
    }

    @Override
    public List<HzPbomLineRecord> searchPbomLineDetail(HzBomLineRecord record) {

       return super.findForList("HzPbomRecordDAOImpl_searchPbomLineDetail",record);

    }

    @Override
    public int insert(HzPbomRecord record) {
        return super.insert("HzPbomRecordDAOImpl_insert",record);
    }

    @Override
    public int update(HzPbomRecord record) {
        return super.update("HzPbomRecordDAOImpl_update",record);
    }

    @Override
    public int deleteByForeignId(String ePuid) {
        return super.update("HzPbomRecordDAOImpl_deleteByForeignId",ePuid);
    }

    @Override
    public List<HzPbomLineRecord> getHzPbomById(Map<String, Object> map) {
        return super.findForList("HzPbomRecordDAOImpl_getHzPbomById",map);
    }

    @Override
    public Page<HzPbomLineRecord> getHzPbomRecordByPage(HzPbomByPageQuery query) {
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
        return super.findForPage("HzPbomRecordDAOImpl_getPbomRecord","HzPbomRecordDAOImpl_getTotalCount",request);
    }

    @Override
    public HzPbomRecord getHzPbomByEbomPuid(String eBomPuid) {

        return (HzPbomRecord) super.findForObject("HzPbomRecordDAOImpl_getHzPbomByEbomPuid",eBomPuid);
    }

}
