package com.connor.hozon.bom.bomSystem.dao.cfg;

import org.springframework.context.annotation.Configuration;
import sql.pojo.cfg.HzCfg0ModelDetail;

import java.util.List;

@Configuration
public interface HzCfg0ModelDetailDao {
    /**
     * @param detail 详细信息
     * @return
     * @Author: Fancyears·Maylos·Mayways
     * @Description: 插入1条车型模型详细信息
     * @Date: 2018/5/21 17:11
     */
    int insertOne(HzCfg0ModelDetail detail);

    /**
     * @param detail
     * @return
     * @Author: Fancyears·Maylos·Mayways
     * @Description: 更新1条车型模型详细信息
     * @Date: 2018/5/21 17:11
     */
    int updateOne(HzCfg0ModelDetail detail);

    /**
     * @param detail 一般只包含车型模型的puid
     * @return
     * @Author: Fancyears·Maylos·Mayways
     * @Description: 根据车型模型的puid搜索1条车型模型详细信息
     * @Date: 2018/5/21 17:11
     */
    HzCfg0ModelDetail selectByModelId(HzCfg0ModelDetail detail);

    List<HzCfg0ModelDetail> selectByModelIds(List<HzCfg0ModelDetail> hzCfg0ModelRecords);
}