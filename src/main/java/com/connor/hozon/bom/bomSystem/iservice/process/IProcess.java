/*
 * Copyright (c) 2018.
 * This file was written by fancyears·milos·malvis @connor. Any question/bug you can post to 1243093366@qq.com.
 * ALL RIGHTS RESERVED.
 */

package com.connor.hozon.bom.bomSystem.iservice.process;

/**
 * @Author: Fancyears·Maylos·Malvis
 * @Description: fuck
 * @Date: Created in 2018/9/6 13:19
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
     * @param params
     * @param vwoId
     * @return
     */
    boolean execute(Long vwoId, Object ... params);
}
