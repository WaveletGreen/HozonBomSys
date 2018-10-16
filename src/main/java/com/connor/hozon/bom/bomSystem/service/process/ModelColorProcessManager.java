/*
 * Copyright (c) 2018.
 * This file was wrote by fancyears·milos·maywas @connor. Any question/bug you can post to 1243093366@qq.com.
 * ALL RIGHTS RESERVED.
 */

package com.connor.hozon.bom.bomSystem.service.process;

import com.connor.hozon.bom.bomSystem.iservice.process.IFunctionDesc;
import com.connor.hozon.bom.bomSystem.iservice.process.IInterruptionCallBack;
import com.connor.hozon.bom.bomSystem.iservice.process.IReleaseCallBack;
import com.connor.hozon.bom.bomSystem.service.modelColor.HzCfg0ModelColorService;
import com.connor.hozon.bom.bomSystem.service.vwo.HzVwoInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import sql.pojo.cfg.vwo.HzVwoInfo;

/**
 * @Author: Fancyears·Maylos·Maywas
 * @Description:
 * @Date: Created in 2018/10/16 13:55
 * @Modified By:
 */
@Configuration
public class ModelColorProcessManager implements IInterruptionCallBack, IReleaseCallBack, IFunctionDesc {

    @Autowired
    HzVwoInfoService hzVwoInfoService;

    /**
     * 中断操作
     * 将src主数据的状态设置为草稿状态(0)，清除vwo号
     *
     * @param vwoId      vwo变更流程ID
     * @param projectUId 项目UID
     * @return
     */
    @Override
    public boolean interrupt(Long vwoId, String projectUId) {
        interruptionFunctionDesc();
        HzVwoInfo info = hzVwoInfoService.doSelectByPrimaryKey(vwoId);
        return false;
    }

    /**
     * 数据发布
     * 将src主数据的状态设置为发布状态(99)，清除vwo号
     *
     * @param vwoId      vwo流程编号ID
     * @param projectUId 项目UID
     * @return
     */
    @Override
    public boolean release(Long vwoId, String projectUId) {
        releaseFunctionDesc();
        HzVwoInfo info = hzVwoInfoService.doSelectByPrimaryKey(vwoId);
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
}
