package com.connor.hozon.bom.bomSystem.service.project;

import com.connor.hozon.bom.bomSystem.dao.project.HzPlatformRecordDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sql.pojo.project.HzPlatformRecord;

import java.util.List;

/*************************************************************************************************************************
 *                                  Author: Fancyears·Maylos·Mayways
 * Date: 2018/6/1 11:30
 *
 * Description:
 *
 * ***********************************************************************************************************************/
@Service("hzPlatformService")
public class HzPlatformService {
    @Autowired
    HzPlatformRecordDao hzPlatformRecordDao;

    public HzPlatformRecord doGetByPuid(String puid) {
        return hzPlatformRecordDao.selectByPrimaryKey(puid);
    }

    public boolean doUpdate(HzPlatformRecord record) {
        return hzPlatformRecordDao.updateByPrimaryKey(record) > 0 ? true : false;
    }

    public boolean doInsertOne(HzPlatformRecord record) {
        return hzPlatformRecordDao.insert(record) > 0 ? true : false;
    }

    public boolean doDeleteByPuid(String puid) {
        return hzPlatformRecordDao.deleteByPrimaryKey(puid) > 0 ? true : false;
    }

    public List<HzPlatformRecord> doGetAllPlatform(){
        return hzPlatformRecordDao.selectAll();
    }
}
