package com.connor.hozon.bom.resources.mybatis.resourcesLibrary.VPPSLibrary.impl;

import com.connor.hozon.bom.resources.domain.query.HzVPPSLibraryQuery;
import com.connor.hozon.bom.resources.mybatis.resourcesLibrary.VPPSLibrary.HzVPPSLibraryDao;
import com.connor.hozon.bom.resources.page.Page;
import com.connor.hozon.bom.resources.page.PageRequest;
import org.springframework.stereotype.Service;
import sql.BaseSQLUtil;
import sql.pojo.resourcesLibrary.VPPSLibrary.HzVPPSLibrary;

import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: xlf
 * Date: 2018/9/4
 * Time: 17:11
 */
@Service("HzVPPSLibraryDao")
public class HzVPPSLibraryDaoImpl extends BaseSQLUtil implements HzVPPSLibraryDao {
    /**
     * 分页获取VPPS库数据
     * @param query
     * @return
     */
    @Override
    public Page findVPPSLibraryToPage(HzVPPSLibraryQuery query){
        PageRequest pageRequest = new PageRequest();
        Map map = new HashMap();
        map.put("vppsLevel",query.getVppsLevel());
        map.put("vsgCode",query.getVsgCode());
        map.put("vppsCode",query.getVppsCode());
        map.put("upc",query.getUpc());
        map.put("fna",query.getFna());
        map.put("standardPartCode",query.getStandardPartCode());
        pageRequest.setPageNumber(query.getPage());
        pageRequest.setPageSize(query.getPageSize());
        pageRequest.setFilters(map);
        return super.findPage("HzVPPSLibraryDaoImpl_select","HzVPPSLibraryDaoImpl_count",pageRequest);
    }

    /**
     * 添加一条数据
     * @param library
     * @return
     */
    @Override
    public int insert(HzVPPSLibrary library) {
        return super.insert("HzVPPSLibraryDaoImpl_insert",library);
    }

    /**
     * 查询一条数据
     * @param puid
     * @return
     */
    @Override
    public HzVPPSLibrary findVPPSLibrary(String puid) {
        return (HzVPPSLibrary) super.findForObject("HzVPPSLibraryDaoImpl_findById",puid);
    }

    /**
     * 编辑一条数据
     * @param library
     * @return
     */
    @Override
    public int update(HzVPPSLibrary library) {
        return super.update("HzVPPSLibraryDaoImpl_update",library);
    }

    /**
     * 删除一条数据
     * @param puid
     * @return
     */
    @Override
    public int delete(String puid) {
        return super.delete("HzVPPSLibraryDaoImpl_delete",puid);
    }

    @Override
    public int findVPPSLibraryOrCodeToCount(String vppsCode) {
        return (Integer) super.findForObject("HzVPPSLibraryDaoImpl_findByCodeOrCount",vppsCode);
    }

    @Override
    public HzVPPSLibrary findVPPSLibraryOrCode(String vppsCode) {
        return (HzVPPSLibrary) super.findForObject("HzVPPSLibraryDaoImpl_findByCode",vppsCode);
    }
}
