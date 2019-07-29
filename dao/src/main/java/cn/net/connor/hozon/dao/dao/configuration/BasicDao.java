/*
 *
 *  * Copyright (c) 2019 Author: Fancyears Milos Malvis @1243093366@qq.com
 *  * ALL COPYRIGHT REVERSED.
 *
 */

package cn.net.connor.hozon.dao.dao.configuration;

import org.springframework.stereotype.Repository;

/**
 * @Author: Fancyears·Maylos·Malvis
 * @Description: dao层基础
 * @Date: Created in 2018/8/30 18:53
 * @Modified By:
 */
@Repository
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
