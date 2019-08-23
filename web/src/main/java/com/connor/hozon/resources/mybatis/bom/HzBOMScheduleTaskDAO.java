package com.connor.hozon.resources.mybatis.bom;

import cn.net.connor.hozon.dao.pojo.bom.bom.HzBOMScheduleTask;

import java.util.List;

/**
 * @Author: haozt
 * @Date: 2019/1/2
 * @Description: BOM 定时同步计划
 */
public interface HzBOMScheduleTaskDAO {

    int insert(HzBOMScheduleTask hzBOMScheduleTask);

    int updateByOrderId(HzBOMScheduleTask hzBOMScheduleTask);

    List<HzBOMScheduleTask> getBOMScheduleTask(Long orderId);

    List<HzBOMScheduleTask> getNoSynchronizedBOMScheduleTask();
}
