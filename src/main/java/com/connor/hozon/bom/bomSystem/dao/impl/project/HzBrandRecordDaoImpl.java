package com.connor.hozon.bom.bomSystem.dao.impl.project;

import com.connor.hozon.bom.bomSystem.dao.project.HzBrandRecordDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sql.IBaseSQLUtil;
import sql.pojo.project.HzBrandRecord;

import java.util.List;

/*************************************************************************************************************************
 *                                  Author: Fancyears·Maylos·Mayways
 * Date: 2018/6/1 11:17
 *
 * Description:
 *
 * ***********************************************************************************************************************/
@Service("hzBrandRecordDao")
public class HzBrandRecordDaoImpl implements HzBrandRecordDao {
    @Autowired
    IBaseSQLUtil baseSQLUtil;

    @Override
    public int deleteByPrimaryKey(String puid) {
        return baseSQLUtil.executeDeleteByPass(puid, "com.connor.hozon.bom.bomSystem.dao.project.HzBrandRecordDao.deleteByPrimaryKey");
    }

    @Override
    public int insert(HzBrandRecord record) {
        return baseSQLUtil.executeInsert(record, "com.connor.hozon.bom.bomSystem.dao.project.HzBrandRecordDao.insert");
    }

    @Override
    public HzBrandRecord selectByPrimaryKey(String puid) {
        return baseSQLUtil.executeQueryByPass(new HzBrandRecord(), puid, "com.connor.hozon.bom.bomSystem.dao.project.HzBrandRecordDao.selectByPrimaryKey", true);
    }

    @Override
    public HzBrandRecord selectByBrandCode(String pBrandCode) {
        return baseSQLUtil.executeQueryByPass(new HzBrandRecord(), pBrandCode, "com.connor.hozon.bom.bomSystem.dao.project.HzBrandRecordDao.selectByBrandCode", true);
    }

    @Override
    public int updateByPrimaryKey(HzBrandRecord record) {
        return baseSQLUtil.executeUpdate(record, "com.connor.hozon.bom.bomSystem.dao.project.HzBrandRecordDao.updateByPrimaryKey");
    }

    @Override
    public int updateSelective(HzBrandRecord record) {
        return baseSQLUtil.executeUpdate(record, "com.connor.hozon.bom.bomSystem.dao.project.HzBrandRecordDao.updateSelective");
    }

    @Override
    public List<HzBrandRecord> selectAll() {
        return baseSQLUtil.executeQuery(new HzBrandRecord(), "com.connor.hozon.bom.bomSystem.dao.project.HzBrandRecordDao.selectAll");
    }

}
