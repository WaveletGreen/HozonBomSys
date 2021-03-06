package com.connor.hozon.resources.mybatis.resourcesLibrary.legislativeLibrary.impl;

import cn.net.connor.hozon.common.util.ListUtils;
import cn.net.connor.hozon.dao.pojo.depository.legislativeLibrary.HzLegislativeItem;
import com.connor.hozon.resources.domain.query.HzLegislativeItemQuery;
import com.connor.hozon.resources.mybatis.resourcesLibrary.legislativeLibrary.HzLegislativeItemDao;
import com.connor.hozon.resources.page.Page;
import com.connor.hozon.resources.page.PageRequestParam;
import com.google.common.collect.Lists;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import sql.BaseSQLUtil;

import java.util.HashMap;
import java.util.List;
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

    /**
     * 批量删除
     * @param puids
     * @return
     */
    @Override
    public int delete(String puids) {
        if(StringUtils.isNotBlank(puids)){
            List<String> list = Lists.newArrayList(puids.split(","));
            return super.delete("HzLegislativeItem_delete",list);
        }else {
            return 0;
        }
    }

    /**
     * 批量删除法规件型号
     * @param puids
     * @return
     */
    @Override
    public int deleteLegislative(String puids) {
        if(StringUtils.isNotBlank(puids)){
            List<String> list = Lists.newArrayList(puids.split(","));
            return super.delete("HzLegislativeItemRecord_delete",list);
        }else {
            return 0;
        }
    }

    /**
     * 插入一条法规件Item
     * @param record
     * @return
     */
    @Override
    public int insertItem(HzLegislativeItem record) {
        return super.insert("HzLegislativeItem_insert",record);
    }

    /**
     * 插入一条法规件
     * @param record
     * @return
     */
    @Override
    public int insertLeg(HzLegislativeItem record) {
        return super.insert("HzLegislativeRecord_insert",record);
    }

    @Override
    public int insertSelective(HzLegislativeItem record) {
        return 0;
    }


    /**
     * 根据legislativeNo查询法规件信息
     * @param legislativeNo
     * @return
     */
    @Override
    public List<HzLegislativeItem> selectBylegislativeNo(String legislativeNo) {
//        Map<String,Object> map = new HashMap<>();
//        map.put("legislativeNo",legislativeNo);
        return super.findForList("HzLegislativeItemDaoImpl_selectByLegislativeNo",legislativeNo);
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

    /**
     * 根据puid查询法规件信息
     * @param puid
     * @return
     */
    @Override
    public HzLegislativeItem selectByPuid(String puid) {
        return (HzLegislativeItem)super.findForObject("HzLegislativeItemDaoImpl_selectByPuid",puid);

    }

    /**
     * 根据eplId查询法规件信息
     * @param eplId
     * @return
     */
    @Override
    public List<HzLegislativeItem> selectByEplId(String eplId) {
        return super.findForList("HzLegislativeItemDaoImpl_selectByEplId",eplId);

    }


    /**
     * 修改一条数据
     * @param record
     * @return
     */
    @Override
    public int updateHzLegislative(HzLegislativeItem record) {
        return super.insert("HzLegislativeItem_update",record);
    }

    @Override
    public int updateByPrimaryKey(HzLegislativeItem record) {
        return 0;
    }
}
