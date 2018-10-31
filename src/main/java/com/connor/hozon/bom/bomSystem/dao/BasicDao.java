/*
 * Copyright (c) 2018.
 * This file was wrote by fancyears·milos·maywas @connor. Any question/bug you can't post to 1243093366@qq.com.
 * ALL RIGHTS RESERVED.
 */

package com.connor.hozon.bom.bomSystem.dao;

/**
 * @Author: Fancyears·Maylos·Maywas
 * @Description: dao层基础
 * @Date: Created in 2018/8/30 18:53
 * @Modified By:
 */
public interface BasicDao<T> {
    /**
     * 主键删除
     *
     * @param t
     * @return
     */
    int deleteByPrimaryKey(T t) ;

    /**
     * 全插入
     *
     * @param t
     * @return
     */
    int insert(T t) ;

    /**
     * 选择插入
     *
     * @param t
     * @return
     */
    int insertSelective(T t) ;

    /**
     * 主键查询
     *
     * @param t
     * @return
     */
    T selectByPrimaryKey(T t) ;

    /**
     * 主键选择更新
     *
     * @param t
     * @return
     */
    int updateByPrimaryKeySelective(T t) ;

    /**
     * 主键全更新
     *
     * @param t
     * @return
     */
    int updateByPrimaryKey(T t) ;
}
