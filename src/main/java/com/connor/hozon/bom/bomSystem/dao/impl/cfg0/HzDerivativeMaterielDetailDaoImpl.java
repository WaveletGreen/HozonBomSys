package com.connor.hozon.bom.bomSystem.dao.impl.cfg0;

import com.connor.hozon.bom.bomSystem.dao.BasicDaoImpl;
import com.connor.hozon.bom.bomSystem.dao.cfg.HzDerivativeMaterielDetailDao;
import org.springframework.context.annotation.Configuration;
import sql.pojo.cfg.HzDerivativeMaterielDetail;

import java.util.List;

/**
 * @Author: Fancyears·Maylos·Maywas
 * @Description:
 * @Date: Created in 2018/8/30 17:00
 * @Modified By:
 */
@Configuration
public class HzDerivativeMaterielDetailDaoImpl
        extends BasicDaoImpl<HzDerivativeMaterielDetail>
        implements HzDerivativeMaterielDetailDao {

    public HzDerivativeMaterielDetailDaoImpl() {
        clz = HzDerivativeMaterielDetailDao.class;
    }

    /**
     * 基本信息外键查询，附带特性值信息
     *
     * @param detail
     * @return
     */
    @Override
    public List<HzDerivativeMaterielDetail> selectByBasicWithCfg(HzDerivativeMaterielDetail detail) {
        return baseSQLUtil.executeQuery(detail, clz.getCanonicalName() + ".selectByBasicWithCfg");
    }

    /**
     * 批量插入
     *
     * @param details
     * @return
     */
    @Override
    public int insertByBatch(List<HzDerivativeMaterielDetail> details) {
        return baseSQLUtil.executeInsert(details, clz.getCanonicalName() + ".insertByBatch");
    }

//    /**
//     * 主键删除
//     *
//     * @param detail
//     * @return
//     */
//    @Override
//    public int deleteByPrimaryKey(HzDerivativeMaterielDetail detail) {
//        return baseSQLUtil.executeDelete(detail, "com.connor.hozon.bom.bomSystem.dao.cfg.HzDerivativeMaterielDetailDao.deleteByPrimaryKey");
//    }
//
//    /**
//     * 全插入
//     *
//     * @param detail
//     * @return
//     */
//    @Override
//    public int insert(HzDerivativeMaterielDetail detail) {
//        return baseSQLUtil.executeInsert(detail, "com.connor.hozon.bom.bomSystem.dao.cfg.HzDerivativeMaterielDetailDao.insert");
//    }
//
//    /**
//     * 选择插入
//     *
//     * @param detail
//     * @return
//     */
//    @Override
//    public int insertSelective(HzDerivativeMaterielDetail detail) {
//        return baseSQLUtil.executeInsert(detail, "com.connor.hozon.bom.bomSystem.dao.cfg.HzDerivativeMaterielDetailDao.insertSelective");
//    }
//
//    /**
//     * 主键查询
//     *
//     * @param detail
//     * @return
//     */
//    @Override
//    public HzDerivativeMaterielDetail selectByPrimaryKey(HzDerivativeMaterielDetail detail) {
//        return baseSQLUtil.executeQueryById(detail, "com.connor.hozon.bom.bomSystem.dao.cfg.HzDerivativeMaterielDetailDao.selectByPrimaryKey");
//    }
//
//    /**
//     * 主键选择更新
//     *
//     * @param detail
//     * @return
//     */
//    @Override
//    public int updateByPrimaryKeySelective(HzDerivativeMaterielDetail detail) {
//        return baseSQLUtil.executeUpdate(detail, "com.connor.hozon.bom.bomSystem.dao.cfg.HzDerivativeMaterielDetailDao.updateByPrimaryKeySelective");
//    }
//
//    /**
//     * 主键全更新
//     *
//     * @param detail
//     * @return
//     */
//    @Override
//    public int updateByPrimaryKey(HzDerivativeMaterielDetail detail) {
//        return baseSQLUtil.executeUpdate(detail, "com.connor.hozon.bom.bomSystem.dao.cfg.HzDerivativeMaterielDetailDao.updateByPrimaryKey");
//    }
}
