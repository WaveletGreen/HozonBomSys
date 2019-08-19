/*
 *
 *  * Copyright (c) 2019 Author: Fancyears Milos Malvis @1243093366@qq.com
 *  * ALL COPYRIGHT REVERSED.
 *
 */

package com.connor.hozon.service.bom.bomData;

import cn.net.connor.hozon.dao.pojo.bom.bom.HzBomLineRecord;

import java.util.List;

/**
 * @Author: Fancyears Milos Malvis
 * @Date: Created in  2019/8/19 13:41
 * @Modified By:
 */
public interface HzBomDataService {
    /**
     * 获取项目下的所有2Y层
     *
     * @param projectPuid
     * @param orderKey
     * @return
     */
    List<HzBomLineRecord> doSelect2YByProjectPuid(String projectPuid, String orderKey);

    /**
     * 传入部门和项目UID，获取部门下2Y层的子总成
     *
     * @param dept       部门名
     * @param projectUid 项目UID
     * @return 一组总成，需要进行查重操作
     */
    List<HzBomLineRecord> doSelectVehicleAssembly(String dept, String projectUid, String modelUid);

    /**
     * 更新单条2Y层数据，主要更新是否颜色件信息
     * @param hzBomLineRecord
     * @return
     */
    int updata2Y(HzBomLineRecord hzBomLineRecord);
}
