package com.connor.hozon.bom.resources.mybatis.bom.impl;

import com.connor.hozon.bom.resources.domain.dto.request.DeleteHzEbomReqDTO;
import com.connor.hozon.bom.resources.domain.query.HzBomRecycleByPageQuery;
import com.connor.hozon.bom.resources.domain.query.HzEbomByPageQuery;
import com.connor.hozon.bom.resources.domain.query.HzEbomTreeQuery;
import com.connor.hozon.bom.resources.mybatis.bom.HzEbomRecordDAO;
import com.connor.hozon.bom.resources.page.Page;
import com.connor.hozon.bom.resources.page.PageRequest;
import com.google.common.collect.Lists;
import org.springframework.stereotype.Service;
import sql.BaseSQLUtil;
import sql.pojo.bom.HzImportEbomRecord;
import sql.pojo.epl.HzEPLManageRecord;

import java.util.ArrayList;
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
        map.put("pBomLinePartClass",query.getpBomLinePartClass());
        map.put("pBomLinePartResource",query.getpBomLinePartResource());
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
        map.put("colorPart",query.getIsColorPart());
        map.put("isCarPart",query.getIsColorPart());
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
    public String findMinOrderNumWhichGreaterThanThisOrderNum(String projectId, String sortNum) {
        Map<String,Object> map = new HashMap<>();
        map.put("projectId",projectId);
        map.put("sortNum",Double.parseDouble(sortNum));
        return (String) super.findForObject("HzEbomRecordDAOImpl_findMinOrderNumWhichGreaterThanThisOrderNum",map);
    }

    @Override
    public int insert(HzEPLManageRecord record) {
        return super.insert("HzEbomRecordDAOImpl_insert",record);
    }

    @Override
    public int importList(List<HzImportEbomRecord> records) {
        int size = records.size();
        //分批插入数据 一次1000条
        int i = 0;
        int cout = 0;
        try {
            synchronized (this){
                if (size > 1000) {
                    for (i = 0; i < size / 1000; i++) {
                        List<HzImportEbomRecord> list = new ArrayList<>();
                        for (int j = 0; j < 1000; j++) {
                            list.add(records.get(cout));
                            cout++;
                        }
                        super.insert("HzEbomRecordDAOImpl_importList",list);
                    }
                }
                if (i * 1000 < size) {
                    List<HzImportEbomRecord> list = new ArrayList<>();
                    for (int j = 0; j < size - i * 1000; j++) {
                        list.add(records.get(cout));
                        cout++;
                    }
                    super.insert("HzEbomRecordDAOImpl_importList",list);
                }
            }
            return 1;
        }catch (Exception e){
            return 0;
        }

    }

    @Override
    public boolean sortNumRepeat(String projectId, String sortNum) {
        Map<String,Object> map = new HashMap<>();
        map.put("projectId",projectId);
        map.put("sortNum",sortNum);
        return (int)super.findForObject("HzEbomRecordDAOImpl_sortNumRepeat",map)>0;
    }

    @Override
    public boolean lineIndexRepeat(String projectId, String lineIndex) {
        Map<String,Object> map = new HashMap<>();
        map.put("projectId",projectId);
        map.put("lineIndex",lineIndex);
        return (int)super.findForObject("HzEbomRecordDAOImpl_lineIndexRepeat",map)>0;
    }

    @Override
    public List<HzEPLManageRecord> getAll2YBomRecord(String projectId,Integer colorPart) {
        Map<String,Object> map = new HashMap<>();
        map.put("projectId",projectId);
        map.put("colorPart",colorPart);
        return super.findForList("HzEbomRecordDAOImpl_getAll2YBomRecord",map);
    }

    @Override
    public List<HzEPLManageRecord> getSameNameLineId(String lineId, String projectId) {
        Map<String,Object> map = new HashMap<>();
        map.put("lineId",lineId);
        map.put("projectId",projectId);
        return super.findForList("HzEbomRecordDAOImpl_getSameNameLineId",map);
    }

    @Override
    public List<HzEPLManageRecord> getPaintAndWhiteBody(String puid, String projectId) {
        Map<String,Object> map = new HashMap<>();
        map.put("puid",puid);
        map.put("projectId",projectId);
        return super.findForList("HzEbomRecordDAOImpl_getPaintAndWhiteBody",map);
    }

    @Override
    public Page<HzEPLManageRecord> getHzEbomTreeByPage(HzEbomByPageQuery query) {
        PageRequest request = new PageRequest();
        Map map = new HashMap();
        map.put("puids", Lists.newArrayList(query.getPuids().split(",")));
        map.put("projectId",query.getProjectId());
        request.setPageNumber(query.getPage());
        request.setPageSize(query.getPageSize());
        request.setFilters(map);
        return super.findPage("HzEbomRecordDAOImpl_getHzEbomTreeByPage","HzEbomRecordDAOImpl_getHzEbomTreeTotalCount",request);

    }

}
