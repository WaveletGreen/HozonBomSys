package com.connor.hozon.bom.process.service;

import com.connor.hozon.bom.bomSystem.iservice.process.IFunctionDesc;
import com.connor.hozon.bom.bomSystem.iservice.process.IInterruptionCallBack;
import com.connor.hozon.bom.process.iservice.IDataModifier;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

@Component
@EnableTransactionManagement(proxyTargetClass = true)
//要在事务的类中抛出RuntimeException异常，而不是抛出Exception
@Transactional(rollbackFor = {IllegalArgumentException.class, RuntimeException.class, Exception.class})
public class InterruptEntity implements IInterruptionCallBack, IFunctionDesc, IDataModifier {
    @Override
    public void interruptionFunctionDesc() {
        System.out.println("执行的是" + this.getClass().getCanonicalName() + ".interruption回调");
    }

    @Override
    public void releaseFunctionDesc() {

    }

    /**
     * 中断流程，所有的数据恢复上一个发布版本
     *
     * @param orderId 变更表单的ID
     * @param params  配置参数，预留
     * @return
     */
    @Override
    public boolean interrupt(Long orderId, Object... params) {
        interruptionFunctionDesc();
        /***
         * 在这里写数据中断操作
         */
        return configuration(orderId, params) && bom(orderId, params);
    }
    /**
     * 在这里写配置管理部分数据的回到上一个版本的代码
     *
     * @param orderId
     * @param params
     * @return
     * @InChage zhudb
     */
    @Override
    public boolean configuration(Long orderId, Object... params) {
        return true;
    }

    /**
     * 在这里写BOM管理部分数据的回到上一个版本的代码
     * BOM 端变更表单数据审核不通过
     * @param orderId 变更表单的ID
     * @param params 配置参数，预留
     * @return
     * @InChage haozt
     */
    @Override
    public boolean bom(Long orderId, Object... params) {
        return true;
    }
}
