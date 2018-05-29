package com.connor.hozon.bom.bomSystem.dao.cfg;

import org.springframework.context.annotation.Configuration;
import sql.pojo.cfg.HzCfg0ModelRecord;

@Configuration
public interface HzCfg0ModelRecordDao {
    /**
     * Author: Fancyears·Maylos·Mayways
     * Description: 根据主键获取到1条产品配置器车型模型（非颜色车型）
     * Date: 2018/5/23 9:57
     *
     * @param record
     * @return
     */
    HzCfg0ModelRecord selectByPrimaryKey(HzCfg0ModelRecord record);
}