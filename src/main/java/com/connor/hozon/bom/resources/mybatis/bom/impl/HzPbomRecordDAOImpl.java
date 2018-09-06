package com.connor.hozon.bom.resources.mybatis.bom.impl;

import com.connor.hozon.bom.resources.domain.dto.request.DeleteHzPbomReqDTO;
import com.connor.hozon.bom.resources.domain.query.HzBomRecycleByPageQuery;
import com.connor.hozon.bom.resources.domain.query.HzPbomByPageQuery;
import com.connor.hozon.bom.resources.domain.query.HzPbomTreeQuery;
import com.connor.hozon.bom.resources.mybatis.bom.HzPbomRecordDAO;
import com.connor.hozon.bom.resources.page.Page;
import com.connor.hozon.bom.resources.page.PageRequest;
import org.springframework.stereotype.Service;
import sql.BaseSQLUtil;
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
    public int recoverBomById(String ePuid) {
        return super.update("HzPbomRecordDAOImpl_recoverBomById",ePuid);
    }

    @Override
    public int deleteList(List<DeleteHzPbomReqDTO> list) {

        return super.update("HzPbomRecordDAOImpl_deleteList",list);
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
        map.put("pBomLinePartClass",query.getpBomLinePartClass());
        map.put("pBomLinePartResource",query.getpBomLinePartResource());
        request.setPageNumber(query.getPage());

        request.setPageSize(query.getPageSize());
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
    public Integer getHzPbomMaxOrderNum(String projectId) {
        return(Integer) super.findForObject("HzPbomRecordDAOImpl_findMaxOrderNum",projectId);
    }

    @Override
    public List<HzPbomLineRecord> getHzPbomTree(HzPbomTreeQuery query) {
        Map<String,Object> map = new HashMap<>();
        map.put("projectId",query.getProjectId());
        map.put("puid",query.getPuid());
        return super.findForList("HzPbomRecordDAOImpl_getHzPbomTree",map);
    }

    @Override
    public Integer getMaxLineIndexFirstNum(String projectId) {
        return (Integer) super.findForObject("HzPbomRecordDAOImpl_getMaxLineIndexFirstNum",projectId);
    }

    @Override
    public boolean checkItemIdIsRepeat(String projectId, String lineId) {
        Map<String,Object> map = new HashMap<>();
        map.put("projectId",projectId);
        map.put("lineId",lineId);
        return (int)super.findForObject("HzPbomRecordDAOImpl_checkItemIdIsRepeat",map)>0;
    }

    @Override
    public Page<HzPbomLineRecord> getHzPbomRecycleRecord(HzBomRecycleByPageQuery query) {
        PageRequest request = new PageRequest();
        Map map = new HashMap();
        map.put("projectId",query.getProjectId());
        request.setPageNumber(query.getPage());
        request.setPageSize(query.getPageSize());
        request.setFilters(map);
        return super.findPage("HzPbomRecordDAOImpl_getHzPbomRecycleRecord","HzPbomRecordDAOImpl_getRecycleTotalCount",request);
    }

    @Override
    public Integer findMinOrderNumWhichGreaterThanThisOrderNum(String projectId,int orderNum) {
        Map<String,Object> map = new HashMap<>();
        map.put("projectId",projectId);
        map.put("orderNum",orderNum);
        return (Integer) super.findForObject("HzPbomRecordDAOImpl_findMinOrderNumWhichGreaterThanThisOrderNum",map);
    }

    @Override
    public int delete(String eBomPuid) {
        return super.delete("HzPbomRecordDAOImpl_delete",eBomPuid);
    }
}
