package com.connor.hozon.bom.process.service;

import com.connor.hozon.bom.bomSystem.iservice.process.IFunctionDesc;
import com.connor.hozon.bom.bomSystem.iservice.process.IReleaseCallBack;
import com.connor.hozon.bom.process.iservice.IDataModifier;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

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
     * @return
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
