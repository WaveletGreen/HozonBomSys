package com.connor.hozon.bom.bomSystem.dao.cfg;

import org.springframework.context.annotation.Configuration;
import sql.pojo.cfg.HzCfg0ModelColor;

import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: Fancyears·Maylos·Mayways
 * Date: 2018/5/22
 * Time: 10:52
 */
@Configuration
public interface HzCfg0ModelColorDao {
    List<HzCfg0ModelColor> selectByMainId(HzCfg0ModelColor color);

    int updateByPrimaryKey(HzCfg0ModelColor color);

    HzCfg0ModelColor selectByPrimaryKey(HzCfg0ModelColor color);

    List<HzCfg0ModelColor> selectAll();

    int insertOne(HzCfg0ModelColor color);

    int deleteByBatch(List<HzCfg0ModelColor> colors);
}
