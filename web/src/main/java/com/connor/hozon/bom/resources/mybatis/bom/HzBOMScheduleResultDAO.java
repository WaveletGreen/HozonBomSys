/*
 *
 *  * Copyright (c) 2019 Author: Fancyears Milos Malvis @1243093366@qq.com
 *  * ALL COPYRIGHT REVERSED.
 *
 */

package com.connor.hozon.bom.resources.mybatis.bom;

import cn.net.connor.hozon.dao.pojo.bom.bom.HzBOMScheduleResult;

/**
 * @Author: haozt
 * @Date: 2019/1/3
 * @Description:
 */
public interface HzBOMScheduleResultDAO {

    int insert(HzBOMScheduleResult hzBOMScheduleResult);

    HzBOMScheduleResult getBOMScheduleResult(Long orderId);
}
