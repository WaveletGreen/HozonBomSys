/*
 * Copyright (c) 2018.
 * This file was wrote by fancyears·milos·malvis @connor. Any question/bug you can post to 1243093366@qq.com.
 * ALL RIGHTS RESERVED.
 */

package com.connor.hozon.bom.bomSystem.dao.model;

import com.connor.hozon.bom.bomSystem.dao.BasicDao;
import org.apache.ibatis.annotations.Param;
import org.springframework.context.annotation.Configuration;
import sql.pojo.cfg.fullCfg.HzFullCfgModelChange;
import sql.pojo.cfg.model.HzCfg0ModelRecord;

import java.util.List;
/**
 * @Author: Fancyears·Maylos·Maywas
 * @Description: 基础车型主数据dao层
 * @Date: Created in 2018/8/27 21:17
 * @Modified By:
 */
@Configuration
public interface HzCfg0ModelRecordDao extends BasicDao<HzCfg0ModelRecord> {
    /**
     * Author: Fancyears·Maylos·Mayways
     * Description: 根据主键获取到1条产品配置器车型模型（非颜色车型）
     * Date: 2018/5/23 9:57
     *
     * @param puid
     * @return
     */
    HzCfg0ModelRecord selectByPrimaryKey(@Param("puid") String puid);

    /**
     * 根据ID修改基本信息字段，仅仅修改了字段P_CFG0_MODEL_BASIC_DETAIL
     *
     * @param modelRecord 车型模型对象，仅仅有puid和P_CFG0_MODEL_BASIC_DETAIL的信息
     * @return
     */
    int updateBasicByPuid(HzCfg0ModelRecord modelRecord);

    /**
     * 根据主键修改车型模型名
     *
     * @param modelRecord 车型模型，只包含puid和车型模型名即可
     * @return
     */
    int updateModelName(HzCfg0ModelRecord modelRecord);

    int insertByBatch(List<HzCfg0ModelRecord> modelRecord);

    /**
     * 该项目下的所有车型
     *
     * @param projectPuid
     * @return
     */
    List<HzCfg0ModelRecord> selectByProjectPuid(@Param("projectPuid") String projectPuid);

    int deleteModelById(String puid);

    List<HzCfg0ModelRecord> selectByFullCfgModel(Integer orderChangeId);
}