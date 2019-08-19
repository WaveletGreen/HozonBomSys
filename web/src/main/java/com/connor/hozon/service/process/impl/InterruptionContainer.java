/*
 *
 *  * Copyright (c) 2019 Author: Fancyears Milos Malvis @1243093366@qq.com
 *  * ALL COPYRIGHT REVERSED.
 *
 */

package com.connor.hozon.service.process.impl;

import com.connor.hozon.service.process.IInterruptionCallBack;
import com.connor.hozon.service.process.IProcess;

/**
 * @Author: Fancyears·Maylos·Malvis
 * @Description: fuck
 * @Date: Created in 2018/10/16 13:44
 * @Modified By:
 */
public class InterruptionContainer implements IProcess {

    private IInterruptionCallBack callBack;

    public InterruptionContainer() {
    }

    /**
     * 设置回调实体
     *
     * @param iProcess
     */
    @Override
    public void setCallBackEntity(Object iProcess) {
        this.callBack = (IInterruptionCallBack) iProcess;
    }

    /**
     * 执行回调操作
     *
     * @param vwoId
     * @param params
     * @return
     */
    @Override
    public boolean execute(Long vwoId, Object ... params) {
        return this.callBack.interrupt(vwoId, params);
    }
}
