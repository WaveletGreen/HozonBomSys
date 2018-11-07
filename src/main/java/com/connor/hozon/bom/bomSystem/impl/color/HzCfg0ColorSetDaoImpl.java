/*
 * Copyright (c) 2018.
 * This file was wrote by fancyears·milos·maywas @connor. Any question/bug you can post to 1243093366@qq.com.
 * ALL RIGHTS RESERVED.
 */

package com.connor.hozon.bom.bomSystem.impl.color;

import com.connor.hozon.bom.bomSystem.dao.color.HzCfg0ColorSetDao;
import com.connor.hozon.bom.bomSystem.impl.BasicDaoImpl;
import com.connor.hozon.bom.common.base.entity.QueryBase;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;
import sql.pojo.cfg.color.HzCfg0ColorSet;

import java.util.List;

/**
 * @Author: Fancyears·Maylos·Maywas
 * @Description: fuck
 * @Date: Created in 2018/9/6 13:19
 * @Modified By:
 */
@Service("hzCfg0ColorSetDaox")
//@Configuration
public class HzCfg0ColorSetDaoImpl extends BasicDaoImpl<HzCfg0ColorSet> implements HzCfg0ColorSetDao {

    private final static HzCfg0ColorSet SET = new HzCfg0ColorSet();

    public HzCfg0ColorSetDaoImpl() {
        clz = HzCfg0ColorSetDao.class;
        clzName = clz.getCanonicalName();
    }

    @Override
    public List<HzCfg0ColorSet> queryAll2(QueryBase queryBase) {
        HzCfg0ColorSet set = new HzCfg0ColorSet();
        queryBase.setSort(set.reflectToDBField(queryBase.getSort()));
        List<HzCfg0ColorSet> colorSet = baseSQLUtil.executeQueryByPass(set, queryBase, clzName + ".selectAll");
        return colorSet;
    }

    /**
     * @return
     * @Author: Fancyears·Maylos·Mayways
     * @Description: 检索所有的颜色集
     * @Date: 2018/5/21 17:09
     */
    @Override
    public List<HzCfg0ColorSet> queryAll2() {
        List<HzCfg0ColorSet> colorSet = baseSQLUtil.executeQueryByPass(SET, new QueryBase(), clzName + ".selectAll");
        return colorSet;
    }

    /**
     * 根据颜色代码寻找颜色对象
     *
     * @param entity
     * @return 找到的颜色对象
     * @Author: Fancyears·Maylos·Mayways
     * @Description: 根据颜色的id，找到该颜色对象
     * @Date: 2018/5/21 17:09
     */
    @Override
    public HzCfg0ColorSet selectByColorCode(HzCfg0ColorSet entity) {
        return baseSQLUtil.executeQueryById(entity, clzName + ".selectByColorCode");
    }

    /**
     * @param entity 更新的颜色对象的状态
     * @return 影响行数
     * @Author: Fancyears·Maylos·Mayways
     * @Description: 执行更细颜色信息
     * @Date: 2018/5/21 17:10
     */
    @Override
    public int updateStatusByPrimaryKey(HzCfg0ColorSet entity) {
        return baseSQLUtil.executeUpdate(entity, clzName + ".updateStatusByPrimaryKey");
    }

    @Override
    public int deleteByList(List<HzCfg0ColorSet> entity) {
        return baseSQLUtil.executeDelete(entity, clzName + ".deleteByBatch");
    }

    /**
     * @param entity 传入的颜色集合
     * @return
     * @Author: Fancyears·Maylos·Mayways
     * @Description: 批量逻辑删除颜色信息，设置颜色删除状态为0
     * @Date: 2018/5/21 17:10
     */
    @Override
    public int logicDeleteByBatch(List<HzCfg0ColorSet> entity) {
        return baseSQLUtil.executeDelete(entity, clzName + ".logicDeleteByBatch");
    }

    /**
     * 查询全部的颜色数量
     *
     * @return
     */
    @Override
    public int tellMeHowManyOfIt() {
        return baseSQLUtil.executeQueryById(new Integer(0), clzName + ".tellMeHowManyOfIt");
    }
}
