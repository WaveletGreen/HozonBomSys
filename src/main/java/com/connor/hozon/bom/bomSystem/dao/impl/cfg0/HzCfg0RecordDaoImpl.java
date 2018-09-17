package com.connor.hozon.bom.bomSystem.dao.impl.cfg0;

import com.connor.hozon.bom.bomSystem.dto.HzFeatureQueryDTO;
import com.connor.hozon.bom.bomSystem.dto.HzMaterielFeatureBean;
import com.connor.hozon.bom.bomSystem.dao.cfg.HzCfg0RecordDao;
import com.connor.hozon.bom.common.base.entity.QueryBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sql.IBaseSQLUtil;
import sql.pojo.cfg.HzCfg0Record;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service("hzCfg0RecordDao")
public class HzCfg0RecordDaoImpl implements HzCfg0RecordDao {

    private final IBaseSQLUtil baseSQLUtil;
    private final static HzCfg0Record RECORD = new HzCfg0Record();

    @Autowired
    public HzCfg0RecordDaoImpl(IBaseSQLUtil baseSQLUtil) {
        this.baseSQLUtil = baseSQLUtil;
    }

    @Override
    public int deleteByPrimaryKey(String puid) {
        return baseSQLUtil.executeDeleteBySome("com.connor.hozon.bom.bomSystem.dao.cfg.HzCfg0RecordDao.deleteByPrimaryKey", puid, "HZ_CFG0_RECORD");

//        HzCfg0Record record = new HzCfg0Record();
//        record.setPuid(puid);
//        record.setWhichTable("HZ_CFG0_RECORD");
//        return baseSQLUtil.executeDelete(record, "com.connor.hozon.bom.bomSystem.dao.cfg.HzCfg0RecordDao.deleteByPrimaryKey");
    }

    @Override
    public int deleteAddCfgByPrimaryKey(String puid) {
        return baseSQLUtil.executeDeleteBySome("com.connor.hozon.bom.bomSystem.dao.cfg.HzCfg0RecordDao.deleteByPrimaryKey", puid, "HZ_CFG0_ADD_CFG_RECORD");
//        HzCfg0Record record = new HzCfg0Record();
//        record.setPuid(puid);
//        record.setWhichTable("HZ_CFG0_ADD_CFG_RECORD");
//        return baseSQLUtil.executeDelete(record, "com.connor.hozon.bom.bomSystem.dao.cfg.HzCfg0RecordDao.deleteByPrimaryKey");
    }

    @Override
    public int insert(HzCfg0Record record) {
        record.setWhichTable("HZ_CFG0_RECORD");
        return baseSQLUtil.executeInsert(record, "com.connor.hozon.bom.bomSystem.dao.cfg.HzCfg0RecordDao.insert");
    }

    @Override
    public int insertAddCfg(HzCfg0Record record) {
        record.setWhichTable("HZ_CFG0_ADD_CFG_RECORD");
        return baseSQLUtil.executeInsert(record, "com.connor.hozon.bom.bomSystem.dao.cfg.HzCfg0RecordDao.insert");
    }

    @Override
    public int updateByPrimaryKey(HzCfg0Record record) {
        record.setWhichTable("HZ_CFG0_RECORD");
        return baseSQLUtil.executeUpdate(record, "com.connor.hozon.bom.bomSystem.dao.cfg.HzCfg0RecordDao.updateByPrimaryKey");
    }

    @Override
    public int updateAddedCfgByPrimaryKey(HzCfg0Record record) {
        record.setWhichTable("HZ_CFG0_ADD_CFG_RECORD");
        return baseSQLUtil.executeUpdate(record, "com.connor.hozon.bom.bomSystem.dao.cfg.HzCfg0RecordDao.updateByPrimaryKey");
    }

    @Override
    public int deleteAddedCfgByList(List<HzCfg0Record> records) {
        return baseSQLUtil.executeDelete(records, "com.connor.hozon.bom.bomSystem.dao.cfg.HzCfg0RecordDao.deleteAddedCfgByList");
    }

    @Override
    public List<HzCfg0Record> selectCfg0ListByPuids(Map<String, Object> _map) {
        return baseSQLUtil.executeQueryByPass(new HzCfg0Record(), _map, "com.connor.hozon.bom.bomSystem.dao.cfg.HzCfg0RecordDao.selectCfg0ListByPuids");
    }

    @Override
    public List<HzCfg0Record> selectByCodeAndDesc(HzCfg0Record record) {
        return baseSQLUtil.executeQuery(record, "com.connor.hozon.bom.bomSystem.dao.cfg.HzCfg0RecordDao.selectByCodeAndDesc");
    }

    @Override
    public int deleteCfgByList(List<HzCfg0Record> records) {
        return baseSQLUtil.executeDelete(records, "com.connor.hozon.bom.bomSystem.dao.cfg.HzCfg0RecordDao.deleteCfgByList");
    }

    @Override
    public int setIsSent(Map<String, Object> _map) {
        return baseSQLUtil.executeUpdate(_map, "com.connor.hozon.bom.bomSystem.dao.cfg.HzCfg0RecordDao.setIsSent");
    }

    /**
     * 设置进入流程状态
     *
     * @param _map
     * @return
     */
    @Override
    public int setToProcess(Map<String, Object> _map) {
        return baseSQLUtil.executeUpdate(_map, "com.connor.hozon.bom.bomSystem.dao.cfg.HzCfg0RecordDao.setToProcess");
    }


    @Override
    public int tellMeHowManyOfThose(HzFeatureQueryDTO dto) {
        List<Integer> result = baseSQLUtil.executeQueryByPass(new Integer(0), dto, "com.connor.hozon.bom.bomSystem.dao.cfg.HzCfg0RecordDao.tellMeHowManyOfThose");
        if (result == null) {
            return 0;
        } else {
            return result.get(0);
        }
    }

    @Override
    public List<HzCfg0Record> selectByCondition(HzFeatureQueryDTO queryBase) {
        return baseSQLUtil.executeQueryByPass(RECORD, queryBase, "com.connor.hozon.bom.bomSystem.dao.cfg.HzCfg0RecordDao.selectByCondition");
    }

    @Override
    public List<HzCfg0Record> selectListByProjectPuid(String projectPuid, HzFeatureQueryDTO queryBase) {
        Map<String, Object> params = new HashMap<>();
        params.put("projectPuid", projectPuid);
        params.put("whichTable", "HZ_CFG0_RECORD");
        params.put("param", queryBase);
        return baseSQLUtil.executeQueryByPass(RECORD, params, "com.connor.hozon.bom.bomSystem.dao.cfg.HzCfg0RecordDao.selectListByProjectPuid");
    }

    @Override
    public List<HzCfg0Record> selectAddedCfgListByProjectPuid(String projectPuid) {
        HzCfg0Record record = new HzCfg0Record();
        record.setProjectPuid(projectPuid);
        record.setWhichTable("HZ_CFG0_ADD_CFG_RECORD");
        return baseSQLUtil.executeQuery(record, "com.connor.hozon.bom.bomSystem.dao.cfg.HzCfg0RecordDao.selectListByProjectPuid");
    }

    @Override
    public List<HzMaterielFeatureBean> selectMaterielFeatureByProjectPuid(String projectPuid) {
        return baseSQLUtil.executeQueryByPass(new HzMaterielFeatureBean(), projectPuid, "com.connor.hozon.bom.bomSystem.dao.cfg.HzCfg0RecordDao.selectMaterielFeatureByProjectPuid");
    }

    @Override
    public HzCfg0Record selectByPrimaryKey(String puid) {
        HzCfg0Record record = new HzCfg0Record();
        record.setPuid(puid);
        record.setWhichTable("HZ_CFG0_RECORD");
        return baseSQLUtil.executeQueryById(record, "com.connor.hozon.bom.bomSystem.dao.cfg.HzCfg0RecordDao.selectByPrimaryKey");
    }

    @Override
    public HzCfg0Record selectOneAddedCfgByPuid(String puid) {
        HzCfg0Record record = new HzCfg0Record();
        record.setPuid(puid);
        record.setWhichTable("HZ_CFG0_ADD_CFG_RECORD");
        return baseSQLUtil.executeQueryById(record, "com.connor.hozon.bom.bomSystem.dao.cfg.HzCfg0RecordDao.selectByPrimaryKey");
    }


}
