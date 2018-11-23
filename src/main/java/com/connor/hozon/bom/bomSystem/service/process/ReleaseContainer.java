/*
 * Copyright (c) 2018.
 * This file was wrote by fancyears·milos·malvis @connor. Any question/bug you can't post to 1243093366@qq.com.
 * ALL RIGHTS RESERVED.
 */

package com.connor.hozon.bom.bomSystem.service.process;

import com.connor.hozon.bom.bomSystem.iservice.process.IProcess;
import com.connor.hozon.bom.bomSystem.iservice.process.IReleaseCallBack;

/**
 * @Author: Fancyears·Maylos·Maywas
 * @Description: fuck
 * @Date: Created in 2018/10/16 13:44
 * @Modified By:
 */
public class ReleaseContainer implements IProcess {
    private IReleaseCallBack callBack;

    public ReleaseContainer() {
    }


    /**
     * 设置回调实体
     *
     * @param iProcess
     */
    @Override
    public void setCallBackEntity(Object iProcess) {
        callBack = (IReleaseCallBack) iProcess;
    }

    /**
     * 执行回调操作
     *
     * @param vwoId
     * @param params
     * @return
     */
    @Override
    public boolean execute(Long vwoId, Object...params) {
        return callBack.release(vwoId, params);
    }
}
