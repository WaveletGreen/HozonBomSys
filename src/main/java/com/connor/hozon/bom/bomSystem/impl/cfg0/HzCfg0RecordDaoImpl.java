/*
 * Copyright (c) 2018.
 * This file was wrote by fancyears·milos·maywas @connor. Any question/bug you can post to 1243093366@qq.com.
 * ALL RIGHTS RESERVED.
 */

package com.connor.hozon.bom.bomSystem.impl.cfg0;

import com.connor.hozon.bom.bomSystem.dto.HzFeatureQueryDto;
import com.connor.hozon.bom.bomSystem.dto.HzMaterielFeatureBean;
import com.connor.hozon.bom.bomSystem.dao.cfg0.HzCfg0RecordDao;
import com.connor.hozon.bom.bomSystem.impl.BasicDaoImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sql.pojo.cfg.cfg0.HzCfg0Record;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: Fancyears·Maylos·Maywas
 * @Description: fuck
 * @Date: Created in 2018/9/6 13:19
 * @Modified By:
 */
@Service("hzCfg0RecordDao")
public class HzCfg0RecordDaoImpl extends BasicDaoImpl<HzCfg0Record> implements HzCfg0RecordDao {

    private final static HzCfg0Record RECORD = new HzCfg0Record();

    @Autowired
    public HzCfg0RecordDaoImpl() {
        clz = HzCfg0RecordDao.class;
        clzName = clz.getCanonicalName();
    }

    @Override
    public int deleteByPrimaryKey(String puid) {
        return baseSQLUtil.executeDeleteBySome(clzName + ".deleteByPrimaryKey", puid, "HZ_CFG0_RECORD");

    }

    @Override
    public List<HzCfg0Record> selectCfg0ListByPuids(Map<String, Object> _map) {
        return baseSQLUtil.executeQueryByPass(RECORD, _map, clzName + ".selectCfg0ListByPuids");
    }

    @Override
    public List<HzCfg0Record> selectByCodeAndDesc(HzCfg0Record record) {
        return baseSQLUtil.executeQuery(record, clzName + ".selectByCodeAndDesc");
    }

    @Override
    public int deleteCfgByList(List<HzCfg0Record> records) {
        return baseSQLUtil.executeDelete(records, clzName + ".deleteCfgByList");
    }

    @Override
    public int setIsSent(Map<String, Object> _map) {
        return baseSQLUtil.executeUpdate(_map, clzName + ".setIsSent");
    }

    /**
     * 设置进入流程状态
     *
     * @param _map
     * @return
     */
    @Override
    public int setToProcess(Map<String, Object> _map) {
        return baseSQLUtil.executeUpdate(_map, clzName + ".setToProcess");
    }


    @Override
    public int tellMeHowManyOfThose(HzFeatureQueryDto dto) {
        List<Integer> result = baseSQLUtil.executeQueryByPass(new Integer(0), dto, clzName + ".tellMeHowManyOfThose");
        if (result == null) {
            return 0;
        } else {
            return result.get(0);
        }
    }

    @Override
    public List<HzCfg0Record> selectByCondition(HzFeatureQueryDto queryBase) {
        return baseSQLUtil.executeQueryByPass(RECORD, queryBase, clzName + ".selectByCondition");
    }

    /**
     * 用HZCSYS+特性值（从颜色中来）查找一条车身颜色特性值
     *
     * @param record
     * @return
     */
    @Override
    public HzCfg0Record selectByCodeAndDescWithMainItem(HzCfg0Record record) {
        record.setWhichTable("HZ_CFG0_RECORD");
        return baseSQLUtil.executeQueryById(record, clzName + ".selectByCodeAndDescWithMainItem");
    }

    /**
     * 用"车身颜色"+特性值（从颜色中来）查找一条车身颜色特性值
     *
     * @param record
     * @return
     */
    @Override
    public HzCfg0Record selectByCodeAndCnDescWithMainItem(HzCfg0Record record) {
        record.setWhichTable("HZ_CFG0_RECORD");
        return baseSQLUtil.executeQueryById(record, clzName + ".selectByCodeAndCnDescWithMainItem");
    }

    @Override
    public List<HzCfg0Record> selectListByProjectPuid(String projectPuid, HzFeatureQueryDto queryBase) {
        Map<String, Object> params = new HashMap<>();
        params.put("projectPuid", projectPuid);
        params.put("whichTable", "HZ_CFG0_RECORD");
        params.put("param", queryBase);
        return baseSQLUtil.executeQueryByPass(RECORD, params, clzName + ".selectListByProjectPuid");
    }


    @Override
    public List<HzMaterielFeatureBean> selectMaterielFeatureByProjectPuid(String projectPuid) {
        return baseSQLUtil.executeQueryByPass(new HzMaterielFeatureBean(), projectPuid, clzName + ".selectMaterielFeatureByProjectPuid");
    }

    @Override
    public HzCfg0Record selectByPrimaryKey(String puid) {
        HzCfg0Record record = new HzCfg0Record();
        record.setPuid(puid);
        record.setWhichTable("HZ_CFG0_RECORD");
        return baseSQLUtil.executeQueryById(record, clzName + ".selectByPrimaryKey");
    }

    /**
     * 根据项目和特性PUID(父)获取一组特性
     *
     * @param familyUid  特性UID
     * @param projectUid 项目UID
     * @return
     */
    @Override
    public List<HzCfg0Record> selectByFamilyUidWithProject(String familyUid, String projectUid) {
        HzCfg0Record record = new HzCfg0Record();
        record.setpCfg0FamilyPuid(familyUid);
        record.setProjectPuid(projectUid);
        return baseSQLUtil.executeQuery(record, clzName + ".selectByFamilyUidWithProject");
    }

    @Override
    public int updateList(List<HzCfg0Record> hzCfg0RecordList) {
        return baseSQLUtil.executeUpdate(hzCfg0RecordList,clzName +".updateList");
    }

    @Override
    public int updateByVwoid(HzCfg0Record hzCfg0Record) {
        return baseSQLUtil.executeUpdate(hzCfg0Record, clzName+".updateByVwoid");
    }

}
