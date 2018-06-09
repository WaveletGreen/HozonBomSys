package com.connor.hozon.bom.bomSystem.dao.impl.project;

import com.connor.hozon.bom.bomSystem.dao.project.HzSuperMaterielDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sql.IBaseSQLUtil;
import sql.pojo.project.HzSuperMateriel;

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
    public int insert(HzSuperMateriel record) {
        return baseSQLUtil.executeInsert(record, "com.connor.hozon.bom.bomSystem.dao.project.HzSuperMaterielDao.insert");
    }

    @Override
    public HzSuperMateriel selectByPrimaryKey(String puid) {
        return baseSQLUtil.executeQueryByPass(new HzSuperMateriel(), puid, "com.connor.hozon.bom.bomSystem.dao.project.HzSuperMaterielDao.selectByPrimaryKey", true);
    }

    @Override
    public HzSuperMateriel selectByProjectPuid(String projectPuid) {
        return baseSQLUtil.executeQueryByPass(new HzSuperMateriel(), projectPuid, "com.connor.hozon.bom.bomSystem.dao.project.HzSuperMaterielDao.selectByProjectPuid", true);
    }

    @Override
    public int updateByPrimaryKey(HzSuperMateriel record) {
        return baseSQLUtil.executeUpdate(record, "com.connor.hozon.bom.bomSystem.dao.project.HzSuperMaterielDao.updateByPrimaryKey");
    }
}
