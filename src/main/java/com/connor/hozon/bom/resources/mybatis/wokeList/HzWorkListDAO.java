package com.connor.hozon.bom.resources.mybatis.wokeList;

import sql.pojo.workList.HzWorkListRecord;

import java.util.List;
import java.util.Map;

public interface HzWorkListDAO {

    int insert(HzWorkListRecord record);

    int update(HzWorkListRecord record);

    List<HzWorkListRecord> findWorkList(Map<String,Object> map);


}
