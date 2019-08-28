/*
 *
 *  * Copyright (c) 2019 Author: Fancyears Milos Malvis @1243093366@qq.com
 *  * ALL COPYRIGHT REVERSED.
 *
 */

package cn.net.connor.hozon.dao.dao;

import cn.net.connor.hozon.dao.basic.MyBatisBaseDao;
import cn.net.connor.hozon.dao.pojo.Test;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLIntegrityConstraintViolationException;

/**
 * 示例Dao层
 *
 * 测试Dao层，Controller层调用dao层进行的任何修改都不会发生回滚，
 * <p>
 * 需要在service层进行调用dao层才能发生回滚，
 * <p>
 * 回滚的方法看web模块的datasource-config.xml文件里的建言设置
 */
@Repository
@Transactional(rollbackFor = SQLIntegrityConstraintViolationException.class)
public interface TestDao extends MyBatisBaseDao<Test, Long, Object> {
}