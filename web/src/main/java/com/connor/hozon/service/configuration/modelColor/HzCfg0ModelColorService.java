/*
 *
 *  * Copyright (c) 2019 Author: Fancyears Milos Malvis @1243093366@qq.com
 *  * ALL COPYRIGHT REVERSED.
 *
 */

package com.connor.hozon.service.configuration.modelColor;

import cn.net.connor.hozon.dao.pojo.configuration.modelColor.HzCfg0ModelColor;
import net.sf.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Author: Fancyears Milos Malvis
 * @Date: Created in  2019/8/19 13:38
 * @Modified By:
 */
public interface HzCfg0ModelColorService {
    List<HzCfg0ModelColor> doLoadModelColorByMainId(HzCfg0ModelColor color);

    boolean doUpdateOne(HzCfg0ModelColor color);

    boolean doInsert(HzCfg0ModelColor color);

    HzCfg0ModelColor doGetById(HzCfg0ModelColor color);

    Map<String, Object> doLoadAll2(String projectPuid);

    /**
     * 根据主键批量删除数据
     *
     * @param colors
     * @return
     */
    int doDelete(List<HzCfg0ModelColor> colors);

    /**
     * 带全部的特性，旧版方法,2018-9-20 11:15
     *
     * @param projectUid
     * @return
     */
    JSONObject getColumn(String projectUid);

    /**
     * 单独查找有颜色的特性，没颜色的特性不允许出现，出现同一个特性出现歧义，则不允许生成表头
     *
     * @param projectUid
     * @return
     */
    JSONObject getColumnOnlyColor(String projectUid);

    /**
     * 发起VOW流程
     *
     * @param colors       需发起VWO流程的配色方案
     * @param projectPuid  项目ID
     * @param dynamicTitle
     * @return
     */
    JSONObject getVWO(List<HzCfg0ModelColor> colors, String projectPuid, ArrayList<String> dynamicTitle, Long changeFromId);


    boolean doRelease(HzCfg0ModelColor hzCfg0ModelColor);

    int doUpdateStatus(List<HzCfg0ModelColor> colors);
}
