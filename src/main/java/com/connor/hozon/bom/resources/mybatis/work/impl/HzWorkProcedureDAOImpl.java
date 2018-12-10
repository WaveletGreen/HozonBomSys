package com.connor.hozon.bom.resources.mybatis.work.impl;

import com.connor.hozon.bom.resources.domain.model.HzBomSysFactory;
import com.connor.hozon.bom.resources.domain.query.HzChangeDataDetailQuery;
import com.connor.hozon.bom.resources.domain.query.HzWorkProcessByPageQuery;
import com.connor.hozon.bom.resources.mybatis.work.HzWorkProcedureDAO;
import com.connor.hozon.bom.resources.page.Page;
import com.connor.hozon.bom.resources.page.PageRequestParam;
import com.connor.hozon.bom.resources.util.ListUtil;
import com.google.common.collect.Lists;
import org.springframework.stereotype.Service;
import sql.BaseSQLUtil;
import sql.pojo.work.HzWorkProcedure;
import sql.pojo.work.HzWorkProcess;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: haozt
 * @Date: 2018/6/30
 * @Description:
 */
@Service("hzWorkProcedureDAO")
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
        PageRequestParam pageRequestParam = new PageRequestParam();
        pageRequestParam.setPageSize(query.getPageSize());
        pageRequestParam.setPageNumber(query.getPage());
        Map<String,Object> map = new HashMap<>();
        map.put("projectId",query.getProjectId());
        map.put("type",query.getType());
        map.put("pMaterielCode",query.getpMaterielCode());
        map.put("pMaterielDesc",query.getpMaterielDesc());
        pageRequestParam.setFilters(map);
        return super.findPage("HzWorkProcedureDAOImpl_findHzWorkProcessByPage","HzWorkProcedureDAOImpl_getTotalCount", pageRequestParam);
    }

    @Override
    public Page<HzWorkProcess> findHzWorkProcessByPage2(HzWorkProcessByPageQuery query) {
        PageRequestParam pageRequestParam = new PageRequestParam();
        pageRequestParam.setPageSize(query.getPageSize());
        pageRequestParam.setPageNumber(query.getPage());
        Map<String,Object> map = new HashMap<>();
        map.put("projectId",query.getProjectId());
        map.put("type",query.getType());
        map.put("pMaterielCode",query.getpMaterielCode());
        map.put("pMaterielDesc",query.getpMaterielDesc());
        pageRequestParam.setFilters(map);
        return super.findPage("HzWorkProcedureDAOImpl_findHzWorkProcessByPage2","HzWorkProcedureDAOImpl_getTotalCount2", pageRequestParam);
    }

    @Override
    public List<HzWorkProcess> getHzWorkProcess(String materielId, String projectId) {
        Map<String,Object> map = new HashMap<>();
        map.put("materielId",materielId);
        map.put("projectId",projectId);
        return  super.findForList("HzWorkProcedureDAOImpl_getHzWorkProcess",map);
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

    @Override
    public List<HzWorkProcedure> queryProcedures(List<HzWorkProcedure> hzWorkProcedureList) {
        return super.executeQueryByPass(new HzWorkProcedure(),hzWorkProcedureList, "HzWorkProcedureDAOImpl_queryProcedures");
    }

    @Override
    public List<HzWorkProcedure> getHzWorkProcedureByPuids(HzChangeDataDetailQuery query) {
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
        return super.findForList("HzWorkProcedureDAOImpl_getHzWorkProcedureByPuids",map);
    }

    @Override
    public HzWorkProcedure getHzWorkProcedureByPuidAndRevision(HzChangeDataDetailQuery query) {
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
        return (HzWorkProcedure) super.findForObject("HzWorkProcedureDAOImpl_getHzWorkProcedureByPuidAndRevision",map);
    }

    @Override
    public int updateList(List<HzWorkProcedure> list) {
        try {
            if (ListUtil.isNotEmpty(list)) {
                int size = list.size();
                //分批更新数据 一次1000条
                int i = 0;
                int cout = 0;
                synchronized (this){
                    if (size > 1000) {
                        for (i = 0; i < size / 1000; i++) {
                            List<HzWorkProcedure> list1 = new ArrayList<>();
                            for (int j = 0; j < 1000; j++) {
                                list1.add(list.get(cout));
                                cout++;
                            }

                            super.update("HzWorkProcedureDAOImpl_updateList",list1);

                        }
                    }
                    if (i * 1000 < size) {
                        List<HzWorkProcedure> list1 = new ArrayList<>();
                        for (int j = 0; j < size - i * 1000; j++) {
                            list1.add(list.get(cout));
                            cout++;
                        }
                        super.update("HzWorkProcedureDAOImpl_updateList",list1);

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
    public int insertList(List<HzWorkProcedure> list, String tableName) {
        Map<String,Object> map = new HashMap<>();
        map.put("tableName",tableName);
        try {
            if (ListUtil.isNotEmpty(list)) {
                int size = list.size();
                //分批更新数据 一次1000条
                int i = 0;
                int cout = 0;
                synchronized (this){
                    if (size > 1000) {
                        for (i = 0; i < size / 1000; i++) {
                            List<HzWorkProcedure> list1 = new ArrayList<>();
                            for (int j = 0; j < 1000; j++) {
                                list1.add(list.get(cout));
                                cout++;
                            }
                            map.put("list",list1);
                            super.insert("HzWorkProcedureDAOImpl_insertList",map);

                        }
                    }
                    if (i * 1000 < size) {
                        List<HzWorkProcedure> list1 = new ArrayList<>();
                        for (int j = 0; j < size - i * 1000; j++) {
                            list1.add(list.get(cout));
                            cout++;
                        }
                        map.put("list",list1);
                        super.insert("HzWorkProcedureDAOImpl_insertList",map);

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
    public int deleteByPuids(List<String> puids,String tableName) {
        int size = puids.size();
        Map<String,Object> m = new HashMap<>();
        m.put("tableName",tableName);
        try {
            if(size>1000){
                HzBomSysFactory<String> factory = new HzBomSysFactory();
                Map<Integer,List<String>> map = factory.spiltList(puids);
                for(List<String> v:map.values()){
                    m.put("puids",v);
                    super.delete("HzWorkProcedureDAOImpl_deleteByPuids",m);
                }
            }else {
                m.put("puids",puids);
                super.delete("HzWorkProcedureDAOImpl_deleteByPuids",m);
            }
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return 1;
    }

    @Override
    public List<HzWorkProcedure> getWorkProcedureByOrderId(HzChangeDataDetailQuery query) {
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
        return super.findForList("HzWorkProcedureDAOImpl_getWorkProcedureByOrderId",map);
    }

}
