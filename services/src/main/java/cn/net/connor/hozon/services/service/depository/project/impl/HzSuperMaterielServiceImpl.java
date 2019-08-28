/*
 *
 *  * Copyright (c) 2019 Author: Fancyears Milos Malvis @1243093366@qq.com
 *  * ALL COPYRIGHT REVERSED.
 *
 */

package cn.net.connor.hozon.services.service.depository.project.impl;

import cn.net.connor.hozon.dao.dao.depository.project.HzSuperMaterielDao;
import cn.net.connor.hozon.services.service.depository.project.HzSuperMaterielService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.net.connor.hozon.dao.pojo.bom.materiel.HzMaterielRecord;

/**
 * @Author: Fancyears·Maylos·Malvis
 * @Description: fuck
 * @Date: Created in  2018/5/30 14:15
 * @Modified By:
 */
@Service("hzSuperMaterielService")
public class HzSuperMaterielServiceImpl implements HzSuperMaterielService {
   @Autowired
    private HzSuperMaterielDao hzSuperMaterielDao;


    public boolean doDeleteByPrimaryKey(String puid) {
        return hzSuperMaterielDao.deleteByPrimaryKey(puid) > 0 ? true : false;
    }

    public boolean doInsertOne(HzMaterielRecord record)  {
        return hzSuperMaterielDao.insert(record) > 0 ? true : false;
    }

    public HzMaterielRecord doSelectByPrimaryKey(String puid) {
        return hzSuperMaterielDao.selectByPrimaryKey(puid);
    }

    public HzMaterielRecord doSelectByProjectPuid(String projectPuid) {
        return hzSuperMaterielDao.selectByProjectPuid(projectPuid);
    }

    public boolean doUpdateByPrimaryKey(HzMaterielRecord record)  {
        return hzSuperMaterielDao.updateByPrimaryKey(record) > 0 ? true : false;
    }
}
