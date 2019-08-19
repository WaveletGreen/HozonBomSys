/*
 *
 *  * Copyright (c) 2019 Author: Fancyears Milos Malvis @1243093366@qq.com
 *  * ALL COPYRIGHT REVERSED.
 *
 */

package com.connor.hozon.dao.modelColor.impl;

import com.connor.hozon.dao.modelColor.HzColorModelDao;
import com.connor.hozon.dao.BasicDaoImpl;
import org.springframework.stereotype.Repository;
import cn.net.connor.hozon.dao.pojo.configuration.modelColor.HzCfg0ModelColor;
import cn.net.connor.hozon.dao.pojo.configuration.modelColor.HzCfg0ModelColorDetail;
import cn.net.connor.hozon.dao.pojo.configuration.modelColor.HzColorModel2;

import java.util.List;

/**
 * @Author: Fancyears·Maylos·Malvis
 * @Description: fuck
 * @Date: Created in 2018/9/6 13:19
 * @Modified By:
 */
@Repository
public class HzColorModelDaoImpl extends BasicDaoImpl<HzCfg0ModelColorDetail> implements HzColorModelDao {
    private final static HzCfg0ModelColorDetail DETAIL = new HzCfg0ModelColorDetail();

    public HzColorModelDaoImpl() {
        clz = HzColorModelDao.class;
        clzName = clz.getCanonicalName();
    }

    /**
     * 主键删除
     *
     * @param puid
     * @return
     */
    @Override
    public int deleteByPrimaryKey(String puid) {
        return baseSQLUtil.executeDeleteByPass(puid,
                clzName + ".deleteByPrimaryKey");
    }


    /**
     * 主键筛选
     *
     * @param puid
     * @return
     */
    @Override
    public HzCfg0ModelColorDetail selectByPrimaryKey(String puid) {
        return baseSQLUtil.executeQueryByPass(DETAIL, puid,
                clzName + ".selectByPrimaryKey", true);
    }

    /**
     * 根据颜色模型
     *
     * @param modelUid
     * @return
     */
    @Override
    public List<HzCfg0ModelColorDetail> selectByModelUid(String modelUid) {
        return baseSQLUtil.executeQueryByPass(DETAIL, modelUid,
                clzName + ".selectByModelUid");
    }

    /**
     * 根据颜色模型，包括颜色一起筛选出来
     *
     * @param modelUid 车型UID
     * @return
     */
    @Override
    public List<HzCfg0ModelColorDetail> selectByModelUidWithColor(String modelUid) {
        return baseSQLUtil.executeQueryByPass(DETAIL, modelUid,
                clzName + ".selectByModelUidWithColor");
    }

    @Override
    public List<HzCfg0ModelColorDetail> selectByModelUidWithColor2(String modelUid) {
        return baseSQLUtil.executeQueryByPass(DETAIL, modelUid,
                clzName + ".selectByModelUidWithColor2");
    }

    /**
     * 根据颜色车型查询对应的车型配置
     *
     * @param hzCfg0ModelColors
     * @return
     */
    @Override
    public List<HzCfg0ModelColorDetail> selectByModelColors(List<HzCfg0ModelColor> hzCfg0ModelColors) {
        return baseSQLUtil.executeQueryByPass(DETAIL, hzCfg0ModelColors,
                clzName + ".selectByModelColors");
    }

    @Override
    public int updateListAll(List<HzCfg0ModelColorDetail> hzCfg0ModelColorDetailsUpdate) {
        return baseSQLUtil.executeUpdate(hzCfg0ModelColorDetailsUpdate,clzName+".updateListAll");
    }

    /**
     * 根据项目ID查找
     *
     * @param projectUid
     * @return
     */
    @Override
    public List<HzCfg0ModelColorDetail> selectByCfgMainUid(String projectUid) {
        return baseSQLUtil.executeQueryByPass(DETAIL, projectUid,
                clzName + ".selectByCfgMainUid");
    }

    /**
     * 批量插入
     *
     * @param colorModels
     * @return
     */
    @Override
    public int insertByBatch(List<HzCfg0ModelColorDetail> colorModels) {
        return baseSQLUtil.executeInsert(colorModels,
                clzName + ".insertByBatch");
    }

    @Override
    public int updateColorModelWithCfg(HzCfg0ModelColorDetail colorModels) {
        return baseSQLUtil.executeUpdate(colorModels,
                clzName + ".updateColorModelWithCfg");
    }

    @Override
    public List<HzColorModel2> selectByProjectPuid(String projectPuid) {
        return baseSQLUtil.executeQueryByPass(new HzColorModel2(), projectPuid,
                clzName + ".selectByProjectId");
    }

}
