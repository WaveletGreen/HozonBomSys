package com.connor.hozon.bom.bomSystem.dao.impl.cfg0;

import com.connor.hozon.bom.bomSystem.bean.HzExFullCfgWithCfg;
import com.connor.hozon.bom.bomSystem.dao.cfg.HzFullCfgWithCfgDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import sql.IBaseSQLUtil;
import sql.pojo.cfg.HzFullCfgModel;
import sql.pojo.cfg.HzFullCfgWithCfg;

import java.math.BigDecimal;
import java.util.List;

/**
 * @Author: Fancyears·Maylos·Maywas
 * @Description:
 * @Date: Created in 2018/8/28 15:58
 * @Modified By:
 */
@Configuration
public class HzFullCfgWithCfgDaoImpl implements HzFullCfgWithCfgDao {
    @Autowired
    IBaseSQLUtil baseSQLUtil;
    private static final HzFullCfgWithCfg WITH_CFG = new HzFullCfgWithCfg();

    @Override
    public int deleteByPrimaryKey(Long id) {
        WITH_CFG.setId(id);
        return baseSQLUtil.executeDelete(WITH_CFG, "com.connor.hozon.bom.bomSystem.dao.cfg.HzFullCfgWithCfgDao.deleteByPrimaryKey");
    }

    @Override
    public int insert(List<HzFullCfgWithCfg> hzFullCfgWithCfgs) {
        return baseSQLUtil.executeInsert(hzFullCfgWithCfgs, "com.connor.hozon.bom.bomSystem.dao.cfg.HzFullCfgWithCfgDao.insert");
    }

    @Override
    public int insertSelective(HzFullCfgWithCfg record) {
        return baseSQLUtil.executeInsert(record, "com.connor.hozon.bom.bomSystem.dao.cfg.HzFullCfgWithCfgDao.insertSelective");
    }

    @Override
    public HzFullCfgWithCfg selectByPrimaryKey(Long id) {
        WITH_CFG.setId(id);
        return baseSQLUtil.executeQueryById(WITH_CFG, "com.connor.hozon.bom.bomSystem.dao.cfg.HzFullCfgWithCfgDao.selectByPrimaryKey");
    }

    @Override
    public List<HzFullCfgWithCfg> selectByMainID(BigDecimal flCfgVersion) {
        WITH_CFG.setFlCfgVersion(flCfgVersion);
        return baseSQLUtil.executeQuery(WITH_CFG, "com.connor.hozon.bom.bomSystem.dao.cfg.HzFullCfgWithCfgDao.selectByMainID");
    }

    @Override
    public int updateByPrimaryKeySelective(HzFullCfgWithCfg record) {
        return baseSQLUtil.executeUpdate(record, "com.connor.hozon.bom.bomSystem.dao.cfg.HzFullCfgWithCfgDao.updateByPrimaryKeySelective");
    }

    @Override
    public int updateByPrimaryKey(HzFullCfgWithCfg record) {
        return baseSQLUtil.executeUpdate(record, "com.connor.hozon.bom.bomSystem.dao.cfg.HzFullCfgWithCfgDao.updateByPrimaryKey");
    }

    @Override
    public int insertBomLine(List<HzFullCfgWithCfg> hzFullCfgWithCfgs) {
        return baseSQLUtil.executeInsert(hzFullCfgWithCfgs, "com.connor.hozon.bom.bomSystem.dao.cfg.HzFullCfgWithCfgDao.insertBomLine");
    }

    @Override
    public int updateByBomLinePuid(HzFullCfgWithCfg hzFullCfgWithCfg) {
        return baseSQLUtil.executeUpdate(hzFullCfgWithCfg, "com.connor.hozon.bom.bomSystem.dao.cfg.HzFullCfgWithCfgDao.updateByBomLinePuid");
    }

    @Override
    public List<HzFullCfgWithCfg> query2YCfgByProjectId(String projectId) {
        return baseSQLUtil.executeQueryByPass(new HzFullCfgWithCfg(), projectId, "com.connor.hozon.bom.bomSystem.dao.cfg.HzFullCfgWithCfgDao.query2YCfgByProjectId");
    }

    @Override
    public HzFullCfgWithCfg selectByBomLineUidWithVersion(BigDecimal version, String puid) {
        HzFullCfgWithCfg cfg = new HzFullCfgWithCfg();
        cfg.setFlCfgVersion(version);
        cfg.setCfgBomlineUid(puid);
        return baseSQLUtil.executeQueryById(cfg, "com.connor.hozon.bom.bomSystem.dao.cfg.HzFullCfgWithCfgDao.selectByBomLineUidWithVersion");
    }

    @Override
    public HzFullCfgWithCfg query2YCfgByBomLineId(String bomLineId) {
        return baseSQLUtil.executeQueryByPass(new HzFullCfgWithCfg(), bomLineId, "com.connor.hozon.bom.bomSystem.dao.cfg.HzFullCfgWithCfgDao.query2YCfgByBomLineId", true);
    }

    @Override
    public HzExFullCfgWithCfg selectByBLOutWithCfg(BigDecimal version, String puid) {
        HzExFullCfgWithCfg cfg = new HzExFullCfgWithCfg();
        cfg.setFlCfgVersion(version);
        cfg.setCfgBomlineUid(puid);
        return baseSQLUtil.executeQueryById(cfg, "com.connor.hozon.bom.bomSystem.dao.cfg.HzFullCfgWithCfgDao.selectByBLOutWithCfg");
    }

    /**
     * 根据2Y和全配主键一起查询，连带出特性值对象和BOMLine对象
     *
     * @param version
     * @param puid
     * @return
     */
    @Override
    public HzExFullCfgWithCfg selectByBLOutWithCfgAndBL(BigDecimal version, String puid) {
        HzExFullCfgWithCfg cfg = new HzExFullCfgWithCfg();
        cfg.setFlCfgVersion(version);
        cfg.setCfgBomlineUid(puid);
        return baseSQLUtil.executeQueryById(cfg, "com.connor.hozon.bom.bomSystem.dao.cfg.HzFullCfgWithCfgDao.selectByBLOutWithCfgAndBL");
    }


}
