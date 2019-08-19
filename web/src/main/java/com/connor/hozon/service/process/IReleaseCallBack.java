/*
 *
 *  * Copyright (c) 2019 Author: Fancyears Milos Malvis @1243093366@qq.com
 *  * ALL COPYRIGHT REVERSED.
 *
 */

package com.connor.hozon.service.process;

/**
 * @Author: Fancyears·Maylos·Malvis
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
