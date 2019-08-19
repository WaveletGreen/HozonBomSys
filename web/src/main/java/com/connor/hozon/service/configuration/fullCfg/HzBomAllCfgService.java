/*
 *
 *  * Copyright (c) 2019 Author: Fancyears Milos Malvis @1243093366@qq.com
 *  * ALL COPYRIGHT REVERSED.
 *
 */

package com.connor.hozon.service.configuration.fullCfg;

import cn.net.connor.hozon.dao.pojo.change.change.HzChangeOrderRecord;
import cn.net.connor.hozon.dao.pojo.configuration.fullConfigSheet.HzFullCfgMain;
import cn.net.connor.hozon.dao.pojo.configuration.model.HzCfg0ModelDetail;
import cn.net.connor.hozon.dao.pojo.main.HzMainConfig;
import net.sf.json.JSONObject;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import java.util.List;
import java.util.Map;

/**
 * @Author: Fancyears Milos Malvis
 * @Date: Created in  2019/8/19 13:50
 * @Modified By:
 */
public interface HzBomAllCfgService {
    /**
     * @param projectPuid 项目puid
     * @param orderKey
     * @return net.sf.json.JSONObject
     * Description: 根据数模层获取到bom的配置信息和车型模型信息
     */
    JSONObject parse(String projectPuid, String orderKey) ;

    /**
     * 初始化添加列信息的项目信息树结构和主配置参数
     * @param projectPuid 项目UID
     * @param detail 基础车型的详情，主要接收项目树结构数据，并不做其他处理
     * @param mainRecord 主配置对象
     */
    void initAddingPageParams(String projectPuid, HzCfg0ModelDetail detail, HzMainConfig mainRecord);

    /**
     * 为withcfg新增数据
     * @param hzCfg0Records     所有特性集合
     * @param user               当前用户
     * @param mainPuid          项目对应主表的ID
     */
    /**
     * 保存单行
     *
     * @param bomLinePuid
     * @param cfgPuid
     * @param msgVal
     * @return
     */
    JSONObject saveOneRow(String bomLinePuid, String cfgPuid, Integer colorPart, String msgVal, String projectPuid);

    /**
     * 保存打点图
     *
     * @param data model集合 格式为<车辆模型ID<特性ID,打点图状态>>
     * @return
     */
    JSONObject savePoint(Map<String, Map<String, String>> data, String projectPuid);

    /**
     * 删除车辆模型
     *
     * @param modelId
     * @return
     */
    JSONObject deleteModel(String modelId) ;

    /**
     * 获取全配置BOM一级清单主数据
     *
     * @param projectUid
     * @return
     */
    HzFullCfgMain getFullCfgMain(String projectUid);

    /**
     * 存储版本信息
     *
     * @param params
     * @return
     */
    JSONObject setVersion(Map<String, String> params) ;

    /**
     * 存储阶段信息
     *
     * @param params
     * @return
     */
    JSONObject setStage(Map<String, String> params) ;

    /**
     * 发布，升小版本
     *
     * @param projectUid
     * @return
     */
    JSONObject promote(String projectUid);

    /**
     * 添加基本车型模型
     *
     * @param params
     * @return
     */
    JSONObject addVehicleModel(Map<String, String> params) ;

    /**
     * 查询所有以关联2Y层的特性和当前2Y层关联的特性，实现前端特性选择下拉列表的动态效果
     *
     * @param projectId
     * @param bomLineId
     * @return
     */
    JSONObject query2YCfg(String projectId, String bomLineId) ;

    /**
     * 保存一个2Y层对应所有车型的打点图
     *
     * @param dataMap
     * @return
     */
    JSONObject saveBomLinePiont(Map<String, Map<String, String>> dataMap) ;

    JSONObject getVwo(String projectId, Integer changeFromId);

    List<HzChangeOrderRecord> getChangeFroms(String projectUid);

    JSONObject goBackData(String projectUid) ;

    SXSSFWorkbook getWorkBook(String projectUid, String orderKey) ;
}
