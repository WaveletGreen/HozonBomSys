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
     * @param orderId
     * @param param
     * @return
     */
    Object start(long orderId, Object... param);

    /**
     * 发布，审核通过
     *
     * @return
     */
    Object release(long orderId, Object... param);
    /**
     * 流程中断，审核不同意
     *
     * @return
     */
    Object interrupt(long orderId, Object... param);
}
