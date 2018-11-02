/*
 * Copyright (c) 2018.
 * This file was wrote by fancyears·milos·maywas @connor. Any question/bug you can post to 1243093366@qq.com.
 * ALL RIGHTS RESERVED.
 */

package com.connor.hozon.bom.bomSystem.service.task;

import com.connor.hozon.bom.bomSystem.dto.task.TaskReceivedDto;
import com.connor.hozon.bom.bomSystem.iservice.task.IHzTaskService;
import org.springframework.stereotype.Service;

/**
 * @Author: Fancyears·Maylos·Maywas
 * @Description:
 * @Date: Created in 2018/10/19 16:49
 * @Modified By:
 */
@Service("hzTaskService")
public class HzTaskService implements IHzTaskService {

    /**
     * 发起流程之后，保存task
     * @return
     */
    @Override
    public boolean saveTask(TaskReceivedDto dto) {
        System.out.println("保存任务");
        return false;
    }
}
