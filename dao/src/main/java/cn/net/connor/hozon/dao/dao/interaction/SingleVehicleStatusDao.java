/*
 *
 *  * Copyright (c) 2019 Author: Fancyears Milos Malvis @1243093366@qq.com
 *  * ALL COPYRIGHT REVERSED.
 *
 */

package cn.net.connor.hozon.dao.dao.interaction;

import cn.net.connor.hozon.dao.MyBatisBaseDao;
import cn.net.connor.hozon.dao.pojo.interaction.SingleVehicleStatus;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 单车状态dao层
 */
@Repository
public interface SingleVehicleStatusDao extends MyBatisBaseDao<SingleVehicleStatus,Long,SingleVehicleStatus> {
    /**
     * 根据项目ID进行批量删除
     * @param projectId
     * @return
     */
    int deleteByProjectId(String projectId);

    /**
     * 批量插入数据
     * @param statusList
     * @return
     */
    int insertList(List<SingleVehicleStatus> statusList);
}