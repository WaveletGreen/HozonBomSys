/*
 * Copyright (c) 2018.
 * This file was written by fancyears·milos·malvis @connor. Any question/bug you can post to 1243093366@qq.com.
 * ALL RIGHTS RESERVED.
 */

package cn.net.connor.hozon.dao.dao.configuration.model;

import cn.net.connor.hozon.dao.dao.configuration.BasicDao;
import org.apache.ibatis.annotations.Param;
import cn.net.connor.hozon.dao.pojo.configuration.model.HzCfg0ModelRecord;
import org.springframework.stereotype.Repository;

import java.util.List;
/**
 * @Author: Fancyears·Maylos·Malvis
 * @Description: 基础车型主数据dao层
 * @Date: Created in 2018/8/27 21:17
 * @Modified By:
 */
@Repository
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

    /**
     * 链接查询出所有的基本车型
     * @param projectId
     * @return
     */
    List<HzCfg0ModelRecord> selectByProjectIdWithJoin(@Param("projectId") String projectId);

    int deleteModelById(@Param("puid") String puid);

    List<HzCfg0ModelRecord> selectByFullCfgModel(@Param("orderChangeId") Integer orderChangeId);

}