package com.connor.hozon.bom.bomSystem.dao.cfg;

import org.apache.ibatis.annotations.Param;
import org.springframework.context.annotation.Configuration;
import sql.pojo.cfg.HzCfg0ModelRecord;

import java.util.List;

@Configuration
public interface HzCfg0ModelRecordDao {
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
     * @param modelRecord 车型模型对象，仅仅有puid和P_CFG0_MODEL_BASIC_DETAIL的信息
     * @return
     */
    int updateBasicByPuid(HzCfg0ModelRecord modelRecord);

    /**
     * 根据主键修改车型模型名
     * @param modelRecord 车型模型，只包含puid和车型模型名即可
     * @return
     */
    int updateModelName(HzCfg0ModelRecord modelRecord);

    int insertByBatch(List<HzCfg0ModelRecord> modelRecord);

    /**
     * 该项目下的所有车型
     * @param projectPuid
     * @return
     */
    List<HzCfg0ModelRecord> selectByProjectPuid(@Param("projectPuid") String projectPuid);

    int deleteModelById(String modelId);
}