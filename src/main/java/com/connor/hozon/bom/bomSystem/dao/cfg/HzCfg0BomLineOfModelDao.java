package com.connor.hozon.bom.bomSystem.dao.cfg;

import org.apache.ibatis.annotations.Param;
import org.springframework.context.annotation.Configuration;
import sql.pojo.cfg.HzCfg0BomLineOfModel;

import java.util.List;

@Configuration
public interface HzCfg0BomLineOfModelDao {
    /**
     * Author: Fancyears·Maylos·Mayways
     * Description: 根据数模层puid,获取到所有的BomLine以及BomLine对应的产品配置信息
     * Date: 2018/5/23 9:58
     *
     * @param modelId
     * @return
     */
    List<HzCfg0BomLineOfModel> selectByModelMainId(@Param("modelId") String modelId);
}