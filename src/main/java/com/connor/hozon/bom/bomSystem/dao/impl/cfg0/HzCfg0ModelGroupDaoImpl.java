package com.connor.hozon.bom.bomSystem.dao.impl.cfg0;

import com.connor.hozon.bom.bomSystem.dao.cfg.HzCfg0ModelGroupDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import sql.IBaseSQLUtil;
import sql.pojo.cfg.HzCfg0ModelGroup;

/**
 * @Author: Fancyears·Maylos·Maywas
 * @Description:
 * @Date: Created in 2018/8/15 10:18
 * @Modified By:
 */
@Configuration
public class HzCfg0ModelGroupDaoImpl implements HzCfg0ModelGroupDao {
    private static final HzCfg0ModelGroup GROUP = new HzCfg0ModelGroup();
    @Autowired
    IBaseSQLUtil baseSQLUtil;

    /**
     * 主键删除
     *
     * @param id
     * @return
     */
    @Override
    public int deleteByPrimaryKey(String id) {
        return baseSQLUtil.executeDeleteByPass(id, "com.connor.hozon.bom.bomSystem.dao.cfg.HzCfg0ModelGroupDao.deleteByPrimaryKey");
    }

    /**
     * 插入1条模型族数据
     *
     * @param record
     * @return
     */
    @Override
    public int insert(HzCfg0ModelGroup record) {
        return baseSQLUtil.executeInsert(record, "com.connor.hozon.bom.bomSystem.dao.cfg.HzCfg0ModelGroupDao.insert");
    }

    /**
     * 主键筛选
     *
     * @param id
     * @return
     */
    @Override
    public HzCfg0ModelGroup selectByPrimaryKey(String id) {
        return baseSQLUtil.executeQueryByPass(GROUP, id, "com.connor.hozon.bom.bomSystem.dao.cfg.HzCfg0ModelGroupDao.selectByPrimaryKey", true);
    }

    /**
     * 根据主配置寻找模型族
     *
     * @param mainUid
     * @return
     */
    @Override
    public HzCfg0ModelGroup selectByMainUid(String mainUid) {
        return baseSQLUtil.executeQueryByPass(GROUP, mainUid, "com.connor.hozon.bom.bomSystem.dao.cfg.HzCfg0ModelGroupDao.selectByMainUid", true);
    }

    /**
     * 主键更新
     *
     * @param record
     * @return
     */
    @Override
    public int updateByPrimaryKey(HzCfg0ModelGroup record) {
        return baseSQLUtil.executeUpdate(record, "com.connor.hozon.bom.bomSystem.dao.cfg.HzCfg0ModelGroupDao.updateByPrimaryKey");
    }
}
