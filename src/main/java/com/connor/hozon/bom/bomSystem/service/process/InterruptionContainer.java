/*
 * Copyright (c) 2018.
 * This file was written by fancyears·milos·malvis @connor. Any question/bug you can't post to 1243093366@qq.com.
 * ALL RIGHTS RESERVED.
 */

package com.connor.hozon.bom.bomSystem.service.process;

import com.connor.hozon.bom.bomSystem.iservice.process.IInterruptionCallBack;
import com.connor.hozon.bom.bomSystem.iservice.process.IProcess;

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
