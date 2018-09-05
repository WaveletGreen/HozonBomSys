package com.connor.hozon.bom.bomSystem.service.project;

import com.connor.hozon.bom.bomSystem.dao.project.HzSuperMaterielDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sql.pojo.project.HzMaterielRecord;

/**
 * Created by Fancyears·Maylos·Mayways
 * Description:
 * Date: 2018/6/9 14:01
 */
@Service("hzSuperMaterielService")
public class HzSuperMaterielService {
    private final HzSuperMaterielDao hzSuperMaterielDao;

    @Autowired
    public HzSuperMaterielService(HzSuperMaterielDao hzSuperMaterielDao) {
        this.hzSuperMaterielDao = hzSuperMaterielDao;
    }

    public boolean doDeleteByPrimaryKey(String puid) {
        return hzSuperMaterielDao.deleteByPrimaryKey(puid) > 0 ? true : false;
    }

    public boolean doInsertOne(HzMaterielRecord record) {
        return hzSuperMaterielDao.insert(record) > 0 ? true : false;
    }

    public HzMaterielRecord doSelectByPrimaryKey(String puid) {
        return hzSuperMaterielDao.selectByPrimaryKey(puid);
    }

    public HzMaterielRecord doSelectByProjectPuid(String projectPuid) {
        return hzSuperMaterielDao.selectByProjectPuid(projectPuid);
    }

    public boolean doUpdateByPrimaryKey(HzMaterielRecord record) {
        return hzSuperMaterielDao.updateByPrimaryKey(record) > 0 ? true : false;
    }
}
