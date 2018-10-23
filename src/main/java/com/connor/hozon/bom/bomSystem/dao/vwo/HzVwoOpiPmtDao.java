package com.connor.hozon.bom.bomSystem.dao.vwo;

import com.connor.hozon.bom.bomSystem.dao.BasicDao;
import org.springframework.context.annotation.Configuration;
import sql.pojo.cfg.vwo.HzVwoOpiPmt;

@Configuration
public interface HzVwoOpiPmtDao extends BasicDao<HzVwoOpiPmt>{
    HzVwoOpiPmt selectByVwoId(Long id);

    int updateUserByVwoId(HzVwoOpiPmt hzVwoOpiPmt);
}