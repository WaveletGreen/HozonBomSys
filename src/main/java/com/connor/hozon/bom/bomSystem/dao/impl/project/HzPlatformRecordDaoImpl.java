package com.connor.hozon.bom.bomSystem.dao.impl.project;

import com.connor.hozon.bom.bomSystem.dao.project.HzPlatformRecordDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sql.IBaseSQLUtil;
import sql.pojo.project.HzPlatformRecord;

import java.util.List;

/*************************************************************************************************************************
 *                                  Author: Fancyears·Maylos·Mayways
 * Date: 2018/6/1 11:17
 *
 * Description:
 *
 * ***********************************************************************************************************************/
@Service("hzPlatformRecordDao")
public class HzPlatformRecordDaoImpl implements HzPlatformRecordDao {
    @Autowired
    IBaseSQLUtil baseSQLUtil;

    @Override
    public int deleteByPrimaryKey(String puid) {
        return baseSQLUtil.executeDeleteByPass(puid, "com.connor.hozon.bom.bomSystem.dao.project.HzPlatformRecordDao.deleteByPrimaryKey");
    }

    @Override
    public int insert(HzPlatformRecord record) {
        return baseSQLUtil.executeInsert(record, "com.connor.hozon.bom.bomSystem.dao.project.HzPlatformRecordDao.insert");
    }

    @Override
    public HzPlatformRecord selectByPrimaryKey(String puid) {
        return baseSQLUtil.executeQueryByPass(new HzPlatformRecord(), puid, "com.connor.hozon.bom.bomSystem.dao.project.HzPlatformRecordDao.selectByPrimaryKey", true);
    }

    @Override
    public int updateByPrimaryKey(HzPlatformRecord record) {
        return baseSQLUtil.executeUpdate(record, "com.connor.hozon.bom.bomSystem.dao.project.HzPlatformRecordDao.updateByPrimaryKey");
    }

    @Override
    public List<HzPlatformRecord> selectAll() {
        return baseSQLUtil.executeQuery(new HzPlatformRecord(),"com.connor.hozon.bom.bomSystem.dao.project.HzPlatformRecordDao.selectAll");
    }

    @Override
    public HzPlatformRecord selectByPlatformCode(String platformCode) {
        return baseSQLUtil.executeQueryByPass(new HzPlatformRecord(),platformCode,"com.connor.hozon.bom.bomSystem.dao.project.HzPlatformRecordDao.selectByPlatformCode",true);
    }
}
