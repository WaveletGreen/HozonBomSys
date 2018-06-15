package com.connor.hozon.bom.bomSystem.dao.impl.project;

import com.connor.hozon.bom.bomSystem.dao.project.HzVehicleRecordDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import sql.IBaseSQLUtil;
import sql.pojo.project.HzVehicleRecord;

import java.util.List;

@Configuration
public class HzVehicleRecordDaoImpl implements HzVehicleRecordDao {
    private final IBaseSQLUtil baseSQLUtil;

    @Autowired
    public HzVehicleRecordDaoImpl(IBaseSQLUtil baseSQLUtil) {
        this.baseSQLUtil = baseSQLUtil;
    }

    @Override
    public int deleteByPrimaryKey(String puid) {
        return baseSQLUtil.executeDeleteByPass(puid, "com.connor.hozon.bom.bomSystem.dao.project.HzVehicleRecordDao.deleteByPrimaryKey");
    }

    @Override
    public int insert(HzVehicleRecord record) {
        return baseSQLUtil.executeInsert(record, "com.connor.hozon.bom.bomSystem.dao.project.HzVehicleRecordDao.insert");
    }

    @Override
    public HzVehicleRecord selectByPrimaryKey(String puid) {
        return baseSQLUtil.executeQueryByPass(new HzVehicleRecord(), puid, "com.connor.hozon.bom.bomSystem.dao.project.HzVehicleRecordDao.selectByPrimaryKey", true);
    }


    @Override
    public int updateByPrimaryKey(HzVehicleRecord record) {
        return baseSQLUtil.executeUpdate(record, "com.connor.hozon.bom.bomSystem.dao.project.HzVehicleRecordDao.updateByPrimaryKey");
    }

    @Override
    public List<HzVehicleRecord> selectAll() {
        return baseSQLUtil.executeQuery(new HzVehicleRecord(), "com.connor.hozon.bom.bomSystem.dao.project.HzVehicleRecordDao.selectAll");
    }
}
