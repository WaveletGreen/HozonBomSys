package com.connor.hozon.bom.bomSystem.service.cfg;

import com.connor.hozon.bom.bomSystem.dao.cfg.HzCfg0RecordDao;
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

    public HzCfg0Record doGetByProjectPuid(String projectPuid) {
        return hzCfg0RecordDao.selectByPrimaryKey(projectPuid);
    }

    public List<HzCfg0Record> doLoadCfgListByProjectPuid(String projectPuid) {
        return hzCfg0RecordDao.selectListByProjectPuid(projectPuid);
    }

    public List<HzCfg0Record> doLoadAddedCfgListByProjectPuid(String projectPuid) {
        return hzCfg0RecordDao.selectAddedCfgListByProjectPuid(projectPuid);
    }

    public boolean doInsertOne(HzCfg0Record record) {
        return hzCfg0RecordDao.insert(record) > 0 ? true : false;
    }

    public boolean doInsertAddCfg(HzCfg0Record record) {
        return hzCfg0RecordDao.insertAddCfg(record) > 0 ? true : false;
    }

    public HzCfg0Record doSelectOneByPuid(String puid) {
        return hzCfg0RecordDao.selectByPrimaryKey(puid);
    }

    public HzCfg0Record doSelectOneAddedCfgByPuid(String puid) {
        return hzCfg0RecordDao.selectOneAddedCfgByPuid(puid);
    }

    public boolean doUpdate(HzCfg0Record record) {
        return hzCfg0RecordDao.updateByPrimaryKey(record) > 0 ? true : false;
    }

    public boolean doUpdateAddedCfg(HzCfg0Record record) {
        return hzCfg0RecordDao.updateAddedCfgByPrimaryKey(record) > 0 ? true : false;
    }

    public boolean doDeleteByPuid(HzCfg0Record record) {
        return false;
    }

    public boolean doDeleteAddedCfgByList(List<HzCfg0Record> records) {
        return hzCfg0RecordDao.deleteAddedCfgByList(records) > 0 ? true : false;
    }


    public boolean doDeleteAddedCfgById(HzCfg0Record record) {
        return hzCfg0RecordDao.deleteAddCfgByPrimaryKey(record.getPuid()) > 0 ? true : false;
    }
}
