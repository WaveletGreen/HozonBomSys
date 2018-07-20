package com.connor.hozon.bom.resources.mybatis.bom.impl;

import com.connor.hozon.bom.resources.dto.request.DeleteHzEbomReqDTO;
import com.connor.hozon.bom.resources.mybatis.bom.HzEbomRecordDAO;
import com.connor.hozon.bom.resources.page.Page;
import com.connor.hozon.bom.resources.page.PageRequest;
import com.connor.hozon.bom.resources.query.HzBomRecycleByPageQuery;
import com.connor.hozon.bom.resources.query.HzEbomByPageQuery;
import com.connor.hozon.bom.resources.query.HzEbomTreeQuery;
import org.springframework.stereotype.Service;
import sql.BaseSQLUtil;
import sql.pojo.epl.HzEPLManageRecord;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by haozt on 2018/06/06
 */
@Service("HzEbomRecordDAO")
public class HzEbomRecordDAOImpl extends BaseSQLUtil implements HzEbomRecordDAO {

    @Override
    public Page<HzEPLManageRecord> getHzEbomPage(HzEbomByPageQuery query) {
        PageRequest request = new PageRequest();
        Map map = new HashMap();
        map.put("projectId",query.getProjectId());
        map.put("isHas",query.getIsHas());
        map.put("pBomOfWhichDept",query.getpBomOfWhichDept());
        map.put("lineIndex",query.getLineIndex());
        map.put("lineId",query.getLineId());
        request.setPageNumber(query.getPage());
        request.setPageSize(query.getPageSize());
        request.setFilters(map);
        return super.findPage("HzEbomRecordDAOImpl_getHzEbomList","HzEbomRecordDAOImpl_findTotalCount",request);

    }

    @Override
    public HzEPLManageRecord findEbomById(String puid,String projectId) {
        Map<String,Object> map = new HashMap<>();
        map.put("puid",puid);
        map.put("projectId",projectId);
        return (HzEPLManageRecord) super.findForObject("HzEbomRecordDAOImpl_findEbomById",map);
    }

    /**
     * 找出一条bomLine的全部子bom
     * @param query
     * @return
     */
    public List<HzEPLManageRecord> getHzBomLineChildren(HzEbomTreeQuery query){
        Map<String,Object> map = new HashMap<>();
        map.put("projectId",query.getProjectId());
        map.put("puid",query.getPuid());
        return super.findForList("HzEbomRecordDAOImpl_getHzBomLineChildren",map);
    }

    @Override
    public boolean checkItemIdIsRepeat(String projectId, String lineId) {
        Map<String,Object> map = new HashMap<>();
        map.put("projectId",projectId);
        map.put("lineID",lineId);
        return (int)super.findForObject("HzEbomRecordDAOImpl_checkItemIdIsRepeat",map)>0;
    }

    @Override
    public int deleteList(List<DeleteHzEbomReqDTO> reqDTOs) {

        return super.update("HzEbomRecordDAOImpl_deleteList",reqDTOs);
    }

    @Override
    public Page<HzEPLManageRecord> getHzRecycleRecord(HzBomRecycleByPageQuery query) {
        PageRequest request = new PageRequest();
        Map map = new HashMap();
        map.put("projectId",query.getProjectId());
        request.setPageNumber(query.getPage());
        request.setPageSize(query.getPageSize());
        request.setFilters(map);
        return super.findPage("HzEbomRecordDAOImpl_getHzRecycleRecord","HzEbomRecordDAOImpl_findRecycleTotalCount",request);
    }

    @Override
    public HzEPLManageRecord getHasDeletedBom(String puid, String projectId) {
        Map<String,Object> map = new HashMap<>();
        map.put("puid",puid);
        map.put("projectId",projectId);
        return (HzEPLManageRecord) super.findForObject("HzEbomRecordDAOImpl_findHasDeletedBom",map);
    }

    @Override
    public List<HzEPLManageRecord> findEbom(Map<String, Object> map) {

        return super.findForList("HzEbomRecordDAOImpl_findEbom",map);
    }

    @Override
    public Integer findMinOrderNumWhichGreaterThanThisOrderNum(String projectId,int orderNum) {
        Map<String,Object> map = new HashMap<>();
        map.put("projectId",projectId);
        map.put("orderNum",orderNum);
        return (Integer) super.findForObject("HzEbomRecordDAOImpl_findMinOrderNumWhichGreaterThanThisOrderNum",map);
    }

}
