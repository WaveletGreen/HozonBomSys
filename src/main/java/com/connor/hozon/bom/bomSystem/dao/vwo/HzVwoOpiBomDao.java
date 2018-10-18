package com.connor.hozon.bom.bomSystem.dao.vwo;

import com.connor.hozon.bom.bomSystem.dao.BasicDao;
import org.springframework.context.annotation.Configuration;
import sql.pojo.cfg.vwo.HzVwoOpiBom;

@Configuration
public interface HzVwoOpiBomDao extends BasicDao<HzVwoOpiBom> {
    HzVwoOpiBom selectByVwoId(Long id);
}