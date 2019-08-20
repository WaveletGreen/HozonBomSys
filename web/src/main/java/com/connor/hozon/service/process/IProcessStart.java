/*
 *
 *  * Copyright (c) 2019 Author: Fancyears Milos Malvis @1243093366@qq.com
 *  * ALL COPYRIGHT REVERSED.
 *
 */

package com.connor.hozon.service.process;

import cn.net.connor.hozon.dao.pojo.sys.User;
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

    /**
     * 检查当前表单是否已存在审核任务，如果已存在，则不允许继续发流程，并返回第一个审核人对象提供给前端展示
     * @param orderId 变更表单ID
     * @return 表单已存在审核任务，则返回当前审核的User对象，照理拉说一个表单只有1个审核的用户
     */
    User checkOrderAuditor(Long orderId);
}
