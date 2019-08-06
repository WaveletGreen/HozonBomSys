/*
 * Copyright (c) 2018.
 * This file was written by fancyears·milos·malvis @connor. Any question/bug you can't post to 1243093366@qq.com.
 * ALL RIGHTS RESERVED.
 */

package com.connor.hozon.bom.bomSystem.impl.derivative;

import cn.net.connor.hozon.dao.dao.configuration.derivative.HzCfg0ToModelRecordDao;
import com.connor.hozon.bom.bomSystem.impl.BasicDaoImpl;
import org.springframework.stereotype.Repository;
import cn.net.connor.hozon.dao.pojo.configuration.derivative.HzCfg0ToModelRecord;

import java.util.List;
/**
 * @Author: Fancyears·Maylos·Malvis
 * @Description: fuck
 * @Date: Created in 2018/9/6 13:19
 * @Modified By:
 */
@Repository
public class HzCfg0ToModelRecordDaoImpl extends BasicDaoImpl<HzCfg0ToModelRecord> implements HzCfg0ToModelRecordDao {
    public HzCfg0ToModelRecordDaoImpl() {
        clz = HzCfg0ToModelRecordDao.class;
        clzName = clz.getCanonicalName();
    }

    @Override
    public int deleteByPrimaryKey(String puid) {
        return baseSQLUtil.executeDeleteBySome(clzName + ".deleteByPrimaryKey", puid);
    }


    @Override
    public HzCfg0ToModelRecord selectByPrimaryKey(String puid) {
        return baseSQLUtil.executeQueryByPass(new HzCfg0ToModelRecord(), puid, clzName + ".selectByPrimaryKey", true);
    }

    /**
     * 批量更新发送到SAP的状态
     *
     * @param list
     * @return
     */
    @Override
    public int setIsSent(List<HzCfg0ToModelRecord> list) {
        return baseSQLUtil.executeUpdate(list, clzName + ".setIsSent");
    }

}
