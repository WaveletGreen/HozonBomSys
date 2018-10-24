/*
 * Copyright (c) 2018.
 * This file was wrote by fancyears·milos·maywas @connor. Any question/bug you can post to 1243093366@qq.com.
 * ALL RIGHTS RESERVED.
 */

package com.connor.hozon.bom.bomSystem.service.process;

import com.connor.hozon.bom.bomSystem.iservice.process.IFunctionDesc;
import com.connor.hozon.bom.bomSystem.iservice.process.IInterruptionCallBack;
import com.connor.hozon.bom.bomSystem.iservice.process.IReleaseCallBack;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: Fancyears·Maylos·Maywas
 * @Description: x
 * @Date: Created in 2018/10/16 13:28
 * @Modified By:
 */
@Configuration
public class FeatureProcessManager implements IInterruptionCallBack, IReleaseCallBack, IFunctionDesc {
    /**
     * 中断操作
     *
     * @param vwoId      vwo变更流程ID
     * @param projectUId 项目UID
     * @return
     */
    @Override
    public boolean interrupt(Long vwoId, String projectUId) {
        interruptionFunctionDesc();
        return false;
    }


    /**
     * 数据发布
     *
     * @param vwoId      vwo流程编号ID
     * @param projectUId 项目UID
     * @return
     */
    @Override
    public boolean release(Long vwoId, String projectUId) {
        releaseFunctionDesc();
        return false;
    }

    @Override
    public void interruptionFunctionDesc() {
        System.out.println("执行的是" + this.getClass().getCanonicalName() + ".interruption回调");
    }

    @Override
    public void releaseFunctionDesc() {
        System.out.println("执行的是" + this.getClass().getCanonicalName() + ".release回调");
    }


    public static void main(String[] args) {
        ReleaseContainer back = new ReleaseContainer();
        back.setCallBackEntity(new ModelColorProcessManager());
        back.execute(123L, "dasd");
    }
}
