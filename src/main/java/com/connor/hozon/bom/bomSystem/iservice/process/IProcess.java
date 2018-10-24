/*
 * Copyright (c) 2018.
 * This file was wrote by fancyears·milos·maywas @connor. Any question/bug you can post to 1243093366@qq.com.
 * ALL RIGHTS RESERVED.
 */

package com.connor.hozon.bom.bomSystem.iservice.process;

/**
 * @Author: Fancyears·Maylos·Maywas
 * @Description: x
 * @Date: Created in 2018/10/16 14:22
 * @Modified By:
 */
public interface IProcess {
    /**
     * 设置回调实体
     *
     * @param iProcess
     */
    void setCallBackEntity(Object iProcess);

    /**
     * 执行回调操作
     *
     * @param projectUId
     * @param vwoId
     * @return
     */
    boolean execute(Long vwoId, String projectUId);
}
