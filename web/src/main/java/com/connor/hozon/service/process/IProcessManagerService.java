/*
 *
 *  * Copyright (c) 2019 Author: Fancyears Milos Malvis @1243093366@qq.com
 *  * ALL COPYRIGHT REVERSED.
 *
 */

package com.connor.hozon.service.process;

import cn.net.connor.hozon.dao.pojo.sys.User;
import com.connor.hozon.service.process.impl.ReleaseEntity;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: Fancyears·Maylos·Malvis
 * @Description: 流程中断和发布回调，在这里统一进行流程的中断与发布
 * <p>
 * 当审核人员同意，执行发布操作{@link IProcessManagerService#release(long, Object...)}
 * 当审核人员不同意，执行中断操作{@link IProcessManagerService#interrupt(long, Object...)}
 * <p>
 * 流程的结束节点自动根据审核人的操作进行
 * @Date: Created in  2018/11/22 15:09
 * @Modified By:
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
     * 审核通过，流程结束，数据发布
     * 根据传入的orderId，调用实现回调的{@link IReleaseCallBack}进行数据的发布操作
     * 目前直接使用{@link ReleaseEntity}实现进行流程结束后的数据发布
     *
     * @param orderId 表单的ID
     * @param param   配置用的参数，可选
     * @return
     */
    Object release(long orderId, Object... param);

    /**
     * 流程终止，审核不通过
     * 根据传入的orderId，调用实现回调的{@link IInterruptionCallBack}进行数据的回到上一个版本的操作
     * 目前直接使用{@link ReleaseEntity}实现进行流程结束后的数据回版操作
     *
     * @param orderId 表单的ID
     * @param param   配置用的参数，可选
     * @return
     */
    Object interrupt(long orderId, Object... param);

    /**
     * 同意审核，流程通过
     */
    int PROCESS_OK = 1;
    /**
     * 不同意审核，流程结束
     */
    int PROCESS_NOT_OK = 0;

    /**
     * 检查当前表单是否已进入流程
     *
     * @param orderId
     * @return
     */
    User checkOrderAuditor(Long orderId);
}
