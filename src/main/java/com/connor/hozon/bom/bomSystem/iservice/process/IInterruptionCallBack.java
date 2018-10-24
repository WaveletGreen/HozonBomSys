/*
 * Copyright (c) 2018.
 * This file was wrote by fancyears·milos·maywas @connor. Any question/bug you can post to 1243093366@qq.com.
 * ALL RIGHTS RESERVED.
 */

package com.connor.hozon.bom.bomSystem.iservice.process;

/**
 * @Author: Fancyears·Maylos·Maywas
 * @Description: 流程中断回调 x
 * @Date: Created in 2018/10/16 13:26
 * @Modified By:
 */
public interface IInterruptionCallBack {
    /**
     * 中断操作
     *
     * @param vwoId vwo变更流程ID
     * @param projectUId 项目UID
     * @return
     */
    boolean interrupt(Long vwoId, String projectUId);

}
