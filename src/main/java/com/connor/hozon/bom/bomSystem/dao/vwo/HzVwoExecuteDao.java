package com.connor.hozon.bom.bomSystem.dao.vwo;

import com.connor.hozon.bom.bomSystem.dao.BasicDao;
import org.springframework.context.annotation.Configuration;
import sql.pojo.cfg.vwo.HzVwoExecute;

import java.util.List;

@Configuration
public interface HzVwoExecuteDao extends BasicDao<HzVwoExecute> {
    /**
     * 根据VWO号ID查询
     *
     * @param vwoId vwo号的ID，主键
     * @return 一组分发与实施对象
     */
    List<HzVwoExecute> selectByVwoId(Long vwoId);
}