package com.connor.hozon.bom.process.iservice;

import org.springframework.stereotype.Component;

@Component
public interface IProcessStart {
    /**
     * 流程开始，进行任务的驱动，表单状态的改变和审核人的记录等
     *
     * @param orderId 变更表单ID
     * @param param   配置参数
     * @return
     */
    Object doStart(long orderId, Object... param) throws Exception;
}
