package com.connor.hozon.bom.resources.mybatis.wokeList;

import com.connor.hozon.bom.resources.domain.query.HzWorkListBasicInfoQuery;
import cn.net.connor.hozon.dao.pojo.workList.HzWorkListRecord;

import java.util.List;
import java.util.Map;

public interface HzWorkListDAO {

    int insert(HzWorkListRecord record);

    int update(HzWorkListRecord record);

    int count(String user,String projectId);

    List<HzWorkListRecord> findWorkList(Map<String,Object> map);

    List<HzWorkListRecord> findHzWorkListBasicInfoList(HzWorkListBasicInfoQuery query);

    List<HzWorkListRecord> findHzWorkListBasicInfoList1(HzWorkListBasicInfoQuery query);
    List<HzWorkListRecord> findHzWorkListBasicInfoList2(HzWorkListBasicInfoQuery query);
    List<HzWorkListRecord> findHzWorkListBasicInfoList3(HzWorkListBasicInfoQuery query);
}
