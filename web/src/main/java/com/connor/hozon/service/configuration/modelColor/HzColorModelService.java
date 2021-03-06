/*
 *
 *  * Copyright (c) 2019 Author: Fancyears Milos Malvis @1243093366@qq.com
 *  * ALL COPYRIGHT REVERSED.
 *
 */

package com.connor.hozon.service.configuration.modelColor;

import cn.net.connor.hozon.dao.pojo.configuration.modelColor.HzCfg0ModelColorDetail;

import java.util.List;

/**
 * @Author: Fancyears·Maylos·Malvis
 * @Description: fuck
 * @Date: Created in 2018/9/6 13:19
 * @Modified By:
 */
public interface HzColorModelService {
    /**
     * 主键删除
     *
     * @param puid 主键
     * @return
     */
    int doDeleteByPrimaryKey(String puid);

    /**
     * 插入
     *
     * @param record 颜色+配置集合
     * @return
     */
    int doInsert(HzCfg0ModelColorDetail record);

    /**
     * 主键筛选
     *
     * @param puid 主键
     * @return
     */

    HzCfg0ModelColorDetail doSelectByPrimaryKey(String puid);

    /**
     * 主键更新
     *
     * @param record 颜色+配置记录
     * @return
     */
    int doUpdateByPrimaryKey(HzCfg0ModelColorDetail record);

    /**
     * 根据颜色模型
     *
     * @param modelUid
     * @return
     */

    List<HzCfg0ModelColorDetail> doSelectByModelUid(String modelUid);
    /**
     * 根据颜色模型，包括颜色一起筛选出来
     *
     * @param modelUid 车型UID
     * @return
     */
    List<HzCfg0ModelColorDetail> doSelectByModelUidWithColor(String modelUid);
    /**
     * 根据颜色模型，包括颜色一起筛选出来
     *
     * @param modelUid 车型UID
     * @return
     */
    List<HzCfg0ModelColorDetail> doSelectByModelUidWithColor2(String modelUid);
    /**
     * 根据项目ID查找
     *
     * @param projectUid
     * @return
     */

    List<HzCfg0ModelColorDetail> doSelectByCfgMainUid(String projectUid);

    /**
     * 批量插入
     *
     * @param colorModels 颜色+配置记录集合
     * @return
     */
    int doInsertByBatch(List<HzCfg0ModelColorDetail> colorModels);

    /**
     * 根据颜色车型和配置更新颜色信息
     * @param model 需要更新的模型集合
     * @return
     */

    boolean doUpdateColorModelWithCfg(HzCfg0ModelColorDetail model);
}
