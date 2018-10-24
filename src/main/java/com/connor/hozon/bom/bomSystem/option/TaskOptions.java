/*
 * Copyright (c) 2018.
 * This file was wrote by fancyears·milos·maywas @connor. Any question/bug you can post to 1243093366@qq.com.
 * ALL RIGHTS RESERVED.
 */

package com.connor.hozon.bom.bomSystem.option;

/**
 * @Author: Fancyears·Maylos·Maywas
 * @Description:
 * @Date: Created in 2018/10/23 14:56
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
     * 任务状态：完成阶段，不需要通知
     */
    public static final int TASK_STATUS_FINISHED = 900;
}
