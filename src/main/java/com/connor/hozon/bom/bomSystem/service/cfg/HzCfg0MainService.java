package com.connor.hozon.bom.bomSystem.service.cfg;

import com.connor.hozon.bom.bomSystem.dao.cfg.HzCfg0MainRecordDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sql.pojo.cfg.HzCfg0MainRecord;

/*************************************************************************************************************************
 *                                  Author: Fancyears·Maylos·Mayways
 * Date: 2018/5/31 14:00
 *
 * Description:
 *
 * ***********************************************************************************************************************/
@Service("hzCfg0MainService")
public class HzCfg0MainService {
    @Autowired
    HzCfg0MainRecordDao hzCfg0MainRecordDao;

    public HzCfg0MainRecord doGetbyProjectPuid(String projectPuid) {
        return hzCfg0MainRecordDao.selectByProjectPuid(projectPuid);
    }

    public HzCfg0MainRecord doGetByPrimaryKey(String puid) {
        return hzCfg0MainRecordDao.selectByPrimaryKey(puid);
    }

    /**
     * 选择更新
     *
     * @param mainRecord
     * @return
     */
    public boolean doUpdateByPrimaryKeySelective(HzCfg0MainRecord mainRecord) {
        return hzCfg0MainRecordDao.updateByPrimaryKeySelective(mainRecord) > 0 ? true : false;
    }

}
