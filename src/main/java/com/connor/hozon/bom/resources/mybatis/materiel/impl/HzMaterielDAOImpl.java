package com.connor.hozon.bom.resources.mybatis.materiel.impl;

import com.connor.hozon.bom.resources.mybatis.materiel.HzMaterielDAO;
import com.connor.hozon.bom.resources.page.Page;
import com.connor.hozon.bom.resources.page.PageRequest;
import com.connor.hozon.bom.resources.query.HzMaterielByPageQuery;
import com.connor.hozon.bom.resources.query.HzMaterielQuery;
import org.springframework.stereotype.Service;
import sql.BaseSQLUtil;
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
        return super.insert("HzMaterialDAOImpl_insert",hzMaterielRecord);
    }

    @Override
    public int update(HzMaterielRecord hzMaterielRecord) {
        return super.update("HzMaterialDAOImpl_update",hzMaterielRecord);
    }

    @Override
    public int delete(String puid) {
        return super.update("HzMaterialDAOImpl_delete",puid);
    }

    @Override
    public int insertList(List<HzMaterielRecord> hzMaterielRecord) {
        return 0;
    }

    @Override
    public Page<HzMaterielRecord> findHzMaterielForPage(HzMaterielByPageQuery query) {
        PageRequest pageRequest = new PageRequest();
        pageRequest.setPageNumber(query.getPage());
        pageRequest.setPageSize(query.getLimit());
        Map map = new HashMap<>();
        map.put("pPertainToProjectPuid",query.getProjectId());
        pageRequest.setFilters(map);
        return super.findPage("HzMaterialDAOImpl_findHzMaterielForPage","HzMaterialDAOImpl_findHzMaterielTotalCount",pageRequest);
    }

    @Override
    public List<HzMaterielRecord> findHzMaterielForList(HzMaterielQuery query) {
        return super.findForList("",query);
    }
}
