package com.connor.hozon.bom.resources.mybatis.work.impl;

import com.connor.hozon.bom.resources.mybatis.work.HzWorkCenterDAO;
import com.connor.hozon.bom.resources.page.Page;
import com.connor.hozon.bom.resources.page.PageRequest;
import com.connor.hozon.bom.resources.query.HzWorkByPageQuery;
import org.springframework.stereotype.Service;
import sql.BaseSQLUtil;
import sql.pojo.work.HzWorkCenter;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: haozt
 * @Date: 2018/6/30
 * @Description:
 */
@Service("HzWorkCenterDAO")
public class HzWorkCenterDAOImpl extends BaseSQLUtil implements HzWorkCenterDAO {
    @Override
    public int insert(HzWorkCenter hzWorkCenter) {
        return super.insert("HzWorkCenterDAOImpl_insert",hzWorkCenter);
    }

    @Override
    public int update(HzWorkCenter hzWorkCenter) {
        return super.update("HzWorkCenterDAOImpl_update",hzWorkCenter);
    }

    @Override
    public Page findWorkCenterForPage(HzWorkByPageQuery query) {
        PageRequest pageRequest = new PageRequest();
        Map map = new HashMap();
        map.put("projectId",query.getProjectId());
        pageRequest.setPageNumber(query.getPage());
        pageRequest.setPageSize(query.getLimit());
        pageRequest.setFilters(map);
        return super.findPage("HzWorkCenterDAOImpl_findWorkCenterForPage","HzWorkCenterDAOImpl_findWorkCenterTotalCount",pageRequest);
    }

    @Override
    public int delete(String puid) {

        return super.update("HzWorkCenterDAOImpl_delete",puid);
    }

    @Override
    public HzWorkCenter findWorkCenterById(String projectId, String puid) {
        Map<String,Object> map = new HashMap<>();
        map.put("projectId",projectId);
        map.put("puid",puid);
        return (HzWorkCenter) super.findForObject("HzWorkCenterDAOImpl_selectByPrimaryKey",map);
    }


    @Override
    public HzWorkCenter findWorkCenter(String projectId, String pWorkCode) {
        Map<String,Object> map = new HashMap<>();
        map.put("pWorkCode",pWorkCode);
        map.put("projectId",projectId);
        return (HzWorkCenter)super.findForObject("",map);
    }


}
