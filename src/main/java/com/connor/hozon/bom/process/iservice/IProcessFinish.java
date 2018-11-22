/*
 * Copyright (c) 2018.
 * This file was written by fancyears·milos·malvis @connor. Any question/bug you can post to 1243093366@qq.com.
 * ALL RIGHTS RESERVED.
 */

package com.connor.hozon.bom.process.iservice;

import org.springframework.stereotype.Component;
/**
 * @Author: Fancyears·Maylos·Malvis
 * @Description:  流程结束接口
 * @Date: Created in  2018/11/22 11:15
 * @Modified By:
 */
@Component
public interface IProcessFinish {
    /**
     * 最后一步是修改任务状态和表单的状态
     *
     * @param orderId 表单的ID
     * @param params  不定参数，根据需要进行传参，然后再强转
     * @return
     */
    boolean doFinish(Long orderId, Object... params);
}
