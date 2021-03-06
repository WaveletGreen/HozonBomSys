package com.connor.hozon.resources.mybatis.work.impl;

import com.connor.hozon.resources.domain.query.HzWorkByPageQuery;
import com.connor.hozon.resources.mybatis.work.HzWorkCenterDAO;
import com.connor.hozon.resources.page.Page;
import com.connor.hozon.resources.page.PageRequestParam;
import org.springframework.stereotype.Service;
import sql.BaseSQLUtil;
import cn.net.connor.hozon.dao.pojo.depository.work.HzWorkCenter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: haozt
 * @Date: 2018/6/30
 * @Description:
 */
@Service("hzWorkCenterDAO")
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
        PageRequestParam pageRequestParam = new PageRequestParam();
        Map map = new HashMap();
        pageRequestParam.setPageNumber(query.getPage());
        pageRequestParam.setPageSize(query.getPageSize());
        pageRequestParam.setFilters(map);
        return super.findPage("HzWorkCenterDAOImpl_findWorkCenterForPage","HzWorkCenterDAOImpl_findWorkCenterTotalCount", pageRequestParam);
    }

    @Override
    public int delete(String puid) {

        return super.update("HzWorkCenterDAOImpl_delete",puid);
    }

    @Override
    public HzWorkCenter findWorkCenterById(String puid) {
        Map<String,Object> map = new HashMap<>();
//        map.put("projectId",projectId);
        map.put("puid",puid);
        return (HzWorkCenter) super.findForObject("HzWorkCenterDAOImpl_selectByPrimaryKey",map);
    }


    @Override
    public List<HzWorkCenter> findWorkCenter(String pWorkCode) {
        Map<String,Object> map = new HashMap<>();
        map.put("pWorkCode",pWorkCode);
//        map.put("projectId",projectId);
        return super.findForList("HzWorkCenterDAOImpl_findWorkCenter",map);
    }


}
