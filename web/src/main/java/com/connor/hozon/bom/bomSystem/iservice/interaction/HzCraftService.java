/*
 * Copyright (c) 2018.
 * This file was written by fancyears·milos·malvis @connor. Any question/bug you can post to 1243093366@qq.com.
 * ALL RIGHTS RESERVED.
 */

package com.connor.hozon.bom.bomSystem.iservice.interaction;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @Author: Fancyears·Maylos·Malvis
 * @Description: fuck
 * @Date: Created in 2018/9/6 13:19
 * @Modified By:
 */
//@Service("hzCraftService")
@Service
public interface HzCraftService {

    /**
     * 自动生成工艺合件
     *
     * @param projectUid    项目UID
     * @param parentUids    合成源父层
     * @param childrenUids  合成源子层
     * @param targetUids    挂载目标UID
     * @param collectedData 新件数据
     * @return
     */
    boolean autoCraft(String projectUid, List<String> parentUids, List<String> childrenUids, List<String> targetUids, Map<String, String> collectedData);

    /**
     * 获取当前挂载对象的零件号
     * @return
     */
    List<String> getTargetPartCodes();
}
