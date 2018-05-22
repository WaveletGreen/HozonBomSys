package com.connor.hozon.bom.bomSystem.dao.cfg;

import org.apache.ibatis.annotations.Param;
import org.springframework.context.annotation.Configuration;
import sql.pojo.cfg.HzCfg0BomLineOfModel;

import java.util.List;

@Configuration
public interface HzCfg0BomLineOfModelDao {
    List<HzCfg0BomLineOfModel> selectByModelMainId(@Param("modelId") String modelId);
}