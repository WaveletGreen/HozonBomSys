package com.connor.hozon.bom.bomSystem.dao.model;

import com.connor.hozon.bom.bomSystem.dao.BasicDao;
import org.springframework.context.annotation.Configuration;
import sql.pojo.cfg.model.HzCfg0ModelDetail;

import java.util.List;

@Configuration
public interface HzCfg0ModelDetailDao  extends BasicDao<HzCfg0ModelDetail>{

    /**
     * @param detail 一般只包含车型模型的puid
     * @return
     * @Author: Fancyears·Maylos·Mayways
     * @Description: 根据车型模型的puid搜索1条车型模型详细信息
     * @Date: 2018/5/21 17:11
     */
    HzCfg0ModelDetail selectByModelId(HzCfg0ModelDetail detail);

    /**
     * 连同车型模型的数据都一起查出来
     * @param detail
     * @return
     */
    HzCfg0ModelDetail selectByModelId2(HzCfg0ModelDetail detail);

    List<HzCfg0ModelDetail> selectByModelIds(List<HzCfg0ModelDetail> hzCfg0ModelRecords);
}