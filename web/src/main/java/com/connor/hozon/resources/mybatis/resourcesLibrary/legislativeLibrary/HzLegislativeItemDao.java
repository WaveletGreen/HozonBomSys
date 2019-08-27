package com.connor.hozon.resources.mybatis.resourcesLibrary.legislativeLibrary;

import cn.net.connor.hozon.dao.pojo.depository.legislativeLibrary.HzLegislativeItem;
import com.connor.hozon.resources.domain.query.HzLegislativeItemQuery;
import com.connor.hozon.resources.page.Page;

import java.util.List;

public interface HzLegislativeItemDao {

    /**
     * 分页查询法规件信息
     * @param query
     * @return
     */
    Page<HzLegislativeItem> findItemToPage(HzLegislativeItemQuery query);

    int delete(String puids);


    /**
     * 插入一条法规件Item
     * @param record
     * @return
     */
    int insertItem(HzLegislativeItem record);

    /**
     * 插入一条法规件
     * @param record
     * @return
     */
    int insertLeg(HzLegislativeItem record);

    int insertSelective(HzLegislativeItem record);

    /**
     * 根据legislativeNo查询法规件信息
     * @param legislativeNo
     * @return
     */
    List<HzLegislativeItem> selectBylegislativeNo(String legislativeNo);

    /**
     * 根据legislativeNo查询数量
     * @param legislativeNo
     * @return
     */
    int selectCountBylegislativeNo(String legislativeNo);

    HzLegislativeItem selectByPuid(String puid);

    /**
     * 修改一条数据
     * @param record
     * @return
     */
    int updateHzLegislative(HzLegislativeItem record);

    int updateByPrimaryKey(HzLegislativeItem record);
}