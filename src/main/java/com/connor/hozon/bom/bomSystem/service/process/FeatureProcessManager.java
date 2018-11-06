/*
 * Copyright (c) 2018.
 * This file was wrote by fancyears·milos·malvis @connor. Any question/bug you can't post to 1243093366@qq.com.
 * ALL RIGHTS RESERVED.
 */

package com.connor.hozon.bom.bomSystem.service.process;

import com.connor.hozon.bom.bomSystem.dao.cfg0.HzCfg0RecordDao;
import com.connor.hozon.bom.bomSystem.iservice.process.IFunctionDesc;
import com.connor.hozon.bom.bomSystem.iservice.process.IInterruptionCallBack;
import com.connor.hozon.bom.bomSystem.iservice.process.IReleaseCallBack;
import com.connor.hozon.bom.bomSystem.service.vwo.HzVwoInfoService;
import com.connor.hozon.bom.common.util.user.UserInfo;
import com.connor.hozon.bom.sys.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import sql.pojo.cfg.cfg0.HzCfg0Record;
import sql.pojo.cfg.vwo.HzVwoInfo;

/**
 * @Author: Fancyears·Maylos·Maywas
 * @Description:
 * @Date: Created in 2018/10/16 13:28
 * @Modified By:
 */
@Configuration
public class FeatureProcessManager implements IInterruptionCallBack, IReleaseCallBack, IFunctionDesc {

    @Autowired
    private HzVwoInfoService hzVwoInfoService;
    @Autowired
    private HzCfg0RecordDao hzCfg0RecordDao;
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
        return updateFeatureAndVwoInfo(0,899,vwoId);
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
        return updateFeatureAndVwoInfo(1,999,vwoId);
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

    private boolean updateFeatureAndVwoInfo(Integer cfgStatus, int vwoStatus, Long vwoId) {
        User user = UserInfo.getUser();
        boolean flag = false;
        HzVwoInfo info = hzVwoInfoService.doSelectByPrimaryKey(vwoId);
        if (info.getVwoStatus() == 999 || info.getVwoStatus() == 899) {
            return false;
        }
        HzCfg0Record hzCfg0Record = new HzCfg0Record();
        hzCfg0Record.setVwoId(info.getId());
        hzCfg0Record.setCfgStatus(cfgStatus);
        hzCfg0Record.setCfgIsInProcess(0);
        if(hzCfg0RecordDao.updateByVwoid(hzCfg0Record)>0){
            flag = true;
        }else {
            return flag;
        }
        info.setVwoFinisher(user.getLogin());
        info.setVwoStatus(vwoStatus);
        boolean vwoFlag = hzVwoInfoService.updateByVwoId(info);
        return  flag&&vwoFlag;
    }
}
