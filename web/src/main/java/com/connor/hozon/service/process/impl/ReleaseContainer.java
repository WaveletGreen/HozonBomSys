/*
 *
 *  * Copyright (c) 2019 Author: Fancyears Milos Malvis @1243093366@qq.com
 *  * ALL COPYRIGHT REVERSED.
 *
 */

package com.connor.hozon.service.process.impl;

import com.connor.hozon.service.process.IProcess;
import com.connor.hozon.service.process.IReleaseCallBack;

/**
 * @Author: Fancyears·Maylos·Malvis
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
