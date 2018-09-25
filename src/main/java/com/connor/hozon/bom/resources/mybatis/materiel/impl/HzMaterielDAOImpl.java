package com.connor.hozon.bom.resources.mybatis.materiel.impl;

import com.connor.hozon.bom.resources.domain.query.HzMaterielByPageQuery;
import com.connor.hozon.bom.resources.domain.query.HzMaterielQuery;
import com.connor.hozon.bom.resources.mybatis.materiel.HzMaterielDAO;
import com.connor.hozon.bom.resources.page.Page;
import com.connor.hozon.bom.resources.page.PageRequest;
import com.connor.hozon.bom.resources.util.ListUtil;
import org.springframework.stereotype.Service;
import sql.BaseSQLUtil;
import sql.pojo.bom.HzMbomLineRecord;
import sql.pojo.cfg.HzCfg0ModelRecord;
import sql.pojo.project.HzMaterielRecord;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: haozt
 * @Date: 2018/7/2
 * @Description:
 */
@Service("HzMaterielDAO")
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
    public int delete(String puid) {
        return super.update("HzMaterialDAOImpl_delete", puid);
    }

    @Override
    public int insertList(List<HzMaterielRecord> hzMaterielRecords) {
        try {
            if (ListUtil.isNotEmpty(hzMaterielRecords)) {
                int size = hzMaterielRecords.size();
                //分批插入数据 一次1000条
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

                            super.insert("HzMaterialDAOImpl_insertList",list1);

                        }
                    }
                    if (i * 1000 < size) {
                        List<HzMaterielRecord> list1 = new ArrayList<>();
                        for (int j = 0; j < size - i * 1000; j++) {
                            list1.add(hzMaterielRecords.get(cout));
                            cout++;
                        }

                        super.insert("HzMaterialDAOImpl_insertList",list1);

                    }
                }

            }
            return 1;
        }catch (Exception e){
            return 0;
        }
//        return super.insert("HzMaterialDAOImpl_insertList", hzMaterielRecords);
    }

    @Override
    public Page<HzMaterielRecord> findHzMaterielForPage(HzMaterielByPageQuery query) {
        PageRequest pageRequest = new PageRequest();
        pageRequest.setPageNumber(query.getPage());
        pageRequest.setPageSize(query.getPageSize());
        Map map = new HashMap<>();
        map.put("projectId", query.getProjectId());
        map.put("pMaterielDataType", query.getpMaterielDataType());
        map.put("pMaterielCode", query.getpMaterielCode());
        map.put("pMaterielType", query.getpMaterielType());
        pageRequest.setFilters(map);
        return super.findPage("HzMaterialDAOImpl_findHzMaterielForPage", "HzMaterialDAOImpl_findHzMaterielTotalCount", pageRequest);
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
        return super.executeUpdate(map, "com.connor.hozon.bom.bomSystem.dao.project.HzSuperMaterielDao.updateByBatch");
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
    public int deleteMaterielList(List<HzMaterielRecord> list) {
        int i;
        try {
            synchronized (this){
                i = super.delete("HzMaterialDAOImpl_deleteMaterielList",list);
            }
        }catch (Exception e){
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
            return 0;
        }
    }

    @Override
    public List<HzMaterielRecord> findHzMaterielForProcess(String projectId) {
        return super.executeQueryByPass(new HzMaterielRecord(), projectId,"HzMaterialDAOImpl_findHzMaterielForProcess");
    }
}
