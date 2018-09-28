package com.connor.hozon.bom.resources.mybatis.accessories.impl;

import com.connor.hozon.bom.resources.domain.dto.request.DeleteHzAccessoriesLibsDTO;
import com.connor.hozon.bom.resources.domain.query.HzAccessoriesLibsPageQuery;
import com.connor.hozon.bom.resources.mybatis.accessories.HzAccessoriesLibsDAO;
import com.connor.hozon.bom.resources.page.Page;
import com.connor.hozon.bom.resources.page.PageRequest;
import org.springframework.stereotype.Service;
import sql.BaseSQLUtil;
import sql.pojo.accessories.HzAccessoriesLibs;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: xlf
 * Date: 2018/9/27
 * Time: 15:19
 */
@Service("HzAccessoriesLibsDAO")
public class HzAccessoriesLibsDAOImpl  extends BaseSQLUtil implements HzAccessoriesLibsDAO {
    @Override
    public int insert(HzAccessoriesLibs accessoriesLibs) {
        return super.insert("HzAccessoriesLibsDAOImpl_insert",accessoriesLibs);
    }

    @Override
    public int update(HzAccessoriesLibs hzAccessoriesLibs) {
        return super.update("HzAccessoriesLibsDAOImpl_update",hzAccessoriesLibs);
    }

    @Override
    public int deleteList(List<DeleteHzAccessoriesLibsDTO> libs) {
        return super.delete("HzAccessoriesLibsDAOImpl_deleteList",libs);
    }

    @Override
    public List<HzAccessoriesLibs> getHzAccessoriesLibs(String puid) {
        Map<String,Object> map = new HashMap<>();
        map.put("puid",puid);
        return super.findForList("HzAccessoriesLibsDAOImpl_getHzAccessoriesLibs",puid);
    }

    @Override
    public Page<HzAccessoriesLibs> getHzAccessoriesByPage(HzAccessoriesLibsPageQuery query) {
        PageRequest request = new PageRequest();
        Map map = new HashMap();
        request.setPageNumber(query.getPage());
        request.setPageSize(query.getPageSize());
        request.setFilters(map);
        return super.findPage("HzAccessoriesLibsDAOImpl_getHzAccessoriesByPage","HzAccessoriesLibsDAOImpl_getHzAccessoriesTotalCount",request);
    }

    @Override
    public int selectHzAccessoriesLibsByCount(String pMaterielCode) {
        return (Integer)super.findForObject("HzAccessoriesLibsDAOImpl_findByCodeOrCount",pMaterielCode);
    }

    @Override
    public HzAccessoriesLibs getHzAccessoriesLibsByCode(String pMaterielCode) {
        return (HzAccessoriesLibs)super.findForObject("HzAccessoriesLibsDAOImpl_findByCode",pMaterielCode);
    }
}
