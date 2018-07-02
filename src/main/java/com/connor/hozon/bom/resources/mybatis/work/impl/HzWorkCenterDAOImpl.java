package com.connor.hozon.bom.resources.mybatis.work.impl;

import com.connor.hozon.bom.resources.mybatis.work.HzWorkCenterDAO;
import com.connor.hozon.bom.resources.page.Page;
import com.connor.hozon.bom.resources.page.PageRequest;
import sql.BaseSQLUtil;
import sql.pojo.work.HzWorkCenter;

import java.util.Map;

/**
 * @Author: haozt
 * @Date: 2018/6/30
 * @Description:
 */
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
    public Page findWorkCenterForPage(Map<String, Object> map) {
        PageRequest pageRequest = new PageRequest();
        pageRequest.setFilters(map);
        return super.findForPage("HzWorkCenterDAOImpl_findWorkCenterForPage","HzWorkCenterDAOImpl_findWorkCenterTotalCount",pageRequest);
    }

    @Override
    public int delete(String puid) {

        return super.update("HzWorkCenterDAOImpl_delete",puid);
    }
}
