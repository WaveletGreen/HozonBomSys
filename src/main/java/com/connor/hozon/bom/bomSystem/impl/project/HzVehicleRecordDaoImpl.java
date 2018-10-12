package com.connor.hozon.bom.bomSystem.impl.project;

import com.connor.hozon.bom.bomSystem.dao.project.HzVehicleRecordDao;
import com.connor.hozon.bom.bomSystem.impl.BasicDaoImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import sql.IBaseSQLUtil;
import sql.pojo.project.HzVehicleRecord;

import java.util.List;

@Configuration
public class HzVehicleRecordDaoImpl extends BasicDaoImpl<HzVehicleRecord> implements HzVehicleRecordDao {
    private final static HzVehicleRecord VEHICLE = new HzVehicleRecord();

    public HzVehicleRecordDaoImpl() {
        clz = HzVehicleRecordDao.class;
        clzName = clz.getCanonicalName();
    }

    @Override
    public int deleteByPrimaryKey(String puid) {
        return baseSQLUtil.executeDeleteByPass(puid,
                clzName + ".deleteByPrimaryKey");
    }


    @Override
    public HzVehicleRecord selectByPrimaryKey(String puid) {
        return baseSQLUtil.executeQueryByPass(VEHICLE, puid,
                clzName + ".selectByPrimaryKey", true);
    }


    @Override
    public List<HzVehicleRecord> selectAll() {
        return baseSQLUtil.executeQuery(VEHICLE,
                clzName + ".selectAll");
    }

    @Override
    public HzVehicleRecord selectByCode(String pVehicleCode) {
        return baseSQLUtil.executeQueryByPass(VEHICLE, pVehicleCode,
                clzName + ".selectByCode", true);
    }
}
