package com.connor.hozon.bom.bomSystem.dao.impl.cfg0;

import com.connor.hozon.bom.bomSystem.dao.cfg.HzColorModelDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import sql.IBaseSQLUtil;
import sql.pojo.cfg.HzColorModel;
import sql.pojo.cfg.HzColorModel2;

import java.util.List;

@Configuration
public class HzColorModelDaoImpl implements HzColorModelDao {
    private static HzColorModel model = new HzColorModel();
    @Autowired
    IBaseSQLUtil baseSQLUtil;

    /**
     * 主键删除
     *
     * @param puid
     * @return
     */
    @Override
    public int deleteByPrimaryKey(String puid) {
        return baseSQLUtil.executeDeleteByPass(puid, "com.connor.hozon.bom.bomSystem.dao.cfg.HzColorModelDao.deleteByPrimaryKey");
    }

    /**
     * 插入
     *
     * @param record
     * @return
     */
    @Override
    public int insert(HzColorModel record) {
        return baseSQLUtil.executeInsert(record, "com.connor.hozon.bom.bomSystem.dao.cfg.HzColorModelDao.insert");
    }

    /**
     * 主键筛选
     *
     * @param puid
     * @return
     */
    @Override
    public HzColorModel selectByPrimaryKey(String puid) {
        return baseSQLUtil.executeQueryByPass(model, puid, "com.connor.hozon.bom.bomSystem.dao.cfg.HzColorModelDao.selectByPrimaryKey", true);
    }

    /**
     * 根据颜色模型
     *
     * @param modelUid
     * @return
     */
    @Override
    public List<HzColorModel> selectByModelUid(String modelUid) {
        return baseSQLUtil.executeQueryByPass(model, modelUid, "com.connor.hozon.bom.bomSystem.dao.cfg.HzColorModelDao.selectByModelUid");
    }

    /**
     * 根据颜色模型，包括颜色一起筛选出来
     *
     * @param modelUid 车型UID
     * @return
     */
    @Override
    public List<HzColorModel> selectByModelUidWithColor(String modelUid) {
        return baseSQLUtil.executeQueryByPass(model, modelUid, "com.connor.hozon.bom.bomSystem.dao.cfg.HzColorModelDao.selectByModelUidWithColor");
    }

    /**
     * 根据项目ID查找
     *
     * @param projectUid
     * @return
     */
    @Override
    public List<HzColorModel> selectByCfgMainUid(String projectUid) {
        return baseSQLUtil.executeQueryByPass(model, projectUid, "com.connor.hozon.bom.bomSystem.dao.cfg.HzColorModelDao.selectByCfgMainUid");
    }

    /**
     * 主键更新
     *
     * @param record
     * @return
     */
    @Override
    public int updateByPrimaryKey(HzColorModel record) {
        return baseSQLUtil.executeUpdate(record, "com.connor.hozon.bom.bomSystem.dao.cfg.HzColorModelDao.updateByPrimaryKey");
    }

    /**
     * 批量插入
     *
     * @param colorModels
     * @return
     */
    @Override
    public int insertByBatch(List<HzColorModel> colorModels) {
        return baseSQLUtil.executeInsert(colorModels, "com.connor.hozon.bom.bomSystem.dao.cfg.HzColorModelDao.insertByBatch");
    }

    @Override
    public int updateColorModelWithCfg(HzColorModel colorModels) {
        return baseSQLUtil.executeUpdate(colorModels, "com.connor.hozon.bom.bomSystem.dao.cfg.HzColorModelDao.updateColorModelWithCfg");
    }

    @Override
    public List<HzColorModel2> selectByProjectPuid(String projectPuid) {
        return  baseSQLUtil.executeQueryByPass(new HzColorModel2(), projectPuid, "com.connor.hozon.bom.bomSystem.dao.cfg.HzColorModelDao.selectByProjectPuid");
    }


}
