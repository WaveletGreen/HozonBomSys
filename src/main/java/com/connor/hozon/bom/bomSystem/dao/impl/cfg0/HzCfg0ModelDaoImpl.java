package com.connor.hozon.bom.bomSystem.dao.impl.cfg0;

import com.connor.hozon.bom.bomSystem.dao.cfg.HzCfg0ModelDetailDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sql.IBaseSQLUtil;
import sql.pojo.cfg.HzCfg0ModelDetail;

import java.util.ArrayList;
import java.util.List;

/**
 * User: Fancyears·Maylos·Mayways
 * Date: 2018/5/21
 * Time: 16:30
 */
@Service("hzCfg0ModelDetailDao")
public class HzCfg0ModelDaoImpl implements HzCfg0ModelDetailDao {
    @Autowired
    IBaseSQLUtil baseSQLUtil;

    @Override
    public int insertOne(HzCfg0ModelDetail detail) {
        return baseSQLUtil.executeInsert(detail, "com.connor.hozon.bom.bomSystem.dao.cfg.HzCfg0ModelDetailDao.insert");
    }

    @Override
    public int updateOne(HzCfg0ModelDetail detail) {
        return baseSQLUtil.executeUpdate(detail, "com.connor.hozon.bom.bomSystem.dao.cfg.HzCfg0ModelDetailDao.update");
    }

    @Override
    public HzCfg0ModelDetail selectByModelId(HzCfg0ModelDetail detail) {
        return baseSQLUtil.executeQueryById(detail, "com.connor.hozon.bom.bomSystem.dao.cfg.HzCfg0ModelDetailDao.selectByModelId");
    }

    /**
     * 连同车型模型的数据都一起查出来
     *
     * @param detail
     * @return
     */
    @Override
    public HzCfg0ModelDetail selectByModelId2(HzCfg0ModelDetail detail) {
        return baseSQLUtil.executeQueryById(detail, "com.connor.hozon.bom.bomSystem.dao.cfg.HzCfg0ModelDetailDao.selectByModelId2");
    }


    @Override
    public List<HzCfg0ModelDetail> selectByModelIds(List<HzCfg0ModelDetail> hzCfg0ModelDetails) {
        return baseSQLUtil.executeQueryByPass(new HzCfg0ModelDetail(), hzCfg0ModelDetails, "com.connor.hozon.bom.bomSystem.dao.cfg.HzCfg0ModelDetailDao.selectByModelIds");
    }


}
