package com.connor.hozon.bom.resources.mybatis.accessories.impl;

import com.connor.hozon.bom.resources.domain.dto.request.DeleteHzAccessoriesDTO;
import com.connor.hozon.bom.resources.domain.query.HzAccessoriesPageQuery;
import com.connor.hozon.bom.resources.mybatis.accessories.HzAccessoriesDAO;
import com.connor.hozon.bom.resources.page.Page;
import com.connor.hozon.bom.resources.page.PageRequestParam;
import org.springframework.stereotype.Service;
import sql.BaseSQLUtil;
import cn.net.connor.hozon.dao.pojo.depository.accessories.HzAccessoriesLib;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: haozt
 * @Date: 2018/7/16
 * @Description: 废除 已不再使用
 * 功能移至 {@link HzAccessoriesLibsDAOImpl}
 */
@Deprecated
@Service("hzAccessoriesDAO")
public class HzAccessoriesDAOImpl  extends BaseSQLUtil implements HzAccessoriesDAO {
    @Override
    public int insert(HzAccessoriesLib accessoriesLib) {
        return super.insert("HzAccessoriesDAOImpl_insert",accessoriesLib);
    }

    @Override
    public int update(HzAccessoriesLib hzAccessoriesLib) {
        return super.update("HzAccessoriesDAOImpl_update",hzAccessoriesLib);
    }

    @Override
    public int deleteList(List<DeleteHzAccessoriesDTO> libs) {
        return super.update("HzAccessoriesDAOImpl_deleteList",libs);
    }

    @Override
    public List<HzAccessoriesLib> getHzAccessoriesLibs(String puid) {
        Map<String,Object> map = new HashMap<>();
        map.put("puid",puid);
        return super.findForList("HzAccessoriesDAOImpl_getHzAccessoriesLibs",map);
    }

    @Override
    public Page<HzAccessoriesLib> getHzAccessoriesByPage(HzAccessoriesPageQuery query) {
        PageRequestParam request = new PageRequestParam();
        Map map = new HashMap();
        request.setPageNumber(query.getPage());
        request.setPageSize(query.getPageSize());
        request.setFilters(map);
        return super.findPage("HzAccessoriesDAOImpl_getHzAccessoriesByPage","HzAccessoriesDAOImpl_getHzAccessoriesTotalCount",request);
    }

}
