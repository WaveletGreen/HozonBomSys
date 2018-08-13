package com.connor.hozon.bom.bomSystem.dao.impl.cfg0.vwo;

import com.connor.hozon.bom.bomSystem.dao.cfg.vwo.HzVwoInfoDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import sql.IBaseSQLUtil;
import sql.pojo.cfg.vwo.HzVwoInfo;

/**
 * @Author: Fancyears·Maylos·Maywas
 * @Description:
 * @Date: Created in 2018/8/10 13:46
 * @Modified By:
 */
@Configuration
public class HzVwoInfoDaoImpl implements HzVwoInfoDao {
    @Autowired
    IBaseSQLUtil baseSQLUtil;

    /**
     * 主键删除
     *
     * @param id
     * @return
     */
    @Override
    public int deleteByPrimaryKey(Long id) {
        return baseSQLUtil.executeDelete(id, "com.connor.hozon.bom.bomSystem.dao.cfg.vwo.HzVwoInfoDao.deleteByPrimaryKey");
    }

    /**
     * 插入1条vwo
     *
     * @param record
     * @return
     */
    @Override
    public int insert(HzVwoInfo record) {
        return baseSQLUtil.executeInsert(record, "com.connor.hozon.bom.bomSystem.dao.cfg.vwo.HzVwoInfoDao.insert");
    }

    /**
     * 主键搜索
     *
     * @param id
     * @return
     */
    @Override
    public HzVwoInfo selectByPrimaryKey(Long id) {
        HzVwoInfo hzVwoInfo = new HzVwoInfo();
        hzVwoInfo.setId(id);
        return baseSQLUtil.executeQueryById(hzVwoInfo, "com.connor.hozon.bom.bomSystem.dao.cfg.vwo.HzVwoInfoDao.selectByPrimaryKey");
    }

    /**
     * 更新vwo
     *
     * @param record
     * @return
     */
    @Override
    public int updateByPrimaryKey(HzVwoInfo record) {
        return baseSQLUtil.executeUpdate(record, "com.connor.hozon.bom.bomSystem.dao.cfg.vwo.HzVwoInfoDao.updateByPrimaryKey");
    }

    /**
     * 寻找当月最大的vwo
     *
     * @return
     */
    @Override
    public HzVwoInfo findMaxAreaVwoNum() {
        return baseSQLUtil.executeQueryById(new HzVwoInfo(), "com.connor.hozon.bom.bomSystem.dao.cfg.vwo.HzVwoInfoDao.findMaxAreaVwoNum");
    }
}
