package com.connor.hozon.bom.bomSystem.dao.impl.cfg0;

import com.connor.hozon.bom.bomSystem.dao.cfg.HzColorLvl2ModelDao;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import sql.IBaseSQLUtil;
import sql.pojo.cfg.HzColorLvl2Model;

import java.util.List;

/**
 * @Author: Fancyears·Maylos·Maywas
 * @Description:
 * @Date: Created in 2018/8/3 10:56
 * @Modified By:
 */
@Configuration
public class HzColorLvl2ModelDaoImpl implements HzColorLvl2ModelDao {
    private final static HzColorLvl2Model lvl2Model = new HzColorLvl2Model();

    @Autowired
    IBaseSQLUtil baseSQLUtil;

    /**
     * 主键删除
     *
     * @param puid 主键
     * @return
     */
    @Override
    public int deleteByPrimaryKey(String puid) {
        return baseSQLUtil.executeDeleteByPass(puid, "com.connor.hozon.bom.bomSystem.dao.cfg.HzColorLvl2ModelDao.deleteByPrimaryKey");
    }

    /**
     * 插入
     *
     * @param record 二级配色方案
     * @return
     */
    @Override
    public int insert(HzColorLvl2Model record) {
        return baseSQLUtil.executeInsert(record, "com.connor.hozon.bom.bomSystem.dao.cfg.HzColorLvl2ModelDao.insert");
    }

    /**
     * 主键筛选
     *
     * @param puid 主键
     * @return
     */
    @Override
    public HzColorLvl2Model selectByPrimaryKey(String puid) {
        return baseSQLUtil.executeQueryByPass(lvl2Model, puid, "com.connor.hozon.bom.bomSystem.dao.cfg.HzColorLvl2ModelDao.selectByPrimaryKey", true);
    }

    /**
     * 模型主键筛选
     *
     * @param pModelUid 模型主键
     * @return
     */
    @Override
    public List<HzColorLvl2Model> selectByModelUid(@Param("pModelUid") String pModelUid) {
        return baseSQLUtil.executeQueryByPass(lvl2Model, pModelUid, "com.connor.hozon.bom.bomSystem.dao.cfg.HzColorLvl2ModelDao.selectByModelUid");
    }

    /**
     * 主键更新
     *
     * @param record 二级配色方案
     * @return
     */
    @Override
    public int updateByPrimaryKey(HzColorLvl2Model record) {
        return baseSQLUtil.executeUpdate(record, "com.connor.hozon.bom.bomSystem.dao.cfg.HzColorLvl2ModelDao.updateByPrimaryKey");
    }


}
