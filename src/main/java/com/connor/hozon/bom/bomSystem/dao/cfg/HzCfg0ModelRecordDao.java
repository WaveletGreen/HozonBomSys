package com.connor.hozon.bom.bomSystem.dao.cfg;

import org.apache.ibatis.annotations.Param;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestParam;
import sql.pojo.cfg.HzCfg0ModelRecord;

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
}