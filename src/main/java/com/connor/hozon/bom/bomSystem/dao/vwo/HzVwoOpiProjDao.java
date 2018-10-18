package com.connor.hozon.bom.bomSystem.dao.vwo;

import com.connor.hozon.bom.bomSystem.dao.BasicDao;
import org.springframework.context.annotation.Configuration;
import sql.pojo.cfg.vwo.HzVwoOpiProj;

@Configuration
public interface HzVwoOpiProjDao extends BasicDao<HzVwoOpiProj>{
    HzVwoOpiProj selectByVwoId(Long id);
}