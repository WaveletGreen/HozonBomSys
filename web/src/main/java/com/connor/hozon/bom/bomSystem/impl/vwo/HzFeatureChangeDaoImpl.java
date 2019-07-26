/*
 * Copyright (c) 2018.
 * This file was written by fancyears·milos·malvis @connor. Any question/bug you can post to 1243093366@qq.com.
 * ALL RIGHTS RESERVED.
 */

package com.connor.hozon.bom.bomSystem.impl.vwo;

import com.connor.hozon.bom.bomSystem.dao.vwo.HzFeatureChangeDao;
import com.connor.hozon.bom.bomSystem.impl.BasicDaoImpl;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Repository;
import sql.pojo.cfg.cfg0.HzCfg0Record;
import sql.pojo.cfg.vwo.HzFeatureChangeBean;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: Fancyears·Maylos·Malvis
 * @Description: fuck
 * @Date: Created in 2018/9/6 13:19
 * @Modified By:
 */
@Repository
public class HzFeatureChangeDaoImpl extends BasicDaoImpl<HzFeatureChangeBean> implements HzFeatureChangeDao {

    public HzFeatureChangeDaoImpl() {
        clz = HzFeatureChangeDao.class;
        clzName = clz.getCanonicalName();
    }

    /**
     * 查找特性下最新的更改
     *
     * @param bean
     * @return
     */
    @Override
    public HzFeatureChangeBean findNewestChange(HzFeatureChangeBean bean) {
        return baseSQLUtil.executeQueryById(bean, clzName + ".findNewestChange");
    }

    @Override
    public List<HzFeatureChangeBean> selectByVwoId(HzFeatureChangeBean bean) {
        return baseSQLUtil.executeQuery(bean, clzName + ".selectByVwoId");
    }

    @Override
    public int insertList(List<HzFeatureChangeBean> hzFeatureChangeBeans) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("tableName", hzFeatureChangeBeans.get(0).getTableName());
        map.put("seqName", hzFeatureChangeBeans.get(0).getSeqName());
        map.put("hzFeatureChangeBeans", hzFeatureChangeBeans);
        return baseSQLUtil.executeInsert(map, clzName + ".insertList");
    }
    /**
     * 根据VWO ID找到当前变更的特性UID
     * @param bean
     * @return
     */
    @Override
    public List<HzFeatureChangeBean> selectCfgUidsByVwoId(HzFeatureChangeBean bean) {
        return baseSQLUtil.executeQuery(bean, clzName + ".selectCfgUidsByVwoId");
    }

    @Override
    public List<HzFeatureChangeBean> doQueryLastTwoChange(HzFeatureChangeBean hzFeatureChangeBean) {
        return baseSQLUtil.executeQuery(hzFeatureChangeBean, clzName+".doQueryLastTwoChange");
    }

    @Override
    public List<HzFeatureChangeBean> doSelectHasEffect(List<HzCfg0Record> records) {
        return baseSQLUtil.executeQueryByPass(new HzFeatureChangeBean(),records,clzName+".doSelectHasEffect");
    }

    @Override
    public int updateStatusByOrderId(Map<String, Object> map) {
        return baseSQLUtil.executeUpdate(map,clzName+".updateStatusByOrderId");
    }

    @Override
    public int doDeleteByPrimaryKeys(List<Long> changeFeatureIds) {
        return baseSQLUtil.executeDelete(changeFeatureIds,clzName+".doDeleteByPrimaryKeys");
    }

    @Override
    public HzFeatureChangeBean selectLast(HzFeatureChangeBean hzFeatureChangeBean) {
        return baseSQLUtil.executeQueryById(hzFeatureChangeBean,clzName+".selectLast");
    }

    @Override
    public HzFeatureChangeBean selectByChangeIdAndCfgid(HzFeatureChangeBean hzFeatureChangeBean) {
        return baseSQLUtil.executeQueryById(hzFeatureChangeBean,clzName+".selectByChangeIdAndCfgid");
    }

    @Override
    public List<HzFeatureChangeBean> doselectByChangeId(Long orderId) {
        return baseSQLUtil.executeQueryByPass(new HzFeatureChangeBean(),orderId,clzName+".doselectByChangeId");
    }

    @Override
    public List<HzFeatureChangeBean> doSelectHasNotEffect(List<Long> changeFeatureIds) {
        return baseSQLUtil.executeQueryByPass(new HzFeatureChangeBean(),changeFeatureIds,clzName+".doSelectHasNotEffect");
    }


}
