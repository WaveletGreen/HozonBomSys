/*
 * Copyright (c) 2018.
 * This file was wrote by fancyears·milos·malvis @connor. Any question/bug you can post to 1243093366@qq.com.
 * ALL RIGHTS RESERVED.
 */

package com.connor.hozon.bom.bomSystem.impl.modelColor;

import com.connor.hozon.bom.bomSystem.dao.modelColor.HzColorModelDao;
import com.connor.hozon.bom.bomSystem.impl.BasicDaoImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import sql.IBaseSQLUtil;
import sql.pojo.cfg.modelColor.HzCfg0ModelColor;
import sql.pojo.cfg.modelColor.HzCfg0ModelColorDetail;
import sql.pojo.cfg.modelColor.HzColorModel2;

import java.util.List;
/**
 * @Author: Fancyears·Maylos·Maywas
 * @Description: fuck
 * @Date: Created in 2018/9/6 13:19
 * @Modified By:
 */
@Configuration
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
                clzName + ".selectByProjectPuid");
    }


}
