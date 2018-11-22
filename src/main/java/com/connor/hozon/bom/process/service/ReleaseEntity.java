/*
 * Copyright (c) 2018.
 * This file was written by fancyears·milos·malvis @connor. Any question/bug you can post to 1243093366@qq.com.
 * ALL RIGHTS RESERVED.
 */

package com.connor.hozon.bom.process.service;

import com.connor.hozon.bom.bomSystem.iservice.process.IFunctionDesc;
import com.connor.hozon.bom.bomSystem.iservice.process.IReleaseCallBack;
import com.connor.hozon.bom.process.iservice.IDataModifier;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Author: Fancyears·Maylos·Malvis
 * @Description: 流程通过，进行数据发布，这里写具体的修改数据的逻辑
 * @Date: Created in  2018/11/22 15:15
 * @Modified By:
 */
@Component
@EnableTransactionManagement(proxyTargetClass = true)
//要在事务的类中抛出RuntimeException异常，而不是抛出Exception，也不知道对不对
@Transactional(rollbackFor = {IllegalArgumentException.class, RuntimeException.class, Exception.class})
public class ReleaseEntity implements IReleaseCallBack, IFunctionDesc, IDataModifier {
    /***
     * 添加多个配置管理的service和BOM管理的service
     */
    @Override
    public void interruptionFunctionDesc() {

    }

    @Override
    public void releaseFunctionDesc() {
        System.out.println("执行的是" + this.getClass().getCanonicalName() + ".release回调");
    }

    /**
     * 发布数据，进行所有数据的统一操作
     *
     * @param orderId 变更表单的ID
     * @param params  配置参数，预留
     * @return 发布成功返回true，反之返回false
     */
    @Override
    public boolean release(Long orderId, Object... params) {
        releaseFunctionDesc();
        /***
         * 在这里写数据发布操作
         */
        return configuration(orderId, params) && bom(orderId, params);
    }

    /**
     * 在这里写BOM管理部分数据的发布代码
     *
     * @param orderId 变更表单的ID
     * @param params  配置参数，预留
     * @return
     * @InChage zhudb
     */
    @Override
    public boolean configuration(Long orderId, Object... params) {
        return true;
    }

    /**
     * 在这里写BOM管理部分数据的发布代码
     *
     * @param orderId 变更表单的ID
     * @param params  配置参数，预留
     * @return
     * @InChage haozt
     */
    @Override
    public boolean bom(Long orderId, Object... params) {
        return true;
    }
}
