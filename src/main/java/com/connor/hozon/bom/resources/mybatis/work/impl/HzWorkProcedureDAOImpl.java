package com.connor.hozon.bom.resources.mybatis.work.impl;

import com.connor.hozon.bom.resources.mybatis.work.HzWorkProcedureDAO;
import com.connor.hozon.bom.resources.page.Page;
import com.connor.hozon.bom.resources.page.PageRequest;
import com.connor.hozon.bom.resources.query.HzWorkProcessByPageQuery;
import sql.BaseSQLUtil;
import sql.pojo.work.HzWorkCenter;
import sql.pojo.work.HzWorkProcedure;
import sql.pojo.work.HzWorkProcess;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: haozt
 * @Date: 2018/6/30
 * @Description:
 */
public class HzWorkProcedureDAOImpl  extends BaseSQLUtil implements HzWorkProcedureDAO {

    @Override
    public int insert(HzWorkProcedure hzWorkProcedure) {
        return super.insert("HzWorkProcedureDAOImpl_insert",hzWorkProcedure);
    }

    @Override
    public int update(HzWorkProcedure hzWorkProcedure) {
        return super.update("HzWorkProcedureDAOImpl_update",hzWorkProcedure);
    }

    @Override
    public int delete(String puid) {
        return super.update("HzWorkProcedureDAOImpl_delete",puid);
    }

    @Override
    public Page<HzWorkProcess> findHzWorkProcessByPage(HzWorkProcessByPageQuery query) {
        PageRequest pageRequest = new PageRequest();
        pageRequest.setPageSize(query.getPage());
        pageRequest.setPageNumber(query.getLimit());
        Map<String,Object> map = new HashMap<>();
        map.put("projectId",query.getProjectId());
        map.put("type",query.getType());
        pageRequest.setFilters(map);
        return super.findPage("HzWorkProcedureDAOImpl_findHzWorkProcessByPage","HzWorkProcedureDAOImpl_getTotalCount",pageRequest);
    }
}
