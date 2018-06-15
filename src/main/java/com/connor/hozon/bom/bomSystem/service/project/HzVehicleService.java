package com.connor.hozon.bom.bomSystem.service.project;

import com.connor.hozon.bom.bomSystem.dao.project.HzVehicleRecordDao;
import com.connor.hozon.bom.bomSystem.service.iservice.project.IHzVehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sql.pojo.project.HzVehicleRecord;

import java.util.List;

@Service("hzVehicleService")
public class HzVehicleService implements IHzVehicleService {
    private final HzVehicleRecordDao hzVehicleRecordDao;

    @Autowired
    public HzVehicleService(HzVehicleRecordDao hzVehicleRecordDao) {
        this.hzVehicleRecordDao = hzVehicleRecordDao;
    }

    @Override
    public HzVehicleRecord doGetByPuid(String puid) {
        return hzVehicleRecordDao.selectByPrimaryKey(puid);
    }

    @Override
    public int doInsertOne(HzVehicleRecord vehicle) {
        return hzVehicleRecordDao.insert(vehicle);
    }

    @Override
    public int doDeleteByPuid(String puid) {
        return hzVehicleRecordDao.deleteByPrimaryKey(puid);
    }

    @Override
    public int doUpdateByPuid(HzVehicleRecord vehicle) {
        return hzVehicleRecordDao.updateByPrimaryKey(vehicle);
    }

    @Override
    public boolean validate(HzVehicleRecord vehicle) {
        if (null == vehicle ||
                null == vehicle.getPuid() ||
                null == vehicle.getpVehicleCode() ||
                null == vehicle.getpVehicleName() ||
                "".equals(vehicle.getPuid()) ||
                "".equals(vehicle.getpVehicleCode()) ||
                "".equals(vehicle.getpVehicleName())
                )
            return false;
        return true;
    }

    @Override
    public List<HzVehicleRecord> doGetAllVehicle() {
        return hzVehicleRecordDao.selectAll();
    }
}
