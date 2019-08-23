package com.connor.hozon.resources.mybatis.resourcesLibrary.legislativeLibrary.impl;

import cn.net.connor.hozon.dao.pojo.depository.legislativeLibrary.HzLegislativeAutoType;
import com.connor.hozon.resources.domain.query.HzLegislativeAutoTypeQuery;
import com.connor.hozon.resources.mybatis.resourcesLibrary.legislativeLibrary.HzLegislativeAutoTypeDao;
import com.connor.hozon.resources.page.Page;
import com.connor.hozon.resources.page.PageRequestParam;
import org.springframework.stereotype.Service;
import sql.BaseSQLUtil;

import java.util.HashMap;
import java.util.Map;

@Service("HzLegislativeAutoTypeDao")
public class HzLegislativeAutoTypeDaoImpl extends BaseSQLUtil implements HzLegislativeAutoTypeDao {
    @Override
    public Page<HzLegislativeAutoType> findAutoTypeToPage(HzLegislativeAutoTypeQuery query) {
        PageRequestParam pageRequestParam = new PageRequestParam();
        Map map = new HashMap();
        map.put("autoType",query.getAutoType());
        map.put("noticeNo",query.getNoticeNo());
        map.put("vinNo",query.getVinNo());
        pageRequestParam.setPageNumber(query.getPage());
        pageRequestParam.setPageSize(query.getPageSize());
        pageRequestParam.setFilters(map);
        return super.findPage("HzLegislativeAutoTypeDaoImpl_select","HzLegislativeAutoTypeDaoImpl_count", pageRequestParam);
    }

    @Override
    public int deleteByPrimaryKey(String puid) {
        return 0;
    }

    @Override
    public int insert(HzLegislativeAutoType record) {
        return 0;
    }

    @Override
    public int insertSelective(HzLegislativeAutoType record) {
        return 0;
    }

    @Override
    public HzLegislativeAutoType selectByPrimaryKey(String puid) {
        return null;
    }

    @Override
    public int updateByPrimaryKeySelective(HzLegislativeAutoType record) {
        return 0;
    }

    @Override
    public int updateByPrimaryKey(HzLegislativeAutoType record) {
        return 0;
    }
}
