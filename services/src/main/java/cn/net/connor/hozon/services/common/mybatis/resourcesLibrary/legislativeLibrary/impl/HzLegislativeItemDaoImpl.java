package com.connor.hozon.bom.resources.mybatis.resourcesLibrary.legislativeLibrary.impl;

import cn.net.connor.hozon.dao.pojo.depository.legislativeLibrary.HzLegislativeItem;
import com.connor.hozon.bom.resources.domain.query.HzLegislativeItemQuery;
import com.connor.hozon.bom.resources.mybatis.resourcesLibrary.legislativeLibrary.HzLegislativeItemDao;
import com.connor.hozon.bom.resources.page.Page;
import com.connor.hozon.bom.resources.page.PageRequestParam;
import org.springframework.stereotype.Service;
import sql.BaseSQLUtil;

import java.util.HashMap;
import java.util.Map;

@Service("HzLegislativeItemDao")
public class HzLegislativeItemDaoImpl extends BaseSQLUtil implements HzLegislativeItemDao {

    /**
     * 分页查询法规件信息
     * @param query
     * @return
     */
    @Override
    public Page<HzLegislativeItem> findItemToPage(HzLegislativeItemQuery query) {
        PageRequestParam pageRequestParam = new PageRequestParam();
        Map map = new HashMap();
        map.put("noticeNo",query.getNoticeNo());
        map.put("legislativeNo",query.getLegislativeNo());
        map.put("applicableModels",query.getApplicableModels());
        pageRequestParam.setPageNumber(query.getPage());
        pageRequestParam.setPageSize(query.getPageSize());
        pageRequestParam.setFilters(map);
        return super.findPage("HzLegislativeItemDaoImpl_select","HzLegislativeItemDaoImpl_count", pageRequestParam);
    }

    @Override
    public int deleteByPrimaryKey(String puid) {
        return 0;
    }

    /**
     * 插入一条法规件
     * @param record
     * @return
     */
    @Override
    public int insert(HzLegislativeItem record) {
        return super.insert("HzLegislativeItem_insert",record);
    }

    @Override
    public int insertSelective(HzLegislativeItem record) {
        return 0;
    }


    /**
     * 根据legislativeNo查询法规件信息
     * @param query
     * @return
     */
    @Override
    public HzLegislativeItem selectBylegislativeNo(HzLegislativeItemQuery query) {
        Map<String,Object> map = new HashMap<>();
        map.put("legislativeNo",query.getLegislativeNo());
        return (HzLegislativeItem)super.findForObject("HzLegislativeItemDaoImpl_selectByLegislativeNo",map);
    }

    /**
     * 根据legislativeNo查询数量
     * @param legislativeNo
     * @return
     */
    @Override
    public int selectCountBylegislativeNo(String legislativeNo) {

        return (int)super.findForObject("HzLegislativeItemDaoImpl_selectByLegislativeNo_count",legislativeNo);
    }


    @Override
    public HzLegislativeItem selectByPrimaryKey(String puid) {
        return null;
    }

    @Override
    public int updateByPrimaryKeySelective(HzLegislativeItem record) {
        return 0;
    }

    @Override
    public int updateByPrimaryKey(HzLegislativeItem record) {
        return 0;
    }
}
