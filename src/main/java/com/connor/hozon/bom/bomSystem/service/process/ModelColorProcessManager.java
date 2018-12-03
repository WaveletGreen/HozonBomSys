/*
 * Copyright (c) 2018.
 * This file was written by fancyears·milos·malvis @connor. Any question/bug you can't post to 1243093366@qq.com.
 * ALL RIGHTS RESERVED.
 */

package com.connor.hozon.bom.bomSystem.service.process;

import com.connor.hozon.bom.bomSystem.iservice.process.IFunctionDesc;
import com.connor.hozon.bom.bomSystem.iservice.process.IInterruptionCallBack;
import com.connor.hozon.bom.bomSystem.iservice.process.IReleaseCallBack;
import com.connor.hozon.bom.bomSystem.service.modelColor.HzCfg0ModelColorService;
import com.connor.hozon.bom.bomSystem.service.vwo.HzVwoInfoService;
import com.connor.hozon.bom.bomSystem.service.vwo.HzVwoManagerService;
import com.connor.hozon.bom.common.util.user.UserInfo;
import com.connor.hozon.bom.sys.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import sql.pojo.cfg.modelColor.HzCfg0ModelColor;
import sql.pojo.cfg.modelColor.HzCmcrChange;
import sql.pojo.cfg.modelColor.HzCmcrDetailChange;
import sql.pojo.cfg.vwo.HzVwoInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: Fancyears·Maylos·Malvis
 * @Description: fuck
 * @Date: Created in 2018/10/16 13:55
 * @Modified By:
 */
@Configuration
public class ModelColorProcessManager implements IInterruptionCallBack, IReleaseCallBack, IFunctionDesc {

    @Autowired
    HzVwoInfoService hzVwoInfoService;

    @Autowired
    HzCfg0ModelColorService hzCfg0ModelColorService;

    /**
     * 中断操作
     * 将src主数据的状态设置为草稿状态(0)，清除vwo号
     *
     * @param vwoId  vwo变更流程ID
     * @param params 项目UID
     * @return
     */
    @Override
    public boolean interrupt(Long vwoId, Object... params) {
        interruptionFunctionDesc();
        return updateModelColorAndVwoInfo("0", 899, vwoId);
    }

    /**
     * 数据发布
     * 将src主数据的状态设置为发布状态(99)，清除vwo号
     *
     * @param vwoId  vwo流程编号ID
     * @param params 项目UID
     * @return
     */
    @Override
    public boolean release(Long vwoId, Object... params) {
        releaseFunctionDesc();
        return updateModelColorAndVwoInfo("999", 999, vwoId);
    }


    @Override
    public void interruptionFunctionDesc() {
        System.out.println("执行的是" + this.getClass().getCanonicalName() + ".interruption回调");
    }

    @Override
    public void releaseFunctionDesc() {
        System.out.println("执行的是" + this.getClass().getCanonicalName() + ".release回调");
    }

    /**
     * 根据VWOid修改配色方案的cmcrStatus和VWO流程中的vwoStatus
     *
     * @param cmcrStatus
     * @param vwoStatus
     * @param vwoId
     * @return
     */
    public boolean updateModelColorAndVwoInfo(String cmcrStatus, Integer vwoStatus, Long vwoId) {
        User user = UserInfo.getUser();
        HzVwoInfo info = hzVwoInfoService.doSelectByPrimaryKey(vwoId);
        if (info.getVwoStatus() == 999 || info.getVwoStatus() == 899) {
            return false;
        }
        HzCfg0ModelColor hzCfg0ModelColor = new HzCfg0ModelColor();
        hzCfg0ModelColor.setCmcrVwoId(info.getId());
        hzCfg0ModelColor.setCmcrStatus(cmcrStatus);
        boolean modelColorFlag = hzCfg0ModelColorService.doRelease(hzCfg0ModelColor);
        info.setVwoFinisher(user.getLogin());
        info.setVwoStatus(vwoStatus);
        boolean vwoFlag = hzVwoInfoService.updateByVwoId(info);
        return modelColorFlag && vwoFlag;
    }


}
