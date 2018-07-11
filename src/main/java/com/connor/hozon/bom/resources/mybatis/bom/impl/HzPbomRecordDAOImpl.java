package com.connor.hozon.bom.resources.mybatis.bom.impl;

import com.connor.hozon.bom.resources.mybatis.bom.HzPbomRecordDAO;
import com.connor.hozon.bom.resources.page.Page;
import com.connor.hozon.bom.resources.page.PageRequest;
import com.connor.hozon.bom.resources.query.HzPbomByPageQuery;
import com.connor.hozon.bom.resources.query.HzPbomTreeQuery;
import org.mapstruct.ap.shaded.freemarker.ext.beans.HashAdapter;
import org.springframework.stereotype.Service;
import sql.BaseSQLUtil;
import sql.pojo.bom.HzBomLineRecord;
import sql.pojo.bom.HzMbomLineRecord;
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
    public List<HzPbomLineRecord> getPbomById(Map<String, Object> map) {

        return super.findForList("HzPbomRecordDAOImpl_getPbomById",map);
    }

    @Override
    public int insert(HzPbomRecord record) {
        return super.insert("HzPbomRecordDAOImpl_insert",record);
    }

    @Override
    public int insertList(List<HzPbomLineRecord> records) {
        return super.insert("HzPbomRecordDAOImpl_insertList",records);
    }

    @Override
    public int update(HzPbomLineRecord record) {
        return super.update("HzPbomRecordDAOImpl_update",record);
    }

    @Override
    public int deleteByForeignId(String ePuid) {
        return super.update("HzPbomRecordDAOImpl_deleteByForeignId",ePuid);
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
    public HzPbomLineRecord getHzPbomByEbomPuid(String eBomPuid,String projectId) {
        Map<String,Object> map = new HashMap<>();
        map.put("projectId",projectId);
        map.put("eBomPuid",eBomPuid);
        return (HzPbomLineRecord) super.findForObject("HzPbomRecordDAOImpl_getHzPbomByEbomPuid",map);
    }

    @Override
    public int  getHzBomLineCount(String projectId) {
       Map<String,Object> map = new HashMap<>();
       map.put("projectId",projectId);
       return (int)super.findForObject("HzPbomRecordDAOImpl_getTotalCount",map);
    }

    @Override
    public int getHzPbomMaxOrderNum() {
        return(int) super.findForObject("HzPbomRecordDAOImpl_findMaxOrderNum",null);
    }

    @Override
    public List<HzPbomLineRecord> getHzPbomTree(HzPbomTreeQuery query) {
        Map<String,Object> map = new HashMap<>();
        map.put("projectId",query.getProjectId());
        map.put("puid",query.getPuid());
        return super.findForList("HzPbomRecordDAOImpl_getHzPbomTree",map);
    }

    @Override
    public int getMaxLineIndexFirstNum(String projectId) {
        return (int)super.findForObject("HzPbomRecordDAOImpl_getMaxLineIndexFirstNum",projectId);
    }

    @Override
    public boolean checkItemIdIsRepeat(String projectId, String lineId) {
        Map<String,Object> map = new HashMap<>();
        map.put("projectId",projectId);
        map.put("lineId",lineId);
        return (int)super.findForObject("HzPbomRecordDAOImpl_checkItemIdIsRepeat",map)>0;
    }
}
