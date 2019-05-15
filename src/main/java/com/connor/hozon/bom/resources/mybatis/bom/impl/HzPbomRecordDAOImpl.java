package com.connor.hozon.bom.resources.mybatis.bom.impl;

import com.connor.hozon.bom.bomSystem.helper.UUIDHelper;
import com.connor.hozon.bom.resources.domain.constant.BOMTransConstants;
import com.connor.hozon.bom.resources.domain.dto.request.DeleteHzPbomReqDTO;
import com.connor.hozon.bom.resources.domain.dto.request.UpdateHzEbomReqDTO;
import com.connor.hozon.bom.resources.domain.model.HzBomSysFactory;
import com.connor.hozon.bom.resources.domain.query.*;
import com.connor.hozon.bom.resources.enumtype.MbomTableNameEnum;
import com.connor.hozon.bom.resources.mybatis.bom.HzPbomRecordDAO;
import com.connor.hozon.bom.resources.page.Page;
import com.connor.hozon.bom.resources.page.PageRequestParam;
import com.connor.hozon.bom.resources.util.ListUtil;
import com.connor.hozon.bom.sys.exception.HzBomException;
import com.google.common.collect.Lists;
import org.springframework.stereotype.Service;
import sql.BaseSQLUtil;
import sql.pojo.bom.HzPbomLineRecord;
import sql.redis.HzDBException;
import sql.pojo.epl.HzEPLManageRecord;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by haozt on 2018/5/25
 */
@Service("hzPbomRecordDAO")
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
    public int insert(HzPbomLineRecord record) {
        return super.insert("HzPbomRecordDAOImpl_insert",record);
    }



    @Override
    public List<HzPbomLineRecord> getPbomById_after(Map<String, Object> map) {
        return super.findForList("HzPbomRecordDAOImpl_getPbomById_after",map);
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
            e.printStackTrace();
            throw new HzBomException("PBOM数据插入失败！",e);
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
            return size;
        }catch (Exception e){
            e.printStackTrace();
            throw new HzDBException("数据插入失败！",e);
        }
    }

    @Override
    public int update(HzPbomLineRecord record) {
        return super.update("HzPbomRecordDAOImpl_update",record);
    }

    @Override
    public int updateList(List<HzPbomLineRecord> records) {
        if(ListUtil.isEmpty(records)){
            return 0;
        }
        int size = records.size();
        //分批更新数据 一次1000条
        try {
            synchronized (this){
                if(size > 1000){
                    Map<Integer,List<HzPbomLineRecord>> map = HzBomSysFactory.spiltList(records);
                    for(List<HzPbomLineRecord> value :map.values()){
                        super.update("HzPbomRecordDAOImpl_updateList",value);
                    }
                }else {
                    super.update("HzPbomRecordDAOImpl_updateList",records);
                }
            }
            return size;
        }catch (Exception e){
            e.printStackTrace();
            throw new HzDBException("数据更新失败！",e);
        }
    }

    @Override
    public int updatePBOMList(List<HzPbomLineRecord> records) {
        if(ListUtil.isEmpty(records)){
            return 0;
        }
        int size = records.size();
        //分批更新数据 一次1000条
        try {
            synchronized (this){
                if(size > 1000){
                    Map<Integer,List<HzPbomLineRecord>> map = HzBomSysFactory.spiltList(records);
                    for(List<HzPbomLineRecord> value :map.values()){
                        super.update("HzPbomRecordDAOImpl_updatePBOMList",value);
                    }
                }else {
                    super.update("HzPbomRecordDAOImpl_updatePBOMList",records);
                }
            }
            return size;
        }catch (Exception e){
            e.printStackTrace();
            throw new HzDBException("数据更新失败！",e);
        }
    }

    @Override
    public int updateListByPuids(List<HzPbomLineRecord> records) {
        if(ListUtil.isEmpty(records)){
            return 0;
        }
        int size = records.size();
        try {
            if(size > 1000){
                Map<Integer,List<HzPbomLineRecord>> map = HzBomSysFactory.spiltList(records);
                for(List<HzPbomLineRecord> value :map.values()){
                    super.update("HzPbomRecordDAOImpl_updateListByPuids",value);
                }
            }else {
                super.update("HzPbomRecordDAOImpl_updateListByPuids",records);
            }
            return size;
        }catch (Exception e){
            e.printStackTrace();
            throw new HzDBException("数据更新失败！",e);
        }
    }

    @Override
    public int updateInput(HzPbomLineRecord record) {
        return super.update("HzPbomRecordDAOImpl_updateInput",record);
    }

    @Override
    public int deleteList(List<DeleteHzPbomReqDTO> list) {

        return super.update("HzPbomRecordDAOImpl_deleteList",list);
    }

    @Override
    public int deleteListByPuids(List<String> puids, String tableName) {
        try {
            int size = puids.size();
            Map<String,Object> m = new HashMap<>();
            m.put("tableName",tableName);
            synchronized (this){
                Map<Integer,List<String>> map = HzBomSysFactory.spiltList(puids);
                for(List<String> v:map.values()){
                    m.put("puids",v);
                    super.delete("HzPbomRecordDAOImpl_deleteListByPuids",m);
                }
            }
            return size;
        }catch (Exception e){
            e.printStackTrace();
            throw new HzBomException("删除数据失败!",e);
        }
    }


    @Override
    public Page<HzPbomLineRecord> getHzPbomRecordByPage(HzPbomByPageQuery query) {
        PageRequestParam request = new PageRequestParam();
        request.setPageNumber(query.getPage());
        if(BOMTransConstants.ALL.equals(query.getLimit())){
            request.setAllNumber(true);
        }else {
            request.setPageSize(Integer.valueOf(query.getLimit()));
        }
        Map map = new HashMap();
        map.put("projectId",query.getProjectId());
        map.put("isHas",query.getIsHas());
        map.put("pBomOfWhichDept",query.getpBomOfWhichDept().trim());
        map.put("lineIndex",query.getLineIndex());
        map.put("lineId",query.getLineId().trim());
        map.put("pBomLinePartClass",query.getpBomLinePartClass());
        map.put("pBomLinePartResource",query.getpBomLinePartResource());
        map.put("pIsNewPart",query.getpIsNewPart());
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
        request.setPageNumber(query.getPage());
        if(BOMTransConstants.ALL.equals(query.getLimit())){
            request.setAllNumber(true);
        }else {
            request.setPageSize(Integer.valueOf(query.getLimit()));
        }
        Map map = new HashMap();
        map.put("projectId",query.getProjectId());
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
        request.setPageNumber(query.getPage());
        if(BOMTransConstants.ALL.equals(query.getLimit())){
            request.setAllNumber(true);
        }else {
            request.setPageSize(Integer.valueOf(query.getLimit()));
        }
        Map map = new HashMap();
        map.put("projectId",query.getProjectId());
        map.put("eBomPuids",Lists.newArrayList(query.geteBomPuids().split(",")));
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
        map.put("puids",Lists.newArrayList(puids.split(",")));
        return super.update("HzPbomRecordDAOImpl_deleteByPuids",map);
    }

    @Override
    public int deleteListByPuids(String puids,String tableName) {
        List<String> list = Lists.newArrayList(puids.split(","));
        try {
            int size = list.size();
            Map<String,Object> m = new HashMap<>();
            m.put("tableName",tableName);
            synchronized (this){
                if(size>1000){
                    Map<Integer,List<String>> map = HzBomSysFactory.spiltList(list);
                    for(List<String> v:map.values()){
                        m.put("puids",v);
                        super.delete("HzPbomRecordDAOImpl_deleteListByPuids",m);
                    }
                }else {
                    m.put("puids",list);
                    super.delete("HzPbomRecordDAOImpl_deleteListByPuids",m);
                }
            }
            return size;
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public HzPbomLineRecord getPBomRecordByPuidAndRevision(HzChangeDataDetailQuery query) {
        Map<String,Object> map = new HashMap<>();
        map.put("puid", query.getPuid());
        map.put("projectId",query.getProjectId());
        if(null != query.getRevision()){
            map.put("revision",query.getRevision() ? query.getRevisionNo(): null);
        }else {
            map.put("revision",null);
        }
        map.put("status",query.getStatus());
        map.put("tableName",query.getTableName());
        return (HzPbomLineRecord)super.findForObject("HzPbomRecordDAOImpl_getEBomRecordByPuidAndRevision",map);
    }

    @Override
    public List<HzPbomLineRecord> getPbomRecordsByOrderId(HzChangeDataDetailQuery query) {
        Map<String,Object> map = new HashMap<>();
        map.put("projectId",query.getProjectId());
        map.put("tableName",query.getTableName());
        map.put("orderId",query.getOrderId());
        map.put("status",query.getStatus());
        if(null!=query.getRevision()){
            map.put("revision",query.getRevision()?"1":"0");
        }else {
            map.put("revision",null);
        }
        return super.findForList("HzPbomRecordDAOImpl_getPbomRecordsByOrderId",map);
    }

    @Override
    public HzPbomLineRecord findMinPBOMRecordWhichLineNoGreaterCurrentLineNo(HzBOMQuery query) {
        return super.findForObject("HzPbomRecordDAOImpl_findMinPBOMRecordWhichLineNoGreaterCurrentLineNo",query);
    }

    @Override
    public HzPbomLineRecord findMaxPBOMRecordWhichLineNoLessCurrentNo(HzBOMQuery query) {
        return super.findForObject("HzPbomRecordDAOImpl_findMaxPBOMRecordWhichLineNoLessCurrentNo",query);
    }

    @Override
    public List<HzPbomLineRecord> findPbomByLineId(HzBOMQuery hzBOMQuery) {
        return super.findForList("HzPbomRecordDAOImpl_findPbomByLineId",hzBOMQuery);
    }

    @Override
    public List<HzPbomLineRecord> findPbomsByEBom(List<HzEPLManageRecord> hzEPLManageRecords) {
        Map<String,Object> map = new HashMap<>();
        map.put("hzEPLManageRecords",hzEPLManageRecords);
        map.put("bomDigifaxId",hzEPLManageRecords.get(0).getBomDigifaxId());
        return super.findForList("HzPbomRecordDAOImpl_findPbomsByEBom",map);
    }

    @Override
    public HzPbomLineRecord findPbomByEBom(String puid, String projectId) {
        Map<String,String> map = new HashMap<>();
        map.put("puid",puid);
        map.put("projectId",projectId);
        return (HzPbomLineRecord)super.findForObject("HzPbomRecordDAOImpl_findPbomByEBom",map);
    }

    @Override
    public HzPbomLineRecord findEbomChildrenByLineIndex(String puid, String lineNo) {
        Map<String,String> map = new HashMap<>();
        map.put("puid",puid);
        map.put("lineNo",lineNo);
        return (HzPbomLineRecord)super.findForObject("HzPbomRecordDAOImpl_findEbomChildrenByLineIndex",map);
    }

    @Override
    public HzPbomLineRecord findNextLineIndex(String puid, String lineNo) {
        Map<String,String> map = new HashMap<>();
        map.put("puid",puid);
        map.put("lineNo",lineNo);
        return (HzPbomLineRecord)super.findForObject("HzPbomRecordDAOImpl_findNextLineIndex",map);
    }

    @Override
    public HzPbomLineRecord findPreviousPbom(HzPbomLineRecord hzPbomLineRecord) {
        return (HzPbomLineRecord)super.findForObject("HzPbomRecordDAOImpl_findPreviousPbom",hzPbomLineRecord);
    }

    @Override
    public HzPbomLineRecord findNextSortNum(HzPbomLineRecord hzPbomLineRecord) {
        return (HzPbomLineRecord)super.findForObject("HzPbomRecordDAOImpl_findNextSortNum",hzPbomLineRecord);
    }

}
