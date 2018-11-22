package com.connor.hozon.bom.bomSystem.impl;

import com.connor.hozon.bom.bomSystem.dao.BasicDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import sql.IBaseSQLUtil;

/**
 * @Author: Fancyears·Maylos·Malvis
 * @Description: 基础dao层基本实现类
 * @Date: Created in 2018/9/6 13:19
 * @Modified By:
 */
@Configuration
public class BasicDaoImpl<T> implements BasicDao<T> {
    /**
     * 目标类
     */
    protected Class clz;
    /**
     * 目标类完整限定名
     */
    protected String clzName;
    @Autowired
    protected IBaseSQLUtil baseSQLUtil;

    /**
     * 主键删除
     *
     * @param t
     * @return
     */
    @Override
    public int deleteByPrimaryKey(T t)  {
        System.out.println("---------->" + clz.getCanonicalName() + "<------deleteByPrimaryKey--------");
        return baseSQLUtil.executeDelete(t, clz.getCanonicalName() + ".deleteByPrimaryKey");
    }

    /**
     * 全插入
     *
     * @param t
     * @return
     */
    @Override
    public int insert(T t) {
        System.out.println("---------->" + clz.getCanonicalName() + "<-------insert-------");
        return baseSQLUtil.executeInsert(t, clz.getCanonicalName() + ".insert");
    }

    /**
     * 选择插入
     *
     * @param t
     * @return
     */
    @Override
    public int insertSelective(T t) {
        System.out.println("---------->" + clz.getCanonicalName() + "<-------insertSelective-------");
        return baseSQLUtil.executeInsert(t, clz.getCanonicalName() + ".insertSelective");
    }

    /**
     * 主键查询
     *
     * @param t
     * @return
     */
    @Override
    public T selectByPrimaryKey(T t) {
        System.out.println("---------->" + clz.getCanonicalName() + "<-------selectByPrimaryKey-------");
        return baseSQLUtil.executeQueryById(t, clz.getCanonicalName() + ".selectByPrimaryKey");
    }

    /**
     * 主键选择更新
     *
     * @param t
     * @return
     */
    @Override
    public int updateByPrimaryKeySelective(T t) {
        System.out.println("---------->" + clz.getCanonicalName() + "<------updateByPrimaryKeySelective--------");
        return baseSQLUtil.executeUpdate(t, clz.getCanonicalName() + ".updateByPrimaryKeySelective");
    }

    /**
     * 主键全更新
     *
     * @param t
     * @return
     */
    @Override
    public int updateByPrimaryKey(T t) {
        System.out.println("---------->" + clz.getCanonicalName() + "<-------updateByPrimaryKey-------");
        return baseSQLUtil.executeUpdate(t, clz.getCanonicalName() + ".updateByPrimaryKey");
    }
}
