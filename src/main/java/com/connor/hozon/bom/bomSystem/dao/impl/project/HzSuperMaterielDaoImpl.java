package com.connor.hozon.bom.bomSystem.dao.impl.project;

import com.connor.hozon.bom.bomSystem.dao.project.HzSuperMaterielDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sql.IBaseSQLUtil;
import sql.pojo.project.HzMaterielRecord;

/**
 * Created by Fancyears·Maylos·Mayways
 * Description:
 * Date: 2018/6/9 13:56
 */
@Service("HzSuperMaterielDao")
public class HzSuperMaterielDaoImpl implements HzSuperMaterielDao {
    private final IBaseSQLUtil baseSQLUtil;

    @Autowired
    public HzSuperMaterielDaoImpl(IBaseSQLUtil baseSQLUtil) {
        this.baseSQLUtil = baseSQLUtil;
    }

    @Override
    public int deleteByPrimaryKey(String puid) {
        return baseSQLUtil.executeDeleteByPass(puid, "com.connor.hozon.bom.bomSystem.dao.project.HzSuperMaterielDao.deleteByPrimaryKey");
    }

    @Override
    public int insert(HzMaterielRecord record) {
        return baseSQLUtil.executeInsert(record, "com.connor.hozon.bom.bomSystem.dao.project.HzSuperMaterielDao.insert");
    }

    @Override
    public HzMaterielRecord selectByPrimaryKey(String puid) {
        return baseSQLUtil.executeQueryByPass(new HzMaterielRecord(), puid, "com.connor.hozon.bom.bomSystem.dao.project.HzSuperMaterielDao.selectByPrimaryKey", true);
    }

    @Override
    public HzMaterielRecord selectByProjectPuid(String projectPuid) {
        return baseSQLUtil.executeQueryByPass(new HzMaterielRecord(), projectPuid, "com.connor.hozon.bom.bomSystem.dao.project.HzSuperMaterielDao.selectByProjectPuid", true);
    }

    @Override
    public int updateByPrimaryKey(HzMaterielRecord record) {
        return baseSQLUtil.executeUpdate(record, "com.connor.hozon.bom.bomSystem.dao.project.HzSuperMaterielDao.updateByPrimaryKey");
    }
}
