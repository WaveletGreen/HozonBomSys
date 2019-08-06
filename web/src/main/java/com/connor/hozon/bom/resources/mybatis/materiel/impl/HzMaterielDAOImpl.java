package com.connor.hozon.bom.resources.mybatis.materiel.impl;

import com.connor.hozon.bom.resources.domain.model.HzBomSysFactory;
import com.connor.hozon.bom.resources.domain.query.HzChangeDataDetailQuery;
import com.connor.hozon.bom.resources.domain.query.HzMaterielByPageQuery;
import com.connor.hozon.bom.resources.domain.query.HzMaterielQuery;
import com.connor.hozon.bom.resources.mybatis.materiel.HzMaterielDAO;
import com.connor.hozon.bom.resources.page.Page;
import com.connor.hozon.bom.resources.page.PageRequestParam;
import com.connor.hozon.bom.resources.util.ListUtil;
import org.springframework.stereotype.Service;
import sql.BaseSQLUtil;
import cn.net.connor.hozon.dao.pojo.configuration.model.HzCfg0ModelRecord;
import cn.net.connor.hozon.dao.pojo.bom.materiel.HzMaterielRecord;
import cn.net.connor.hozon.common.exception.HzDBException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: haozt
 * @Date: 2018/7/2
 * @Description:
 */
@Service("hzMaterielDAO")
public class HzMaterielDAOImpl extends BaseSQLUtil implements HzMaterielDAO {

    @Override
    public int insert(HzMaterielRecord hzMaterielRecord) {
        return super.insert("HzMaterialDAOImpl_insert", hzMaterielRecord);
    }

    @Override
    public int update(HzMaterielRecord hzMaterielRecord) {
        return super.update("HzMaterialDAOImpl_update", hzMaterielRecord);
    }

    @Override
    public int deleteList(List<String> puids) {
        if(ListUtil.isEmpty(puids)){
            return 0;
        }
        return  super.update("HzMaterialDAOImpl_deleteList",puids);
    }


    @Override
    public int insertList(List<HzMaterielRecord> hzMaterielRecords,String tableName) {
            Map<String,Object> map = new HashMap<>();
            map.put("tableName",tableName);
        try {
            if (ListUtil.isNotEmpty(hzMaterielRecords)) {
                int size = hzMaterielRecords.size();
                //分批更新数据 一次1000条
                int i = 0;
                int cout = 0;
                synchronized (this){
                    if (size > 1000) {
                        for (i = 0; i < size / 1000; i++) {
                            List<HzMaterielRecord> list1 = new ArrayList<>();
                            for (int j = 0; j < 1000; j++) {
                                list1.add(hzMaterielRecords.get(cout));
                                cout++;
                            }
                            map.put("list",list1);
                            super.insert("HzMaterialDAOImpl_insertList",map);

                        }
                    }
                    if (i * 1000 < size) {
                        List<HzMaterielRecord> list1 = new ArrayList<>();
                        for (int j = 0; j < size - i * 1000; j++) {
                            list1.add(hzMaterielRecords.get(cout));
                            cout++;
                        }
                        map.put("list",list1);
                        super.insert("HzMaterialDAOImpl_insertList",map);

                    }
                }

            }
            return 1;
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public Page<HzMaterielRecord> findHzMaterielForPage(HzMaterielByPageQuery query) {
        PageRequestParam pageRequestParam = new PageRequestParam();
        pageRequestParam.setPageNumber(query.getPage());
        pageRequestParam.setPageSize(query.getPageSize());
        Map map = new HashMap<>();
        map.put("projectId", query.getProjectId());
        map.put("pMaterielDataType", query.getpMaterielDataType());
        map.put("pMaterielCode", query.getpMaterielCode().trim());
        map.put("pMaterielType", query.getpMaterielType());
        pageRequestParam.setFilters(map);
        return super.findPage("HzMaterialDAOImpl_findHzMaterielForPage", "HzMaterialDAOImpl_findHzMaterielTotalCount", pageRequestParam);
    }


    @Override
    public List<HzMaterielRecord> findHzMaterielForList(HzMaterielQuery query) {
        Map<String, Object> map = new HashMap<>();
        map.put("projectId", query.getProjectId());
        map.put("puid", query.getPuid());
        map.put("pMaterielDataType", query.getpMaterielDataType());
        map.put("materielResourceId", query.getMaterielResourceId());
        map.put("pMaterielCode",query.getpMaterielCode());
        return super.findForList("HzMaterialDAOImpl_findHzMaterielForList", query);
    }

    @Override
    public List<HzCfg0ModelRecord> findHzCfg0ModelRecord(HzMaterielByPageQuery query) {
        Map<String, Object> map = new HashMap<>();
        map.put("offset", (query.getPage() - 1) * query.getPageSize());
        map.put("limit", query.getPage() * query.getPageSize());
        map.put("projectId", query.getProjectId());
        return super.findForList("HzMaterialDAOImpl_findHzCfg0ModelRecord", map);
    }

    @Override
    public boolean HzMaterielIsExist(Map<String, Object> map) {
        return (int) super.findForObject("HzMaterialDAOImpl_HzMaterielIsExist", map) > 0;
    }

    @Override
    public HzMaterielRecord getHzMaterielRecord(HzMaterielQuery query) {
        Map<String, Object> map = new HashMap<>();
        map.put("puid", query.getPuid());
        map.put("projectId", query.getProjectId());
        map.put("pMaterielDataType", query.getpMaterielDataType());
        return (HzMaterielRecord) super.findForObject("HzMaterialDAOImpl_getHzMaterielRecord", map);
    }

    @Override
    public Integer getHzMaterielCount(String projectId, Integer pMaterielDataType) {
        Map<String, Object> map = new HashMap<>();
        map.put("projectId", projectId);
        map.put("pMaterielDataType", pMaterielDataType);
        return (Integer) super.findForObject("HzMaterialDAOImpl_findHzMaterielTotalCount", map);
    }


    @Override
    public List<HzCfg0ModelRecord> findHzCfg0ModelRecordAll(HzMaterielByPageQuery query) {
        Map<String, Object> map = new HashMap<>();
        map.put("projectId", query.getProjectId());
        return super.findForList("HzMaterialDAOImpl_findHzCfg0ModelRecordAll", map);
    }

    /**
     * 批量更新数据
     *
     * @param list
     * @return
     */
    @Override
    public int updateByBatch(List<String> list, String tableName, String field) {
        Map<String, Object> map = new HashMap<>();
        map.put("list", list);
        map.put("tableName", tableName);
        map.put("field", field);
        return super.executeUpdate(map, "HzMaterialDAOImpl_updateByBatch");
    }

    @Override
    public int realDelete(String materielResourceId) {
        return super.delete("HzMaterialDAOImpl_realDelete", materielResourceId);
    }

    @Override
    public boolean isRepeat(HzMaterielQuery query) {
        Map<String,Object> map = new HashMap<>();
        map.put("pMaterielCode",query.getpMaterielCode());
        map.put("projectId",query.getProjectId());
        return (int)super.findForObject("HzMaterialDAOImpl_isRepeat",map)>0;
    }

    @Override
    public List<HzMaterielRecord> getAllMaterielExceptVehicleMateriel(String projectId) {

        return super.findForList("HzMaterialDAOImpl_getAllMaterielExceptVehicleMateriel",projectId);
    }

    @Override
    public int deleteMaterielList(List<HzMaterielRecord> list,String tableName) {
        Map<String,Object> map =new HashMap<>();
        map.put("tableName",tableName);
        map.put("list",list);
        int i;
        try {
            synchronized (this){
                i = super.delete("HzMaterialDAOImpl_deleteMaterielList",map);
            }
        }catch (Exception e){
            e.printStackTrace();
            return 0;
        }
        return i;
    }

    @Override
    public int updateList(List<HzMaterielRecord> list) {
        try {
            if (ListUtil.isNotEmpty(list)) {
                int size = list.size();
                //分批更新数据 一次1000条
                int i = 0;
                int cout = 0;
                synchronized (this){
                    if (size > 1000) {
                        for (i = 0; i < size / 1000; i++) {
                            List<HzMaterielRecord> list1 = new ArrayList<>();
                            for (int j = 0; j < 1000; j++) {
                                list1.add(list.get(cout));
                                cout++;
                            }

                            super.update("HzMaterialDAOImpl_updateList",list1);

                        }
                    }
                    if (i * 1000 < size) {
                        List<HzMaterielRecord> list1 = new ArrayList<>();
                        for (int j = 0; j < size - i * 1000; j++) {
                            list1.add(list.get(cout));
                            cout++;
                        }
                        super.update("HzMaterialDAOImpl_updateList",list1);

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
    public int updateMaterielList(List<HzMaterielRecord> records) {
        if(ListUtil.isEmpty(records)){
            return 0;
        }
        int size = records.size();
        //分批更新数据 一次1000条
        try {
            synchronized (this){
                if(size > 1000){
                    Map<Integer,List<HzMaterielRecord>> map = HzBomSysFactory.spiltList(records);
                    for(List<HzMaterielRecord> value :map.values()){
                        super.update("HzMaterialDAOImpl_updateMaterielList",value);
                    }
                }else {
                    super.update("HzMaterialDAOImpl_updateMaterielList",records);
                }
            }
            return size;
        }catch (Exception e){
            e.printStackTrace();
            throw new HzDBException("物料数据更新失败！",e);
        }
    }

    @Override
    public List<HzMaterielRecord> findHzMaterielForProcess(String projectId) {
        return super.executeQueryByPass(new HzMaterielRecord(), projectId,"HzMaterialDAOImpl_findHzMaterielForProcess");
    }

    @Override
    public int deleteMaterielByProjectId(String projectId) {
        return super.delete("HzMaterialDAOImpl_deleteMaterielByProjectId",projectId);
    }

    @Override
    public List<HzMaterielRecord> getMaterialRecordsByPuids(HzChangeDataDetailQuery query) {
        Map<String,Object> map = new HashMap<>();
        map.put("puids", query.getPuids());
        map.put("projectId",query.getProjectId());
        if(null!=query.getRevision()){
            map.put("revision",query.getRevision()?"":null);
        }else {
            map.put("revision",null);
        }
        map.put("pValidFlag",query.getStatus());
        map.put("tableName",query.getTableName());
        map.put("orderId",query.getOrderId());
        return super.findForList("HzMaterialDAOImpl_getMaterialRecordsByPuids",map);
    }

    @Override
    public HzMaterielRecord getMaterialRecordByPuidAndRevision(HzChangeDataDetailQuery query) {
        Map<String,Object> map = new HashMap<>();
        map.put("puid", query.getPuid());
        map.put("projectId",query.getProjectId());
        if(null != query.getRevision()){
            map.put("revision",query.getRevision() ? query.getRevisionNo(): null);
        }else {
            map.put("revision",null);
        }
        map.put("pValidFlag",query.getStatus());
        map.put("tableName",query.getTableName());
        return (HzMaterielRecord)super.findForObject("HzMaterialDAOImpl_getMaterialRecordByPuidAndRevision",map);
    }

    @Override
    public List<HzMaterielRecord> getMaterielRecordsByOrderId(HzChangeDataDetailQuery query) {
        Map<String,Object> map = new HashMap<>();
        map.put("projectId",query.getProjectId());
        map.put("tableName",query.getTableName());
        map.put("orderId",query.getOrderId());
        map.put("pValidFlag",query.getStatus());
        if(null!=query.getRevision()){
            map.put("revision",query.getRevision()?"1":"0");
        }else {
            map.put("revision",null);
        }
        return super.findForList("HzMaterialDAOImpl_getMaterielRecordsByOrderId",map);
    }
}
