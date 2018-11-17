package com.connor.hozon.bom.resources.mybatis.bom.impl;

import com.connor.hozon.bom.bomSystem.helper.UUIDHelper;
import com.connor.hozon.bom.resources.domain.dto.request.DeleteHzPbomReqDTO;
import com.connor.hozon.bom.resources.domain.query.HzBomRecycleByPageQuery;
import com.connor.hozon.bom.resources.domain.query.HzChangeDataDetailQuery;
import com.connor.hozon.bom.resources.domain.query.HzPbomByPageQuery;
import com.connor.hozon.bom.resources.domain.query.HzPbomTreeQuery;
import com.connor.hozon.bom.resources.enumtype.MbomTableNameEnum;
import com.connor.hozon.bom.resources.mybatis.bom.HzPbomRecordDAO;
import com.connor.hozon.bom.resources.page.Page;
import com.connor.hozon.bom.resources.page.PageRequestParam;
import com.google.common.collect.Lists;
import org.springframework.stereotype.Service;
import sql.BaseSQLUtil;
import sql.pojo.bom.HzPbomLineRecord;

import java.util.ArrayList;
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
    public List<HzPbomLineRecord> getPbomById_before(Map<String, Object> map) {
        return super.findForList("HzPbomRecordDAOImpl_getPbomById_before",map);
    }

    @Override
    public List<HzPbomLineRecord> findPbomByItemId(String itemId,String projectId){
        Map<String,Object> map = new HashMap<>();
        map.put("puid",itemId);
        map.put("projectId",projectId);
        return super.findForList("HzPbomRecordDAOImpl_getPbomByItemId",map);
    }

    @Override
    public int insert(HzPbomLineRecord record) {
        return super.insert("HzPbomRecordDAOImpl_insert",record);
    }

    @Override
    public int insert2(HzPbomLineRecord record){
        return super.insert("HzPbomRecordDAOImpl_insert2",record);
    }

    @Override
    public int insert_before(HzPbomLineRecord record) {
        return super.insert("HzPbomRecordDAOImpl_insert_before",record);
    }

    @Override
    public int update_before(HzPbomLineRecord record) {
        return super.update("HzPbomRecordDAOImpl_update_before",record);
    }

    @Override
    public List<HzPbomLineRecord> getPbomById_after(Map<String, Object> map) {
        return super.findForList("HzPbomRecordDAOImpl_getPbomById_after",map);
    }

    @Override
    public int insert_after(HzPbomLineRecord record) {
        return super.insert("HzPbomRecordDAOImpl_insert_after",record);
    }

    @Override
    public int update_after(HzPbomLineRecord record) {
        return super.update("HzPbomRecordDAOImpl_update_after",record);
    }

    @Override
    public int insertList(List<HzPbomLineRecord> records) {
        int size = records.size();
        //分批插入数据 一次1000条
        int i = 0;
        int cout = 0;
        try {
            synchronized (this){
                if (size > 1000) {
                    for (i = 0; i < size / 1000; i++) {
                        List<HzPbomLineRecord> list = new ArrayList<>();
                        for (int j = 0; j < 1000; j++) {
                            list.add(records.get(cout));
                            cout++;
                        }
                        super.insert("HzPbomRecordDAOImpl_insertList",list);
                    }
                }
                if (i * 1000 < size) {
                    List<HzPbomLineRecord> list = new ArrayList<>();
                    for (int j = 0; j < size - i * 1000; j++) {
                        list.add(records.get(cout));
                        cout++;
                    }
                    super.insert("HzPbomRecordDAOImpl_insertList",list);
                }
            }
            return 1;
        }catch (Exception e){
            return 0;
        }
    }

    @Override
    public int insertListForChange(List<HzPbomLineRecord> records, String tableName) {
        Map<String,Object> map = new HashMap<>();
        map.put("tableName",tableName);
        int size = records.size();
        //分批插入数据 一次1000条
        int i = 0;
        int cout = 0;
        try {
            synchronized (this){
                if (size > 1000) {
                    for (i = 0; i < size / 1000; i++) {
                        List<HzPbomLineRecord> list = new ArrayList<>();
                        for (int j = 0; j < 1000; j++) {
                            list.add(records.get(cout));
                            cout++;
                        }
                        map.put("list",list);//map key相同 value会被替代
                        super.insert("HzPbomRecordDAOImpl_insertListForChange",map);
                    }
                }
                if (i * 1000 < size) {
                    List<HzPbomLineRecord> list = new ArrayList<>();
                    for (int j = 0; j < size - i * 1000; j++) {
                        list.add(records.get(cout));
                        cout++;
                    }
                    map.put("list",list);
                    super.insert("HzPbomRecordDAOImpl_insertListForChange",map);
                }
            }
            return 1;
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public int update(HzPbomLineRecord record) {
        return super.update("HzPbomRecordDAOImpl_update",record);
    }

    @Override
    public int updateList(List<HzPbomLineRecord> records) {
        int size = records.size();
        //分批更新数据 一次1000条
        int i = 0;
        int cout = 0;
        try {
            synchronized (this){
                if (size > 1000) {
                    for (i = 0; i < size / 1000; i++) {
                        List<HzPbomLineRecord> list = new ArrayList<>();
                        for (int j = 0; j < 1000; j++) {
                            list.add(records.get(cout));
                            cout++;
                        }
                        super.update("HzPbomRecordDAOImpl_updateList",list);
                    }
                }
                if (i * 1000 < size) {
                    List<HzPbomLineRecord> list = new ArrayList<>();
                    for (int j = 0; j < size - i * 1000; j++) {
                        list.add(records.get(cout));
                        cout++;
                    }
                    super.update("HzPbomRecordDAOImpl_updateList",list);
                }
            }
            return 1;
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public int updateInput(HzPbomLineRecord record) {
        return super.update("HzPbomRecordDAOImpl_updateInput",record);
    }

    @Override
    public int recoverBomById(String ePuid) {
        return super.update("HzPbomRecordDAOImpl_recoverBomById",ePuid);
    }

    @Override
    public int deleteList(String puids) {
        Map<String,Object> map = new HashMap<>();
        map.put("eBomPuids",Lists.newArrayList(puids.split(",")));
        return super.delete("HzPbomRecordDAOImpl_deleteListByPuids",map);
    }

    @Override
    public int deleteList(List<DeleteHzPbomReqDTO> list) {

        return super.update("HzPbomRecordDAOImpl_deleteList",list);
    }


    @Override
    public Page<HzPbomLineRecord> getHzPbomRecordByPage(HzPbomByPageQuery query) {
        PageRequestParam request = new PageRequestParam();
        Map map = new HashMap();
        map.put("projectId",query.getProjectId());
        map.put("isHas",query.getIsHas());
        map.put("pBomOfWhichDept",query.getpBomOfWhichDept().trim());
        map.put("lineIndex",query.getLineIndex());
        map.put("lineId",query.getLineId().trim());
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
    public Double getHzPbomMaxOrderNum(String projectId) {
        String maxOrderNum = (String) super.findForObject("HzPbomRecordDAOImpl_findMaxOrderNum",projectId);
        if(maxOrderNum == null){
            return null;
        }
        return Double.parseDouble(maxOrderNum);
    }

    @Override
    public List<HzPbomLineRecord> getHzPbomTree(HzPbomTreeQuery query) {
        Map<String,Object> map = new HashMap<>();
        map.put("projectId",query.getProjectId());
        map.put("puid",query.getPuid());
        map.put("colorPart",query.getIsColorPart());
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
        PageRequestParam request = new PageRequestParam();
        Map map = new HashMap();
        map.put("projectId",query.getProjectId());
        request.setPageNumber(query.getPage());
        request.setPageSize(query.getPageSize());
        request.setFilters(map);
        return super.findPage("HzPbomRecordDAOImpl_getHzPbomRecycleRecord","HzPbomRecordDAOImpl_getRecycleTotalCount",request);
    }

    @Override
    public String findMinOrderNumWhichGreaterThanThisOrderNum(String projectId,String orderNum) {
        Map<String,Object> map = new HashMap<>();
        map.put("projectId",projectId);
        map.put("sortNum",Double.parseDouble(orderNum));
        return (String) super.findForObject("HzPbomRecordDAOImpl_findMinOrderNumWhichGreaterThanThisOrderNum",map);
    }

    @Override
    public List<HzPbomLineRecord> getAll2YBomRecord(String projectId) {

        return super.findForList("HzPbomRecordDAOImpl_getAll2YBomRecord",projectId);
    }

    @Override
    public int delete(String eBomPuid) {
        return super.delete("HzPbomRecordDAOImpl_delete",eBomPuid);
    }

    @Override
    public List<HzPbomLineRecord> findPbom(Map<String, Object> map) {
        return super.findForList("HzPbomRecordDAOImpl_findPbom",map);
    }

    @Override
    public List<HzPbomLineRecord> getPaintAndWhiteBody(String puid, String projectId) {
        Map<String,Object> map = new HashMap<>();
        map.put("puid",puid);
        map.put("projectId",projectId);
        return super.findForList("HzPbomRecordDAOImpl_getPaintAndWhiteBody",map);
    }

    @Override
    public List<HzPbomLineRecord> getSameNameLineId(String lineId, String projectId) {
        Map<String,Object> map = new HashMap<>();
        map.put("lineId",lineId);
        map.put("projectId",projectId);

        return super.findForList("HzPbomRecordDAOImpl_getSameNameLineId",map);
    }

    @Override
    public int insertAccessories(String puid, String materielCode) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("puid",puid);
        map.put("materielCode",materielCode);
        String secondPuid = UUIDHelper.generateUid();
        map.put("secondPuid",secondPuid);
        return super.executeInsert(map,"HzPbomRecordDAOImpl_insertAccessories");
    }

    @Override
    public List<HzPbomLineRecord> queryAllBomLineIdByPuid(String puid) {
        return super.executeQueryByPass(new HzPbomLineRecord(),puid,"HzPbomRecordDAOImpl_queryAllBomLineIdByPuid");
    }

    @Override
    public List<HzPbomLineRecord> getFirstLevelBomByParentId(String parnetId, String projectId) {
        Map<String,Object> map = new HashMap<>();
        map.put("parentId",parnetId);
        map.put("projectId",projectId);
        return super.findForList("HzPbomRecordDAOImpl_getFirstLevelBomByParentId",map);
    }




    @Override
    public List<HzPbomLineRecord> queryAllBomLineIdByPuid(String puid, String projectId) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("puid",puid);
        map.put("projectId",projectId);
        return super.executeQueryByPass(new HzPbomLineRecord(),map,"HzPbomRecordDAOImpl_queryAllBomLineIdByPuid");
    }

    @Override
    public Page<HzPbomLineRecord> getPbomTreeByPage(HzPbomByPageQuery query) {
        PageRequestParam request = new PageRequestParam();
        Map map = new HashMap();
        map.put("projectId",query.getProjectId());
        map.put("eBomPuids",Lists.newArrayList(query.geteBomPuids().split(",")));
        request.setPageNumber(query.getPage());
        request.setPageSize(query.getPageSize());
        request.setFilters(map);
        return super.findPage("HzPbomRecordDAOImpl_getHzPbomTreeByPage","HzPbomRecordDAOImpl_getHzPbomTreeTotalCount",request);
    }

    @Override
    public List<HzPbomLineRecord> getPbomRecordsByPuids(HzChangeDataDetailQuery query) {
        Map<String,Object> map = new HashMap<>();
        map.put("puids", query.getPuids());
        map.put("projectId",query.getProjectId());
        if(null!=query.getRevision()){
            map.put("revision",query.getRevision()?"":null);
        }else {
            map.put("revision",null);
        }
        map.put("status",query.getStatus());
        map.put("tableName",query.getTableName());
        map.put("orderId",query.getOrderId());
        return super.findForList("HzPbomRecordDAOImpl_getPbomRecordsByPuids",map);
    }

    @Override
    public int deleteByPuids(String puids) {
        Map<String,Object> map = new HashMap<>();
        map.put("eBomPuids",Lists.newArrayList(puids.split(",")));
        return super.update("HzPbomRecordDAOImpl_deleteByPuids",map);
    }

    @Override
    public HzPbomLineRecord getPBomRecordByPuidAndRevision(HzChangeDataDetailQuery query) {
        Map<String,Object> map = new HashMap<>();
        map.put("puids", query.getPuids());
        map.put("projectId",query.getProjectId());
        if(null != query.getRevision()){
            map.put("revision",query.getRevision()?null:query.getRevisionNo());
        }else {
            map.put("revision",null);
        }
        map.put("status",query.getStatus());
        map.put("tableName",query.getTableName());
        return (HzPbomLineRecord)super.findForObject("HzPbomRecordDAOImpl_getEBomRecordByPuidAndRevision",map);
    }

}
