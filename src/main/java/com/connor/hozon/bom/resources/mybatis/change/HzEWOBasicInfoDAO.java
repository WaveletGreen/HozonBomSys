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

    /**
     * 获取当前年月的EWO编号的最大后四位
     * @param projectId 项目id
     * @param ym 当前年月字符串
     * @return
     */
    String getMaxEWONoLastFourIndexInThisMonth(String projectId,String ym);
}
