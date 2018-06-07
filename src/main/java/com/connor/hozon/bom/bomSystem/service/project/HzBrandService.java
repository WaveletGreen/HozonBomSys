package com.connor.hozon.bom.bomSystem.service.project;

import com.connor.hozon.bom.bomSystem.dao.project.HzBrandRecordDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sql.pojo.project.HzBrandRecord;

import java.util.List;

/*************************************************************************************************************************
 *                                  Author: Fancyears·Maylos·Mayways
 * Date: 2018/6/1 11:30
 *
 * Description:
 *
 * ***********************************************************************************************************************/
@Service("hzBrandService")
public class HzBrandService {
    @Autowired
    HzBrandRecordDao hzBrandRecordDao;

    public HzBrandRecord doGetByPuid(String puid) {
        return hzBrandRecordDao.selectByPrimaryKey(puid);
    }

    public boolean doUpdate(HzBrandRecord record) {
        return hzBrandRecordDao.updateByPrimaryKey(record) > 0 ? true : false;
    }

    public boolean doInsertOne(HzBrandRecord record) {
        return hzBrandRecordDao.insert(record) > 0 ? true : false;
    }

    public boolean doDeleteByPuid(String puid) {
        return hzBrandRecordDao.deleteByPrimaryKey(puid) > 0 ? true : false;
    }

    public List<HzBrandRecord> doGetAllBrand(){
        return hzBrandRecordDao.selectAll();
    };
}
