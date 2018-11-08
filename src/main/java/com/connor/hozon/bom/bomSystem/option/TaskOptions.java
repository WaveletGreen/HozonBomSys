/*
 * Copyright (c) 2018.
 * This file was wrote by fancyears·milos·malvis @connor. Any question/bug you can post to 1243093366@qq.com.
 * ALL RIGHTS RESERVED.
 */

package com.connor.hozon.bom.bomSystem.option;

/**
 * @Author: Fancyears·Maylos·Maywas
 * @Description: fuck
 * @Date: Created in 2018/9/6 13:19
 * @Modified By:
 */
public class TaskOptions {
    /**
     * VWO表单
     */
    public static final int FORM_TYPE_VWO = 1;
    /**
     * VWO表单
     */
    public static final int FORM_TYPE_EWO = 2;
    /**
     * VWO表单
     */
    public static final int FORM_TYPE_MWO = 3;
    /**
     * 任务状态：草稿状态，不需要通知，甚至都不需要保存
     */
    public static final int TASK_STATUS_DRAFT = 0;
    /**
     * 任务状态：执行阶段，需要通知到任务栏中
     */
    public static final int TASK_STATUS_EXECUTING = 1;
    /**
     * 任务状态:跟踪阶段，需要通知，但是没说要放在通知栏中
     */
    public static final int TASK_STATUS_TRACKING = 800;
    /**
     * 任务状态：被动完成状态，非当前用户完成，受制于其他用户进行终止或者完成造成
     */
    public static final int TASK_STATUS_BE_FINISHED = 998;
    /**
     * 任务状态：完成阶段，不需要通知
     */
    public static final int TASK_STATUS_FINISHED = 999;
}
