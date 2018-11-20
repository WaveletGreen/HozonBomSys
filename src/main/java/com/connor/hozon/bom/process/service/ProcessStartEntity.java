package com.connor.hozon.bom.process.service;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

@Component
@EnableTransactionManagement(proxyTargetClass = true)
@Transactional(rollbackFor = {IllegalArgumentException.class, RuntimeException.class, Exception.class})
public class ProcessStartEntity {
    /**
     * 流程开始
     *
     * @param orderId 变更表单ID
     * @param param   配置参数
     * @return
     */
    public Object doStart(long orderId, Object... param) {
        return null;
    }
}
