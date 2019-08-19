/*
 *
 *  * Copyright (c) 2019 Author: Fancyears Milos Malvis @1243093366@qq.com
 *  * ALL COPYRIGHT REVERSED.
 *
 */

package com.connor.hozon.bom.service.interaction;

import org.springframework.stereotype.Service;
import cn.net.connor.hozon.dao.pojo.interaction.HzConfigBomColorBean;

import java.util.List;

/**
 * @Author: Fancyears·Maylos·Malvis
 * @Description:
 * @Date: Created in 2018/9/19 12:26
 * @Modified By:
 */
@Service("iHzConfigBomColorService")
public interface HzConfigBomColorService {
    /**
     * 根据某个2Y的主键和项目主键查找2Y的所有配色代码
     *
     * @param bomline    2Y主键
     * @param projectUid 项目主键
     * @param paintFlag 是否为油漆车身
     * @return
     */
    List<HzConfigBomColorBean> doSelectBy2YUidWithProject(String bomline, String projectUid,boolean paintFlag);
}
