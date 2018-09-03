package com.connor.hozon.bom.bomSystem.dao.impl.cfg0;

import com.connor.hozon.bom.bomSystem.dao.BasicDaoImpl;
import com.connor.hozon.bom.bomSystem.dao.cfg.HzDerivativeMaterielBasicDao;
import org.springframework.context.annotation.Configuration;
import sql.pojo.cfg.HzDerivativeMaterielBasic;

import java.util.List;
import java.util.Map;

/**
 * @Author: Fancyears·Maylos·Maywas
 * @Description:
 * @Date: Created in 2018/8/30 17:00
 * @Modified By:
 */
@Configuration
public class HzDerivativeMaterielBasicDaoImpl extends BasicDaoImpl<HzDerivativeMaterielBasic> implements HzDerivativeMaterielBasicDao {

    private final static HzDerivativeMaterielBasic BASIC = new HzDerivativeMaterielBasic();

    public HzDerivativeMaterielBasicDaoImpl() {
        clz = HzDerivativeMaterielBasicDao.class;
    }

    /**
     * 根据项目查找项目下的所有配置物料特性数据
     *
     * @param params
     * @return
     */
    public List<HzDerivativeMaterielBasic> selectByProjectUid(Map<String, Object> params) {
        return baseSQLUtil.executeQueryByPass(BASIC, params, clz.getCanonicalName() + ".selectByProjectUid");
    }

//    /**
//     * 主键删除
//     *
//     * @param basic
//     * @return
//     */
//    @Override
//    public int deleteByPrimaryKey(HzDerivativeMaterielBasic basic) {
//        return baseSQLUtil.executeDelete(basic, "com.connor.hozon.bom.bomSystem.dao.cfg.HzDerivativeMaterielBasicDao.deleteByPrimaryKey");
//    }

//    /**
//     * 全插入
//     *
//     * @param basic
//     * @return
//     */
//    @Override
//    public int insert(HzDerivativeMaterielBasic basic) {
//        return baseSQLUtil.executeInsert(basic, "com.connor.hozon.bom.bomSystem.dao.cfg.HzDerivativeMaterielBasicDao.insert");
//    }
//
//    /**
//     * 选择插入
//     *
//     * @param basic
//     * @return
//     */
//    @Override
//    public int insertSelective(HzDerivativeMaterielBasic basic) {
//        return baseSQLUtil.executeInsert(basic, "com.connor.hozon.bom.bomSystem.dao.cfg.HzDerivativeMaterielBasicDao.insertSelective");
//    }
//
//    /**
//     * 主键查询
//     *
//     * @param basic
//     * @return
//     */
//    @Override
//    public HzDerivativeMaterielBasic selectByPrimaryKey(HzDerivativeMaterielBasic basic) {
//        return baseSQLUtil.executeQueryById(basic, "com.connor.hozon.bom.bomSystem.dao.cfg.HzDerivativeMaterielBasicDao.selectByPrimaryKey");
//    }
//
//    /**
//     * 主键选择更新
//     *
//     * @param basic
//     * @return
//     */
//    @Override
//    public int updateByPrimaryKeySelective(HzDerivativeMaterielBasic basic) {
//        return baseSQLUtil.executeUpdate(basic, "com.connor.hozon.bom.bomSystem.dao.cfg.HzDerivativeMaterielBasicDao.updateByPrimaryKeySelective");
//    }
//
//    /**
//     * 主键全更新
//     *
//     * @param basic
//     * @return
//     */
//    @Override
//    public int updateByPrimaryKey(HzDerivativeMaterielBasic basic) {
//        return baseSQLUtil.executeUpdate(basic, "com.connor.hozon.bom.bomSystem.dao.cfg.HzDerivativeMaterielBasicDao.updateByPrimaryKey");
//    }
}
