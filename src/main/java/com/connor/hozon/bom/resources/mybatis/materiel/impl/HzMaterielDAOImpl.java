package com.connor.hozon.bom.resources.mybatis.materiel.impl;

import com.connor.hozon.bom.resources.mybatis.materiel.HzMaterielDAO;
import com.connor.hozon.bom.resources.page.Page;
import com.connor.hozon.bom.resources.page.PageRequest;
import com.connor.hozon.bom.resources.query.HzMaterielByPageQuery;
import com.connor.hozon.bom.resources.query.HzMaterielQuery;
import org.springframework.stereotype.Service;
import sql.BaseSQLUtil;
import sql.pojo.bom.HzMbomLineRecord;
import sql.pojo.cfg.HzCfg0ModelRecord;
import sql.pojo.project.HzMaterielRecord;

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
        return super.insert("HzMaterialDAOImpl_insertList", hzMaterielRecords);
    }

    @Override
    public Page<HzMaterielRecord> findHzMaterielForPage(HzMaterielByPageQuery query) {
        PageRequest pageRequest = new PageRequest();
        pageRequest.setPageNumber(query.getPage());
        pageRequest.setPageSize(query.getLimit());
        Map map = new HashMap<>();
        map.put("projectId", query.getProjectId());
        map.put("pMaterielDataType", query.getpMaterielDataType());
        pageRequest.setFilters(map);
        return super.findPage("HzMaterialDAOImpl_findHzMaterielForPage", "HzMaterialDAOImpl_findHzMaterielTotalCount", pageRequest);
    }


    @Override
    public List<HzMaterielRecord> findHzMaterielForList(HzMaterielQuery query) {
        Map<String, Object> map = new HashMap<>();
        map.put("projectId", query.getProjectId());
        map.put("puid", query.getPuid());
        map.put("pMaterielDataType", query.getpMaterielDataType());
        return super.findForList("HzMaterialDAOImpl_findHzMaterielForList", query);
    }

    @Override
    public List<HzCfg0ModelRecord> findHzCfg0ModelRecord(HzMaterielByPageQuery query) {
        Map<String, Object> map = new HashMap<>();
        map.put("projectId", query.getProjectId());
        map.put("pMaterielDataType", query.getpMaterielDataType());
        return super.findForList("HzMaterialDAOImpl_findHzCfg0ModelRecord", map);
    }

    @Override
    public List<HzMbomLineRecord> findHz2YMbomRecord(HzMaterielByPageQuery query) {
        Map<String, Object> map = new HashMap<>();
        map.put("projectId", query.getProjectId());
        map.put("pMaterielDataType", query.getpMaterielDataType());
        return super.findForList("HzMaterialDAOImpl_findHz2YMbomRecord", map);
    }

    @Override
    public List<HzMbomLineRecord> findHzResourceMakeSingleMbomRecord(HzMaterielByPageQuery query) {
        Map<String, Object> map = new HashMap<>();
        map.put("projectId", query.getProjectId());
        map.put("pMaterielDataType", query.getpMaterielDataType());
        return super.findForList("HzMaterialDAOImpl_findHzResourceMakeSingleMbomRecord", map);
    }

    @Override
    public List<HzMbomLineRecord> findHzResourceBuyMbomRecord(HzMaterielByPageQuery query) {
        Map<String, Object> map = new HashMap<>();
        map.put("projectId", query.getProjectId());
        map.put("pMaterielDataType", query.getpMaterielDataType());
        return super.findForList("HzMaterialDAOImpl_findHzResourceBuyMbomRecord", map);
    }

    @Override
    public List<HzMbomLineRecord> findHzMadeBySelfSpareMbomRecord(HzMaterielByPageQuery query) {
        Map<String, Object> map = new HashMap<>();
        map.put("projectId", query.getProjectId());
        map.put("pMaterielDataType", query.getpMaterielDataType());
        return super.findForList("HzMaterialDAOImpl_findHzMadeBySelfSpareMbomRecord", map);
    }

    @Override
    public boolean HzMaterielIsExist(Map<String, Object> map) {
        return false;
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
        Map<String,Object> map = new HashMap<>();
        map.put("projectId",projectId);
        map.put("pMaterielDataType",pMaterielDataType);
        return(Integer) super.findForObject("HzMaterialDAOImpl_findHzMaterielTotalCount",map);
    }
}
