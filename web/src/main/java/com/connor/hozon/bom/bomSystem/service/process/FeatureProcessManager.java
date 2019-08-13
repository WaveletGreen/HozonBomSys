/*
 * Copyright (c) 2018.
 * This file was written by fancyears·milos·malvis @connor. Any question/bug you can't post to 1243093366@qq.com.
 * ALL RIGHTS RESERVED.
 */

package com.connor.hozon.bom.bomSystem.service.process;

import cn.net.connor.hozon.dao.dao.configuration.feature.HzFeatureValueDao;
import com.connor.hozon.bom.bomSystem.iservice.process.IFunctionDesc;
import com.connor.hozon.bom.bomSystem.iservice.process.IInterruptionCallBack;
import com.connor.hozon.bom.bomSystem.iservice.process.IReleaseCallBack;
import com.connor.hozon.bom.bomSystem.service.vwo.HzVwoInfoServiceImpl;
import com.connor.hozon.bom.common.util.user.UserInfo;
import cn.net.connor.hozon.dao.pojo.sys.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import cn.net.connor.hozon.dao.pojo.configuration.feature.HzFeatureValue;
import cn.net.connor.hozon.dao.pojo.change.vwo.HzVwoInfo;

/**
 * @Author: Fancyears·Maylos·Malvis
 * @Description:
 * @Date: Created in 2018/10/16 13:28
 * @Modified By:
 */
@Configuration
public class FeatureProcessManager implements IInterruptionCallBack, IReleaseCallBack, IFunctionDesc {

    @Autowired
    private HzVwoInfoServiceImpl hzVwoInfoServiceImpl;
    @Autowired
    private HzFeatureValueDao hzFeatureValueDao;
    /**
     * 中断操作
     *
     * @param vwoId      vwo变更流程ID
     * @param params 项目UID
     * @return
     */
    @Override
    public boolean interrupt(Long vwoId, Object...params) {
        interruptionFunctionDesc();
        return updateFeatureAndVwoInfo(0,899,vwoId);
    }

    /**
     * 数据发布
     *
     * @param vwoId      vwo流程编号ID
     * @param params 项目UID
     * @return
     */
    @Override
    public boolean release(Long vwoId, Object...params) {
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
        HzVwoInfo info = hzVwoInfoServiceImpl.doSelectByPrimaryKey(vwoId);
        if (info.getVwoStatus() == 999 || info.getVwoStatus() == 899) {
            return false;
        }
        HzFeatureValue hzFeatureValue = new HzFeatureValue();
        hzFeatureValue.setVwoId(info.getId());
        hzFeatureValue.setCfgStatus(cfgStatus);
        hzFeatureValue.setCfgIsInProcess(0);
        if(hzFeatureValueDao.updateByVwoid(hzFeatureValue)>0){
            flag = true;
        }else {
            return flag;
        }
        info.setVwoFinisher(user.getLogin());
        info.setVwoStatus(vwoStatus);
        boolean vwoFlag = hzVwoInfoServiceImpl.updateByVwoId(info);
        return  flag&&vwoFlag;
    }
}
