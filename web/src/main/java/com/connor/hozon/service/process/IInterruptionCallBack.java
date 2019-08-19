/*
 *
 *  * Copyright (c) 2019 Author: Fancyears Milos Malvis @1243093366@qq.com
 *  * ALL COPYRIGHT REVERSED.
 *
 */

package com.connor.hozon.service.process;

/**
 * @Author: Fancyears·Maylos·Malvis
 * @Description: 中断回调
 * @Date: Created in 2018/9/6 13:19
 * @Modified By:
 */
public interface IInterruptionCallBack {
    /**
     * 中断操作
     *
     * @param formId vwo变更流程ID
     * @param params 项目UID
     * @return
     */
    boolean interrupt(Long formId,Object ... params);
}
