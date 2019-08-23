package com.connor.hozon.resources.mybatis.resourcesLibrary.legislativeLibrary;

import cn.net.connor.hozon.dao.pojo.depository.legislativeLibrary.HzLegislativeAutoType;
import com.connor.hozon.resources.domain.query.HzLegislativeAutoTypeQuery;
import com.connor.hozon.resources.page.Page;

public interface HzLegislativeAutoTypeDao {

    Page<HzLegislativeAutoType> findAutoTypeToPage(HzLegislativeAutoTypeQuery query);

    int deleteByPrimaryKey(String puid);

    int insert(HzLegislativeAutoType record);

    int insertSelective(HzLegislativeAutoType record);

    HzLegislativeAutoType selectByPrimaryKey(String puid);

    int updateByPrimaryKeySelective(HzLegislativeAutoType record);

    int updateByPrimaryKey(HzLegislativeAutoType record);
}