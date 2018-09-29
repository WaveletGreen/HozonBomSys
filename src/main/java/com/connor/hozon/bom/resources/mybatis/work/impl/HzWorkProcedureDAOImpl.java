package com.connor.hozon.bom.resources.mybatis.work.impl;

import com.connor.hozon.bom.resources.domain.query.HzWorkProcessByPageQuery;
import com.connor.hozon.bom.resources.mybatis.work.HzWorkProcedureDAO;
import com.connor.hozon.bom.resources.page.Page;
import com.connor.hozon.bom.resources.page.PageRequest;
import org.springframework.stereotype.Service;
import sql.BaseSQLUtil;
import sql.pojo.work.HzWorkProcedure;
import sql.pojo.work.HzWorkProcess;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: haozt
 * @Date: 2018/6/30
 * @Description:
 */
@Service("HzWorkProcedureDAO")
public class HzWorkProcedureDAOImpl  extends BaseSQLUtil implements HzWorkProcedureDAO {

    @Override
    public int insert(HzWorkProcedure hzWorkProcedure) {
        return super.insert("HzWorkProcedureDAOImpl_insert",hzWorkProcedure);
    }

    @Override
    public int update(HzWorkProcedure hzWorkProcedure) {
        return super.update("HzWorkProcedureDAOImpl_update",hzWorkProcedure);
    }

    public int update2(HzWorkProcedure hzWorkProcedure) {
        return super.update("HzWorkProcedureDAOImpl_update2",hzWorkProcedure);
    }

    @Override
    public int delete(String puid) {
        return super.update("HzWorkProcedureDAOImpl_delete",puid);
    }

    @Override
    public Page<HzWorkProcess> findHzWorkProcessByPage(HzWorkProcessByPageQuery query) {
        PageRequest pageRequest = new PageRequest();
        pageRequest.setPageSize(query.getPageSize());
        pageRequest.setPageNumber(query.getPage());
        Map<String,Object> map = new HashMap<>();
        map.put("projectId",query.getProjectId());
        map.put("type",query.getType());
        map.put("pMaterielCode",query.getpMaterielCode());
        map.put("pMaterielDesc",query.getpMaterielDesc());
        pageRequest.setFilters(map);
        return super.findPage("HzWorkProcedureDAOImpl_findHzWorkProcessByPage","HzWorkProcedureDAOImpl_getTotalCount",pageRequest);
    }

    @Override
    public Page<HzWorkProcess> findHzWorkProcessByPage2(HzWorkProcessByPageQuery query) {
        PageRequest pageRequest = new PageRequest();
        pageRequest.setPageSize(query.getPageSize());
        pageRequest.setPageNumber(query.getPage());
        Map<String,Object> map = new HashMap<>();
        map.put("projectId",query.getProjectId());
        map.put("type",query.getType());
        map.put("pMaterielCode",query.getpMaterielCode());
        map.put("pMaterielDesc",query.getpMaterielDesc());
        pageRequest.setFilters(map);
        return super.findPage("HzWorkProcedureDAOImpl_findHzWorkProcessByPage2","HzWorkProcedureDAOImpl_getTotalCount2",pageRequest);
    }

    @Override
    public HzWorkProcess getHzWorkProcess(String materielId, String projectId) {
        Map<String,Object> map = new HashMap<>();
        map.put("materielId",materielId);
        map.put("projectId",projectId);
        return (HzWorkProcess) super.findForObject("HzWorkProcedureDAOImpl_getHzWorkProcess",map);
    }

    @Override
    public HzWorkProcess getHzWorkProcess2(String materielId, String projectId, String procedureDesc) {
        Map<String,Object> map = new HashMap<>();
        if(procedureDesc!=null&&!"null".equals(procedureDesc)){
            map.put("procedureDesc",procedureDesc);
        }
        map.put("materielId",materielId);
        map.put("projectId",projectId);
        return (HzWorkProcess) super.findForObject("HzWorkProcedureDAOImpl_getHzWorkProcess2",map);
    }

    @Override
    public HzWorkProcedure getHzWorkProcessByMaterielId(String materielId) {
        Map<String,Object> map = new HashMap<>();
        map.put("materielId",materielId);
        return (HzWorkProcedure) super.findForObject("HzWorkProcedureDAOImpl_getHzWorkProcessByMaterielId",map);
    }

    @Override
    public int updateSendFlag(Map<String, Object> map) {
        return super.update("HzWorkProcedureDAOImpl_updateSendFlag",map);
    }

    @Override
    public int insertHzWorkProcedures(List<HzWorkProcedure> hzWorkProcedures) {
        return super.executeInsert(hzWorkProcedures, "HzWorkProcedureDAOImpl_insertHzWorkProcedures");
    }

    @Override
    public List<HzWorkProcedure> findHzWorkProcessByProjectId(String projectId) {
        return super.executeQueryByPass(new HzWorkProcedure(), projectId, "HzWorkProcedureDAOImpl_findHzWorkProcessByProjectId");
    }

    @Override
    public int deleteHzWorkProcessByMaterielIds(List<HzWorkProcedure> hzWorkProceduresDel) {
        return super.executeDelete(hzWorkProceduresDel,"HzWorkProcedureDAOImpl_deleteHzWorkProcessByMaterielIds");
    }

    @Override
    public List<String> queryProcessDescsByPuid(List<String> puidList) {
        return super.executeQueryByPass(new String(), puidList,"HzWorkProcedureDAOImpl_queryProcessDescsByPuid");
    }

    @Override
    public int deleteHzWorkProcesses(List<HzWorkProcedure> hzWorkProceduresDel) {
        return super.executeDelete(hzWorkProceduresDel, "HzWorkProcedureDAOImpl_deleteHzWorkProcesses");
    }

}
