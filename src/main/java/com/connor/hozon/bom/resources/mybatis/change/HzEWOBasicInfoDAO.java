package com.connor.hozon.bom.resources.mybatis.change;

import com.connor.hozon.bom.resources.query.HzEWOBasicInfoQuery;
import sql.pojo.change.HzEWOBasicInfo;

import java.util.List;

/**
 * @Author: haozt
 * @Date: 2018/8/8
 * @Description:
 */
public interface HzEWOBasicInfoDAO {
    int insert(HzEWOBasicInfo hzEWOBasicInfo);

    int update(HzEWOBasicInfo hzEWOBasicInfo);

    List<HzEWOBasicInfo> findHzEWOBasicInfoList(HzEWOBasicInfoQuery query);
}
