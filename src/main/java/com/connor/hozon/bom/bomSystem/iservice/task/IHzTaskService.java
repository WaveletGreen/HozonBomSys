/*
 * Copyright (c) 2018.
 * This file was wrote by fancyears·milos·maywas @connor. Any question/bug you can post to 1243093366@qq.com.
 * ALL RIGHTS RESERVED.
 */

package com.connor.hozon.bom.bomSystem.iservice.task;

import com.connor.hozon.bom.bomSystem.dto.task.TaskReceivedDto;
import org.springframework.context.annotation.Configuration;
import sql.pojo.task.HzTasks;

import java.util.List;

/**
 * @Author: Fancyears·Maylos·Maywas
 * @Description: fuck
 * @Date: Created in 2018/9/6 13:19
 * @Modified By:
 */
@Configuration
public interface IHzTaskService {
    /**
     * 发起流程之后，保存task
     *
     * @return
     */
    boolean saveTask(TaskReceivedDto dto);

    /**
     * 用户跟踪的任务
     *
     * @param userId
     * @return
     */
    List<HzTasks> doSelectUserTrackingTasks(Long userId);

    /**
     * 用户需要执行的任务
     *
     * @param userId
     * @return
     */
    List<HzTasks> doSelectUserExecutingTasks(Long userId);

    /**
     * 用户已完成的任务
     *
     * @param userId
     * @return
     */
    List<HzTasks> doSelectUserFinishTasks(Long userId);

    /**
     * 根据表单的ID、用户ID、具体的表单类型和变更主键，查找一组当前用户的任务
     *
     * @param taskFormType   表单类型,@see{com.connor.hozon.bom.bomSystem.option.TaskOptions}
     * @param taskTargetId   目标表单ID，数据库主键，VWO/EWO/MWO主键
     * @param taskUserId     用户ID
     * @param taskTargetType 表单类型，@see {com.connor.hozon.bom.bomSystem.option.TaskOptions}
     * @param status         任务状态
     * @return
     */
    List<HzTasks> doSelectUserTargetTaskByType(Integer taskFormType, Integer taskTargetType, Long taskTargetId, Long taskUserId, Integer status);

    /**
     * 根据VWO主键和用户主键，获取到当前VWO变更表单该用户的任务
     *
     * @param vwoId      VWO ID
     * @param userId     用户ID
     * @param targetType 变更类型，针对VWO来说：1:特性变更；2：配色方案；3：配置物料特性表；4：全配置BOM一级清单；5：相关性
     * @return
     */
    List<HzTasks> doSelectUserVWOTaskByIds(Long vwoId, Long userId, Integer targetType);

    /**
     * 批量插入任务
     *
     * @param list
     * @return
     */
    boolean doInsertByBatch(List<HzTasks> list);

    boolean doInsert(HzTasks task);

    boolean doUpdateByPrimaryKeySelective(HzTasks task);

    boolean doStopTask(int formTypeVwo, Integer vwoType, Long id);
}
