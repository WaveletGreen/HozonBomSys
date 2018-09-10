package com.connor.hozon.bom.resources.mybatis.bom.impl;

import com.connor.hozon.bom.resources.domain.dto.request.DeleteHzMbomReqDTO;
import com.connor.hozon.bom.resources.domain.query.HzBomRecycleByPageQuery;
import com.connor.hozon.bom.resources.domain.query.HzMbomByPageQuery;
import com.connor.hozon.bom.resources.domain.query.HzMbomTreeQuery;
import com.connor.hozon.bom.resources.mybatis.bom.HzMbomRecordDAO;
import com.connor.hozon.bom.resources.page.Page;
import com.connor.hozon.bom.resources.page.PageRequest;
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
    public int insertList(List<HzMbomLineRecord> list) {
        return super.insert("HzMbomRecordDAOImpl_insertList",list);
    }

    @Override
    public int insert(HzMbomRecord record) {
        return super.insert("HzMbomRecordDAOImpl_insert",record);
    }

    @Override
    public int update(HzMbomLineRecord record) {
        return super.update("HzMbomRecordDAOImpl_update",record);
    }
    @Override
    public int recoverBomById(String eBomPuid) {
        return super.update("HzMbomRecordDAOImpl_recoverBomById",eBomPuid);
    }

    @Override
    public int deleteList(List<DeleteHzMbomReqDTO> reqDTOS) {
        return super.update("HzMbomRecordDAOImpl_deleteList",reqDTOS);
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
        map.put("pBomLinePartClass",query.getpBomLinePartClass());
        map.put("pBomLinePartResource",query.getpBomLinePartResource());
        request.setPageNumber(query.getPage());
        request.setPageSize(query.getPageSize());
        request.setFilters(map);
        return super.findForPage("HzMbomRecordDAOImpl_getMBomRecord","HzMbomRecordDAOImpl_getTotalCount",request);

    }

    @Override
    public List<HzMbomLineRecord> findHzMbomByPuid(Map<String, Object> map) {
        return  super.findForList("HzMbomRecordDAOImpl_findHzMbomByPuid",map);
    }

    @Override
    public List<HzMbomLineRecord> getHzVehicleModelName(String projectId) {

        return super.findForList("HzMbomRecordDAOImpl_getHzVehicleModelName",projectId);
    }

    @Override
    public Page<HzMbomLineRecord> getHzSuberMbomByPage(HzMbomByPageQuery query) {
        PageRequest pageRequest = new PageRequest();
        Map map = new HashMap();
        map.put("projectId",query.getProjectId());
        map.put("isHas",query.getIsHas());
        map.put("pBomOfWhichDept",query.getpBomOfWhichDept());
        map.put("lineIndex",query.getLineIndex());
        map.put("lineId",query.getLineId());
        map.put("cfg0ModelRecordId",query.getCfg0ModelRecordId());
        pageRequest.setPageNumber(query.getPage());
        pageRequest.setPageSize(query.getPageSize());
        pageRequest.setFilters(map);
        return super.findForPage("HzMbomRecordDAOImpl_getHzSuberMbomByPage","HzMbomRecordDAOImpl_getHzSuberMbomTotalCount",pageRequest);
    }

    @Override
    public HzMbomLineRecord getHzSuperMbomByPuid(String projectId, String pPuid) {
        Map<String,Object> map = new HashMap<>();
        map.put("projectId",projectId);
        map.put("pPuid",pPuid);
        return(HzMbomLineRecord) super.findForObject("HzMbomRecordDAOImpl_getHzSuberMbomByPuid",map);
    }

    @Override
    public HzMbomLineRecord getHzMbom(String projectId, String parentPuid) {
        Map<String,Object> map = new HashMap<>();
        map.put("projectId",projectId);
        map.put("parentUid",parentPuid);
        return (HzMbomLineRecord) super.findForObject("HzMbomRecordDAOImpl_getHzMbom",map);
    }

    @Override
    public Integer getHzMbomTotalCount(String projectId) {
        Map<String,Object> map = new HashMap<>();
        map.put("projectId",projectId);
        return (Integer) super.findForObject("HzMbomRecordDAOImpl_getTotalCount",map);
    }

    @Override
    public Integer getHzMbomMaxOrderNum(String projectId) {
        return (Integer) super.findForObject("HzMbomRecordDAOImpl_getHzMbomMaxOrderNum",projectId);
    }

    @Override
    public List<HzMbomLineRecord> findHz2YMbomRecord(HzMbomByPageQuery query) {
        Map<String,Object> map = new HashMap<>();
        map.put("projectId",query.getProjectId());
        map.put("offset",(query.getPage() - 1) * query.getPageSize());
        map.put("limit",query.getPage()*query.getPageSize());
        return super.findForList("HzMbomRecordDAOImpl_findHz2YMbomRecord",map);
    }

    @Override
    public List<HzMbomLineRecord> findHzMbomByResource(HzMbomByPageQuery query) {
        Map<String,Object> map = new HashMap<>();
        map.put("projectId",query.getProjectId());
        map.put("offset",(query.getPage() - 1) * query.getPageSize());
        map.put("limit",query.getPage()*query.getPageSize());
        map.put("pBomLinePartResource",query.getpBomLinePartResource());
        map.put("sparePart",query.getSparePart());
        return super.findForList("HzMbomRecordDAOImpl_findHzMbomByResource",map);
    }

    @Override
    public List<HzMbomLineRecord> findHz2YMbomRecordAll(HzMbomByPageQuery query) {
        Map<String,Object> map = new HashMap<>();
        map.put("projectId",query.getProjectId());
        return super.findForList("HzMbomRecordDAOImpl_findHz2YMbomRecordAll",map);
    }

    @Override
    public List<HzMbomLineRecord> findHzMbomByResourceAll(HzMbomByPageQuery query) {
        Map<String,Object> map = new HashMap<>();
        map.put("projectId",query.getProjectId());
        map.put("pBomLinePartResource",query.getpBomLinePartResource());
        map.put("sparePart",query.getSparePart());
        return super.findForList("HzMbomRecordDAOImpl_findHzMbomByResourceAll",map);
    }

    @Override
    public List<HzMbomLineRecord> getHzMbomTree(HzMbomTreeQuery query) {
        Map<String,Object> map = new HashMap<>();
        map.put("projectId",query.getProjectId());
        map.put("puid",query.getPuid());
        return super.findForList("HzMbomRecordDAOImpl_getHzMbomTree",map);
    }

    @Override
    public Page<HzMbomLineRecord> getHzMbomRecycleRecord(HzBomRecycleByPageQuery query) {
        PageRequest request = new PageRequest();
        Map map = new HashMap();
        map.put("projectId",query.getProjectId());
        request.setPageNumber(query.getPage());
        request.setPageSize(query.getPageSize());
        request.setFilters(map);
        return super.findPage("HzMbomRecordDAOImpl_getHzMbomRecycleRecord","HzMbomRecordDAOImpl_getRecycleTotalCount",request);

    }

    @Override
    public Integer getMaxLineIndexFirstNum(String projectId) {
        return (Integer) super.findForObject("HzMbomRecordDAOImpl_getMaxLineIndexFirstNum",projectId);
    }

    @Override
    public int delete(String eBomPuid) {
        return super.delete("HzMbomRecordDAOImpl_delete",eBomPuid);
    }

    @Override
    public Integer findMinOrderNumWhichGreaterThanThisOrderNum(String projectId,int orderNum) {
        Map<String,Object> map = new HashMap<>();
        map.put("projectId",projectId);
        map.put("orderNum",orderNum);
        return (Integer) super.findForObject("HzMbomRecordDAOImpl_findMinOrderNumWhichGreaterThanThisOrderNum",map);
    }
}
