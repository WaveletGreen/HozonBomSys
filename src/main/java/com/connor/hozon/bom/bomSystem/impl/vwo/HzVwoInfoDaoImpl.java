/*
 * Copyright (c) 2018.
 * This file was wrote by fancyears·milos·maywas @connor. Any question/bug you can post to 1243093366@qq.com.
 * ALL RIGHTS RESERVED.
 */

package com.connor.hozon.bom.bomSystem.impl.vwo;

import com.connor.hozon.bom.bomSystem.dao.vwo.HzVwoInfoDao;
import com.connor.hozon.bom.bomSystem.impl.BasicDaoImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import sql.IBaseSQLUtil;
import sql.pojo.cfg.vwo.HzVwoInfo;

import java.util.List;
import java.util.Map;

/**
 * @Author: Fancyears·Maylos·Maywas
 * @Description: fuck
 * @Date: Created in 2018/9/6 13:19
 * @Modified By:
 */
@Configuration
public class HzVwoInfoDaoImpl extends BasicDaoImpl<HzVwoInfo> implements HzVwoInfoDao {

    private static final HzVwoInfo INFO = new HzVwoInfo();

    public HzVwoInfoDaoImpl() {
        clz = HzVwoInfoDao.class;
        clzName = clz.getCanonicalName();
    }

    /**
     * 主键删除
     *
     * @param id
     * @return
     */
    @Override
    public int deleteByPrimaryKey(Long id) {
        return baseSQLUtil.executeDelete(id,
                clzName + ".deleteByPrimaryKey");
    }


    /**
     * 主键搜索
     *
     * @param id
     * @return
     */
    @Override
    public HzVwoInfo selectByPrimaryKey(Long id) {
        HzVwoInfo hzVwoInfo = new HzVwoInfo();
        hzVwoInfo.setId(id);
        return baseSQLUtil.executeQueryById(hzVwoInfo,
                clzName + ".selectByPrimaryKey");
    }

    /**
     * 寻找当月最大的vwo
     *
     * @return
     */
    @Override
    public HzVwoInfo findMaxAreaVwoNum() {
        return baseSQLUtil.executeQueryById(INFO,
                clzName + ".findMaxAreaVwoNum");
    }

    /**
     * 分页查询
     *
     * @param params
     * @return QueryBase queryBase,String projectUid
     */
    @Override
    public List<HzVwoInfo> selectListByProjectUid(Map<String, Object> params) {
        return baseSQLUtil.executeQueryByPass(INFO, params,
                clzName + ".selectListByProjectUid");
    }

    /**
     * 获取当前项目下的变更总数
     *
     * @param projectUid
     * @return
     */
    @Override
    public int tellMeHowManyOfIt(String projectUid) {
        return baseSQLUtil.executeQueryByPass(new Integer(-1), projectUid,
                clzName + ".tellMeHowManyOfIt", true);
    }


}
