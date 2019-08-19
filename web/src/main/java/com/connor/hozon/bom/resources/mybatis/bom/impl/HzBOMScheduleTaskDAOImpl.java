/*
 *
 *  * Copyright (c) 2019 Author: Fancyears Milos Malvis @1243093366@qq.com
 *  * ALL COPYRIGHT REVERSED.
 *
 */

package com.connor.hozon.bom.resources.mybatis.bom.impl;

import com.connor.hozon.bom.resources.mybatis.bom.HzBOMScheduleTaskDAO;
import org.springframework.stereotype.Service;
import sql.BaseSQLUtil;
import cn.net.connor.hozon.dao.pojo.bom.bom.HzBOMScheduleTask;

import java.util.List;

/**
 * @Author: haozt
 * @Date: 2019/1/2
 * @Description:
 */
@Service("hzBOMScheduleTaskDAO")
public class HzBOMScheduleTaskDAOImpl extends BaseSQLUtil implements HzBOMScheduleTaskDAO {
    @Override
    public int insert(HzBOMScheduleTask hzBOMScheduleTask) {
        return super.insert("HzBOMScheduleTaskDAOImpl_insert",hzBOMScheduleTask);
    }

    @Override
    public int updateByOrderId(HzBOMScheduleTask hzBOMScheduleTask) {
        return super.update("HzBOMScheduleTaskDAOImpl_updateByOrderId",hzBOMScheduleTask);
    }

    @Override
    public List<HzBOMScheduleTask> getBOMScheduleTask(Long orderId) {
        return super.findForList("HzBOMScheduleTaskDAOImpl_getBOMScheduleTask",orderId);
    }

    @Override
    public List<HzBOMScheduleTask> getNoSynchronizedBOMScheduleTask() {
        return super.findForList("HzBOMScheduleTaskDAOImpl_getNoSynchronizedBOMScheduleTask",null);
    }
}
