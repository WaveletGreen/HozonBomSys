/*
 * Copyright (c) 2018.
 * This file was wrote by fancyears·milos·malvis @connor. Any question/bug you can post to 1243093366@qq.com.
 * ALL RIGHTS RESERVED.
 */

package com.connor.hozon.bom.bomSystem.iservice.process;

/**
 * @Author: Fancyears·Maylos·Maywas
 * @Description: 发布数据的回调接口
 * @Date: Created in 2018/9/6 13:19
 * @Modified By:
 */
public interface IReleaseCallBack {
    /**
     * 数据发布
     *
     * @param formId      vwo流程编号ID
     * @param params 项目UID
     * @return
     */
    boolean release(Long formId, Object ...params);

}
