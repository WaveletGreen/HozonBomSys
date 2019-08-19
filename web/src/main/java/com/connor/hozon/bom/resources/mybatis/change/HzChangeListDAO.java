/*
 *
 *  * Copyright (c) 2019 Author: Fancyears Milos Malvis @1243093366@qq.com
 *  * ALL COPYRIGHT REVERSED.
 *
 */

package com.connor.hozon.bom.resources.mybatis.change;

import cn.net.connor.hozon.dao.pojo.change.change.HzChangeListRecord;

import java.util.List;

/**
 * @Author: haozt
 * @Date: 2018/11/22
 * @Description:
 */
public interface HzChangeListDAO {
    /**
     * 获取TC端同步的零件号和版本号
     * @param fromId
     * @return
     */
    List<HzChangeListRecord> findItemListByFormId(String fromId);

    //引用对象
    List<HzChangeListRecord> findChangeList(String formID);
}
