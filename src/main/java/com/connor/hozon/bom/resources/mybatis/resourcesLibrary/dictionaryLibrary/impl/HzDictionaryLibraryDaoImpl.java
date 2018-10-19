package com.connor.hozon.bom.resources.mybatis.resourcesLibrary.dictionaryLibrary.impl;

import com.connor.hozon.bom.resources.domain.query.HzDictionaryLibraryQuery;
import com.connor.hozon.bom.resources.mybatis.resourcesLibrary.dictionaryLibrary.HzDictionaryLibraryDao;
import com.connor.hozon.bom.resources.page.Page;
import com.connor.hozon.bom.resources.page.PageRequestParam;
import org.springframework.stereotype.Service;
import sql.BaseSQLUtil;
import sql.pojo.resourcesLibrary.dictionaryLibrary.HzDictionaryLibrary;

import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: xlf
 * Date: 2018/8/29
 * Time: 16:10
 */

@Service("HzDictionaryLibraryDao")
public class HzDictionaryLibraryDaoImpl extends BaseSQLUtil implements HzDictionaryLibraryDao {

    @Override
    public int insert(HzDictionaryLibrary library) {
        return super.insert("HzDictionaryLibraryDaoImpl_insert",library);
    }

    @Override
    public int update(HzDictionaryLibrary library) {
        return super.update("HzDictionaryLibraryDaoImpl_update",library);
    }

    @Override
    public Page findDictionaryLibraryToPage(HzDictionaryLibraryQuery query) {
        PageRequestParam pageRequestParam = new PageRequestParam();
        Map map = new HashMap();
        map.put("groupCode",query.getGroupCode());
        map.put("groupCh",query.getGroupCh());
        map.put("famillyCode",query.getFamillyCode());
        map.put("famillyCh",query.getFamillyCh());
        map.put("eigenValue",query.getEigenValue());
        map.put("valueDescCh",query.getValueDescCh());
        pageRequestParam.setPageNumber(query.getPage());
        pageRequestParam.setPageSize(query.getPageSize());
        pageRequestParam.setFilters(map);
        return super.findPage("HzDictionaryLibraryDaoImpl_select","HzDictionaryLibraryDaoImpl_count", pageRequestParam);
    }

    @Override
    public HzDictionaryLibrary findDictionaryLibrary(String puid) {
        return (HzDictionaryLibrary) super.findForObject("HzDictionaryLibraryDaoImpl_findById",puid);
    }

    @Override
    public int delete(String puid) {
        return super.update("HzDictionaryLibraryDaoImpl_delete",puid);
    }

    @Override
    public int findDictionaryLibraryOrCodeToCount(String eigenValue) {
        return (Integer) super.findForObject("HzDictionaryLibraryDaoImpl_findByCodeOrCount", eigenValue);
    }

    @Override
    public HzDictionaryLibrary findDictionaryLibraryOrCode(String eigenValue) {
        return (HzDictionaryLibrary) super.findForObject("HzDictionaryLibraryDaoImpl_findByCode",eigenValue);
    }

}
