package com.connor.hozon.bom.process.iservice;

import org.springframework.context.annotation.Configuration;

/**
 * 流程中断和发布回调，在这里统一进行流程的中断与发布
 * 当审核人员同意，执行发布操作{@link IProcessManagerService#release}
 * 当审核人员不同意，执行中断操作{@link IProcessManagerService#interrupt(long, Object...)}
 */
@Configuration
public interface IProcessManagerService {
    /**
     * 流程开始，启动流程，修改数据
     *
     * @param orderId 传入的表单ID
     * @param param
     * @return
     */
    Object start(long orderId, Object... param);

    /**
     * 发布，审核通过
     * 根据传入的orderId，调用实现回调的{@link com.connor.hozon.bom.bomSystem.iservice.process.IReleaseCallBack}进行数据的发布操作
     * 目前直接使用{@link com.connor.hozon.bom.process.service.ReleaseEntity}实现进行流程结束后的数据发布
     *
     * @param orderId 表单的ID
     * @param param   配置用的参数，可选
     * @return
     */
    Object release(long orderId, Object... param);

    /**
     * 发布，审核通过
     * 根据传入的orderId，调用实现回调的{@link com.connor.hozon.bom.bomSystem.iservice.process.IInterruptionCallBack}进行数据的回到上一个版本的操作
     * 目前直接使用{@link com.connor.hozon.bom.process.service.ReleaseEntity}实现进行流程结束后的数据回版操作
     *
     * @param orderId 表单的ID
     * @param param   配置用的参数，可选
     * @return
     */
    Object interrupt(long orderId, Object... param);

    int PROCESS_OK = 1;
    int PROCESS_NOT_OK = 0;
}
