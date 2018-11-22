/*
 * Copyright (c) 2018.
 * This file was written by fancyears·milos·malvis @connor. Any question/bug you can post to 1243093366@qq.com.
 * ALL RIGHTS RESERVED.
 */

package com.connor.hozon.bom.process.iservice;

import com.connor.hozon.bom.sys.entity.User;
import org.springframework.stereotype.Component;
/**
 * @Author: Fancyears·Maylos·Malvis
 * @Description:  流程开启控制接口，做流程启动的前驱操作，将表单，审核人和任务等信息记录
 * @Date: Created in  2018/11/22 11:13
 * @Modified By:
 */
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

    User checkOrderAuditor(Long orderId);
}
