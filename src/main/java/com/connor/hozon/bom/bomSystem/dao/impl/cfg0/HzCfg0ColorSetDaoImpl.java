package com.connor.hozon.bom.bomSystem.dao.impl.cfg0;

import com.connor.hozon.bom.bomSystem.dao.cfg.HzCfg0ColorSetDao;
import com.connor.hozon.bom.common.base.entity.QueryBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sql.BaseSQLUtil;
import sql.IBaseSQLUtil;
import sql.pojo.cfg.HzCfg0ColorSet;

import java.util.List;

/**
 * User: Fancyears·Maylos·Mayways
 * Date: 2018/5/21
 * Time: 14:48
 */
@Service("hzCfg0ColorSetDao")
public class HzCfg0ColorSetDaoImpl implements HzCfg0ColorSetDao {

    @Autowired
    IBaseSQLUtil baseSQLUtil;

    @Override
    public List<HzCfg0ColorSet> queryAll2(QueryBase queryBase) {
        HzCfg0ColorSet set = new HzCfg0ColorSet();
        queryBase.setSort(set.reflectToDBField(queryBase.getSort()));
        List<HzCfg0ColorSet> colorSet = baseSQLUtil.executeQueryByPass(set, queryBase, "com.connor.hozon.bom.bomSystem.dao.cfg.HzCfg0ColorSetDao.selectAll");
        return colorSet;
    }

    /**
     * @return
     * @Author: Fancyears·Maylos·Mayways
     * @Description: 检索所有的颜色集
     * @Date: 2018/5/21 17:09
     */
    @Override
    public List<HzCfg0ColorSet> queryAll2() {
        List<HzCfg0ColorSet> colorSet = baseSQLUtil.executeQueryByPass(new HzCfg0ColorSet(), new QueryBase(), "com.connor.hozon.bom.bomSystem.dao.cfg.HzCfg0ColorSetDao.selectAll");
        return colorSet;
    }

    @Override
    public HzCfg0ColorSet selectById(HzCfg0ColorSet entity) {
        return baseSQLUtil.executeQueryById(entity, "com.connor.hozon.bom.bomSystem.dao.cfg.HzCfg0ColorSetDao.selectByPrimaryKey");
    }

    /**
     * 根据颜色代码寻找颜色对象
     *
     * @param entity
     * @return 找到的颜色对象
     * @Author: Fancyears·Maylos·Mayways
     * @Description: 根据颜色的id，找到该颜色对象
     * @Date: 2018/5/21 17:09
     */
    @Override
    public HzCfg0ColorSet selectByColorCode(HzCfg0ColorSet entity) {
        return baseSQLUtil.executeQueryById(entity, "com.connor.hozon.bom.bomSystem.dao.cfg.HzCfg0ColorSetDao.selectByColorCode");
    }

    @Override
    public int updateOne(HzCfg0ColorSet entity) {
        return baseSQLUtil.executeUpdate(entity, "com.connor.hozon.bom.bomSystem.dao.cfg.HzCfg0ColorSetDao.updateByPrimaryKey");
    }

    /**
     * @param entity 更新的颜色对象的状态
     * @return 影响行数
     * @Author: Fancyears·Maylos·Mayways
     * @Description: 执行更细颜色信息
     * @Date: 2018/5/21 17:10
     */
    @Override
    public int updateStatusByPk(HzCfg0ColorSet entity) {
        return baseSQLUtil.executeUpdate(entity, "com.connor.hozon.bom.bomSystem.dao.cfg.HzCfg0ColorSetDao.updateStatusByPk");
    }

    @Override
    public int deleteByList(List<HzCfg0ColorSet> entity) {
        return baseSQLUtil.executeDelete(entity, "com.connor.hozon.bom.bomSystem.dao.cfg.HzCfg0ColorSetDao.deleteByBatch");
    }

    /**
     * @param entity 传入的颜色集合
     * @return
     * @Author: Fancyears·Maylos·Mayways
     * @Description: 批量逻辑删除颜色信息，设置颜色删除状态为0
     * @Date: 2018/5/21 17:10
     */
    @Override
    public int logicDeleteByBatch(List<HzCfg0ColorSet> entity) {
        return baseSQLUtil.executeDelete(entity, "com.connor.hozon.bom.bomSystem.dao.cfg.HzCfg0ColorSetDao.logicDeleteByBatch");
    }

    /**
     * 查询全部的颜色数量
     *
     * @return
     */
    @Override
    public int tellMeHowManyOfIt() {
        return baseSQLUtil.executeQueryById(new Integer(0), "com.connor.hozon.bom.bomSystem.dao.cfg.HzCfg0ColorSetDao.tellMeHowManyOfIt");
    }

    @Override
    public int insertOne(HzCfg0ColorSet entity) {
        return baseSQLUtil.executeInsert(entity, "com.connor.hozon.bom.bomSystem.dao.cfg.HzCfg0ColorSetDao.insertOne");
    }

    private final void preCheck() {
        if (baseSQLUtil == null) {
            baseSQLUtil = new BaseSQLUtil();
            System.out.println(IBaseSQLUtil.class.getName() + "没有自动装配");
        }
    }
}
