package com.connor.hozon.bom.bomSystem.service.project;

import com.connor.hozon.bom.bomSystem.dao.project.HzVehicleRecordDao;
import com.connor.hozon.bom.bomSystem.iservice.project.IHzVehicleService;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sql.pojo.project.HzVehicleRecord;

import java.util.List;

import static com.connor.hozon.bom.bomSystem.helper.StringHelper.checkString;

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
    public boolean doInsertOne(HzVehicleRecord vehicle) {
        return hzVehicleRecordDao.insert(vehicle) > 0 ? true : false;
    }

    @Override
    public boolean doDeleteByPuid(String puid) {
        return hzVehicleRecordDao.deleteByPrimaryKey(puid) > 0 ? true : false;
    }

    @Override
    public boolean doUpdateByPuid(HzVehicleRecord vehicle) {
        return hzVehicleRecordDao.updateByPrimaryKey(vehicle) > 0 ? true : false;
    }

    @Override
    public boolean validate(HzVehicleRecord vehicle) {
        if (null == vehicle ||
//                null == vehicle.getPuid() ||
                null == vehicle.getpVehicleCode() ||
                null == vehicle.getpVehicleName() ||
//                "".equals(vehicle.getPuid()) ||
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

    @Override
    public HzVehicleRecord doGetByVehicleCode(String s) {
        return hzVehicleRecordDao.selectByCode(s);
    }

    @Override
    public JSONObject doValidateCodeWithPuid(IProject vehicle) {
        JSONObject result = new JSONObject();
        result.put("valid", true);
        HzVehicleRecord _vehicle;
        //有puid，则时更新，没有则为新增
        if (checkString(vehicle.getPuid())) {
            _vehicle = doGetByPuid(vehicle.getPuid());
            //根据puid查出来的同名，代表自身，则验证通过
            if (_vehicle.getCode().trim().equals(vehicle.getCode().trim())) {
                result.put("valid", true);
            } else if ((_vehicle = doGetByVehicleCode(vehicle.getCode().trim())) != null) {
                //不是自身，更换了代号，检查是否有同代号的，有同代号则不允许验证通过
                result.put("valid", false);
            }
        } else if ((_vehicle = doGetByVehicleCode(vehicle.getCode().trim())) != null) {
            result.put("valid", false);
        }
        return result;
    }
}
