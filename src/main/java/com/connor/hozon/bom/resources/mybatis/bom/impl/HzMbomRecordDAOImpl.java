package com.connor.hozon.bom.resources.mybatis.bom.impl;

import com.connor.hozon.bom.resources.domain.dto.request.DeleteHzMbomReqDTO;
import com.connor.hozon.bom.resources.domain.query.HzBomRecycleByPageQuery;
import com.connor.hozon.bom.resources.domain.query.HzChangeDataDetailQuery;
import com.connor.hozon.bom.resources.domain.query.HzMbomByPageQuery;
import com.connor.hozon.bom.resources.domain.query.HzMbomTreeQuery;
import com.connor.hozon.bom.resources.enumtype.MbomTableNameEnum;
import com.connor.hozon.bom.resources.mybatis.bom.HzMbomRecordDAO;
import com.connor.hozon.bom.resources.page.Page;
import com.connor.hozon.bom.resources.page.PageRequestParam;
import com.connor.hozon.bom.resources.util.ListUtil;
import com.google.common.collect.Lists;
import org.springframework.stereotype.Service;
import sql.BaseSQLUtil;
import sql.pojo.bom.HzMbomLineRecord;
import sql.pojo.bom.HzMbomLineRecordVO;
import sql.pojo.bom.HzMbomRecord;
import sql.pojo.bom.HzPbomLineRecord;

import java.util.ArrayList;
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
    public int updateList(List<HzMbomLineRecord> records) {
        int size = records.size();
        //分批更新数据 一次1000条
        int i = 0;
        int cout = 0;
        try {
            synchronized (this){
                if (size > 1000) {
                    for (i = 0; i < size / 1000; i++) {
                        List<HzMbomLineRecord> list = new ArrayList<>();
                        for (int j = 0; j < 1000; j++) {
                            list.add(records.get(cout));
                            cout++;
                        }
                        super.update("HzMbomRecordDAOImpl_updateList",list);
                    }
                }
                if (i * 1000 < size) {
                    List<HzMbomLineRecord> list = new ArrayList<>();
                    for (int j = 0; j < size - i * 1000; j++) {
                        list.add(records.get(cout));
                        cout++;
                    }
                    super.update("HzMbomRecordDAOImpl_updateList",list);
                }
            }
            return 1;
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public int insert(HzMbomRecord record) {
        return super.insert("HzMbomRecordDAOImpl_insert",record);
    }

    @Override
    public int insert2(HzMbomLineRecord record){
        return super.insert("HzMbomRecordDAOImpl_insert2",record);
    }

    @Override
    public List<HzMbomLineRecord> findHzMbomByPuid_before(Map<String, Object> map) {
        return  super.findForList("HzMbomRecordDAOImpl_findHzMbomByPuid_before",map);
    }

    @Override
    public int insert_before(HzMbomLineRecord record){
        return super.insert("HzMbomRecordDAOImpl_insert_before",record);
    }

    @Override
    public int update_before(HzMbomLineRecord record){
        return super.insert("HzMbomRecordDAOImpl_update_before",record);
    }

    @Override
    public List<HzMbomLineRecord> findHzMbomByPuid_after(Map<String, Object> map) {
        return  super.findForList("HzMbomRecordDAOImpl_findHzMbomByPuid_after",map);
    }

    @Override
    public int insert_after(HzMbomLineRecord record){
        return super.insert("HzMbomRecordDAOImpl_insert_after",record);
    }

    @Override
    public int update_after(HzMbomLineRecord record){
        return super.insert("HzMbomRecordDAOImpl_update_after",record);
    }

    @Override
    public int update(HzMbomLineRecord record) {
        if(record.getTableName() == null || "".equals(record.getTableName())){
            record.setTableName("HZ_MBOM_RECORD");
        }
        return super.update("HzMbomRecordDAOImpl_update",record);
    }

    @Override
    public int updateInput(HzMbomLineRecord record) {
        if(record.getTableName() == null || "".equals(record.getTableName())){
            record.setTableName("HZ_MBOM_RECORD");
        }
        return super.update("HzMbomRecordDAOImpl_updateInput",record);
    }

    @Override
    public int updateInputProduct(HzMbomLineRecord record) {
        if(record.getTableName() == null || "".equals(record.getTableName())){
            record.setTableName("HZ_MBOM_OF_PRODUCT");
        }
        return super.update("HzMbomRecordDAOImpl_updateInput",record);
    }

    @Override
    public int updateInputFinance(HzMbomLineRecord record) {
        if(record.getTableName() == null || "".equals(record.getTableName())){
            record.setTableName("HZ_MBOM_OF_FINANCE");
        }
        return super.update("HzMbomRecordDAOImpl_updateInput",record);
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
        PageRequestParam request = new PageRequestParam();
        Map map = new HashMap();
        map.put("projectId",query.getProjectId());
        map.put("isHas",query.getIsHas());
        map.put("pBomOfWhichDept",query.getpBomOfWhichDept().trim());
        map.put("lineIndex",query.getLineIndex());
        map.put("lineId",query.getLineId().trim());
        map.put("pBomLinePartClass",query.getpBomLinePartClass());
        map.put("pBomLinePartResource",query.getpBomLinePartResource());
        map.put("tableName",MbomTableNameEnum.tableName(query.getType()));

        request.setPageNumber(query.getPage());
        request.setPageSize(query.getPageSize());
        request.setFilters(map);
        return super.findForPage("HzMbomRecordDAOImpl_getMBomRecord","HzMbomRecordDAOImpl_getTotalCount",request);

    }

    @Override
    public List<HzMbomLineRecord> findHzMbomByPuid(Map<String, Object> map) {
        if(map.get("tableName")==null || map.get("tableName") == ""){
            map.put("tableName","HZ_MBOM_RECORD");
        }
        return  super.findForList("HzMbomRecordDAOImpl_findHzMbomByPuid",map);
    }

    @Override
    public List<HzMbomLineRecord> findMbomByItemId(String itemId,String projectId){
        Map<String,Object> map = new HashMap<>();
        map.put("lineId",itemId);
        map.put("projectId",projectId);
        return super.findForList("HzMbomRecordDAOImpl_findMbomByItemId",map);
    }

    @Override
    public boolean checkItemIdIsRepeat(String projectId, String lineId) {
        Map<String,Object> map = new HashMap<>();
        map.put("projectId",projectId);
        map.put("lineId",lineId);
        return (int)super.findForObject("HzMbomRecordDAOImpl_checkItemIdIsRepeat",map)>0;
    }

    @Override
    public List<HzMbomLineRecord> getHzVehicleModelName(String projectId) {

        return super.findForList("HzMbomRecordDAOImpl_getHzVehicleModelName",projectId);
    }

    @Override
    public Page<HzMbomLineRecord> getHzSuberMbomByPage(HzMbomByPageQuery query) {
        PageRequestParam pageRequestParam = new PageRequestParam();
        Map map = new HashMap();
        map.put("projectId",query.getProjectId());
        map.put("isHas",query.getIsHas());
        map.put("pBomOfWhichDept",query.getpBomOfWhichDept());
        map.put("lineIndex",query.getLineIndex());
        map.put("lineId",query.getLineId());
        map.put("cfg0ModelRecordId",query.getCfg0ModelRecordId());
        pageRequestParam.setPageNumber(query.getPage());
        pageRequestParam.setPageSize(query.getPageSize());
        pageRequestParam.setFilters(map);
        return super.findForPage("HzMbomRecordDAOImpl_getHzSuberMbomByPage","HzMbomRecordDAOImpl_getHzSuberMbomTotalCount", pageRequestParam);
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
    public Double getHzMbomMaxOrderNum(String projectId) {
        String maxSortNum = (String)super.findForObject("HzMbomRecordDAOImpl_getHzMbomMaxOrderNum",projectId);
        if(maxSortNum ==  null){
            return null;
        }
        return Double.parseDouble(maxSortNum);
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
        if(query.getTableName() == null || query.getTableName() == ""){
            map.put("tableName","HZ_MBOM_RECORD");
        }else {
            map.put("tableName",query.getTableName());
        }
        map.put("colorId",query.getColorId());
        return super.findForList("HzMbomRecordDAOImpl_getHzMbomTree",map);
    }

    @Override
    public Page<HzMbomLineRecord> getHzMbomRecycleRecord(HzBomRecycleByPageQuery query) {
        PageRequestParam request = new PageRequestParam();
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
    public String findMinOrderNumWhichGreaterThanThisOrderNum(String projectId,String orderNum) {
        Map<String,Object> map = new HashMap<>();
        map.put("projectId",projectId);
        map.put("sortNum",Double.parseDouble(orderNum));
        return (String) super.findForObject("HzMbomRecordDAOImpl_findMinOrderNumWhichGreaterThanThisOrderNum",map);
    }

    @Override
    public List<HzMbomLineRecord> findHzMbomAll(String projectId,String tableName) {
        Map<String,Object> map = new HashMap<>();
        map.put("projectId",projectId);
        map.put("tableName",tableName);
        return super.findForList("HzMbomRecordDAOImpl_findHzMbomAll",map);
    }

    @Override
    public int insertVO(HzMbomLineRecordVO hzMbomLineRecordVO) {
        Map<String,Object> map = new HashMap<>();
        map.put("tableName",hzMbomLineRecordVO.getTableName());
        try {
            if (ListUtil.isNotEmpty(hzMbomLineRecordVO.getRecordList())) {
                List<HzMbomLineRecord> lineRecords = hzMbomLineRecordVO.getRecordList();
                int size = lineRecords.size();
                //分批插入数据 一次1000条
                int i = 0;
                int cout = 0;
                synchronized (this){
                    if (size > 1000) {
                        for (i = 0; i < size / 1000; i++) {
                            List<HzMbomLineRecord> list = new ArrayList<>();
                            for (int j = 0; j < 1000; j++) {
                                list.add(lineRecords.get(cout));
                                cout++;
                            }
                            map.put("list",list);

                            super.insert("HzMbomRecordDAOImpl_insertVO",map);

                        }
                    }
                    if (i * 1000 < size) {
                        List<HzMbomLineRecord> list = new ArrayList<>();
                        for (int j = 0; j < size - i * 1000; j++) {
                            list.add(lineRecords.get(cout));
                            cout++;
                        }
                        map.put("list",list);

                        super.insert("HzMbomRecordDAOImpl_insertVO",map);

                    }
                }

            }
            return 1;
        }catch (Exception e){
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public int updateVO(HzMbomLineRecordVO hzMbomLineRecordVO) {
        Map<String,Object> map = new HashMap<>();
        map.put("tableName",hzMbomLineRecordVO.getTableName());
        try {
            if (ListUtil.isNotEmpty(hzMbomLineRecordVO.getRecordList())) {
                List<HzMbomLineRecord> lineRecords = hzMbomLineRecordVO.getRecordList();
                int size = lineRecords.size();
                //分批更新数据 一次1000条
                int i = 0;
                int cout = 0;
                synchronized (this){
                    if (size > 1000) {
                        for (i = 0; i < size / 1000; i++) {
                            List<HzMbomLineRecord> list = new ArrayList<>();
                            for (int j = 0; j < 1000; j++) {
                                list.add(lineRecords.get(cout));
                                cout++;
                            }
                            map.put("list",list);

                            super.update("HzMbomRecordDAOImpl_updateVO",map);

                        }
                    }
                    if (i * 1000 < size) {
                        List<HzMbomLineRecord> list = new ArrayList<>();
                        for (int j = 0; j < size - i * 1000; j++) {
                            list.add(lineRecords.get(cout));
                            cout++;
                        }
                        map.put("list",list);

                        super.update("HzMbomRecordDAOImpl_updateVO",map);

                    }
                }

            }
            return 1;
        }catch (Exception e){
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public HzMbomLineRecord findHzMbomByEbomIdAndLineIndex(String ebomPuid, String lineIndex,String tableName) {
        Map<String,Object> map = new HashMap<>();
        map.put("puid",ebomPuid);
        map.put("lineIndex",lineIndex);
        map.put("tableName",tableName);
        return (HzMbomLineRecord)super.findForObject("HzMbomRecordDAOImpl_findHzMbomByEbomIdAndLineIndex",map);
    }

    @Override
    public int deleteMbomList(HzMbomLineRecordVO record) {
        Map<String,Object> map = new HashMap<>();
        map.put("tableName",record.getTableName());
        map.put("list",record.getRecordList());
        try {
            synchronized (this){
                super.delete("HzMbomRecordDAOImpl_deleteMbomList",map);
            }
        }catch (Exception e){
           return 0;
        }

        return 1;
    }

    @Override
    public int deleteMbomByProjectId(String projectId,String tableName) {
        Map<String,Object> map = new HashMap<>();
        map.put("projectId",projectId);
        map.put("tableName",tableName);
        return super.delete("HzMbomRecordDAOImpl_deleteMbomByProjectId",map);
    }

    @Override
    public Page<HzMbomLineRecord> getHzMbomTreeByPage(HzMbomByPageQuery query) {
        PageRequestParam request = new PageRequestParam();
        Map map = new HashMap();
        map.put("projectId",query.getProjectId());
        map.put("eBomPuids", Lists.newArrayList(query.geteBomPuids().split(",")));
        map.put("tableName",MbomTableNameEnum.tableName(query.getType()));
        if(ListUtil.isNotEmpty(Lists.newArrayList(query.getColorIds().split(",")))){
            map.put("colorIds",Lists.newArrayList(query.getColorIds().split(",")));
        }else {
            map.put("colorIds",null);
        }
        request.setPageNumber(query.getPage());
        request.setPageSize(query.getPageSize());
        request.setFilters(map);
        return super.findPage("HzMbomRecordDAOImpl_getHzMbomTreeByPage","HzMbomRecordDAOImpl_getHzMbomTreeTotalCount",request);

    }

    @Override
    public List<HzMbomLineRecord> getMbomRecordsByPuids(HzChangeDataDetailQuery query) {
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
        return super.findForList("HzMbomRecordDAOImpl_getMbomRecordsByPuids",map);

    }

    @Override
    public HzMbomLineRecord getMBomRecordByPuidAndRevision(HzChangeDataDetailQuery query) {
        Map<String,Object> map = new HashMap<>();
        map.put("puid", query.getPuid());
        map.put("projectId",query.getProjectId());
        if(null != query.getRevision()){
            map.put("revision",query.getRevision()?null:query.getRevisionNo());
        }else {
            map.put("revision",null);
        }
        map.put("status",query.getStatus());
        map.put("tableName",query.getTableName());
        return (HzMbomLineRecord)super.findForObject("HzMbomRecordDAOImpl_getMBomRecordByPuidAndRevision",map);
    }

}
