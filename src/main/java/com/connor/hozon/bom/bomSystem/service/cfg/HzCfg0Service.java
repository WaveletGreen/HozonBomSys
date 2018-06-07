package com.connor.hozon.bom.bomSystem.service.cfg;

import com.connor.hozon.bom.bomSystem.dao.cfg.HzCfg0RecordDao;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;
import sql.pojo.cfg.HzCfg0Record;

import java.util.List;

/**
 * Created by Fancyears·Maylos·Mayways
 * Description:
 * Date: 2018/6/6
 */
@Service("hzCfg0Service")
public class HzCfg0Service {
    private HzCfg0RecordDao hzCfg0RecordDao;

    public HzCfg0Service(HzCfg0RecordDao hzCfg0RecordDao) {
        this.hzCfg0RecordDao = hzCfg0RecordDao;
    }

    public List<HzCfg0Record> doLoadCfgByProjectPuid(String projectPuid) {
        return hzCfg0RecordDao.selectByProjectPuid(projectPuid);
    }

}
