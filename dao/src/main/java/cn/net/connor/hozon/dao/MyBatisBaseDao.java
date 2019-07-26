/*
 * Copyright (c) 2019 Author: Fancyears Milos Malvis @1243093366@qq.com
 * ALL COPYRIGHT REVERSED.
 */

package cn.net.connor.hozon.dao;

import java.io.Serializable;

/**
 * DAO公共基类
 * 基础的增删改查都有，如果需要进行批量操作，需要在子类/子接口中重新定义
 *
 * @param <Model> The Model Class 这里是泛型不是Model类，对应数据库中数据实体对象
 * @param <PK>    The Primary Key Class 主键查询的类型，如主键是Number则可以设置为Long或Integer；VARCHAR2则可以设置为String
 *                <p>
 *                如果确定主键是int，char等基本数据类型，不要继承该类
 * @param <E>     辅助类，用于分页查询，批量插入，批量更新等操作，如果没有具体的辅助类可以用Object类作为泛型
 * @Author: Fancyears Milos Malvis
 * @Date: Created in  2019/5/8 13:30
 * @Modified By:
 */
public interface MyBatisBaseDao<Model, PK extends Serializable, E> {
    /**
     * 根据主键删除，方法名与生成的XML保持一致
     *
     * @param id 主键
     * @return
     */
    int deleteByPrimaryKey(PK id);

    /**
     * 单个插入，方法名与生成的XML保持一致
     *
     * @param record 数据库模型对象
     * @return
     */
    int insert(Model record);

    /**
     * 选择性插入，方法名与生成的XML保持一致
     *
     * @param record 数据库模型对象
     * @return
     */
    int insertSelective(Model record);

    /**
     * 根据主键查询出单个结果
     *
     * @param id 主键
     * @return
     */
    Model selectByPrimaryKey(PK id);

    /**
     * 根据主键选择性更新
     *
     * @param record 数据库模型对象
     * @return
     */
    int updateByPrimaryKeySelective(Model record);

    /**
     * 根据主键全部更新字段数据
     *
     * @param record 数据库模型对象
     * @return
     */
    int updateByPrimaryKey(Model record);
}