/*
 * Copyright (c) 2018.
 * This file was wrote by fancyears·milos·maywas @connor. Any question/bug you can post to 1243093366@qq.com.
 * ALL RIGHTS RESERVED.
 */

package com.connor.hozon.bom.bomSystem.iservice.task;

import com.connor.hozon.bom.bomSystem.dto.task.TaskReceivedDto;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: Fancyears·Maylos·Maywas
 * @Description:
 * @Date: Created in 2018/10/19 16:49
 * @Modified By:
 */
@Configuration
public interface IHzTaskService {
    /**
     * 发起流程之后，保存task
     * @return
     */
    boolean saveTask(TaskReceivedDto dto);

}
