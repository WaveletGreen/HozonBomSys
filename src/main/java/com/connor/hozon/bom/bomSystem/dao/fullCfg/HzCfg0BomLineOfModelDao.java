/*
 * Copyright (c) 2018.
 * This file was wrote by fancyears·milos·maywas @connor. Any question/bug you can post to 1243093366@qq.com.
 * ALL RIGHTS RESERVED.
 */

package com.connor.hozon.bom.bomSystem.dao.fullCfg;

import com.connor.hozon.bom.bomSystem.dao.BasicDao;
import org.apache.ibatis.annotations.Param;
import org.springframework.context.annotation.Configuration;
import sql.pojo.cfg.fullCfg.HzCfg0BomLineOfModel;

import java.util.List;
/**
 * @Author: Fancyears·Maylos·Maywas
 * @Description: 忘了
 * @Date: Created in 2018/8/27 21:17
 * @Modified By:
 */
@Configuration
public interface HzCfg0BomLineOfModelDao  extends BasicDao<HzCfg0BomLineOfModel>{
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