/*
 * Copyright (c) 2018.
 * This file was wrote by fancyears·milos·maywas @connor. Any question/bug you can post to 1243093366@qq.com.
 * ALL RIGHTS RESERVED.
 */

package sql.pojo.task;

import lombok.Data;
@Data
public class HzTasks {
    /**
     *主键
     */
    private Long id;
    /**
     *关联用户的ID，用于通知用户该任务
     */
    private Long taskUserId;
    /**
     *任务状态,0：草稿状态，不需要进行通知与执行；1：执行阶段，需要当前用户去执行;800:用户需要跟踪该任务;999:用户已执行完成
     */
    private Integer taskStatus;
    /**
     *表单ID，该ID存在于mysql中，需要从前端中的div获取到该ID，根据该ID可获取到表单的url
     */
    private Long taskFormId;
    /**
     *表单类型，1:VWO表单；2:EWO表单；3:MWO表单
     */
    private Integer taskFormType;
    /**
     *表单的ID，都存放VWO、EWO和MWO表单的主键
     */
    private Long taskTargetId;
    /**
     *目标表单类型，如：1：特性变更表单，2:配色方案变更，3：配置物料特性数据变更，4：全配置BOM一级清单变更，5：相关性变更等
     */
    private Integer taskTargetType;
    /**
     *保留字段
     */
    private String taskReserve;

    private String taskReserve2;

    private String taskReserve3;

    private String taskReserve4;

    private String taskReserve5;

    private String taskReserve6;

    private String taskReserve7;

    private String taskReserve8;

    private String taskReserve9;

    private String taskReserve10;

    private String taskReserve11;

    private String taskReserve12;

    private String taskReserve13;

    private String taskReserve14;

    private String taskReserve15;
}