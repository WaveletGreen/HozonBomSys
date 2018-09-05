package com.connor.hozon.bom.bomSystem.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import sql.IBaseSQLUtil;

/**
 * @Author: Fancyears·Maylos·Maywas
 * @Description:
 * @Date: Created in 2018/8/30 19:41
 * @Modified By:
 */
@Configuration
public class BasicDaoImpl<T> implements BasicDao<T> {
    /**
     * 目标类
     */
    protected Class clz;


    @Autowired
    protected IBaseSQLUtil baseSQLUtil;

    /**
     * 主键删除
     *
     * @param t
     * @return
     */
    @Override
    public int deleteByPrimaryKey(T t) {
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
        System.out.println("---------->" + clz.getCanonicalName() + "<-------insertSelective-------");
        return baseSQLUtil.executeQueryById(t, clz.getCanonicalName() + ".insertSelective");
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
