package com.connor.hozon.bom.process.iservice;

import org.springframework.stereotype.Component;

@Component
public interface IProcessFinish {
    /**
     * 最后一步是修改任务状态和表单的状态
     *
     * @param orderId 表单的ID
     * @param params  不定参数，根据需要进行传参，然后再强转
     * @return
     */
    boolean doFinish(Long orderId, Object... params);
}
