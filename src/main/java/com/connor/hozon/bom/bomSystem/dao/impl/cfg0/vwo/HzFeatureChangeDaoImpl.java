package com.connor.hozon.bom.bomSystem.dao.impl.cfg0.vwo;

import com.connor.hozon.bom.bomSystem.dao.cfg.vwo.HzFeatureChangeDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import sql.IBaseSQLUtil;
import sql.pojo.cfg.vwo.HzFeatureChangeBean;

/**
 * @Author: Fancyears·Maylos·Maywas
 * @Description:
 * @Date: Created in 2018/8/9 19:15
 * @Modified By:
 */
@Configuration
public class HzFeatureChangeDaoImpl implements HzFeatureChangeDao {
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
        HzFeatureChangeBean bean = new HzFeatureChangeBean();
        bean.setId(id);
        return baseSQLUtil.executeDelete(bean, "com.connor.hozon.bom.bomSystem.dao.cfg.vwo.HzFeatureChangeDao.deleteByPrimaryKey");
    }

    /**
     * 插入1条数据
     *
     * @param record
     * @return
     */
    @Override
    public int insert(HzFeatureChangeBean record) {
        return baseSQLUtil.executeInsert(record, "com.connor.hozon.bom.bomSystem.dao.cfg.vwo.HzFeatureChangeDao.insert");
    }

    /**
     * 主键查找
     *
     * @param id
     * @return
     */
    @Override
    public HzFeatureChangeBean selectByPrimaryKey(Long id) {
        HzFeatureChangeBean bean = new HzFeatureChangeBean();
        bean.setId(id);
        return baseSQLUtil.executeQueryById(bean, "com.connor.hozon.bom.bomSystem.dao.cfg.vwo.HzFeatureChangeDao.selectByPrimaryKey");
    }

    /**
     * 查找特性下最新的更改
     *
     * @param bean
     * @return
     */
    @Override
    public HzFeatureChangeBean findNewestChange(HzFeatureChangeBean bean) {
        return baseSQLUtil.executeQueryById(bean, "com.connor.hozon.bom.bomSystem.dao.cfg.vwo.HzFeatureChangeDao.findNewestChange");
    }


    /**
     * 主键更新
     *
     * @param record
     * @return
     */
    @Override
    public int updateByPrimaryKey(HzFeatureChangeBean record) {
        return baseSQLUtil.executeUpdate(record, "com.connor.hozon.bom.bomSystem.dao.cfg.vwo.HzFeatureChangeDao.updateByPrimaryKey");
    }
}
